package com.example.appreadbook.Model;

import java.io.Serializable;

public class Chap   implements Serializable {
    String content;
    int Matruyen,id,chapso;

    public Chap(String content, int matruyen, int id, int chapso) {
        this.content = content;
        Matruyen = matruyen;
        this.id = id;
        this.chapso = chapso;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getMatruyen() {
        return Matruyen;
    }

    public void setMatruyen(int matruyen) {
        Matruyen = matruyen;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getChapso() {
        return chapso;
    }

    public void setChapso(int chapso) {
        this.chapso = chapso;
    }

}
