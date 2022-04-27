package com.example.appreadbook.Activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Base64;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appreadbook.API.ApiService;
import com.example.appreadbook.Adapters.ChapAdapter;
import com.example.appreadbook.Listener.ChapListener;
import com.example.appreadbook.Model.Chap;
import com.example.appreadbook.Model.Info;
import com.example.appreadbook.Model.Product;
import com.example.appreadbook.R;
import com.example.appreadbook.databinding.ActivityChapterBinding;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Chapter extends AppCompatActivity implements ChapListener {
    ActivityChapterBinding binding;
    Chapter mainchap;
    List<Chap> chap=null;
    ChapAdapter chapAdapter;
    AlertDialog.Builder builder;
    Product truyen;
    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityChapterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        truyen=(Product)getIntent().getSerializableExtra("product");
        mainchap=this;
        getListchap();
        if(Info.darkmode==true){
            binding.getRoot().setBackgroundColor(Color.BLACK);
        }
        binding.imgAnh.setImageBitmap(getUserImage(truyen.getImg()));
        binding.tvtentruyen.setText(truyen.getName());

        binding.Tacgia.setText("Tác giả: "+truyen.getTacgia());
        binding.ngayxuat.setText("Ngày xuất: "+truyen.getNgayxuat());
        if(Info.darkmode==true){
            binding.tvtentruyen.setTextColor(Color.WHITE);
            binding.Tacgia.setTextColor(Color.WHITE);
            binding.ngayxuat.setTextColor(Color.WHITE);
        }

        binding.suatruyen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Chapter.this,Updatetruyen.class);
                intent.putExtra("product",truyen);
                startActivity(intent);
            }
        });
        binding.Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i=new Intent(Chapter.this,Home.class);
                startActivity(i);
                finish();
            }
        });
        binding.read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(chap!=null){
                    OnClikchap(chap.get(0));
                }

            }
        });
    }

    private void getListchap(){
        ApiService.apiService.getchap( truyen.getId()).enqueue(new Callback<List<Chap>>() {
            @Override
            public void onResponse(Call<List<Chap>> call, Response<List<Chap>> response) {

                chap= response.body();
                chapAdapter=new ChapAdapter(chap,mainchap,mainchap,truyen);
                binding.listproduct.setAdapter(chapAdapter);
               // Toast.makeText(getApplication(), "chay thanh cong", Toast.LENGTH_LONG).show();

            }
            @Override
            public void onFailure(Call<List<Chap>> call, Throwable t) {
               // Toast.makeText(getApplication(), "chay that bai", Toast.LENGTH_LONG).show();
            }
        });
        binding.optiondetailchap.setOnClickListener(v->{
            PopupMenu popupMenu=new PopupMenu(this,binding.optiondetailchap);
            popupMenu.inflate(R.menu.menu_update_truyen);
            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem menuItem) {
                    switch (menuItem.getItemId()) {
                        case R.id.updatebook:
                            if(Info.permission==1){
                            Intent intent=new Intent(Chapter.this,Updatetruyen.class);
                            intent.putExtra("product",truyen);
                            startActivity(intent);
                            }else{
                                Toast.makeText(Chapter.this,"Bạn không có quyền sửa đổi",Toast.LENGTH_LONG).show();
                            }
                            break;
                        case R.id.removebook:
                            if(Info.permission==1){
                            ApiService.apiService.removetruyen(truyen.getId()).enqueue(new Callback<String>() {
                                @Override
                                public void onResponse(Call<String> call, Response<String> response) {
                                    String rep=response.body();
                                    Toast.makeText(getApplication(), rep,Toast.LENGTH_LONG).show();
                                    Intent intent=new Intent(Chapter.this,Home.class);
                                    startActivity(intent);
                                }
                                @Override
                                public void onFailure(Call<String> call, Throwable t) {
                                    Toast.makeText(getApplication(), "Xóa thất bại",Toast.LENGTH_LONG).show();
                                }
                            });
                            }else{
                                Showmessage("Bạn không được phép thay đổi");
                            }

                            break;
                        case R.id.addchap:
                            if(Info.permission==1){
                                Intent intent1=new Intent(Chapter.this,AddChapActivity.class);
                                intent1.putExtra("product",truyen);
                                startActivity(intent1);
                            }else{
                                Showmessage("Bạn không được phép thay đổi");
                            }

                            break;
                        case R.id.addtofavorite:
                            ApiService.apiService.addfavorite(Info.taikhoan,Info.matkhau,truyen.getId()).enqueue(new Callback<Integer>() {
                                @Override
                                public void onResponse(Call<Integer> call, Response<Integer> response) {
                                    int check=response.body();
                                    if(check==1){
                                        Toast.makeText(getApplication(), "Đã thêm vào danh sách yêu thích",Toast.LENGTH_LONG).show();
                                    }else{
                                        Toast.makeText(getApplication(), "Đã có trong danh sách yêu thích",Toast.LENGTH_LONG).show();
                                    }
                                }
                                @Override
                                public void onFailure(Call<Integer> call, Throwable t) {
                                    Toast.makeText(getApplication(), "Xóa thất bại",Toast.LENGTH_LONG).show();
                                }
                            });

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
    private Bitmap getUserImage(String encodeImage){
        byte[] bytes= Base64.decode(encodeImage,Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes,0,bytes.length);
    }
    @Override
    public void OnClikchap (Chap chap) {
        Intent intent=new Intent(Chapter.this,ReadMainActivity.class);
        intent.putExtra("chap",chap);
        intent.putExtra("ten",truyen.getName());
        startActivity(intent);
    }

    @Override
    public void UpdateChap(Chap chap,Product truyen) {
        if(Info.permission==1){
        Intent intent=new Intent(Chapter.this,AddChapActivity.class);
        intent.putExtra("chap",chap);
        intent.putExtra("truyen",truyen);
        startActivity(intent);
        }else{
            Toast.makeText(Chapter.this,"Ban không có quyền sửa đổi",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void RemoveChap(Chap chap) {
            if(Info.permission==1){
        ApiService.apiService.removechap( chap.getChapso(),chap.getMatruyen()).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                String rep= response.body();
               // Toast.makeText(getApplication(), rep, Toast.LENGTH_LONG).show();

            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(getApplication(), "Thất bại", Toast.LENGTH_LONG).show();
            }
        }); }else{
                Toast.makeText(Chapter.this,"Ban không có quyền sửa đổi",Toast.LENGTH_LONG).show();
            }
    }
    public void Showmessage(String a){
         Toast.makeText(Chapter.this,a,Toast.LENGTH_LONG).show();
    }
}