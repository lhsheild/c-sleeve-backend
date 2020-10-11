package com.sheildog.csleevebackend.api.v1;

import com.sheildog.csleevebackend.dto.TokenDTO;
import com.sheildog.csleevebackend.dto.TokenGetDTO;
import com.sheildog.csleevebackend.exception.http.NotFoundException;
import com.sheildog.csleevebackend.service.WxAuthenticationService;
import com.sheildog.csleevebackend.util.JwtToken;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private WxAuthenticationService wxAuthenticationService;

    @PostMapping("")
    public Map<String, String> getToken(@RequestBody @Validated TokenGetDTO userData){
        Map<String, String> map = new HashMap<>();
        String token = null;

        switch (userData.getLoginType()) {
            case USER_WX:
                token = wxAuthenticationService.code2Session(userData.getAccount());
                break;
            case USER_EMAIL:
                break;
            default:
                throw new NotFoundException(10003);
        }
        map.put("token", token);
        return map;
    }

    @PostMapping("/verify")
    public Map<String , Boolean> verify(@RequestBody TokenDTO tokenDTO){
        Map<String, Boolean> map = new HashMap<>();
        Boolean valid = JwtToken.verifyToken(tokenDTO.getToken());
        map.put("is_valid", valid);
        return map;
    }
}
