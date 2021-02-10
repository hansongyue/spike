package top.doperj.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import top.doperj.domain.Customer;
import top.doperj.domain.LoginTicket;
import top.doperj.service.CustomerService;
import top.doperj.util.redis.RedisUtil;
import top.doperj.util.security.Encoder;

import java.util.Map;


@Controller
public class IndexController {
    @Autowired
    CustomerService customerService;

    @RequestMapping("")
    public String index() {
        return "index";
    }

    @PostMapping("/login")
    @ResponseBody
    public LoginTicket login(@RequestBody Map<String, String> payload) {
        String username = payload.getOrDefault("username", "");
        String password = payload.getOrDefault("password", "");
        return customerService.loginByUsername(username, password);
    }

    @PostMapping("/logout")
    @ResponseBody
    public Customer logout(@RequestBody Map<String, String> payload) {
        String username = payload.getOrDefault("username", "");
        String sessionId = payload.getOrDefault("sessionId", "");
        return customerService.logoutByUsername(username, sessionId);
    }

    @PostMapping("/register")
    @ResponseBody
    public Customer register(@RequestBody Map<String, String> payload) {
        String username = payload.get("username");
        String email = payload.get("email");
        String password = payload.get("password");
        Customer res = customerService.register(username, email, password);
        System.out.println(res);
        return res;
    }
}
