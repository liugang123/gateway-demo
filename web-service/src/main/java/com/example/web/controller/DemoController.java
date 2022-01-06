package com.example.web.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liugang
 * @create 2021/12/24
 */
@RestController
@RequestMapping("/demo")
public class DemoController {

    @RequestMapping("/method")
    public String method(@RequestParam("param") String param) {
        return param;
    }
}
