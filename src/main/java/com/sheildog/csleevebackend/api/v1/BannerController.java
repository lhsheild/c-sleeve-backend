package com.sheildog.csleevebackend.api.v1;

import com.sheildog.csleevebackend.dto.PersonDTO;
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
    private ISkill iSkill;
    @Autowired
    private BannerService bannerService;

    @GetMapping("/name/{name}")
    public void getByName(@PathVariable String name) {

    }

    @PostMapping("/test/{id}")
    public PersonDTO test(@PathVariable("id") @Max(value = 8) int id, @RequestBody @Validated PersonDTO person) throws Exception {
        iSkill.r();
        /* PersonDTO dto = PersonDTO.builder().name("7yue").age(18).build(); */
        PersonDTO dto = new PersonDTO();
        dto.setAge(person.getAge());
        dto.setName(person.getName());
        dto.setPassword1(person.getPassword1());
        dto.setPassword2(person.getPassword2());
        return dto;
        /* throw new ForbiddenException(10001); */
    }
}
