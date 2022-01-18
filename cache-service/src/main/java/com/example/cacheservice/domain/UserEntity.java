package com.example.cacheservice.domain;

import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "user")
@ToString
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "username", nullable = false, length = 80)
    private String username;

    @Column(name = "password", nullable = false, length = 60)
    private String password;

    @Column(name = "name", nullable = false, length = 80)
    private String name;

    @Column(name = "birthday", length = 40)
    private String birthday;

    @Column(name = "address", nullable = false, length = 80)
    private String address;

    @Column(name = "phone", nullable = false, length = 11)
    private String phone;

    @Column(name = "sex", nullable = false)
    private Boolean sex = false;

    @Column(name = "higth_cm")
    private Integer higthCm;

    @Column(name = "width_kg")
    private Integer widthKg;

    public Integer getWidthKg() {
        return widthKg;
    }

    public void setWidthKg(Integer widthKg) {
        this.widthKg = widthKg;
    }

    public Integer getHigthCm() {
        return higthCm;
    }

    public void setHigthCm(Integer higthCm) {
        this.higthCm = higthCm;
    }

    public Boolean getSex() {
        return sex;
    }

    public void setSex(Boolean sex) {
        this.sex = sex;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}