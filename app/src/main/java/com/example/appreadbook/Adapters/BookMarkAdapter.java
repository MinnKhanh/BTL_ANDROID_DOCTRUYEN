package com.example.appreadbook.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.example.appreadbook.Listener.BookmaskListener;
import com.example.appreadbook.Model.Info;
import com.example.appreadbook.Model.Product;
import com.example.appreadbook.R;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

public class BookMarkAdapter extends BaseAdapter {
    private Context context;
    private List<Product> products;
    private int Layout;
    private BookmaskListener bookmaskListener;

    public BookMarkAdapter(Context context, int layout, List<Product> products,BookmaskListener bookmaskListener) {
        this.context = context;
        this.products = products;
        Layout = layout;
        this.bookmaskListener=bookmaskListener;
    }

    @Override
    public int getCount() {
        return products.size();
    }

    @Override
    public Object getItem(int i) {
        return products.get(i);
    }

    @Override
    public long getItemId(int i) {
        return products.get(i).getId();
    }


    private class ViewHolder{
        RoundedImageView img;
        TextView loai;
        TextView Name;
        RoundedImageView option;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;

        Product product1=products.get(i);
        Log.d("productss",product1.getName());
        if(view==null){
            holder=new ViewHolder();
            LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view=inflater.inflate(Layout,null);
            holder.img=view.findViewById(R.id.anhbook);
            holder.loai=view.findViewById(R.id.loai);
            holder.Name=view.findViewById(R.id.tentruyen);
            holder.option=view.findViewById(R.id.optionfavorite);
            if(Info.darkmode==true){
                holder.Name.setTextColor(Color.WHITE);
            }
            view.setTag(holder);
        }
        else{
            holder=(ViewHolder) view.getTag();
            Product product=products.get(i);
            Log.d("productss",product.getName());
            holder.img.setImageBitmap(getImage(product.getImg()));
            holder.loai.setText(product.getName());
            holder.Name.setText(product.getName());
            if(Info.darkmode==true){
                holder.Name.setTextColor(Color.WHITE);
            }
        }
        holder.img.setOnClickListener(v->{
            bookmaskListener.OnitemBookmaskClick(product1);
        });
        holder.Name.setOnClickListener(v->{
            bookmaskListener.OnitemBookmaskClick(product1);
        });
        if(Info.darkmode==true)
        holder.option.setImageDrawable(Drawable.createFromPath("@drawable/ic_optinitem.xml"));
        holder.option.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    PopupMenu popupMenu=new PopupMenu(context,holder.option);
                    popupMenu.inflate(R.menu.menu_item_favorite);
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem menuItem) {
                            switch (menuItem.getItemId()){
                                case R.id.menuremove:
                                 products.remove(i);
                                 notifyDataSetChanged();
                                 bookmaskListener.OnremoveBookmaskitem(product1);
                                    break;
                                case R.id.menudetail:
                                    bookmaskListener.OnitemBookmaskClick(product1);
                                    break;
                                default:
                                    break;
                            }
                            return false;
                        }
                    });
                    popupMenu.show();
                }
            });
    return view;
        }



    private Bitmap getImage(String encodeImage){
        byte[] bytes= Base64.decode(encodeImage,Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes,0,bytes.length);
    }
}
