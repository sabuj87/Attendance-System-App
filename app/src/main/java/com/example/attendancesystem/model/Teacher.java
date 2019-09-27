package com.example.attendancesystem.model;

public class Teacher {
   private String id;
   private String name;
   private String department;
   private String designation;
   private String joined_date;
   private String phone;
   private String email;
   private String dob;
   private String nid;
   private String address;
   private String bio;
   private String password;
   private String shift;

    public Teacher() {
    }

    public Teacher(String id, String name, String department, String designation, String joined_date, String phone, String email, String dob, String nid, String address, String bio, String password,String shift) {
        this.id = id;
        this.name = name;
        this.department = department;
        this.designation = designation;
        this.joined_date = joined_date;
        this.phone = phone;
        this.email = email;
        this.dob = dob;
        this.nid = nid;
        this.address = address;
        this.bio = bio;
        this.password = password;
        this.shift=shift;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public void setJoined_date(String joined_date) {
        this.joined_date = joined_date;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public void setNid(String nid) {
        this.nid = nid;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDepartment() {
        return department;
    }

    public String getDesignation() {
        return designation;
    }

    public String getJoined_date() {
        return joined_date;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getDob() {
        return dob;
    }

    public String getNid() {
        return nid;
    }

    public String getAddress() {
        return address;
    }

    public String getBio() {
        return bio;
    }

    public String getPassword() {
        return password;
    }

    public String getShift() {
        return shift;
    }

    public void setShift(String shift) {
        this.shift = shift;
    }
}
