package com.example.appreadbook.Model;

public class TaiKhoan {
    private String taikhoan;
    private String matkhau;
    private int pers;

    public TaiKhoan(String taikhoan, String matkhau, int pers) {
        this.taikhoan = taikhoan;
        this.matkhau = matkhau;
        this.pers = pers;
    }

    public String getTaikhoan() {
        return taikhoan;
    }

    public void setTaikhoan(String taikhoan) {
        this.taikhoan = taikhoan;
    }

    public String getMatkhau() {
        return matkhau;
    }

    public void setMatkhau(String matkhau) {
        this.matkhau = matkhau;
    }

    public int getPers() {
        return pers;
    }

    public void setPers(int pes) {
        this.pers = pes;
    }
}
