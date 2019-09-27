package com.example.attendancesystem.model;

public class Student {
    private String name;
  private   String id;
  private   String year;
  private   String semester;
  private   String department;
  private   String batch;
  private   String section;
  private   String email;
  private   String phone;
  private   String gender;
  private   String course;
  private   String course_code;
  private   String shift;
  private   String password;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getCourse_code() {
        return course_code;
    }

    public void setCourse_code(String course_code) {
        this.course_code = course_code;
    }
    public  Student (){

    }

    public String getShift() {
        return shift;
    }

    public void setShift(String shift) {
        this.shift = shift;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Student(String name, String id, String year, String semester, String department, String batch, String section, String email, String phone, String gender, String course, String course_code, String shift, String password) {
        this.name = name;
        this.id = id;
        this.year = year;
        this.semester = semester;
        this.department = department;
        this.batch = batch;
        this.section = section;
        this.email = email;
        this.phone = phone;
        this.gender = gender;
        this.course = course;
        this.course_code = course_code;
        this.shift=shift;
        this.password=password;


    }
}
