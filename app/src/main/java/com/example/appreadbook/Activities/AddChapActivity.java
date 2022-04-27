package com.example.appreadbook.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appreadbook.API.ApiService;
import com.example.appreadbook.Model.Chap;
import com.example.appreadbook.Model.Info;
import com.example.appreadbook.Model.Product;
import com.example.appreadbook.databinding.ActivityAddChapBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddChapActivity extends AppCompatActivity {
    ActivityAddChapBinding binding;
    Chap chap=null;
    boolean update=false;
    Product truyen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityAddChapBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        checkupdate();
        if(Info.darkmode){
            binding.getRoot().setBackgroundColor(Color.BLACK);
        }
        binding.buttonaddchap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if(isvalid()){
                    if(update==true){

                       updatechap(chap,truyen);
                   }else{

                       getinsert();
                   }
                }


            }
        });
        binding.Back.setOnClickListener(v->onBackPressed());
    }
    private void checkupdate(){

           if((Chap)getIntent().getSerializableExtra("chap")!=null){
             //  Toast.makeText(getApplication(), "update",Toast.LENGTH_LONG).show();
          update=true;
          truyen=(Product) getIntent().getSerializableExtra("truyen");
          chap=(Chap)getIntent().getSerializableExtra("chap");
          binding.textAddImage.setText("Sua Chap");
          binding.chap.setText(String.valueOf(chap.getChapso()));
          binding.content.setText(chap.getContent());
          binding.buttonaddchap.setText("Update");
         }
    }
    private boolean isvalid(){
        if(binding.chap.getText().toString().trim().length()==0){
            Toast.makeText(getApplication(), "Yeu cau nhap thong tin chap",Toast.LENGTH_LONG).show();
            return false;
        }
        else if(binding.content.getText().toString().trim().length()==0){
            Toast.makeText(getApplication(), "Yeu cau nhap thong tin content",Toast.LENGTH_LONG).show();
            return false;
        }
        else{
            return true;
        }
    }

    private void updatechap(Chap chapupdate,Product truyen){
        Toast.makeText(getApplication(), "update",Toast.LENGTH_LONG).show();
        ApiService.apiService.updatechap(chapupdate.getChapso(),Integer.valueOf(binding.chap.getText().toString()) ,binding.content.getText().toString(),chapupdate.getMatruyen()).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                String rep=response.body();
                Toast.makeText(getApplication(), rep,Toast.LENGTH_LONG).show();
                Toast.makeText(getApplication(), String.valueOf(Info.permission),Toast.LENGTH_LONG).show();
                Intent intent=new Intent(AddChapActivity.this,Chapter.class);
                intent.putExtra("product",truyen);
                startActivity(intent);

            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(getApplication(), "Update that bai",Toast.LENGTH_LONG).show();
            }
        });
    }
    private void getinsert(){
       int namechap=Integer.valueOf(binding.chap.getText().toString());
        String content=binding.content.getText().toString();
        Product product=(Product) getIntent().getSerializableExtra("product");
        int Matruyen = product.getId();
        ApiService.apiService.addchap(namechap,content,Matruyen).enqueue(new Callback<Chap>() {
            @Override
            public void onResponse(Call<Chap> call, Response<Chap> response) {


                Toast.makeText(getApplication(), "Add thanh cong",Toast.LENGTH_LONG).show();
                Intent intent=new Intent(AddChapActivity.this,Chapter.class);
                intent.putExtra("product",product);
               startActivity(intent);

            }
            @Override
            public void onFailure(Call<Chap> call, Throwable t) {
                Toast.makeText(getApplication(), "Add that bai",Toast.LENGTH_LONG).show();
            }
        });

    }
    private Boolean isValidSignUpDetail(){

        if(binding.chap.getText().toString().trim().isEmpty()){
            showToast("enter title");
            return false;
        }
        else if(binding.content.getText().toString().trim().isEmpty()){
            showToast("enter description");
            return false;}

        else{
            return true;
        }
    }
    private void showToast(String message){
        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
    }
}