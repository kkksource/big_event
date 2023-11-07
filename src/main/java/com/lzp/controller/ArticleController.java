package com.lzp.controller;

import com.lzp.pojo.Category;
import com.lzp.pojo.Result;
import com.lzp.service.ArticleService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @GetMapping("/list")
    public Result<String> list(@RequestHeader(name = "Authorization") String token, HttpServletResponse response){
//        //验证token
//        try {
//            Map<String, Object> claims = JwtUtil.parseToken(token);
//            return Result.success("所有的文章数据");
//        }catch (Exception e) {
//            //http响应码401
//            response.setStatus(401);
//            return Result.error("未登录");
//        }
        return Result.success("所有的文章数据");
    }
}
