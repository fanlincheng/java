package com.example.mall.dao;

import com.example.mall.pojo.Product;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

public interface ProductMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Product record);

    int insertSelective(Product record);

    Product selectByPrimaryKey(Integer id);
    List<Product> selectByProductIdSets(@Param("productIds") Set<Integer> productIds);

    int updateByPrimaryKeySelective(Product record);

    int updateByPrimaryKey(Product record);
    List<Product> getAllProductByCategoryId(@Param("categoryId") Set<Integer> categoryId);
    List<Product> getAllProduct();
}