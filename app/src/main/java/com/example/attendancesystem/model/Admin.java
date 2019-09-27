package com.example.attendancesystem.model;

public class Admin {
    private String name;
    private String email;
    private String job;
    private String type;
    private String address;
    private String uId;
    private String nid;
    private String verified;
    private String device_token;
    private String profile_image;
    private int total_count;
    private String created_at;
    private String updated_at;
    private int total_contact;
    private String gender;
    private String password;

    public Admin() {
    }

    public Admin(String name, String email, String job, String type, String address, String uId, String nid, String verified, String device_token, String profile_image, int total_count, String created_at, String updated_at, int total_contact, String gender, String password) {
        this.name = name;
        this.email = email;
        this.job = job;
        this.type = type;
        this.address = address;
        this.uId = uId;
        this.nid = nid;
        this.verified = verified;
        this.device_token = device_token;
        this.profile_image = profile_image;
        this.total_count = total_count;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.total_contact = total_contact;
        this.gender = gender;
        this.password=password;
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

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getNid() {
        return nid;
    }

    public void setNid(String nid) {
        this.nid = nid;
    }

    public String getVerified() {
        return verified;
    }

    public void setVerified(String verified) {
        this.verified = verified;
    }

    public String getDevice_token() {
        return device_token;
    }

    public void setDevice_token(String device_token) {
        this.device_token = device_token;
    }

    public String getProfile_image() {
        return profile_image;
    }

    public void setProfile_image(String profile_image) {
        this.profile_image = profile_image;
    }

    public int getTotal_count() {
        return total_count;
    }

    public void setTotal_count(int total_count) {
        this.total_count = total_count;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public int getTotal_contact() {
        return total_contact;
    }

    public void setTotal_contact(int total_contact) {
        this.total_contact = total_contact;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
