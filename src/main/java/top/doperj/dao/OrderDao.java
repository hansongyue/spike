package top.doperj.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import top.doperj.domain.Order;

import java.util.List;

public interface OrderDao extends JpaRepository<Order, Integer> {
    @Query(value = "SELECT o FROM Order o WHERE o.userId = (:customerId)")
    List<Order> findAllByCustomerId(@Param("customerId") int customerId);
}
