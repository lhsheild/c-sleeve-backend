package com.sheildog.csleevebackend.api.v1;

import com.sheildog.csleevebackend.dto.PersonDTO;
import com.sheildog.csleevebackend.exception.http.ForbiddenException;
import com.sheildog.csleevebackend.exception.http.NotFoundException;
import com.sheildog.csleevebackend.sample.hero.ISkill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author a7818
 */
@RestController
@RequestMapping("/banner")
public class BannerController {
    @Autowired
    private ISkill iSkill;

    @PostMapping("/test/{id}/{pid}")
    public String test(@PathVariable String id, @PathVariable String pid, @RequestBody PersonDTO person) throws Exception {
        iSkill.r();
        throw new ForbiddenException(10001);
    }
}
