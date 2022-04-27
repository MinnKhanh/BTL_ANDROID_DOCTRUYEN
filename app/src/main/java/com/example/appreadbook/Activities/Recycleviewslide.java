package com.example.appreadbook.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.appreadbook.API.ApiService;
import com.example.appreadbook.Adapters.IntroAdapter;
import com.example.appreadbook.Model.Product;
import com.example.appreadbook.R;
import com.example.appreadbook.databinding.ActivityRecycleviewslideBinding;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Recycleviewslide extends AppCompatActivity {

    RecyclerView phoneRecycler;
    RecyclerView.Adapter adapter;
    ActivityRecycleviewslideBinding binding;
    List<Product> productList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityRecycleviewslideBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //Hooks
        phoneRecycler = findViewById(R.id.my_recycler);
        phoneRecycler.setHasFixedSize(true);
        phoneRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        phoneRecycler();

    }

    private void phoneRecycler() {



        ApiService.apiService.gettruyen().enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {

//                productList= response.body();
//                adapter = new IntroAdapter(productList);
//                phoneRecycler.setAdapter(adapter);


            }
            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {

                Toast.makeText(getApplication(), "chay that bai", Toast.LENGTH_LONG).show();
            }
        });

    }


    public void onphoneListClick(int clickedItemIndex) {


//            Intent mIntent;
//            switch (clickedItemIndex){
//                case 0: //first item in Recycler view
//                    mIntent  = new Intent(FirstActivity.this, samsung.class);
//                    startActivity(mIntent);
//                    break;
//                case 1: //second item in Recycler view
//                    mIntent = new Intent(FirstActivity.this, vivo.class);
//                    startActivity(mIntent);
//                    break;
//                case 2: //third item in Recycler view
//                    mIntent = new Intent(FirstActivity.this, apple.class);
//                    startActivity(mIntent);
//                    break;
        //              case 3: //third item in Recycler view
//                    mIntent = new Intent(FirstActivity.this, realme.class);
//                    startActivity(mIntent);
//                    break;
//                case 4: //third item in Recycler view
//                    mIntent = new Intent(FirstActivity.this, poco.class);
//                    startActivity(mIntent);
//                    break;
//
//        }


    }
}