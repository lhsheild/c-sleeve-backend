package com.sheildog.csleevebackend.api.v1;

import com.sheildog.csleevebackend.dto.TokenGetDTO;
import com.sheildog.csleevebackend.exception.http.NotFoundException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RequestMapping(value = "token")
@RestController
public class TokenController {
    @PostMapping("")
    public Map<String, String> getToken(@RequestBody @Validated TokenGetDTO userData){
        Map<String, String> map = new HashMap<>();
        String token = null;

        switch (userData.getLoginType()) {
            case USER_WX:
            case USER_EMAIL:
                break;
            default:
                throw new NotFoundException(10003);
        }
        return map;
    }
}
