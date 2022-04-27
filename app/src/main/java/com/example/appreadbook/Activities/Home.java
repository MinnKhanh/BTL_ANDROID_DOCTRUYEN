package com.example.appreadbook.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appreadbook.API.ApiService;
import com.example.appreadbook.Adapters.IntroAdapter;
import com.example.appreadbook.Adapters.homeAdapter;
import com.example.appreadbook.Listener.HomeListener;
import com.example.appreadbook.Model.Info;
import com.example.appreadbook.Model.PreferenceManager;
import com.example.appreadbook.Model.Product;
import com.example.appreadbook.R;
import com.example.appreadbook.databinding.ActivityHomeBinding;
import com.google.android.material.navigation.NavigationView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Home extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener , HomeListener {
    GridView gridView;
    RecyclerView NewRecycler;
    RecyclerView.Adapter adapter;
    List<Product> productList;
    List<Product> NewList;
    homeAdapter homeadapter;
    SearchView searchView;
    TextView nameUser;
    ActivityHomeBinding binding;
    ProgressDialog progress;
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    NavigationView navigationView;
    PreferenceManager preferenceManager;
    EditText input;
    Home productBytypeActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        preferenceManager=new PreferenceManager(getApplicationContext());
       // preferenceManager.clear();
       // Toast.makeText(getApplication(), "Tai khoan la: "+Info.taikhoan+"  Mat khau la: "+Info.matkhau,Toast.LENGTH_LONG).show();
        productBytypeActivity=this;
        input=findViewById(R.id.Searchtype);
        gridView=findViewById(R.id.listproducts);
        gridView.setNumColumns(Info.numbercol);
        toolbar=findViewById(R.id.toolbar);
        drawerLayout=findViewById(R.id.drawview);

        nameUser=(TextView)findViewById(R.id.nameUsermenunav);
       // binding.Searchtype.setFocusable(false);
        //Toast.makeText(this,nameUser.getText().toString(),Toast.LENGTH_LONG).show();

        if(Info.darkmode==true){
          binding.getRoot().setBackgroundColor(Color.BLACK);
          binding.textlist.setTextColor(Color.WHITE);
            binding.textnew.setTextColor(Color.WHITE);
            binding.scollnew.setBackgroundColor(Color.BLACK);
        }
        getList();
        progress = new ProgressDialog(this);
        progress.setTitle("Loading...");
        progress.setCancelable(false);
        progress.show();

        setlistener();

        input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                homeadapter.getFilter().filter(input.getText().toString());
            }
        });
        NewRecycler = findViewById(R.id.my_recycler);
        NewRecycler.setHasFixedSize(true);
        NewRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        phoneRecycler();
    }
    private void setlistener(){
        binding.Home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Home.this,Home.class);
                startActivity(intent);
                finish();
            }
        });
        binding.category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Home.this,SliderCategory.class);
                startActivity(intent);
                finish();
            }
        });
        binding.bookmask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Home.this,BookMarkActivity.class);
                startActivity(intent);
                finish();
            }
        });

        binding.menumyinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                binding.drawview.openDrawer(GravityCompat.START);
            }
        });
        binding.navigation.setNavigationItemSelectedListener(this);
    }
    private void actionToolbar(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open_drawer,R.string.close_drawer);
//        drawerLayout.addDrawerListener(toggle);

    }
    private void getList(){
        ApiService.apiService.gettruyen().enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {

                productList= response.body();
                homeadapter=new homeAdapter(productBytypeActivity,R.layout.item_truyen,productList,Home.this);
                gridView.setAdapter(homeadapter);
                progress.dismiss();
//                gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
////                        Info.truyendangdoc=productList.get(i);
////                        Intent intent=new Intent(Home.this,Chapter.class);
////                        intent.putExtra("product",productList.get(i));
////                        startActivity(intent);
//                    }
//                });

            }
            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                progress.dismiss();
                Toast.makeText(getApplication(), "Thất bại", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void OnclickItemHome (Product product) {
        Intent intent = new Intent(Home.this,Chapter.class);
        intent.putExtra("product",product);
        startActivity(intent);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        Intent intent;
        switch ( id ) {
            case  R.id.navcategory:
                intent=new Intent(Home.this,Categories.class);
                startActivity(intent);
                break;
            case  R.id.navbookmask:
                intent=new Intent(Home.this,BookMarkActivity.class);
                startActivity(intent);
                break;
            case  R.id.Addtruyen:
                if(Info.permission==1) {
                    intent = new Intent(Home.this, AddproductActivity.class);
                    startActivity(intent);
                }else{
                        Toast.makeText(Home.this,"Bạn không có quyền sửa đổi",Toast.LENGTH_LONG).show();
                    }
                break;
            case  R.id.changeacount:
                intent=new Intent(Home.this,DangKy.class);
                Bundle bun=new Bundle();
                bun.putString("taikhoan",Info.taikhoan);
                bun.putString("matkhau",Info.matkhau);
                bun.putInt("ischange",1);
                bun.putInt("permission",Info.permission);
                bun.putInt("isadd",0);
                intent.putExtras(bun);
                startActivity(intent);
                break;
            case  R.id.addaccount:
                if(Info.permission==1){
                intent=new Intent(Home.this,DangKy.class);
                Bundle bun1=new Bundle();
                bun1.putString("taikhoan",Info.taikhoan);
                bun1.putString("matkhau",Info.matkhau);
                bun1.putInt("ischange",0);
                bun1.putInt("permission",Info.permission);
                bun1.putInt("isadd",1);
                intent.putExtras(bun1);
                startActivity(intent);}
                else{
                    Toast.makeText(Home.this,"Bạn không có quyền sửa đổi",Toast.LENGTH_LONG).show();
                }
                break;
            case  R.id.Setting:
                Intent i = new Intent(Home.this, SettingsActivity.class);
                startActivity(i);
                break;
            case  R.id.signout:
                preferenceManager.clear();
                startActivity(new Intent(getApplicationContext(),DangNhap.class));
                finish();
                break;
            default:
                break;
        }
        return false;
    }
    private void phoneRecycler() {



        ApiService.apiService.getIntro().enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {

                NewList= response.body();
                adapter = new IntroAdapter(NewList,Home.this);
                NewRecycler.setAdapter(adapter);


            }
            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {

                Toast.makeText(getApplication(), "Thất bại", Toast.LENGTH_LONG).show();
            }
        });

    }




}