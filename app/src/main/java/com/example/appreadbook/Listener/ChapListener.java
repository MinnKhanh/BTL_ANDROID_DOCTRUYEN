package com.example.appreadbook.Listener;

import com.example.appreadbook.Model.Chap;
import com.example.appreadbook.Model.Product;

public interface ChapListener {
    void OnClikchap(Chap chap);
    void UpdateChap(Chap chap, Product truyen);
    void RemoveChap(Chap chap);
}
