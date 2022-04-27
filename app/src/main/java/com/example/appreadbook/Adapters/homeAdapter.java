package com.example.appreadbook.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.appreadbook.Listener.HomeListener;
import com.example.appreadbook.Model.Info;
import com.example.appreadbook.Model.Product;
import com.example.appreadbook.R;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.List;

public class homeAdapter extends BaseAdapter implements Filterable {
    private Context context;
    private List<Product> products;
    private List<Product> productsOld;
    private int Layout;
    private HomeListener homeListener;

    public homeAdapter(Context context, int layout, List<Product> products,HomeListener homeListener) {
        this.context = context;
        this.products = products;
        this.productsOld=products;
        Layout = layout;
        this.homeListener=homeListener;
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

    @Override
    public View getView (int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;

        Product product1=products.get(i);
        Log.d("productss",product1.getName());
        if(view==null){
            holder=new ViewHolder();
            LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view=inflater.inflate(Layout,null);
            holder.img=view.findViewById(R.id.imgbook);
            holder.Name=view.findViewById(R.id.Namebook);
            holder.Chap=view.findViewById(R.id.chap);
            holder.img.setOnClickListener(v->{homeListener.OnclickItemHome(products.get(i));});
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
            holder.Chap.setText(product.getName());
            holder.Name.setText(product.getName());
            holder.img.setOnClickListener(v->{homeListener.OnclickItemHome(products.get(i));});
            if(Info.darkmode==true){
                holder.Name.setTextColor(Color.WHITE);
            }
        }

        return view;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String stringSearch=charSequence.toString();
                if(stringSearch.isEmpty() || stringSearch.length()==0){
                    products=productsOld;
                }
                else{
                    List<Product> list=new ArrayList<>();
                    for(Product pro : productsOld){
                        if(pro.getName().toLowerCase().contains(stringSearch.toLowerCase())){
                            list.add(pro);
                        }
                    }
                    products=list;
                }
                FilterResults filterResults=new FilterResults();
                filterResults.values=products;
                filterResults.count=products.size();
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                products=(List<Product>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    private class ViewHolder{
        RoundedImageView img;
        TextView Chap;
        TextView Name;

    }


    private Bitmap getImage(String encodeImage){
        byte[] bytes= Base64.decode(encodeImage,Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes,0,bytes.length);
    }
}


