package com.example.appreadbook.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.PopupMenu;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appreadbook.Listener.ChapListener;
import com.example.appreadbook.Model.Chap;
import com.example.appreadbook.Model.Info;
import com.example.appreadbook.Model.Product;
import com.example.appreadbook.R;
import com.example.appreadbook.databinding.ItemChapBinding;

import java.util.List;

public class ChapAdapter  extends RecyclerView.Adapter<ChapAdapter.ItemchapViewHolder>{
    private List<Chap> chap;
    private ChapListener chapListener;
    private Context context;
    Product truyen;
    public ChapAdapter(List<Chap> chap, ChapListener chapListener,Context context,Product truyen) {
        this.chap = chap;
        this.chapListener = chapListener;
        this.context=context;
        this.truyen=truyen;
    }




    @NonNull
    @Override
    public ItemchapViewHolder onCreateViewHolder (@NonNull ViewGroup parent, int viewType) {
        ItemChapBinding resultSearchBinding=ItemChapBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,false
        );
        return new ItemchapViewHolder(resultSearchBinding);
    }

    @Override
    public void onBindViewHolder (@NonNull ItemchapViewHolder holder, int position) {
        holder.setUserData(chap.get(position),position);
    }

    @Override
    public int getItemCount ( ) {
        return chap.size();
    }

    class ItemchapViewHolder extends RecyclerView.ViewHolder{
        ItemChapBinding binding;

        ItemchapViewHolder(ItemChapBinding resultSearchBinding) {
            super(resultSearchBinding.getRoot());
            binding=resultSearchBinding;
        }
        void setUserData(Chap chapdetail,int positon){
            binding.namechap.setText("Chap"+chapdetail.getChapso());
            binding.namechap.setOnClickListener(v->chapListener.OnClikchap(chapdetail));
            binding.optionchap.setOnClickListener(v->{
                PopupMenu popupMenu=new PopupMenu(context,binding.optionchap);
                popupMenu.inflate(R.menu.option_chap);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()){
                            case R.id.update:
                                chapListener.UpdateChap(chapdetail,truyen);
                                break;
                            case R.id.menuremove:
                                if(Info.permission==1) {
                                    chap.remove(positon);
                                    notifyDataSetChanged();
                                    chapListener.RemoveChap(chapdetail);
                                    notifyDataSetChanged();
                                }
                                break;
                            case R.id.menudetail:
                                chapListener.OnClikchap(chapdetail);
                                break;
                            default:
                                break;
                        }
                        return false;
                    }
                });
                popupMenu.show();
            });

        }
    }
    private Bitmap getUserImage(String encodeImage){
        byte[] bytes= Base64.decode(encodeImage,Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes,0,bytes.length);
    }
}