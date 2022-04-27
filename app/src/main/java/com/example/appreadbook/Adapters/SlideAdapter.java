package com.example.appreadbook.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.appreadbook.Listener.CategoriListener;
import com.example.appreadbook.Model.Categorydata;
import com.example.appreadbook.Model.Product;
import com.example.appreadbook.R;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

public class SlideAdapter extends PagerAdapter{
    Context context;
    LayoutInflater layoutInflater;
    private List<Categorydata> products;
    private final CategoriListener categoriListener;
    public SlideAdapter(Context context, List<Categorydata> products, CategoriListener categoriListener) {
        this.context = context;
        this.products = products;
        this.categoriListener = categoriListener;
    }

    @Override
    public int getCount() {
        return products.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {

        return view == (RelativeLayout) object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater=(LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view=layoutInflater.inflate(R.layout.item_slide,container,false);
        RoundedImageView slideimage=view.findViewById(R.id.imgeslide);
        TextView header=view.findViewById(R.id.heatdersp);
        TextView catedescription=view.findViewById(R.id.descriptioncategory);
        catedescription.setText(products.get(position).getDescription());
        slideimage.setImageBitmap(getImage(products.get(position).getIcon()));
        header.setText(products.get(position).getTitle());

        slideimage.setOnClickListener(v->{
            categoriListener.OnCategoryClick(products.get(position));
        });
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((RelativeLayout)object);
    }

    private Bitmap getImage(String encodeImage){
        byte[] bytes= Base64.decode(encodeImage,Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes,0,bytes.length);
    }


}
