package top.doperj.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import top.doperj.dao.CustomerDao;
import top.doperj.dao.MessageDao;
import top.doperj.dao.OrderDao;
import top.doperj.dao.ProductDao;
import top.doperj.domain.Customer;
import top.doperj.domain.Order;
import top.doperj.domain.Product;
import top.doperj.util.data.JsonUtil;
import top.doperj.util.exception.NotFoundException;
import top.doperj.util.exception.ServiceException;

import java.util.Date;
import java.util.List;

@Service
public class OrderService {
    private CustomerDao customerDao;
    private OrderDao orderDao;
    private ProductDao productDao;

    private final String TOPIC = "my-order";

    @Autowired
    MessageDao messageDao;

    @Autowired
    JsonUtil jsonUtil;

    @Autowired
    public void setCustomerDao(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    @Autowired
    public void setOrderDao(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    @Autowired
    public void setProductDao(ProductDao productDao) {
        this.productDao = productDao;
    }

    public Order findOrderById(int id) {
        return orderDao.findById(id).orElseThrow(() -> new NotFoundException("Order " + id + " is not found."));
    }

    public List<Order> findOrdersByCustomerId(int customerId) {
        return orderDao.findAllByCustomerId(customerId);
    }

    public Order requestOrder(int customerId, int productId) throws JsonProcessingException {
        Order newOrder = new Order();
        newOrder.setUserId(customerId);
        newOrder.setProductId(productId);
        System.out.println("request " + newOrder);
        messageDao.sendMessage(TOPIC, jsonUtil.toJson(newOrder));
        return newOrder;
    }

    @KafkaListener(groupId = "0", topics = TOPIC)
    public void listen(ConsumerRecord<?, String> record) throws JsonProcessingException {
        System.out.println(record.value());
        Order newOrder = jsonUtil.fromJson(record.value(), Order.class);
        newOrder.setCreatedTime(new Date(System.currentTimeMillis()));
        newOrder.setState(0);
        int customerId = newOrder.getUserId();
        int productId = newOrder.getProductId();
        Customer customer = customerDao.findById(customerId).orElseThrow(() -> new NotFoundException("Customer Not Found"));
        Product product = productDao.findById(productId).orElseThrow(() -> new NotFoundException("Product Not Found"));
        if (product.getStock() == 0) {
            throw new ServiceException("Product Sold Out");
        }
        productDao.decrementProductStockById(productId);
        orderDao.save(newOrder);
    }
    public void deleteOrder(Order order) {
        Product product = productDao.findById(order.getProductId()).orElseThrow(() -> new NotFoundException("Product Not Found"));
        productDao.incrementProductStockById(product.getId());
        orderDao.deleteById(order.getId());
    }
}
