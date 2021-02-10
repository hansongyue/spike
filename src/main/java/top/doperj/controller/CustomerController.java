package top.doperj.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;
import top.doperj.domain.Customer;
import top.doperj.service.CustomerService;
import top.doperj.util.customer.CustomerUtil;
import top.doperj.util.exception.NotFoundException;

import java.util.Map;

@RestController
public class CustomerController {
    private CustomerService customerService;

    @Autowired
    public void setCustomerService(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/customer")
    public Customer getCustomer(@Param("username") String username, @Param("sessionId") String sessionId) {
        /* TODO: validate session id */
        if (username == null || sessionId == null) {
            throw new IllegalArgumentException("Empty username or session id.");
        }
        Customer customer = customerService.findUserByName(username);
        if (customer == null) {
            throw new NotFoundException("User not found.");
        }
        System.out.println(customer);
        return customer;
    }

    @PostMapping("/customer")
    public Customer updateCustomer(@RequestBody Map<String, String> payload) {
        System.out.println("hello");
        Customer customer = new Customer();
        customer.setName(payload.getOrDefault("username", null));
        customer.setPassword(payload.getOrDefault("password", null));
        customer.setHeadline(payload.getOrDefault("headline", null));
        customer.setBase64Avatar(payload.getOrDefault("avatar", null));
        customer.setDateOfBirth(CustomerUtil.parseDate(payload.getOrDefault("dob", null)));
        customer.setZipCode(payload.getOrDefault("zip-code", null));
        System.out.println(customer);
        return customerService.updateCustomer(customer);
    }
}
