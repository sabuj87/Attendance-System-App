package com.example.attendancesystem.model;

public class Course {
    private String id;
    private String course_name;
    private String course_code;
    private String teacher;
    private String teacher_id;
    private String selected_batch;

    public Course() {
    }

    public Course(String id, String course_name, String course_code, String teacher,String teacher_id, String selected_batch) {
        this.id = id;
        this.course_name = course_name;
        this.course_code = course_code;
        this.teacher = teacher;
        this.teacher_id=teacher_id;
        this.selected_batch = selected_batch;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCourse_name() {
        return course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }

    public String getCourse_code() {
        return course_code;
    }

    public void setCourse_code(String course_code) {
        this.course_code = course_code;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getTeacher_id() {
        return teacher_id;
    }

    public void setTeacher_id(String teacher_id) {
        this.teacher_id = teacher_id;
    }

    public String getSelected_batch() {
        return selected_batch;
    }

    public void setSelected_batch(String selected_batch) {
        this.selected_batch = selected_batch;
    }
}
