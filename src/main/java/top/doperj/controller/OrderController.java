package top.doperj.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;
import top.doperj.domain.Customer;
import top.doperj.domain.Order;
import top.doperj.service.CustomerService;
import top.doperj.service.OrderService;
import top.doperj.service.ProductService;
import top.doperj.util.exception.NotFoundException;
import top.doperj.util.redis.RedisUtil;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/order")
public class OrderController {
    private OrderService orderService;

    private CustomerService customerService;

    @Autowired
    private RedisUtil<String> redisUtil;

    @Autowired
    private ProductService productService;

    @Autowired
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    @Autowired
    public void setCustomerService(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping(path = "/{id}")
    public Order getOrderById(@PathVariable int id) {
        System.out.println(id);
        return orderService.findOrderById(id);
    }

    @GetMapping
    public List<Order> getOrdersByCustomerId(@Param("customer") int customer) {
        System.out.println(customer);
        return orderService.findOrdersByCustomerId(customer);
    }

    @PostMapping(path = "/{uri}")
    public Order postOrder(@RequestBody Map<String, String> payload, @PathVariable String uri) throws JsonProcessingException {
        int customerId = Integer.parseInt(payload.get("customer"));
        int productId = Integer.parseInt(payload.get("product"));
        String realUri = productService.getProductUri(productId);
        if (!uri.equals(realUri)) {
            throw new IllegalArgumentException("Cannot complete order with given URI.");
        }

        return orderService.requestOrder(customerId, productId);
    }

    @DeleteMapping
    public Order deleteOrder(@Param(value = "id") int id, @Param("username") String username, @Param("sessionId") String sessionId) {
        if (username == null || sessionId == null) {
            throw new IllegalArgumentException("Empty username or session id.");
        }
        Customer customer = customerService.findUserByName(username);
        if (customer == null) {
            throw new NotFoundException("User not found.");
        }
        String currentSessionId = redisUtil.getMapAsSingleEntry("users", String.valueOf(customer.getId()));
        if (!currentSessionId.equals(sessionId)) {
            throw new IllegalArgumentException("User has not logged in.");
        }
        Order order = orderService.findOrderById(id);
        if (order.getUserId() != customer.getId()) {
            throw new IllegalArgumentException("Unable to delete order.");
        }
        orderService.deleteOrder(order);
        return order;
    }
}
