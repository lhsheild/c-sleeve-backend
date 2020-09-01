package com.sheildog.csleevebackend.api.v1;

import com.sheildog.csleevebackend.sample.hero.ISkill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author a7818
 */
@RestController
@RequestMapping("v1/banner")
public class BannerController {
//    private final ISkill diana;
//    private final ISkill irelia;
//
//    public BannerController(ISkill diana, ISkill irelia) {
//        this.diana = diana;
//        this.irelia = irelia;
//    }
    @Autowired
    private ISkill camille;

    @GetMapping("/test")
    public String test() {
        /* this.diana.r(); */
        camille.r();
        return "hello, sheildog!";
    }
}
