package com.example.CareFitMain.model;

import java.io.Serializable;

public final class appointments implements Serializable {
    private String pname;
    private String email;
    private String num;
    private String slotdate;
    private String time;
    private String concern;

    public appointments(String uname, String email, String contact) {
    }

    public appointments(String pname, String email, String num, String slotdate, String time, String concern) {
        this.pname = pname;
        this.email = email;
        this.num = num;
        this.slotdate = slotdate;
        this.time = time;
        this.concern = concern;
    }

    public String getPname() {
        return pname;
    }

    public String getEmail() {
        return email;
    }

    public String getNum() {
        return num;
    }

    public String getSlotdate() {
        return slotdate;
    }

    public String getTime() {
        return time;
    }

    public String getConcern() {
        return concern;
    }
}
