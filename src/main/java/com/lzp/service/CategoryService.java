package com.lzp.service;

import com.lzp.pojo.Category;

import java.util.List;

public interface CategoryService {

    //新增文章分类
    void add(Category category);

    //文章分类列表查询
    List<Category> list();

    //分类详情查询
    Category findById(Integer id);

    //更新文章分类
    void update(Category category);

    //删除分类
    void delete(Integer id);
}
