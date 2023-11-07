package com.lzp.controller;

import com.lzp.pojo.Category;
import com.lzp.pojo.Result;
import com.lzp.service.CategoryService;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * 新增文章分类
     * @return
     */
    @PostMapping
    public Result add(@RequestBody @Validated(Category.Add.class) Category category){
        categoryService.add(category);
        return Result.success();
    }

    /**
     * 文章分类列表详情
     * @return
     */
    @GetMapping
    public Result<List<Category>> list(){
        List<Category> cs = categoryService.list();
        return Result.success(cs);
    }

    /**
     * 分类详情查询
     * @return
     */
    @GetMapping("/detail")
    public Result<Category> detail(Integer id){
        Category category = categoryService.findById(id);
        return Result.success(category);
    }

    /**
     * 更新文章分类
     * @param category
     * @return
     */
    @PutMapping
    public  Result update(@RequestBody @Validated(Category.Update.class) Category category){
        categoryService.update(category);
        return Result.success();
    }

    /**
     * 删除分类
     * @param id
     * @return
     */
    @DeleteMapping
    public Result delete(Integer id){
        categoryService.delete(id);
        return Result.success();
    }
}
