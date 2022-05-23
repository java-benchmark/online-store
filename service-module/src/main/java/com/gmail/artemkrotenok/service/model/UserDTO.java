package com.gmail.artemkrotenok.service.model;

import com.gmail.artemkrotenok.repository.model.UserRoleEnum;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserDTO {

    private Long id;
    @NotNull(message = "The field 'surname' must be filled")
    @Size(min = 1, max = 40, message = "'surname' size must be between 1 and 40 characters")
    private String surname;
    @NotNull(message = "The field 'name' must be filled")
    @Size(min = 1, max = 40, message = "'name' size must be between 1 and 40 characters")
    private String name;
    @NotNull(message = "The field 'middle name' must be filled")
    @Size(min = 1, max = 40, message = "'middle name' size must be between 1 and 40 characters")
    private String middleName;
    @NotNull(message = "The field 'email' must be filled")
    @Size(min = 1, max = 5000, message = "'email' size must be between 1 and 5000 characters")
    private String email;
    private String password;
    private String phone;
    private String address;
    private UserRoleEnum role;
    private Boolean isDeleted;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public UserRoleEnum getRole() {
        return role;
    }

    public void setRole(UserRoleEnum role) {
        this.role = role;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

}
