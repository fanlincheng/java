package com.example.mall.Service.Impl;

import com.example.mall.Form.CartAddForm;
import com.example.mall.Form.CartPutForm;
import com.example.mall.Service.CartService;
import com.example.mall.dao.ProductMapper;
import com.example.mall.enums.ProductStatusEnum;
import com.example.mall.pojo.Cart;
import com.example.mall.pojo.Product;
import com.example.mall.vo.CartProductVo;
import com.example.mall.vo.CartVo;
import com.example.mall.vo.ResponseVo;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.example.mall.enums.ResponseEnum.*;

@Service
public class CartServiceImpl implements CartService {
    /**
     * 将商品加入购物车需要
     * 1.判断商品是否存在
     * 2.商品是否在售
     * 3.商品库存是否充足
     *
     * @param cartAddForm
     * @return
     */
    private static final String CART_REDIS_KEY_TEMPLATE = "cart_%d";
    private Gson gson = new Gson();
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public ResponseVo<CartVo> addCart(Integer uid, CartAddForm cartAddForm) {
        Integer count = 1;
        Product product = productMapper.selectByPrimaryKey(cartAddForm.getProductId());
        //判断商品是否存在
        if (StringUtils.isEmpty(product)) {
            return ResponseVo.error(PRODUCT_NOTEXIST);
        }
        //判断商品的状态是否在售
        Integer status = product.getStatus();
        if (!status.equals(ProductStatusEnum.ON_SALE.getCode())) {
            return ResponseVo.error(PRODUCT_OFFSALE_OR_DELETE);
        }
        Integer stock = product.getStock();
        //因为每次都是添加一件商品到购物车，只需要库存大于1即可
        if (stock <= 0) {
            return ResponseVo.error(PRODUCT_SALEOUT);
        }
        //获得redis中的对应数据
        String redisKey = String.format(CART_REDIS_KEY_TEMPLATE, uid);
        HashOperations<String, String, String> opsForHash = redisTemplate.opsForHash();
        String value = opsForHash.get(redisKey, String.valueOf(product.getId()));
        //写入redis
        Cart cart;
        if (!StringUtils.isEmpty(value)) {
            cart = gson.fromJson(value, Cart.class);
            cart.setQuantity(cart.getQuantity() + count);
        } else {
            cart = new Cart(product.getId(), count, cartAddForm.getSelected());
        }
        opsForHash.put(redisKey, String.valueOf(product.getId()), gson.toJson(cart));

//        redisTemplate.opsForValue().set(String.format(CART_REDIS_KEY_TEMPLATE,uid),
//                gson.toJson(new Cart(product.getId(),count,cartAddForm.getSelected())));

        return null;
    }

    @Override
    public ResponseVo<CartVo> list(Integer uid) {
        HashOperations<String, String, String> hashOperations = redisTemplate.opsForHash();
        String redisKey = String.format(CART_REDIS_KEY_TEMPLATE, uid);
        Map<String, String> entries = hashOperations.entries(redisKey);
        CartVo cartVo = new CartVo();
        List<CartProductVo> cartProductVos = new ArrayList<>();
        BigDecimal total = BigDecimal.ZERO;
        boolean selectedAll = true;
        Integer totalQuality = 0;
        for (Map.Entry<String, String> entry : entries.entrySet()) {
            Integer productId = Integer.valueOf(entry.getKey());
            Cart cart = gson.fromJson(entry.getValue(), Cart.class);
            //TODO 需要优化 使用sql里面的in而不是在for里面每次调用查询
            Product product = productMapper.selectByPrimaryKey(productId);
            if (!StringUtils.isEmpty(product)) {
                CartProductVo cartProductVo = new CartProductVo(productId,
                        cart.getQuantity(),
                        product.getName(),
                        product.getSubtitle(),
                        product.getMainImage(),
                        product.getPrice(),
                        product.getStatus(),
                        product.getPrice().multiply(BigDecimal.valueOf(cart.getQuantity())),
                        product.getStock(),
                        cart.getProductSelected());
                //只计算选中的商品
                if (cartProductVo.getProductSelected()) {
                    total=total.add(cartProductVo.getProductTotalPrice());
                }
                totalQuality += cartProductVo.getQuantity();
                cartProductVos.add(cartProductVo);
                if (!cartProductVo.getProductSelected()) {
                    selectedAll = false;
                }
            }

        }
        cartVo.setCartProductVoList(cartProductVos);
        cartVo.setSelectedAll(selectedAll);
        cartVo.setCartTotalPrice(total);
        cartVo.setCartTotalQuantity(totalQuality);
        return ResponseVo.success(cartVo);
    }

    @Override
    public ResponseVo<CartVo> updateCart(Integer uid, Integer productId, CartPutForm cartPutForm) {
        HashOperations<String, String, String> hashOperations = redisTemplate.opsForHash();
        String redisKey = String.format(CART_REDIS_KEY_TEMPLATE, uid);
        //自己思路根据Uid作为key值去检索，还需要遍历得到的用户购物车中的商品id和用户输入的id是否相等，繁琐
        //优化方法：将uid和商品id共同作为key值去检索，因为用户的更新是针对商品的
        String productDetail = hashOperations.get(redisKey, String.valueOf(productId));
        if (StringUtils.isEmpty(productDetail)) {
            return ResponseVo.error(CART_PRODUCT_NOT_EXIST);
        }
        Cart cart = gson.fromJson(productDetail, Cart.class);
        if (cartPutForm.getQuantity() != null
             && cartPutForm.getQuantity()>=0) {
            cart.setQuantity(cartPutForm.getQuantity());
        }
        if (cartPutForm.getSelected() != cart.getProductSelected()) {
            cart.setProductSelected(cartPutForm.getSelected());
        }
        //写入redis
        hashOperations.put(redisKey, String.valueOf(productId), gson.toJson(cart));




//        Map<String, String> entries = hashOperations.entries(redisKey);
//        for (Map.Entry<String, String> entry : entries.entrySet()) {
//            Integer productid = Integer.valueOf(entry.getKey());
//            if (productId.equals(productid)) {
//                Cart cart = gson.fromJson(entry.getValue(), Cart.class);
//                if (cartPutForm.getQuantity() != null) {
//                    cart.setQuantity(cartPutForm.getQuantity());
//                }
//                if (cartPutForm.getSelected() != cart.getProductSelected()) {
//                    cart.setProductSelected(cartPutForm.getSelected());
//                }
//                //写入redis
//                hashOperations.put(redisKey, String.valueOf(productId), gson.toJson(cart));
//            }
//        }
        ResponseVo<CartVo> cartVoResponseVo = list(uid);
        return cartVoResponseVo;

    }
    //全选、全不选代码优化，把共同调用的部分提取出来写成函数，代码复用

    @Override
    public ResponseVo<CartVo> deleteCartById(Integer uid, Integer productId) {
        HashOperations<String, String, String> hashOperations = redisTemplate.opsForHash();
        String redisKey = String.format(CART_REDIS_KEY_TEMPLATE, uid);
        String productDetail = hashOperations.get(redisKey, String.valueOf(productId));
        if(StringUtils.isEmpty(productDetail)){
            return ResponseVo.error(CART_PRODUCT_NOT_EXIST);
        }
        hashOperations.delete(redisKey,String.valueOf(productId));
        return list(uid);
    }

    @Override
    public ResponseVo<CartVo> selectAll(Integer uid) {
        HashOperations<String, String, String> hashOperations = redisTemplate.opsForHash();
        String redisKey = String.format(CART_REDIS_KEY_TEMPLATE, uid);
        List<Cart> cartById = getCartById(uid);
        for(Cart cart:cartById){
           cart.setProductSelected(true);
           //存入redis
          hashOperations.put(redisKey,String.valueOf(cart.getProductId()),gson.toJson(cart));
       }
       return list(uid);
    }

    @Override
    public ResponseVo<CartVo> unSelectAll(Integer uid) {
        HashOperations<String, String, String> hashOperations = redisTemplate.opsForHash();
        String redisKey = String.format(CART_REDIS_KEY_TEMPLATE, uid);
        List<Cart> cartById = getCartById(uid);
        for(Cart cart:cartById){
            cart.setProductSelected(false);
            //存入redis
            hashOperations.put(redisKey,String.valueOf(cart.getProductId()),gson.toJson(cart));
        }
        return list(uid);
    }

    @Override
    public ResponseVo<Integer> sumCart(Integer uid) {
        //我的思路直接利用查询中的对象属性
//        ResponseVo<CartVo> list = list(uid);
//        Integer cartTotalQuantity = list.getData().getCartTotalQuantity();
        //更快速从redis中查找相加
        Integer reduce = getCartById(uid).stream()
                .map(Cart::getQuantity)
                .reduce(0, Integer::sum);
        return ResponseVo.success(reduce);
    }
    @Override
    public List<Cart> getCartById(Integer uid){
        HashOperations<String, String, String> hashOperations = redisTemplate.opsForHash();
        String redisKey = String.format(CART_REDIS_KEY_TEMPLATE, uid);
        Map<String, String> entries = hashOperations.entries(redisKey);
        List<Cart> userCart=new ArrayList<>();
        for(Map.Entry<String,String> entry:entries.entrySet()){
            Cart cart = gson.fromJson(entry.getValue(), Cart.class);
            userCart.add(cart);
        }
        return userCart;
    }
}