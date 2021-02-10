import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import top.doperj.Application;
import top.doperj.dao.ProductDao;
import top.doperj.domain.Product;
import top.doperj.service.ProductService;
import top.doperj.util.security.SaltMap;

import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

@ContextConfiguration(classes = {Application.class, ProductService.class, SaltMap.class})
@RunWith(SpringJUnit4ClassRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)
public class ProductTest {
    @Autowired
    ProductService productService;

    @Autowired
    ProductDao productDao;

    @Test
    public void testDao() {
        Product product = new Product();
        productDao.save(product);
        System.out.println(productDao.findById(product.getId()).orElse(Product.getNullProduct()));
    }
}
