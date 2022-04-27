package com.example.appreadbook.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appreadbook.API.ApiService;
import com.example.appreadbook.Model.Constants;
import com.example.appreadbook.Model.Info;
import com.example.appreadbook.Model.PreferenceManager;
import com.example.appreadbook.Model.TaiKhoan;
import com.example.appreadbook.R;
import com.example.appreadbook.databinding.ActivityDangKyBinding;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DangKy extends AppCompatActivity {
    String taikhoan;
    String matkhau;
    int permission;
    int ischange=0;
    int isadd;
    EditText edt_TaiKhoan;
    EditText edt_MatKhau;
    EditText edt_XacNhan;
    CheckBox ShowPass;
    private PreferenceManager preferenceManager;
    Button btn_DangKy;
    ArrayAdapter arraypes;
    ActivityDangKyBinding binding;
    Spinner pers;
    int valuepers=0;
    ArrayList<String> listrpes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityDangKyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        preferenceManager=new PreferenceManager(getApplicationContext());
       // preferenceManager.clear();
        edt_TaiKhoan = (EditText) findViewById(R.id.edt_TenTaiKhoan);
        edt_MatKhau = (EditText) findViewById(R.id.edt_MatKhau);
        edt_XacNhan = (EditText) findViewById(R.id.edt_XacNhanMatKhau);
        ShowPass = (CheckBox) findViewById(R.id.checkbox_HienThiMatKhau);
        btn_DangKy = (Button) findViewById(R.id.btn_DangKy);
        pers=findViewById(R.id.listpers);
        checkupdate();
        listrpes=new ArrayList<>();
        listrpes.add("Người Dùng");
        listrpes.add("Quản lý");
        arraypes=new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item,listrpes);
        pers.setAdapter(arraypes);

        pers.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                valuepers=i;
                Toast.makeText(DangKy.this,String.valueOf(i),Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
//        //check changaccount
        if(getIntent().getExtras()!=null){
            Bundle bundle=getIntent().getExtras();
            taikhoan=bundle.getString("taikhoan");
            matkhau=bundle.getString("matkhau");
            permission=bundle.getInt("permission");
            ischange=bundle.getInt("ischange");
            isadd=bundle.getInt("isadd");
            if(ischange==1){
                binding.edtTenTaiKhoan.setText(taikhoan);
                binding.edtMatKhau.setText(matkhau);
                binding.btnDangKy.setText("Update");
            }
            if(permission==1){
                binding.imagepers.setVisibility(View.VISIBLE);
                binding.listpers.setVisibility(View.VISIBLE);
            }
        }
        ShowPass.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b)
                {
                    edt_MatKhau.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    edt_XacNhan.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                else
                {
                    edt_MatKhau.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    edt_XacNhan.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

        // Khi click vào nút đăng ký:
        btn_DangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String TenDangNhap = edt_TaiKhoan.getText().toString();
                String MatKhau = edt_MatKhau.getText().toString();
                String XacNhanMatKhau = edt_XacNhan.getText().toString();
                if(TenDangNhap.length() == 0 || MatKhau.length() == 0 || XacNhanMatKhau.length() == 0)
                {
                    Toast.makeText(DangKy.this, "Cần phải điền đầy đủ thông tin để có thế đăng ký tài khoản", Toast.LENGTH_SHORT).show();
                    edt_TaiKhoan.requestFocus();
                    return;
                }
                else
                {
                    // Nếu 2 mật khẩu đã đồng nhất
                    if(MatKhau.equals(XacNhanMatKhau) == true && MatKhau.length() >= 6) {

                        if(ischange==1){

                            String newtaikhoan=binding.edtTenTaiKhoan.getText().toString();
                            String newmatkhau=binding.edtMatKhau.getText().toString();
                            ApiService.apiService.changeaccount(taikhoan,matkhau,valuepers,newtaikhoan,newmatkhau).enqueue(new Callback<TaiKhoan>() {
                                @Override
                                public void onResponse(Call<TaiKhoan> call, Response<TaiKhoan> response) {

                                    TaiKhoan taiKhoan = response.body();
                                    if (taiKhoan.getTaikhoan().trim().length() == 0) {
                                        Toast.makeText(getApplication(), "Cập nhật thất bại", Toast.LENGTH_LONG).show();
                                    } else {
                                        Info.taikhoan = taiKhoan.getTaikhoan();
                                        Info.matkhau = taiKhoan.getMatkhau();
                                        Info.permission=taiKhoan.getPers();
                                        preferenceManager.clear();
                                        preferenceManager.putBoolean(Constants.KEY_IS_SIGNED_IN,true);
                                        preferenceManager.putString(Constants.KEY_TAIKHOAN,Info.taikhoan);
                                        preferenceManager.putString(Constants.KEY_PASS,Info.matkhau);
                                        preferenceManager.putString(Constants.KEY_PERS,String.valueOf(Info.permission));
                                        Toast.makeText(getApplication(), "Cập nhật thành công", Toast.LENGTH_LONG).show();
                                        Intent home = new Intent(DangKy.this, Home.class);
                                        startActivity(home);
                                        finish();
                                    }


                                }

                                @Override
                                public void onFailure(Call<TaiKhoan> call, Throwable t) {
                                    Toast.makeText(getApplication(), "Cập nhật thất bại", Toast.LENGTH_LONG).show();
                                }
                            });
                        }else{

                            ApiService.apiService.dangky(edt_TaiKhoan.getText().toString(), edt_MatKhau.getText().toString(),valuepers).enqueue(new Callback<TaiKhoan>() {
                                @Override
                                public void onResponse(Call<TaiKhoan> call, Response<TaiKhoan> response) {

                                    TaiKhoan taiKhoan = response.body();
                                    if (taiKhoan.getTaikhoan().trim().length() == 0) {
                                        Toast.makeText(getApplication(), "Đăng kí thất bại", Toast.LENGTH_LONG).show();
                                    } else {
                                        Toast.makeText(getApplication(), "Đang kí thành công", Toast.LENGTH_LONG).show();
                                        Intent home = new Intent(DangKy.this, DangNhap.class);
                                       startActivity(home);


                                    }


                                }

                                @Override
                                public void onFailure(Call<TaiKhoan> call, Throwable t) {
                                    Toast.makeText(getApplication(), "Thất bại", Toast.LENGTH_LONG).show();
                                }
                            });
                        }



                    }
                    // Nếu 2 mật khẩu không đồng nhất
                    else
                    {
                        Toast.makeText(DangKy.this, "Mật khẩu phải nhiều hơn 6 kí tự và 2 mật khẩu phải đồng nhất", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
            }
        });
    }
    private void checkupdate(){

        if(Info.taikhoan.trim().length()!=0 &&Info.permission==1){
            binding.imagepers.setVisibility(View.VISIBLE);
          //  binding.listpers.setVisibility(View.VISIBLE);
        }

    }
}

