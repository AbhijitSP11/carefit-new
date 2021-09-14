package com.example.CareFitMain.model;

import java.io.Serializable;

public final class UserDetails implements Serializable {
    private String userid;
    private String age;
    private String height;
    private String weight;
    private String profession;

    public UserDetails(String ageVal, String heightVal, String weightVal) {
    }

    public UserDetails(String userid, String age, String height, String weight, String profession) {
        this.userid = userid;
        this.age = age;
        this.height = height;
        this.weight = weight;
        this.profession = profession;
    }

    public String getUserid() {
        return userid;
    }

    public String getAge() {
        return age;
    }

    public String getHeight() {
        return height;
    }

    public String getWeight() {
        return weight;
    }

    public String getProfession() {
        return profession;
    }
}
