package com.dongleproject.controller;

import com.dongleproject.common.R;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Title: InterceptorDemo01
 * @Author wyh@qq.com
 * @Package com.dongleproject.controller
 * @Date 2025/11/14 9:37
 * @description:
 */
@RequestMapping("/demo")
@RestController
public class InterceptorDemo01 {

    @GetMapping("/demoInterceptor01/getName/{name}")
    public R getName(@PathVariable String name) {

        return R.ok().data("result", "Hello ， " + name);
    }
}
