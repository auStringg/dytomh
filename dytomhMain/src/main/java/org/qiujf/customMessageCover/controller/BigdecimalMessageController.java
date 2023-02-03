package org.qiujf.customMessageCover.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.qiujf.customMessageCover.entity.User;
import org.qiujf.customMessageCover.module.BigdecimalModule;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;

@Controller
public class BigdecimalMessageController {

    @ResponseBody
    @GetMapping("/returnSmall")
    public String returnSmall() throws JsonProcessingException {
        User user = new User();
        user.setName("qiujf");
        user.setMoney(new BigDecimal("123.00001"));
        user.setMoney2(new BigDecimal("123.00002"));
        user.setAge(26);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new BigdecimalModule());
        System.out.println(objectMapper.writeValueAsString(user));
        return objectMapper.writeValueAsString(user);
    }
}
