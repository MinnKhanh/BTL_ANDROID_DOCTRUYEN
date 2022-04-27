package com.example.appreadbook.Model;

import java.io.Serializable;

public class Categorydata implements Serializable {
    private String icon;


    private String title;
    private String id;
    private String description;

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Categorydata(String icon, String title, String id,String description) {
        this.icon = icon;
        this.title = title;
        this.id = id;
        this.description=description;
    }
}
