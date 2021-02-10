package top.doperj.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.doperj.dao.MessageDao;
import top.doperj.domain.Product;
import top.doperj.service.ProductService;
import top.doperj.util.redis.RedisUtil;
import top.doperj.util.security.SaltMap;

import java.util.List;


@RestController
@RequestMapping("/product")
public class ProductController {
    private ProductService productService;
    private RedisUtil<String> redisUtil;

    @Autowired
    MessageDao messageDao;

    @Autowired
    SaltMap saltMap;

    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    @Autowired
    public void setRedisUtil(RedisUtil<String> redisUtil) {
        this.redisUtil = redisUtil;
    }

    @GetMapping(path = "/uri")
    public String getProductUri(@Param("id") int id) {
        return productService.getProductUri(id);
    }

    @GetMapping(path = "/{id}")
    public Product getProduct(@PathVariable int id) {
        return productService.findProductById(id);
    }

    @GetMapping(path = "")
    public List<Product> getAllProducts() {
        return productService.findAllProducts();
    }
}
