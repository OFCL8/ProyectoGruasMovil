package com.example.gruasappmoviles;

import java.util.ArrayList;

public class Forms extends ArrayList<Forms> {
    private String Date, FormID, Plates, Company;

    public Forms() {
    }

    public Forms(String date, String formID, String plates, String company) {
        Date = date;
        FormID = formID;
        Plates = plates;
        Company = company;
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

    public String getPlates() { return Plates; }

    public void setPlates(String plates) { Plates = plates; }

    public String getCompany() { return Company; }

    public void setCompany(String company) { Company = company; }
}
