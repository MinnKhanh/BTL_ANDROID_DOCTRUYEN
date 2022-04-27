package com.example.appreadbook.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.appreadbook.API.ApiService;
import com.example.appreadbook.Adapters.SlideAdapter;
import com.example.appreadbook.Listener.CategoriListener;
import com.example.appreadbook.Model.Categorydata;
import com.example.appreadbook.Model.Info;
import com.example.appreadbook.R;
import com.example.appreadbook.databinding.ActivitySliderBinding;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SliderCategory extends AppCompatActivity implements CategoriListener{
private ViewPager SlidViewPager;
private LinearLayout DotLayout;
Intent intentcate;
Button next;
    ProgressDialog progress;
Button pev;
TextView[] Dot;
ActivitySliderBinding binding;
private List<Categorydata> productList;
private SlideAdapter slideAdapter;
    int CurrentPage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivitySliderBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        SlidViewPager=(ViewPager) findViewById(R.id.slideViewpager);

        setslide();

        binding.optiondetailcate.setOnClickListener(v->{
            PopupMenu popupMenu=new PopupMenu(this,binding.optiondetailcate);
            popupMenu.inflate(R.menu.menu_cate);

            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem menuItem) {
                    switch (menuItem.getItemId()) {
                        case R.id.addcate:
                            if(Info.permission==1){
                                intentcate=new Intent(SliderCategory.this,AddCategoryActivity.class);
                                startActivity(intentcate);
                            }else{

                            }
                            break;
                        case R.id.updatecate:
                            if(Info.permission==1){
                                intentcate=new Intent(SliderCategory.this,AddCategoryActivity.class);
                                intentcate.putExtra("Loai",productList.get(CurrentPage));
                                startActivity(intentcate);

                                }else{
                                    Toast.makeText(SliderCategory.this,"Bạn không có quyền sửa đổi",Toast.LENGTH_LONG).show();
                                }
                            break;
                        case R.id.removecate:

                            if(Info.permission==1){
                                progress = new ProgressDialog(SliderCategory.this);
                                progress.setTitle("Loading...");
                                progress.setCancelable(false);
                                progress.show();
                                ApiService.apiService.removecate(productList.get(CurrentPage).getId()).enqueue(new Callback<String>() {
                                    @Override
                                    public void onResponse(Call<String> call, Response<String> response) {

                                        String mess= response.body();
                                        Toast.makeText(SliderCategory.this,mess,Toast.LENGTH_LONG).show();
                                        productList.remove(CurrentPage);
                                        slideAdapter=new SlideAdapter(SliderCategory.this,productList, SliderCategory.this);
                                        SlidViewPager.setAdapter(slideAdapter);
                                        progress.dismiss();

                                    }
                                    @Override
                                    public void onFailure(Call<String> call, Throwable t) {

                                        Toast.makeText(getApplication(), "Không thành công", Toast.LENGTH_LONG).show();
                                    }
                                });
                            }else{
                                Toast.makeText(SliderCategory.this,"Bạn không có quyền sửa đổi",Toast.LENGTH_LONG).show();
                            }

                            break;

                        default:
                            break;
                    }
                    return false;
                }
            });
            popupMenu.show();
        });
        SlidViewPager.addOnPageChangeListener(viewListener);

       setlistener();
    }

    private void setlistener(){
        binding.search.setOnClickListener(v->{
            Intent intent=new Intent(SliderCategory.this,SearchActivity.class);
            startActivity(intent);
        });
        binding.Home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(SliderCategory.this,Home.class);
                startActivity(intent);
                finish();
            }
        });
        binding.category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(SliderCategory.this,SliderCategory.class);
                startActivity(intent);
                finish();
            }
        });
        binding.bookmask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(SliderCategory.this,BookMarkActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
    public void setslide(){
        ApiService.apiService.getStudents().enqueue(new Callback<List<Categorydata>>() {
            @Override
            public void onResponse(Call<List<Categorydata>> call, Response<List<Categorydata>> response) {
               productList= response.body();
                slideAdapter=new SlideAdapter(SliderCategory.this,productList, SliderCategory.this);
               SlidViewPager.setAdapter(slideAdapter);

            }
            @Override
            public void onFailure(Call<List<Categorydata>> call, Throwable t) {
                Toast.makeText(getApplication(), "chay that bai", Toast.LENGTH_LONG).show();
            }
        });

    }
    public void addDotsIndicator(int position){
        Dot=new TextView[4];
        DotLayout.removeAllViews();
        for(int i=0;i<Dot.length;i++){
            Dot[i]=new TextView(this);
            Dot[i].setText(Html.fromHtml("&#8226"));
            Dot[i].setTextSize(35);
            Dot[i].setTextColor(getResources().getColor(R.color.colorrTranparentWhite));
            DotLayout.addView(Dot[i]);
        }
        if(Dot.length>0){
            Dot[position].setTextColor(getResources().getColor(R.color.colorrTranparentWhiteactive));
        }

    }
    ViewPager.OnPageChangeListener viewListener=new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
         //   addDotsIndicator(position);
            CurrentPage=position;
//            if(position==0){
//                next.setEnabled(true);
//                pev.setEnabled(true);
//                pev.setVisibility(View.INVISIBLE);
//                next.setText("Next");
//                pev.setText("");
//            }else if(position==productList.size()-1){
//                next.setEnabled(true);
//                pev.setEnabled(true);
//                pev.setVisibility(View.VISIBLE);
//                next.setText("Finish");
//                pev.setText("Back");
//            }
//            else{
//                next.setEnabled(true);
//                pev.setEnabled(true);
//                pev.setVisibility(View.VISIBLE);
//                next.setText("Next");
//                pev.setText("Back");
//            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    @Override
    public void OnCategoryClick(Categorydata cate) {
        Intent intent=new Intent(SliderCategory.this, ProductBytypeActivity.class);
        intent.putExtra("Loai",cate);
        startActivity(intent);
        finish();
    }


}