package com.example.appreadbook.Adapters;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appreadbook.Model.Categorydata;
import com.example.appreadbook.R;

import java.util.List;

public class CategoryselectAdapter extends ArrayAdapter<Categorydata> {
    public CategoryselectAdapter(@NonNull Context context, int resource, @NonNull List<Categorydata> objects) {
        super(context, resource, objects);
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_selected,parent,false);
        TextView select=convertView.findViewById(R.id.tvs_selected);
        Categorydata categorydata=this.getItem(position);
        if(categorydata!=null){
            select .setText(categorydata.getTitle());

        }
        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_select_category,parent,false);
        TextView tvCategory=convertView.findViewById(R.id.tvs_category);
        Categorydata categorydata=this.getItem(position);
        if(categorydata!=null){
            tvCategory.setText(categorydata.getTitle());

        }
        return convertView;
    }


}
