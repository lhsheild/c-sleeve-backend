package com.sheildog.csleevebackend.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sheildog.csleevebackend.exception.http.ParameterException;
import com.sheildog.csleevebackend.exception.http.ServerErrorException;
import com.sheildog.csleevebackend.model.User;
import com.sheildog.csleevebackend.repository.UserRepository;
import com.sheildog.csleevebackend.util.JwtToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class WxAuthenticationService {

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private UserRepository userRepository;

    @Value("${wx.code2session}")
    private String code2SessionUrl;

    @Value("${wx.appid}")
    private String appid;

    @Value("${wx.appsecret}")
    private String appsecret;

    public String code2Session(String code){
        String url = MessageFormat.format(this.code2SessionUrl, this.appid, this.appsecret,  code);
        RestTemplate rest = new RestTemplate();
        Map<String, Object> session = new HashMap<>();
        String sessionText = rest.getForObject(url, String.class);
        try {
            session = mapper.readValue(sessionText, Map.class);
            String token = registerUser(session);
            return token;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new ServerErrorException(9999);
        }
    }

    private String registerUser(Map<String, Object> session){
        String openId = (String) session.get("openid");
        if (openId == null){
            throw new ParameterException(20004);
        }
        Optional<User> userOptional = this.userRepository.findByOpenid(openId);
        if (userOptional.isPresent()){
            // TODO:返回JWT
            return JwtToken.makeToken(userOptional.get().getId());
        }
        User user = User.builder().openid(openId).build();
        this.userRepository.save(user);
        // TODO:返回JWT
        Long uid = user.getId();
        return JwtToken.makeToken(uid);
    }
}
