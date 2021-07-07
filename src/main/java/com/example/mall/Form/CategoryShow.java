package com.example.mall.Form;

import lombok.Data;

import java.util.List;

@Data
public class CategoryShow {
    private Integer id;
    private Integer parentId;
    private String name;
    private Integer sortOrder;
    private List<CategoryShow> subCategories;
}
