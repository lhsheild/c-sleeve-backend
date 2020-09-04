package com.sheildog.csleevebackend.api.v1;

import com.sheildog.csleevebackend.exception.http.ForbiddenException;
import com.sheildog.csleevebackend.exception.http.NotFoundException;
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
    @Autowired
    private ISkill iSkill;

    @GetMapping("/test")
    public String test() throws Exception {
        iSkill.r();
        throw new ForbiddenException(10001);
    }
}
