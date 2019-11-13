package com.example.gruasappmoviles;

import java.util.ArrayList;

public class Forms extends ArrayList<Forms> {
    private String ID, Date, Plates, Company;

    private int image;

    public Forms() {
    }

    public Forms(String id, String date, String plates, String company) {
        ID = id;
        Date = date;
        Plates = plates;
        Company = company;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getPlates() { return Plates; }

    public void setPlates(String plates) { Plates = plates; }

    public String getCompany() { return Company; }

    public void setCompany(String company) { Company = company; }

    public int getImage() { return image; }

    public void setImage(int image) { this.image = image; }

    public String getID() { return ID; }

    public void setID(String ID) { this.ID = ID; }

}
