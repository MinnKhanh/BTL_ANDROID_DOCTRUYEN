package com.example.appreadbook.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appreadbook.API.ApiService;
import com.example.appreadbook.Adapters.BookMarkAdapter;
import com.example.appreadbook.Listener.BookmaskListener;
import com.example.appreadbook.Model.Info;
import com.example.appreadbook.Model.Product;
import com.example.appreadbook.R;
import com.example.appreadbook.databinding.ActivityBookMarkBinding;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookMarkActivity extends AppCompatActivity implements BookmaskListener {
    GridView gridView;
    List<Product> productList;
    ActivityBookMarkBinding binding;
    BookMarkActivity bookmark;
    BookMarkAdapter bookMarkAdapter;
    ProgressDialog progress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityBookMarkBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        bookmark=this;
        progress = new ProgressDialog(this);

        progress.setTitle("Loading");
        progress.setCancelable(false);
        progress.show();
        gridView=findViewById(R.id.listfavorite);
        gridView.setNumColumns(Info.numbercol);
        getList();
        if(Info.darkmode==true){
            binding.getRoot().setBackgroundColor(Color.BLACK);

        }
        setlistener();
    }
    private void setlistener(){
        binding.Home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(BookMarkActivity.this,Home.class);
                startActivity(intent);
                finish();
            }
        });
        binding.category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(BookMarkActivity.this,SliderCategory.class);
                startActivity(intent);
                finish();
            }
        });
        binding.bookmask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(BookMarkActivity.this,BookMarkActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
    private void getList(){
      //  Toast.makeText(getApplication(), "Tai khoan la: "+Info.taikhoan+"  Mat khau la: "+Info.matkhau,Toast.LENGTH_LONG).show();
        ApiService.apiService.getfavorite(Info.taikhoan,Info.matkhau).enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {


                productList= response.body();
                if(productList.size()>=1){
                    binding.empty.setVisibility(View.GONE);
                    binding.listfavorite.setVisibility(View.VISIBLE);
                    bookMarkAdapter=new BookMarkAdapter(bookmark,R.layout.item_favorite,productList,bookmark);
                    gridView.setAdapter(bookMarkAdapter);
                    gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                        }
                    });
                }
                else{
                    binding.listfavorite.setVisibility(View.GONE);
                    binding.empty.setVisibility(View.VISIBLE);
                }

                progress.dismiss();
                Toast.makeText(getApplication(), "chay thanh cong",Toast.LENGTH_LONG).show();


            }
            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                progress.dismiss();
                Toast.makeText(getApplication(), "Chay That Bai",Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void OnitemBookmaskClick(Product productdt) {
        Intent intent=new Intent(BookMarkActivity.this,Chapter.class);
        intent.putExtra("product",productdt);
        startActivity(intent);
    }

    @Override
    public void OnremoveBookmaskitem(Product productrm) {
        Toast.makeText(getApplication(), "Tai khoan la: "+Info.taikhoan+"  Mat khau la: "+Info.matkhau+" Id san pham: "+productrm.getId(),Toast.LENGTH_LONG).show();
        ApiService.apiService.deletefavorite(Info.taikhoan,Info.matkhau,productrm.getId()).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {


                String producrm1= response.body();
                Toast.makeText(getApplication(), producrm1,Toast.LENGTH_LONG).show();


            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {

                Toast.makeText(getApplication(), "xoa that bai",Toast.LENGTH_LONG).show();
            }
        });
    }
}