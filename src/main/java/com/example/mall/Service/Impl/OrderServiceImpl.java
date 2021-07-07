package com.example.mall.Service.Impl;

import com.example.mall.Service.CartService;
import com.example.mall.Service.OrderService;
import com.example.mall.dao.*;
import com.example.mall.enums.ResponseEnum;
import com.example.mall.pojo.Cart;
import com.example.mall.pojo.Product;
import com.example.mall.pojo.Shipping;
import com.example.mall.vo.OrderVo;
import com.example.mall.vo.ResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    /**
     * 校验收获地址
     * 获取购物车，校验库存是否选中
     * 计算选中商品总价（困难 优惠券）
     * 生成订单，入库 Order OrderItem ，使用事务
     * 下单成功减库存
     *更新购物车，删除选中的商品
     * @param uid
     * @param shippingId
     * @return
     */
    @Autowired
    private ShippingMapper shippingMapper;
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private OrderItemMapper orderItemMapper;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private CartService cartService;
    @Autowired
    private ProductMapper productMapper;

    @Override
    public ResponseVo<OrderVo> create(Integer uid, Integer shippingId) {
        //收获地址校验
        Shipping shipping = shippingMapper.selectByUserIdAndShipId(uid,shippingId);
        if(StringUtils.isEmpty(shipping)){
            return ResponseVo.error(ResponseEnum.SHIPPING_NOT_EXIST);
        }
        //获取购物车，校验
        List<Cart> cartlist = cartService.getCartById(uid).stream()
                .filter(Cart::getProductSelected)
                .collect(Collectors.toList());
        if(CollectionUtils.isEmpty(cartlist)){
            return ResponseVo.error(ResponseEnum.CART_SELECTED_IS_EMPTY);
        }
        Set<Integer> productIds = cartlist.stream()
                .map(Cart::getProductId)
                .collect(Collectors.toSet());
        List<Product> products = productMapper.selectByProductIdSets(productIds);
        Map<Integer, Product> productsMap = products.stream()
                .collect(Collectors.toMap(Product::getId, product -> product));
        //根据商品id校验库存信息
        for(Cart cart:cartlist){
            Product product = productsMap.get(cart.getProductId());
            if(StringUtils.isEmpty(product)){
                return ResponseVo.error(ResponseEnum.PRODUCT_NOTEXIST);
            }
            if(product.getStock()<cart.getQuantity()){
                return ResponseVo.error(ResponseEnum.PRODUCT_SALEOUT);
            }
            
        }


        return null;
    }
}
