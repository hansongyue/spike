package top.doperj.dao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import top.doperj.domain.Customer;

import javax.transaction.Transactional;
import java.util.Date;

@Repository
@Transactional
public interface CustomerDao extends JpaRepository<Customer, Integer> {
    @Query(value = "SELECT c FROM Customer c WHERE c.name = (:name)")
    Customer findByName(@Param("name") String name);

    @Modifying
    @Query("UPDATE Customer customer SET customer.dateOfBirth = (:dob) where customer.name = (:name)")
    void setDateOfBirthByUsername(@Param("name") String name, @Param("dob") Date dateOfBirth);

    @Modifying
    @Query("UPDATE Customer customer SET customer.headline = (:headline) where customer.name = (:name)")
    void setHeadlineByUsername(@Param("name") String name, @Param("headline") String headline);

    @Modifying
    @Query("UPDATE Customer customer SET customer.zipCode = (:zip-code) where customer.name = (:name)")
    void setZipCodeByUsername(@Param("zip-code") String zipCode, @Param("name") String name);

    @Modifying
    @Query("UPDATE Customer customer SET customer.avatar = (:avatar) where customer.name = (:name)")
    void setAvatarByUsername(@Param("name") String name, @Param("avatar") byte[] avatar);
}
