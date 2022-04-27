package com.example.appreadbook.Adapters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appreadbook.Listener.CategoriListener;
import com.example.appreadbook.Model.Categorydata;
import com.example.appreadbook.databinding.ItemcategoryBinding;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CatetgoriesViewHolder>{
    private  final List<Categorydata> categorylist;
    private final CategoriListener categoriListener;

    public CategoryAdapter(List<Categorydata> categorylist, CategoriListener categoriListener) {
        this.categorylist = categorylist;
        this.categoriListener = categoriListener;
    }

    @NonNull
    @Override
    public CatetgoriesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemcategoryBinding itemContainerUserBinding=ItemcategoryBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,false
        );
        return new CatetgoriesViewHolder(itemContainerUserBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull CatetgoriesViewHolder holder, int position) {
        holder.setUserData(categorylist.get(position));
    }

    @Override
    public int getItemCount() {
        return categorylist.size();
    }

    class CatetgoriesViewHolder extends RecyclerView.ViewHolder{
        ItemcategoryBinding binding;

        CatetgoriesViewHolder(ItemcategoryBinding itemContainerUserBinding) {
            super(itemContainerUserBinding.getRoot());
            binding=itemContainerUserBinding;
        }
        void setUserData(Categorydata type){
            binding.textcategory.setText(type.getTitle());
            // binding.textEmail.setText(user.email);

            binding.itemimg.setImageBitmap(getUserImage(type.getIcon()));
            binding.getRoot().setOnClickListener(v->categoriListener.OnCategoryClick(type));
        }
    }
    private Bitmap getUserImage(String encodeImage){
        byte[] bytes= Base64.decode(encodeImage,Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes,0,bytes.length);
    }
}
