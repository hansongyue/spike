package top.doperj.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.doperj.dao.CustomerDao;
import top.doperj.domain.Customer;
import top.doperj.domain.LoginTicket;
import top.doperj.util.exception.NotFoundException;
import top.doperj.util.redis.RedisUtil;
import top.doperj.util.security.Encoder;

import java.util.Optional;

import static org.springframework.util.StringUtils.isEmpty;

@Service
public class CustomerService {
    private CustomerDao customerDao;

    @Autowired
    RedisUtil<String> redisUtil;

    @Autowired
    public void setCustomerDao(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    public Customer findUserByName(String username) {
        Customer customer = customerDao.findByName(username);
        if (customer == null) {
            throw new NotFoundException("User not found.");
        }
        return customer;
    }

    public Customer findUserById(int id) {
        Optional<Customer> optionalUserEntity = customerDao.findById(id);
        return optionalUserEntity.orElse(null);
    }

    public Customer register(String username, String email, String password) {
        if (isEmpty(username) || isEmpty(email) || isEmpty(password)) {
            throw new IllegalArgumentException("No enough information to register!");
        }
        Customer user = new Customer();
        user.setName(username);
        /* TODO: validate email type */
        user.setEmail(email);
        String hash = Encoder.md5Encode(password);
        user.setPassword(hash);
        System.out.println(user);
        return customerDao.save(user);
    }

    public Customer updateCustomer(Customer customer) {
        String username = customer.getName();
        if (isEmpty(username)) {
            throw new IllegalArgumentException("Please specify username.");
        }
        Customer cur = customerDao.findByName(username);
        if (cur == null) {
            return customer;
        }
        if (!isEmpty(customer.getHeadline())) {
            cur.setName(customer.getName());
        }
        if (!isEmpty(customer.getZipCode())) {
            cur.setZipCode(customer.getZipCode());
        }
        if (customer.getDateOfBirth() != null) {
            cur.setDateOfBirth(customer.getDateOfBirth());
        }
        if (customer.getAvatar() != null) {
            cur.setAvatar(customer.getAvatar());
        }
        System.out.println(cur);
        return customerDao.save(cur);
    }

    public LoginTicket loginByUsername(String username, String password) {
        if (isEmpty(username) || isEmpty(password)) {
            throw new IllegalArgumentException("No enough information to login!");
        }
        Customer customer = customerDao.findByName(username);
        if (customer == null) {
            /* TODO: fix exception not handled bug */
            throw new NotFoundException("Username " + username + " is not found.");
        }
        if (!customer.getPassword().equals(Encoder.md5Encode(password))) {
            throw new IllegalArgumentException("Wrong password!");
        }
        String sessionId = redisUtil.getMapAsSingleEntry("users", String.valueOf(customer.getId()));
        if (sessionId != null && !sessionId.equals("")) {
            System.out.println("already logged in as: " + sessionId);
            return new LoginTicket(customer, sessionId);
        }
        long timestamp = System.currentTimeMillis();
        sessionId = Encoder.md5Encode(customer.getName() + "#" + Encoder.getSalt() + "#" + timestamp);
        redisUtil.putMap("users", String.valueOf(customer.getId()), sessionId);
        return new LoginTicket(customer, sessionId);
    }

    public Customer logoutByUsername(String username, String requestSessionId) {
        Customer customer = this.findUserByName(username);
        String registeredSessionId = redisUtil.getMapAsSingleEntry("users", String.valueOf(customer.getId()));
        if (registeredSessionId == null || registeredSessionId.equals("") || !registeredSessionId.equals(requestSessionId)) {
            return customer;
        }
        redisUtil.removeMap("users", String.valueOf(customer.getId()));
        return customer;
    }

    public Customer loginByEmail(String email, String password) {
        if (isEmpty(email) || isEmpty(password)) {
            throw new IllegalArgumentException("No enough information to login!");
        }
        return null;
    }
}
