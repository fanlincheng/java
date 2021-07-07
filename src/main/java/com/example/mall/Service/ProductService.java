package com.example.mall.Service;

import com.example.mall.vo.ProductDetailVo;
import com.example.mall.vo.ResponseVo;
import com.github.pagehelper.PageInfo;

public interface ProductService {
    ResponseVo<PageInfo> selectAllProduct(Integer categoryId, Integer pageNumber, Integer pageSize);
    ResponseVo<ProductDetailVo> getProductDetailById(Integer id);
}
