package com.example.mall.Controller;

import com.example.mall.Form.CategoryShow;
import com.example.mall.Service.CategoryService;
import com.example.mall.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/category")
@Slf4j
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @GetMapping("/")
    public ResponseVo<List<CategoryShow>> selectAllCatogoryLayer(){
       return categoryService.selectAllCategoryLayer();
    }
    @GetMapping("/{id}")
    public ResponseVo<List<CategoryShow>> selectChildCategoryLayer(@PathVariable Integer id){
        return categoryService.selectChildCategoryLayer(id);
    }

}
