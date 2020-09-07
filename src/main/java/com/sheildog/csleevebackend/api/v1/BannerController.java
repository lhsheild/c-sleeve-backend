package com.sheildog.csleevebackend.api.v1;

import com.sheildog.csleevebackend.dto.PersonDTO;
import com.sheildog.csleevebackend.model.Banner;
import com.sheildog.csleevebackend.sample.hero.ISkill;
import com.sheildog.csleevebackend.service.BannerService;
import com.sheildog.csleevebackend.service.BannerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Max;

/**
 * @author a7818
 */
@RestController
@RequestMapping("/banner")
@Validated
public class BannerController {
    @Autowired
    private BannerService bannerService;

    @GetMapping("/name/{name}")
    public Banner getByName(@PathVariable String name) {
        Banner banner = bannerService.getByName(name);
        return banner;
    }
}
