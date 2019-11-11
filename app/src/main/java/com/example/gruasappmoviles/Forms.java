package com.example.gruasappmoviles;

import java.util.ArrayList;

public class Forms extends ArrayList<Forms> {
    private String Date;
    private String FormID;

    public Forms() {
    }

    public Forms(String date, String formID) {
        Date = date;
        FormID = formID;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getFormID() {
        return FormID;
    }

    public void setFormID(String formID) {
        FormID = formID;
    }
}
