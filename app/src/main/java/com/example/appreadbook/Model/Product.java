package com.example.appreadbook.Model;

import java.io.Serializable;

public class Product  implements Serializable {
    private String name,img,MaTL,description,nametype,ngayxuat,tacgia;
    private int id;

    public Product(String name, String img, String maTL,String description, String nametype, int id,String ngayxuat,String tacgia) {
        this.name = name;
        this.img = img;
        MaTL = maTL;
        this.nametype = nametype;
        this.id = id;
        this.description = description;
        this.tacgia=tacgia;
        this.ngayxuat=ngayxuat;
    }

    public String getNgayxuat() {
        return ngayxuat;
    }

    public void setNgayxuat(String ngayxuat) {
        this.ngayxuat = ngayxuat;
    }

    public String getTacgia() {
        return tacgia;
    }

    public void setTacgia(String tacgia) {
        this.tacgia = tacgia;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getMaTL() {
        return MaTL;
    }

    public void setMaTL(String maTL) {
        MaTL = maTL;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNametype() {
        return nametype;
    }

    public void setNametype(String nametype) {
        this.nametype = nametype;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", img='" + img + '\'' +
                ", MaTL='" + MaTL + '\'' +
                ", description='" + description + '\'' +
                ", nametype='" + nametype + '\'' +
                ", id=" + id +
                '}';
    }
}
