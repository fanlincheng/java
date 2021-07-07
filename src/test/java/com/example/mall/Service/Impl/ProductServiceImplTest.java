package com.example.mall.Service.Impl;

import com.example.mall.MallApplicationTests;
import com.example.mall.Service.ProductService;
import com.example.mall.enums.ResponseEnum;
import com.example.mall.vo.ProductDetailVo;
import com.example.mall.vo.ResponseVo;
import com.github.pagehelper.PageInfo;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class ProductServiceImplTest extends MallApplicationTests {
@Autowired
private ProductService productService;
    @Test
    public void selectAllProduct() {
        ResponseVo<PageInfo> listResponseVo = productService.selectAllProduct(null, 1, 1);
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(),listResponseVo.getStatus());
    }
    @Test
    public void detailProduct(){
        ResponseVo<ProductDetailVo> productDetailById = productService.getProductDetailById(1);
        Assert.assertEquals(ResponseVo.success().getStatus(),productDetailById.getStatus());
    }
}