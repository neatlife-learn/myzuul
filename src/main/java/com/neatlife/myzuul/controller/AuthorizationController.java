package com.neatlife.myzuul.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 授权
 */
@RestController
@RequestMapping("authorization")
@Slf4j
public class AuthorizationController {

    @PostMapping("/login")
    public String login(@RequestParam(name = "username") String username,
                        @RequestParam(name = "password") String password
    ) {

        String token;

        // 到数据中检查用户名和密码是否合法
        if ("admin".equals(username) && "admin".equals(password)) {
            // 生成token，保存到redis中
            token = "mytoken";
        } else {
            throw new RuntimeException("用户名或密码错误");
        }

        // 返回token给前端，用来认证使用
        return token;
    }

}
