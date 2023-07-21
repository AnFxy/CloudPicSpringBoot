package com.fangxiaoyun.springboot.myspringboot;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestController {
    @RequestMapping("/banana")
    @ResponseBody
    public String doTest() {
        return "这个我的测试请求，请求成功了！！！！";
    }
}
