package com.example.appreadbook.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Toast;

import com.example.appreadbook.API.ApiService;
import com.example.appreadbook.Adapters.CategoryAdapter;
import com.example.appreadbook.Listener.CategoriListener;
import com.example.appreadbook.MainActivity;
import com.example.appreadbook.Model.Categorydata;
import com.example.appreadbook.R;
import com.example.appreadbook.databinding.ActivityCategoriesBinding;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Categories extends AppCompatActivity implements CategoriListener {
    private ActivityCategoriesBinding binding;
    private Categories CaActivity;
    ProgressDialog progress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityCategoriesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        progress = new ProgressDialog(this);
        progress.setTitle("Loading");
        progress.setCancelable(false);
        progress.show();
        CaActivity=this;
        getCategories();
        setlistener();

    }
private void setlistener(){
        binding.search.setOnClickListener(v->{
            Intent intent=new Intent(CaActivity,SearchActivity.class);
            startActivity(intent);
        });
    binding.Home.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent=new Intent(Categories.this,Home.class);
            startActivity(intent);
        }
    });
    binding.category.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent=new Intent(Categories.this,Categories.class);
            startActivity(intent);
        }
    });
    binding.bookmask.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent=new Intent(Categories.this,BookMarkActivity.class);
            startActivity(intent);
        }
    });
}
    private void getCategories(){

               ApiService.apiService.getStudents().enqueue(new Callback<List<Categorydata>>() {
                @Override
            public void onResponse(Call<List<Categorydata>> call, Response<List<Categorydata>> response) {

                List<Categorydata> data= response.body();
                CategoryAdapter categoriesAdapter=new CategoryAdapter(data,CaActivity);
                binding.CateRecyclerView.setAdapter(categoriesAdapter);
                progress.dismiss();

            }
            @Override
            public void onFailure(Call<List<Categorydata>> call, Throwable t) {
                Toast.makeText(binding.getRoot().getContext(), "chay that bai",Toast.LENGTH_LONG).show();
            }
        });}

    @Override
    public void OnCategoryClick(Categorydata cate) {

        Intent intent=new Intent(Categories.this, ProductBytypeActivity.class);
        intent.putExtra("Loai",cate);
        startActivity(intent);
        finish();
    }
}