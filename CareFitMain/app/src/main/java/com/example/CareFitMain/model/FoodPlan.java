package com.example.CareFitMain.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class FoodPlan implements Serializable {
    @SerializedName("morning")
    private String morning;
    @SerializedName("breakfast")
    private String breakfast;
    @SerializedName("midday")
    private String midday;
    @SerializedName("lunch")
    private String lunch;
    @SerializedName("snacks")
    private String snacks;
    @SerializedName("dinner")
    private String dinner;

    public FoodPlan(String morning, String breakfast, String midday, String lunch, String snacks, String dinner) {
        this.morning = morning;
        this.breakfast = breakfast;
        this.midday = midday;
        this.lunch = lunch;
        this.snacks = snacks;
        this.dinner = dinner;
    }

    public String getMorning() {
        return morning;
    }

    public String getBreakfast() {
        return breakfast;
    }

    public String getMidday() {
        return midday;
    }

    public String getLunch() {
        return lunch;
    }

    public String getSnacks() {
        return snacks;
    }

    public String getDinner() {
        return dinner;
    }
}


