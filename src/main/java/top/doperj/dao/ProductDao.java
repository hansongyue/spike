package top.doperj.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import top.doperj.domain.Product;

import javax.transaction.Transactional;
import java.util.Date;

@Repository
@Transactional
public interface ProductDao extends JpaRepository<Product, Integer> {

    @Modifying
    @Query("UPDATE Product product SET product.stock = product.stock - 1 where product.id = (:id)")
    void decrementProductStockById(@Param("id") int productId);

    @Modifying
    @Query("UPDATE Product product SET product.stock = product.stock + 1 where product.id = (:id)")
    void incrementProductStockById(@Param("id") int productId);
}
