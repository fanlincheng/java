package com.example.mall.Service.Impl;

import com.example.mall.Service.CategoryService;
import com.example.mall.Service.ProductService;
import com.example.mall.dao.ProductMapper;
import com.example.mall.enums.ProductStatusEnum;
import com.example.mall.pojo.Product;
import com.example.mall.vo.ProductDetailVo;
import com.example.mall.vo.ProductVo;
import com.example.mall.vo.ResponseVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.example.mall.enums.ResponseEnum.PRODUCT_NOTEXIST;
import static com.example.mall.enums.ResponseEnum.PRODUCT_OFFSALE_OR_DELETE;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private CategoryService categoryService;

    @Override
    public ResponseVo<PageInfo> selectAllProduct(Integer categoryId, Integer pageNumber, Integer pageSize) {
        Set<Integer> categoryChildSet=new HashSet<>();
           if(categoryId!=null){
               categoryService.selectAllChildCategoryId(categoryId, categoryChildSet);
               categoryChildSet.add(categoryId);
           }
        PageHelper.startPage(pageNumber,pageSize);
            List<Product> allProduct = productMapper.getAllProductByCategoryId(categoryChildSet);
        List<ProductVo> productVoList = allProduct.stream()
                .map(e -> {
                    ProductVo productVo = new ProductVo();
                    BeanUtils.copyProperties(e, productVo);
                    return productVo;
                })
                .collect(Collectors.toList());
        PageInfo pageInfo=new PageInfo<>(productVoList);
        pageInfo.setList(productVoList);
        return ResponseVo.success(pageInfo);

//         for(Product product:allProduct){
//             if(setResponseVo.getData().contains(product.getCategoryId())){
//                 ProductVo findProduct = new ProductVo();
//                 BeanUtils.copyProperties(product,findProduct);
//                 productVos.add(findProduct);
//             }
//         }


        //return ResponseVo.success(productVos);
    }

    @Override
    public ResponseVo<ProductDetailVo> getProductDetailById(Integer id) {
        Product product = productMapper.selectByPrimaryKey(id);
        //只做确定性的返回
        if(StringUtils.isEmpty(product)){
            return ResponseVo.error(PRODUCT_NOTEXIST);
        }
        //对敏感字段的处理
        product.setStock(product.getStock()>100?100:product.getStock());
        if(product.getStatus().equals(ProductStatusEnum.DELETE)||product.getStatus().equals(ProductStatusEnum.OFF_SALE)){
            return ResponseVo.error(PRODUCT_OFFSALE_OR_DELETE);
        }
        ProductDetailVo productDetailVo=new ProductDetailVo();
        BeanUtils.copyProperties(product,productDetailVo);
        return ResponseVo.success(productDetailVo);
    }
}
