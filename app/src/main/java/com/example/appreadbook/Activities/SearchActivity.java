package com.example.appreadbook.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appreadbook.API.ApiService;
import com.example.appreadbook.Adapters.CategoryAdapter;
import com.example.appreadbook.Adapters.ResultSearchAdapter;
import com.example.appreadbook.Listener.SearchListener;
import com.example.appreadbook.Model.Categorydata;
import com.example.appreadbook.Model.Info;
import com.example.appreadbook.Model.Product;
import com.example.appreadbook.R;
import com.example.appreadbook.databinding.ActivityCategoriesBinding;
import com.example.appreadbook.databinding.ActivitySearchBinding;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity implements SearchListener{

    private ActivitySearchBinding binding;
    private SearchActivity searchActivity;
    private SearchView searchView;
    ProgressDialog progress;
    private EditText input;
    private ResultSearchAdapter resultSearchAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivitySearchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        progress = new ProgressDialog(this);
        progress.setTitle("Loading");
        progress.setCancelable(false);
        progress.show();
        if(Info.darkmode==true){
            binding.getRoot().setBackgroundColor(Color.BLACK);
        }
input=findViewById(R.id.input);
        searchActivity=this;
        getCategories();
        input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                resultSearchAdapter.getFilter().filter(input.getText().toString());

            }
        });
        binding.backsearch.setOnClickListener(v->onBackPressed());
    }

    private void getCategories(){

        ApiService.apiService.gettruyen().enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {

                List<Product> data= response.body();
                Toast.makeText(binding.getRoot().getContext(), data.get(1).getName(),Toast.LENGTH_LONG).show();
                resultSearchAdapter=new ResultSearchAdapter(data,searchActivity);
                binding.listproduct.setAdapter(resultSearchAdapter);
                Toast.makeText(binding.getRoot().getContext(), "chay thanh cong",Toast.LENGTH_LONG).show();
                progress.dismiss();
              // RecyclerView.ItemDecoration itemDecoration=new DividerItemDecoration(searchActivity,DividerItemDecoration.VERTICAL);
              //  binding.listproduct.addItemDecoration(itemDecoration);
                //binding.CateRecyclerView.setVisibility(View.VISIBLE);

            }
            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                progress.dismiss();
                Toast.makeText(binding.getRoot().getContext(), "chay that bai",Toast.LENGTH_LONG).show();
            }
        });}

    @Override
    public void SearchOnClick(Product product) {
        Intent intent=new Intent(SearchActivity.this,Chapter.class);
        intent.putExtra("product",product);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search,menu);
        SearchManager searchManager=(SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView= (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                resultSearchAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                resultSearchAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }


}