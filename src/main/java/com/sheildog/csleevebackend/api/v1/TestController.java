package com.sheildog.csleevebackend.api.v1;

import com.sheildog.csleevebackend.sample.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private Test test;

    @GetMapping("")
    public void getTest(){
        System.out.println(test);
    }
}
