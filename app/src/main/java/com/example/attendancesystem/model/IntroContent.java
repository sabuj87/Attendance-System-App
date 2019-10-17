package com.example.attendancesystem.model;

public class IntroContent {

    private String intro_title, intro_details;
    private int intro_image;

    public IntroContent(String intro_title, String intro_details, int intro_image) {
        this.intro_title = intro_title;
        this.intro_details = intro_details;
        this.intro_image = intro_image;


    }

    public String getIntro_title() {
        return intro_title;
    }

    public void setIntro_title(String into_title) {
        this.intro_title = into_title;
    }

    public String getIntro_details() {
        return intro_details;
    }

    public void setIntro_details(String into_details) {
        this.intro_details = into_details;
    }

    public int getIntro_image() {
        return intro_image;
    }

    public void setIntro_image(int into_image) {
        this.intro_image = into_image;
    }
}
