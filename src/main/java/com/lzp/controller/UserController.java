package com.lzp.controller;

import com.lzp.pojo.Result;
import com.lzp.pojo.User;
import com.lzp.service.UserService;
import com.lzp.utils.JwtUtil;
import com.lzp.utils.Md5Util;
import com.lzp.utils.ThreadLocalUtil;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
@Validated
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 注册用户
     * @param username
     * @param password
     * @return
     */
    @PostMapping("/register")
    public Result register(@Pattern(regexp = "^\\S{5,16}$") String username,@Pattern(regexp = "^\\S{5,16}$") String password) {
        //查询用户是否已注册
        User user = userService.findByUserName(username);

        if (user == null) {
            //未注册
            //注册
            userService.register(username,password);
            return Result.success();
        } else {
            //已注册
            return Result.error("该名称已被占用");
        }
    }

    /**
     * 用户登录
     * @param username
     * @param password
     * @return
     */
    @PostMapping("/login")
    public Result<String> login(@Pattern(regexp = "^\\S{5,16}$") String username,@Pattern(regexp = "^\\S{5,16}$") String password){
        //根据用户名查询用户
        User user = userService.findByUserName(username);

        //判断用户是否存在
        if (user == null){
            return Result.error("用户名错误");
        }

        //判断密码是否正确
        if (Md5Util.getMD5String(password).equals(user.getPassword())){
            //登录成功
            Map<String, Object> claims = new HashMap<>();
            claims.put("id",user.getId());
            claims.put("username",user.getUsername());
            String token = JwtUtil.genToken(claims);
            return Result.success(token);
        }
        return Result.error("密码错误");
    }

    /**
     * 获取用户详情
     * @param
     * @return
     */
    @GetMapping("/userInfo")
    public Result<User> userInfo(/*@RequestHeader(name = "Authorization") String token*/){
//        Map<String, Object> claims = JwtUtil.parseToken(token);
//        String username = (String)claims.get("username");
        Map<String, Object> claims = ThreadLocalUtil.get();
        String username = (String)claims.get("username");
        User user = userService.findByUserName(username);
        return Result.success(user);
    }

    /**
     * 更新用户信息
     * @return
     */
    @PutMapping("/update")
    public Result update(@RequestBody @Validated User user){
        userService.update(user);
        return Result.success();
    }

    /**
     * 更新用户头像
     * @param avatarUrl
     * @return
     */
    @PatchMapping("updateAvatar")
    public Result updateAvatar(@RequestParam @URL String avatarUrl){
        userService.updateAvatar(avatarUrl);
        return Result.success();
    }

    /**
     * 更新密码
     * @param params
     * @return
     */
    @PatchMapping("/updatePwd")
    public Result updatePwd(@RequestBody Map<String,String> params){
        //校验参数
        String oldPwd = params.get("old_pwd");
        String newPwd = params.get("new_pwd");
        String rePwd = params.get("re_pwd");

        if (!StringUtils.hasLength(oldPwd) || !StringUtils.hasLength(newPwd) || !StringUtils.hasLength(rePwd)){
            return Result.error("缺少必要的参数");
        }

        //判断密码是否正确
        Map<String,Object> map = ThreadLocalUtil.get();
        String username = (String) map.get("username");
        User loginUser = userService.findByUserName(username);
        if (!Md5Util.getMD5String(oldPwd).equals(loginUser.getPassword())){
            return Result.error("原密码填写错误");
        }

        //判断密码格式
        if (!(5 <= newPwd.length() && newPwd.length() <= 15)){
            return Result.error("密码长度错误");
        }
        //判断newPwd与rePwd是否一样
        if (!rePwd.equals(newPwd)){
            return Result.error("两次填写的密码不一致");
        }
        //调用service完成密码更新
        userService.updatePwd(newPwd);

        return Result.success();
    }
}
