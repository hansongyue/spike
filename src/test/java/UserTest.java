import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import top.doperj.Application;
import top.doperj.dao.CustomerDao;
import top.doperj.domain.Customer;
import top.doperj.service.CustomerService;

import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;
import static org.junit.Assert.*;

@ContextConfiguration(classes = {Application.class, CustomerService.class})
@RunWith(SpringJUnit4ClassRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)
public class UserTest {
    @Autowired
    CustomerDao customerDao;

    @Autowired
    CustomerService customerService;

    /*
    * TODO: Switch to Log4j
    */
    private Logger logger = LoggerFactory.getLogger(Logger.class);

    @Test
    public void testUserDao() {
        logger.info("All users: " + customerDao.findAll());
        // logger.info(userDao.save(new UserEntity()).toString());
    }

    @Test
    public void testUserService() {
        // logger.info(customerService.findUserById(2).toString());
    }

    @Test
    public void testRegistration() throws Exception {
        Customer user = customerService.register("doperj", "zj9@rice.edu", "515300");
        logger.info(user.toString());
    }

    @Test
    public void testAvatar() throws Exception {
        Customer customer = new Customer();
        byte[] arr = new byte[]{1, 2, 3};
        customer.setName("doperj");
        customer.setAvatar(arr);
        customer = customerService.updateCustomer(customer);
        customer = customerService.findUserByName(customer.getName());
        logger.info(customer.toString());
        logger.info(customer.getBase64Avatar());
        customer.setBase64Avatar(customer.getBase64Avatar());
        assertArrayEquals(arr, customer.getAvatar());
    }
}
