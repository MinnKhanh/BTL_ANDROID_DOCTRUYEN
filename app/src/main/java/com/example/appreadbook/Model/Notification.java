package com.example.appreadbook.Model;

public class Notification {
    public String name;
    public String Mataikhoan;
    public String date;
    public String addr;

    public Notification(String name, String mataikhoan, String date, String addr, String gioitinh, String img) {
        this.name = name;
        Mataikhoan = mataikhoan;
        this.date = date;
        this.addr = addr;
        this.gioitinh = gioitinh;
        this.img = img;
    }

    public String gioitinh;
    public String img;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMataikhoan() {
        return Mataikhoan;
    }

    public void setMataikhoan(String mataikhoan) {
        Mataikhoan = mataikhoan;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getGioitinh() {
        return gioitinh;
    }

    public void setGioitinh(String gioitinh) {
        this.gioitinh = gioitinh;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
