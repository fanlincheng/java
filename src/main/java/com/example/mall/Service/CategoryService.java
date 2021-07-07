package com.example.mall.Service;

import com.example.mall.Form.CategoryShow;
import com.example.mall.vo.ResponseVo;

import java.util.List;
import java.util.Set;

public interface CategoryService {
    ResponseVo<List<CategoryShow>>  selectAllCategoryLayer();
    ResponseVo<List<CategoryShow>> selectChildCategoryLayer(Integer id);
    ResponseVo<Set<Integer>> selectAllChildCategoryId(Integer  id,Set<Integer> categoryIdSet);
}
