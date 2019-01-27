package com.winterchen.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2017/8/16.
 */
@Controller
@RequestMapping(value = "/")
public class HelloServletController {

    @ResponseBody
    @GetMapping("/webServlet")
    public String webServlet(){
        try {
            TimeUnit.MILLISECONDS.sleep(10);
        } catch (InterruptedException e) {
            return "Error during thread sleep";
        }
        return "Hello World-w";
    }

}
