package com.example.mall.Controller;

import com.example.mall.Service.ProductService;
import com.example.mall.vo.ProductDetailVo;
import com.example.mall.vo.ResponseVo;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductService productService;
    @GetMapping("/")
    public ResponseVo<PageInfo> getPorductById(@RequestParam(required = false) Integer id, @RequestParam(required = false,defaultValue = "1") Integer pageNumber, @RequestParam (required = false,defaultValue = "1")Integer pageSize){
        ResponseVo<PageInfo> listResponseVo = productService.selectAllProduct(id, pageNumber, pageSize);
        return listResponseVo;

    }
    @GetMapping("/{id}")
    public ResponseVo<ProductDetailVo> getProductDetailById(@PathVariable("id") Integer id){
        ResponseVo<ProductDetailVo> productDetailById = productService.getProductDetailById(id);
        return productDetailById;
    }
}
