package top.doperj.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.springframework.util.StringUtils.isEmpty;

@Entity
@Table(name = "tb_customer")
public class Customer {
    private static final long serialVersionUID = 1L;

    private int id;
    private String name;

    @JsonProperty("pwd")
    private String password;

    private String email;

    @JsonFormat(pattern="yyyy-MM-dd")
    @JsonProperty("dob")
    private Date dateOfBirth;

    private String zipCode;
    private String headline;
    private byte[] avatar;

    @Transient
    private String base64Avatar;

    public Customer() {}

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "date_of_birth")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    @Basic
    @Column(name = "zip_code")
    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    @Basic
    @Column(name = "headline")
    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    @Lob
    @Basic
    @Column(name = "avatar")
    public byte[] getAvatar() {
        return avatar;
    }

    public void setAvatar(byte[] avatar) {
        this.avatar = avatar;
        this.base64Avatar = avatar == null ? null : Base64.getEncoder().encodeToString(avatar);
    }

    @Transient
    public String getBase64Avatar() {
        return base64Avatar;
    }

    @Transient
    public void setBase64Avatar(String base64Avatar) {
        if (isEmpty(base64Avatar)) {
            return;
        }
        this.base64Avatar = base64Avatar;
        this.avatar = Base64.getDecoder().decode(base64Avatar);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer that = (Customer) o;
        return id == that.id &&
                Objects.equals(name, that.name) &&
                Objects.equals(password, that.password) &&
                Objects.equals(email, that.email) &&
                Objects.equals(dateOfBirth, that.dateOfBirth) &&
                Objects.equals(zipCode, that.zipCode) &&
                Objects.equals(headline, that.headline) &&
                Arrays.equals(avatar, that.avatar);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, password, email, dateOfBirth, zipCode, headline, avatar);
    }

    @Override
    public String toString() {
        SimpleDateFormat ft =
                new SimpleDateFormat ("yyyy-MM-dd");
        ft.setTimeZone(TimeZone.getTimeZone("UTC"));
        String dateString = null;
        if (dateOfBirth != null) {
            dateString = ft.format(dateOfBirth);
        }
        return "UserEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", dateOfBirth=" + dateString +
                ", zipCode='" + zipCode + '\'' +
                ", headline='" + headline + '\'' +
                ", avatar='" + Arrays.toString(avatar) + '\'' +
                '}';
    }
}
