package com.bosch.stocktoship.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class RAForm {
  
    @Id
    String name ;
    String email ;
    String empId ;
    String role;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public RAForm()
    {}


    public RAForm(String role, String name, String email, String empId) {
        this.role = role;
        this.name = name;
        this.email = email;
        this.empId = empId;
    }
}
