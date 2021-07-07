package com.example.mall.Service.Impl;

import com.example.mall.Form.CategoryShow;
import com.example.mall.Service.CategoryService;
import com.example.mall.dao.CategoryMapper;
import com.example.mall.pojo.Category;
import com.example.mall.vo.ResponseVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static com.example.mall.MallConstant.MallConstant.ROOT_PARENT;
import static com.example.mall.enums.ResponseEnum.PARA_ERROR;

@Service
public class CategoryServieImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;
    @Override
    public ResponseVo<List<CategoryShow>> selectAllCategoryLayer() {
        List<Category> categorys = categoryMapper.selectAll();
        //List<CategoryShow> categoryShowList=new ArrayList<>();
        //第一层（父节点=0）
//       for(Category category:categorys){
//            if(category.getParentId().equals(ROOT_PARENT)){
//                CategoryShow categoryShowCur = new CategoryShow();
//                BeanUtils.copyProperties(category,categoryShowCur);
//                categoryShowList.add(categoryShowCur);
//            }
//        }
//       //第二层
//       for(CategoryShow categoryShow:categoryShowList){
//           Integer parentId = categoryShow.getId();
//           for(Category category:categorys){
//               if(category.getParentId().equals(parentId)){
//                   CategoryShow categoryShowCurrent = new CategoryShow();
//                   BeanUtils.copyProperties(category,categoryShowCurrent);
//                   categoryShowList.add(categoryShowCurrent);
//               }
//               else{
//                   category
//               }
//           }
//       }
        //lambda+Stream
        List<CategoryShow> categoryShowList = categorys.stream()
                .filter(e -> e.getParentId().equals(ROOT_PARENT))
                .map(e -> category2CategoryShow(e))
                .sorted(Comparator.comparing(CategoryShow::getSortOrder).reversed())
//                .map(this::category2CategoryShow(e))
                .collect(Collectors.toList());
            categoryChlidList(categorys,categoryShowList);

        return ResponseVo.success(categoryShowList);

    }

    @Override
    public ResponseVo<List<CategoryShow>> selectChildCategoryLayer(Integer id) {
        if(id<0){
            return ResponseVo.error(PARA_ERROR);}
        List<Category> categories = categoryMapper.selectAll();
        List<CategoryShow> categoryShowList = categories.stream()
                .filter(e -> e.getId().equals(id))
                .map(e -> category2CategoryShow(e))
                .sorted(Comparator.comparing(CategoryShow::getSortOrder).reversed())
                .collect(Collectors.toList());
        categoryChlidList(categories,categoryShowList);
        return ResponseVo.success(categoryShowList);

    }

    @Override
    public ResponseVo<Set<Integer>> selectAllChildCategoryId(Integer id,Set<Integer> categoryIdSet) {
        List<Category> categories = categoryMapper.selectAll();
        //categoryIdSet.add(id);
        selectAllChildCategoryId(categories,categoryIdSet,id);
        return ResponseVo.success(categoryIdSet);
    }
private void selectAllChildCategoryId(List<Category> categories,Set<Integer> categoryIdSet,Integer id){

        for(Category category:categories){
        if(category.getParentId().equals(id)){
            categoryIdSet.add(category.getId());
            selectAllChildCategoryId(categories,categoryIdSet,category.getId());
        }
        }
    }

    private void categoryChlidList(List<Category> categoryList,List<CategoryShow> categoryShows){
        for (CategoryShow categoryShow:categoryShows) {
            List<CategoryShow> subCategoryShow=new ArrayList<>();
            for(Category category:categoryList){
                if(category.getParentId().equals(categoryShow.getId())){
                    subCategoryShow.add(category2CategoryShow(category));
                }
            }
            //按照sortId由大到小排序
            subCategoryShow.sort(Comparator.comparing(CategoryShow::getSortOrder).reversed());
            categoryShow.setSubCategories(subCategoryShow);
            categoryChlidList(categoryList,subCategoryShow);
        }
    }
    private CategoryShow category2CategoryShow(Category category){
        CategoryShow categoryShow = new CategoryShow();
        BeanUtils.copyProperties(category,categoryShow);
        return categoryShow;
    }
}
