package com.example.CareFitMain.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Exercises  implements Serializable {
    @SerializedName("exName")
    private String exName;
    @SerializedName("exImage")
    private String exImage;
    @SerializedName("exDescription")
    private String exDescription;

    public Exercises() {
    }

    public Exercises(String exName, String exImage, String exDescription) {
        this.exName = exName;
        this.exImage = exImage;
        this.exDescription = exDescription;
    }

    public String getExName() {
        return exName;
    }

    public String getExImage() {
        return exImage;
    }

    public String getExDescription() {
        return exDescription;
    }
}
