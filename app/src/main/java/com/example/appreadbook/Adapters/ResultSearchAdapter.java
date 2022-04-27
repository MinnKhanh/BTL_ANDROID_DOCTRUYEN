package com.example.appreadbook.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appreadbook.Listener.SearchListener;
import com.example.appreadbook.Model.Info;
import com.example.appreadbook.Model.Product;
import com.example.appreadbook.databinding.ItemSearchBinding;
import com.example.appreadbook.databinding.ResultSearchBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ResultSearchAdapter  extends RecyclerView.Adapter<ResultSearchAdapter.ResultSearchViewHolder> implements Filterable {
    private List<Product> products;
    private SearchListener searchListener;
    private List<Product> productsOld;

    public ResultSearchAdapter(List<Product> products, SearchListener searchListener) {
        this.products = products;
        this.searchListener = searchListener;
        this.productsOld=products;
    }

    @NonNull
    @Override
    public ResultSearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemSearchBinding resultSearchBinding=ItemSearchBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,false
        );
        return new ResultSearchViewHolder(resultSearchBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ResultSearchViewHolder holder, int position) {
        holder.setUserData(products.get(position));
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String stringSearch=charSequence.toString();
                if(stringSearch.isEmpty()){
                    products=productsOld;
                }
                else{
                    List<Product> list=new ArrayList<>();
                    for(Product pro : productsOld){
                        if(pro.getName().toLowerCase().contains(stringSearch)){
                            list.add(pro);
                        }
                    }
                    products=list;
                }
                FilterResults filterResults=new FilterResults();
                filterResults.values=products;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                products=(List<Product>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    class ResultSearchViewHolder extends RecyclerView.ViewHolder{
        ItemSearchBinding binding;

        ResultSearchViewHolder(ItemSearchBinding resultSearchBinding) {
            super(resultSearchBinding.getRoot());
            binding=resultSearchBinding;
        }
        void setUserData(Product product){
            binding.nameitem.setText(product.getName());
            binding.searchimg.setImageBitmap(getUserImage(product.getImg()));
            binding.typeitem.setText(product.getNametype());
            if(Info.darkmode==true){
                binding.nameitem.setTextColor(Color.WHITE);
                binding.typeitem.setTextColor(Color.WHITE);
            }
            binding.getRoot().setOnClickListener(v->searchListener.SearchOnClick(product));
        }
    }
    private Bitmap getUserImage(String encodeImage){
        byte[] bytes= Base64.decode(encodeImage,Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes,0,bytes.length);
    }
}
