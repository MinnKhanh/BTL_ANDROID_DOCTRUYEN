package com.example.appreadbook.Activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appreadbook.API.ApiService;
import com.example.appreadbook.Adapters.ApiClient;
import com.example.appreadbook.Adapters.CategoryselectAdapter;
import com.example.appreadbook.Model.Categorydata;
import com.example.appreadbook.Model.Info;
import com.example.appreadbook.Model.Product;
import com.example.appreadbook.R;
import com.example.appreadbook.databinding.ActivityUpdatetruyenBinding;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Updatetruyen extends AppCompatActivity {

    List<Categorydata> categorydatalist;
    ActivityUpdatetruyenBinding binding;
    Spinner listtype;
    private String encodedImage;
    int Matl=0;
    Product productupdate;
    Updatetruyen updatetruyen;
    CategoryselectAdapter categoryselectAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityUpdatetruyenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        if(Info.darkmode){
            binding.getRoot().setBackgroundColor(Color.BLACK);
        }
        productupdate=(Product)getIntent().getSerializableExtra("product");
        binding.titletruyen.setText(productupdate.getName());
        binding.mota.setText(productupdate.getDescription());
        binding.tacgia.setText(productupdate.getTacgia());
        encodedImage=productupdate.getImg();
        binding.imgtruyen.setImageBitmap(getImage(productupdate.getImg()));
        binding.addimg.setOnClickListener(v->{
            Intent intent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            pickImage.launch(intent);
        });
        updatetruyen=this;
       getCategories();
        binding.loai.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Matl=Integer.valueOf(categorydatalist.get(i).getId()) ;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
//        binding.buttonupremove.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                  ApiService.apiService.removetruyen(productupdate.getId()).enqueue(new Callback<String>() {
//                        @Override
//                        public void onResponse(Call<String> call, Response<String> response) {
//                            String rep=response.body();
//                            Toast.makeText(getApplication(), rep,Toast.LENGTH_LONG).show();
//                            Intent intent=new Intent(Updatetruyen.this,Home.class);
//                            startActivity(intent);
//                        }  @Override
//                    public void onFailure(Call<String> call, Throwable t) {
//                        Toast.makeText(getApplication(), "Xoa that bai",Toast.LENGTH_LONG).show();
//                    }
//                });
//            }
//        });
        binding.backupdate.setOnClickListener(v->onBackPressed());
        binding.buttonupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isValidSignUpDetail()){
                    getupdate();
                }
            }


        });
    }
    private void getupdate(){
       String name=binding.titletruyen.getText().toString();
        String tacgia=binding.tacgia.getText().toString();
        String img=encodedImage;
        String maTL=String.valueOf(Matl);
        String description=binding.mota.getText().toString();
        int id=productupdate.getId();
//        ApiService.apiService.update(id,name,tacgia,productupdate.getNgayxuat(),img,maTL,description).enqueue(new Callback<Product>() {
//            @Override
//            public void onResponse(Call<Product> call, Response<Product> response) {
//
//
//
//                Product product=response.body();
//                Toast.makeText(getApplication(), "update thanh cong",Toast.LENGTH_LONG).show();
//                Intent intent=new Intent(Updatetruyen.this,Chapter.class);
//                intent.putExtra("product",product);
//                startActivity(intent);
//            }
//            @Override
//            public void onFailure(Call<Product> call, Throwable t) {
//                Toast.makeText(getApplication(), "update that bai",Toast.LENGTH_LONG).show();
//            }
//        });
        Call<Product> productCall= ApiClient.getUserServicd().update(id,name,tacgia,productupdate.getNgayxuat(),img,maTL,description);
        productCall.enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                Toast.makeText(getApplication(), "thanh cong",Toast.LENGTH_LONG).show();
                Product product=response.body();
                Toast.makeText(getApplication(), "update thanh cong",Toast.LENGTH_LONG).show();
                Intent intent=new Intent(Updatetruyen.this,Chapter.class);
                intent.putExtra("product",product);
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                Toast.makeText(getApplication(), "chay that bai",Toast.LENGTH_LONG).show();
            }
        });
    }
    private final ActivityResultLauncher<Intent> pickImage=registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), result->{
                if(result.getResultCode()==RESULT_OK){
                    if(result.getData()!=null){
                        Uri imageUri=result.getData().getData();
                        try {
                            InputStream inputStream=getContentResolver().openInputStream(imageUri);
                            Bitmap bitmap= BitmapFactory.decodeStream(inputStream);
                            binding.imgtruyen.setImageBitmap(bitmap);
                            binding.textAddImage.setVisibility(View.GONE);
                            encodedImage=encodeImage(bitmap);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
    );

    private String encodeImage(Bitmap bitmap){
        int previewWidth=150;
        int previewHeight=bitmap.getHeight() * previewWidth / bitmap.getWidth();
        Bitmap previewBitmap=Bitmap.createScaledBitmap(bitmap,previewWidth,previewHeight,false);
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        previewBitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
        byte[] bytes=byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(bytes,Base64.DEFAULT);

    }
    private void getCategories(){

        ApiService.apiService.getcate().enqueue(new Callback<List<Categorydata>>() {
            @Override
            public void onResponse(Call<List<Categorydata>> call, Response<List<Categorydata>> response) {

                categorydatalist=response.body();
                categoryselectAdapter=new CategoryselectAdapter(Updatetruyen.this, R.layout.item_selected,categorydatalist);
                binding.loai.setAdapter(categoryselectAdapter);



            }
            @Override
            public void onFailure(Call<List<Categorydata>> call, Throwable t) {
                Toast.makeText(getApplication(), "chay that bai",Toast.LENGTH_LONG).show();
            }
        });
    }
    private Boolean isValidSignUpDetail(){


        if(encodedImage==null){
            showToast("Select profile image");
            return false;
        }else if(binding.titletruyen.getText().toString().trim().isEmpty()){
            showToast("enter title");
            return false;
        }

        else if(binding.mota.getText().toString().trim().isEmpty()){
            showToast("enter description");
            return false;}

        else if(Matl==0){
            showToast("select type");
            return false;
        }
        else{
            return true;
        }
    }
    private void showToast(String message){
        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
    }
    private Bitmap getImage(String encodeImage){
        byte[] bytes= Base64.decode(encodeImage,Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes,0,bytes.length);
    }
}