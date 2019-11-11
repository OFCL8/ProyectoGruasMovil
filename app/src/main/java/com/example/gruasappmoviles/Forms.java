package com.example.gruasappmoviles;

import java.util.ArrayList;

public class Forms extends ArrayList<Forms> {
    private String Date, Plates, Company, Type;

    public Forms() {
    }

    public Forms(String date, String type, String plates, String company) {
        Date = date;
        Type = type;
        Plates = plates;
        Company = company;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getType() { return Type; }

    public void setType(String type) { Type = type; }
    
    public String getPlates() { return Plates; }

    public void setPlates(String plates) { Plates = plates; }

    public String getCompany() { return Company; }

    public void setCompany(String company) { Company = company; }
}
