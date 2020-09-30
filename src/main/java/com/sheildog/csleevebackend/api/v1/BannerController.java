package com.sheildog.csleevebackend.api.v1;

import com.sheildog.csleevebackend.dto.PersonDTO;
import com.sheildog.csleevebackend.exception.http.HttpException;
import com.sheildog.csleevebackend.exception.http.NotFoundException;
import com.sheildog.csleevebackend.exception.http.ServerErrorException;
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
        if (banner == null) {
            throw new NotFoundException(30005);
        }
        return banner;
    }

    @GetMapping("/test")
    public String test() {
        System.out.println(123);
        throw new ServerErrorException(9999);
    }
}
