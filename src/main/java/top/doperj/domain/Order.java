package top.doperj.domain;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "tb_order")
public class Order {
    private static final long serialVersionUID = 2L;

    private int id;

    private int userId;

    private int productId;

    /**
     * state 0: created
     * state 1: paid
     * state 2: finished
     * state 3: canceled
     */
    private int state;

    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date createdTime;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Basic
    @Column
    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    @Basic
    @Column
    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    @Basic
    @Column
    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", userId=" + userId +
                ", productId=" + productId +
                ", state=" + state +
                ", createdTime=" + createdTime +
                '}';
    }
}
