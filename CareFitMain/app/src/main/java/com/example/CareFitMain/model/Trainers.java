package com.example.CareFitMain.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public final class Trainers implements Serializable {
    @SerializedName("name")
    private String tname;
    @SerializedName("age")
    private String age;
    @SerializedName("image")
    private String image;
    @SerializedName("experience")
    private String experience;
    @SerializedName("fee")
    private String fee;
    @SerializedName("rating")
    private String rating;
    @SerializedName("description")
    private String description;

    public Trainers(String insName, String insAge, String insSkills, String insFees, String insAbout) {
    }

    public Trainers(String name,  String age, String image,String experience, String fee, String rating, String description) {
        this.tname = name;
        this.age = age;
        this.image = image;
        this.experience = experience;
        this.fee = fee;
        this.rating = rating;
        this.description = description;
    }

    public String getTName() {
        return tname;
    }

    public String getTAge() {
        return age;
    }

    public String getTImage() {
        return image;
    }

    public String getTExperience() {
        return experience;
    }

    public String getTFee() {
        return fee;
    }

    public String getTRating() { return rating; }

    public String getTDescription() { return description; }
}
