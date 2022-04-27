package com.example.appreadbook.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appreadbook.API.ApiService;
import com.example.appreadbook.Model.Constants;
import com.example.appreadbook.Model.Info;
import com.example.appreadbook.Model.PreferenceManager;
import com.example.appreadbook.Model.TaiKhoan;
import com.example.appreadbook.R;
import com.example.appreadbook.databinding.ActivityDangNhapBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DangNhap extends AppCompatActivity {


    TextView txt_DangKy;
    EditText edt_tentaikhoan;
    EditText edt_matkhau;
    Button btn_DangNhap;
    ActivityDangNhapBinding binding;
    private PreferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding= ActivityDangNhapBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        preferenceManager=new PreferenceManager(getApplicationContext());
        //preferenceManager.clear();
        btn_DangNhap = (Button) findViewById(R.id.btn_DangNhap);
        txt_DangKy = (TextView) findViewById(R.id.txt_DangKy);
        edt_tentaikhoan = (EditText) findViewById(R.id.edt_account);
        edt_matkhau = (EditText) findViewById(R.id.edt_password);
        if(preferenceManager.getBoolean(Constants.KEY_IS_SIGNED_IN)){
            Info.taikhoan=preferenceManager.getString(Constants.KEY_TAIKHOAN);
            Info.matkhau=preferenceManager.getString(Constants.KEY_PASS);
            Info.permission=Integer.valueOf(preferenceManager.getString(Constants.KEY_PERS));
            Intent intent = new Intent(getApplicationContext(),Home.class);
            startActivity(intent);
            finish();
        }
//Toast.makeText(this,"Tai khoan: "+Info.taikhoan+" Mat khau: "+Info.matkhau,Toast.LENGTH_LONG).show();
        btn_DangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String userName = binding.edtAccount.getText().toString();
                String passWord = binding.edtPassword.getText().toString();
                if(userName.length() == 0 || passWord.length() == 0)
                {
                    Toast.makeText(DangNhap.this, "Cần điền đầy đủ thông tin để đăng nhập", Toast.LENGTH_SHORT).show();
                    binding.edtAccount.requestFocus();
                    return;
                }
                 else
                {
                    ApiService.apiService.dangnhap2(binding.edtAccount.getText().toString() , binding.edtPassword.getText().toString()).enqueue(new Callback<TaiKhoan>() {
                        @Override
                        public void onResponse(Call<TaiKhoan> call, Response<TaiKhoan> response) {

                            TaiKhoan taiKhoan=response.body();
                            if(taiKhoan.getTaikhoan().trim().length()==0){
                                Toast.makeText(getApplication(), "Đang nhập thất bại",Toast.LENGTH_LONG).show();
                            }else{
                                Info.taikhoan=taiKhoan.getTaikhoan();
                                Info.matkhau=taiKhoan.getMatkhau();
                                Info.permission=taiKhoan.getPers();
                                preferenceManager.putBoolean(Constants.KEY_IS_SIGNED_IN,true);
                                preferenceManager.putString(Constants.KEY_TAIKHOAN,Info.taikhoan);
                                preferenceManager.putString(Constants.KEY_PASS,Info.matkhau);
                                preferenceManager.putString(Constants.KEY_PERS,String.valueOf(Info.permission));
                              //  Toast.makeText(getApplication(), String.valueOf(Info.permission),Toast.LENGTH_LONG).show();
                                Intent home = new Intent(DangNhap.this , Home.class);
                               startActivity(home);

                            }




                        }
                        @Override
                        public void onFailure(Call<TaiKhoan> call, Throwable t) {
                            Toast.makeText(getApplication(), "Đang nhập thất bại",Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });

        binding.txtDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Form_DangKy = new Intent(DangNhap.this , DangKy.class);
                startActivity(Form_DangKy);
                Toast.makeText(DangNhap.this, "Bạn đã chuyển sang chế độ đăng ký", Toast.LENGTH_SHORT).show();
            }
        });
    }
}