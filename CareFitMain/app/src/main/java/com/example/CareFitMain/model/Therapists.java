package com.example.CareFitMain.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public final class Therapists implements Serializable {
    @SerializedName("name")
    private String name;
    @SerializedName("image")
    private String image;
    @SerializedName("tage")
    private String tage;
    @SerializedName("taddress")
    private String taddress;
    @SerializedName("tbio")
    private String tbio;
    @SerializedName("tratings")
    private String tratings;
    @SerializedName("desc")
    private String tdesc;


    public Therapists() {
    }

    public Therapists(String name, String image, String tage, String taddress, String tbio, String tratings, String tdesc) {
        this.name = name;
        this.image = image;
        this.tage = tage;
        this.taddress = taddress;
        this.tbio = tbio;
        this.tratings = tratings;
        this.tdesc = tdesc;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public String getTage() {
        return tage;
    }

    public String getTaddress() {
        return taddress;
    }

    public String getTbio() {
        return tbio;
    }

    public String getTratings() {
        return tratings;
    }

    public String getTdesc() {
        return tdesc;
    }
}
