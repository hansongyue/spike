package top.doperj.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.doperj.dao.ProductDao;
import top.doperj.domain.Product;
import top.doperj.util.exception.NotFoundException;
import top.doperj.util.exception.ServiceException;
import top.doperj.util.security.Encoder;
import top.doperj.util.security.SaltMap;

import java.util.Date;
import java.util.List;

@Service
public class ProductService {
    private ProductDao productDao;

    @Autowired
    private SaltMap saltMap;

    @Autowired
    public void setProductDao(ProductDao productDao) {
        this.productDao = productDao;
    }

    public Product findProductById(int id) {
        return productDao.findById(id).orElseThrow(() -> new NotFoundException("Product " + id + " is not found."));
    }

    public List<Product> findAllProducts() {
        return productDao.findAll();
    }

    public String getProductUri(int productId) {
        Product product = findProductById(productId);
        Date now = new Date(System.currentTimeMillis());
        if (product.getStartTime().after(now) || product.getEndTime().before(now)) {
            throw new IllegalArgumentException("Cannot get order URI at this time.");
        }
        return Encoder.md5Encode(productId + "#" + saltMap.getSalt(productId));
    }
}
