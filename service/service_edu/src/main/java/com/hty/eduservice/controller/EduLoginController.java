package com.hty.eduservice.controller;

import com.hty.utils.R;
import org.springframework.web.bind.annotation.*;

/**
 * create by Semineces on 2020-08-09
 */
@RestController
@RequestMapping("/eduservice/user")
public class EduLoginController {

    //登录
    @PostMapping("/login")
    public R login() {
        return R.success().data("token", "admin");
    }

    //info
    @GetMapping("/info")
    public R info() {
        return R.success()
                .data("roles", "[admin]")
                .data("name", "admin")
                .data("avatar", "https://safebooru.org//images/3038/0fda857a147f672f20651bf32f6a8934d600a84e.jpg?3163615");
    }

}
