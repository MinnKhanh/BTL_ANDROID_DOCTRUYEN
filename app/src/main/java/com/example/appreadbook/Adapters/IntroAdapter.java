package com.example.appreadbook.Adapters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appreadbook.Listener.HomeListener;
import com.example.appreadbook.Model.Product;
import com.example.appreadbook.databinding.ItemIntroBinding;
import com.example.appreadbook.databinding.ItemSearchBinding;

import java.util.List;

public class IntroAdapter extends RecyclerView.Adapter<IntroAdapter.IntroViewHolder>{
    List<Product> product;
    HomeListener homeListener;
    public IntroAdapter(List<Product> product,HomeListener homeListener) {
        this.homeListener=homeListener;
        this.product = product;
    }

    @NonNull
    @Override
    public IntroViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemIntroBinding itemIntroBinding=ItemIntroBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,false
        );
        return new IntroViewHolder(itemIntroBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull IntroViewHolder holder, int position) {
        holder.setUserData(product.get(position));
    }

    @Override
    public int getItemCount() {
        return product.size();
    }

    class IntroViewHolder extends RecyclerView.ViewHolder{
        ItemIntroBinding binding;

        IntroViewHolder(ItemIntroBinding introBinding) {
            super(introBinding.getRoot());
            binding=introBinding;
        }
        void setUserData(Product product){
            binding.imgPhone.setImageBitmap(getUserImage(product.getImg()));
            binding.phoneTitle.setText(product.getName());
            binding.tacgia.setText(product.getTacgia());
            binding.getRoot().setOnClickListener(v->{
                homeListener.OnclickItemHome(product);
            });
        }
    }
    private Bitmap getUserImage(String encodeImage){
        byte[] bytes= Base64.decode(encodeImage,Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes,0,bytes.length);
    }
}
