package com.example.appreadbook.Activities;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import com.example.appreadbook.API.ApiService;
import com.example.appreadbook.Adapters.ProductTypeAdapter;
import com.example.appreadbook.Listener.ProductTypeListener;
import com.example.appreadbook.Model.Categorydata;
import com.example.appreadbook.Model.Info;
import com.example.appreadbook.Model.Product;
import com.example.appreadbook.R;
import com.example.appreadbook.databinding.ActivityProductBytypeBinding;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductBytypeActivity extends AppCompatActivity implements ProductTypeListener {
    GridView gridView;
    List<Product> productList;
    ProductTypeAdapter productTypeAdapter;
    Categorydata category;
    SearchView searchView;
    ActivityProductBytypeBinding binding;
    ProgressDialog progress;
    private EditText input;
    ProductBytypeActivity productBytypeActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityProductBytypeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        productBytypeActivity=this;
        input=findViewById(R.id.Searchtype);
        gridView=findViewById(R.id.listproducts);
        gridView.setNumColumns(Info.numbercol);
        getinfo();
        getList();
        progress = new ProgressDialog(this);
        progress.setTitle("Loading");
        progress.setCancelable(false);
        progress.show();
        if(Info.darkmode==true){
            binding.getRoot().setBackgroundColor(Color.BLACK);
        }
        binding.backbytype.setOnClickListener(v->{
            Intent i=new Intent(ProductBytypeActivity.this,SliderCategory.class);
            startActivity(i);
            finish();
        });
        input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                productTypeAdapter.getFilter().filter(input.getText().toString());
            }
        });

    }
    private void getinfo(){
        category=(Categorydata) getIntent().getSerializableExtra("Loai");
    }

    private void getList(){
        ApiService.apiService.getloaitruyen(category.getId()).enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {

                productList= response.body();
                productTypeAdapter=new ProductTypeAdapter(productBytypeActivity,R.layout.item_truyen,productList,ProductBytypeActivity.this);
                gridView.setAdapter(productTypeAdapter);
                progress.dismiss();
//                gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                        Intent intent=new Intent(ProductBytypeActivity.this,Chapter.class);
//                        intent.putExtra("product",productList.get(i));
//                        startActivity(intent);
//                    }
//                });

            }
            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Toast.makeText(getApplication(), "chay that bai",Toast.LENGTH_LONG).show();
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search,menu);
        SearchManager searchManager=(SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView= (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                productTypeAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                productTypeAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }

    @Override
    public void ProductTypeOnClick(Product product) {
        Intent intent = new Intent(ProductBytypeActivity.this,Chapter.class);
        intent.putExtra("product",product);
        startActivity(intent);
    }
}