package com.example.appreadbook.Activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appreadbook.Model.PreferenceManager;
import com.example.appreadbook.R;
import com.makeramen.roundedimageview.RoundedImageView;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class NotificationActivity extends AppCompatActivity {
EditText name;
EditText addr;
EditText date;
FrameLayout frameLayoutimg;
String gioitinh="nam";
String encodedImage;
TextView textaddimg;
RadioButton nam;
RadioButton nu;
    PreferenceManager preferenceManager;
RoundedImageView imguser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
//        preferenceManager=new PreferenceManager(getApplicationContext());
//        preferenceManager.clear();
//        name=findViewById(R.id.inputnameaccount);
//        addr=findViewById(R.id.inputlocal);
//        date=findViewById(R.id.date);
//        nam=findViewById(R.id.nam);
//        nu=findViewById(R.id.nu);
//        frameLayoutimg=findViewById(R.id.limguser);
//        frameLayoutimg.setOnClickListener(v->{
//            Intent intent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//            pickImage.launch(intent);
//        });
//        date.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//            chonngay();
//            }
//        });
    }
    private void chonngay(){
        final Calendar calendar=Calendar.getInstance();
        int ngay=calendar.get(Calendar.DATE);
        int thang=calendar.get(Calendar.MONTH);
        int nam=calendar.get(Calendar.YEAR);
        DatePickerDialog datePickerDialog=new DatePickerDialog(NotificationActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                calendar.set(i,i1,i2);
                SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd/mm/yy");
                date.setText(simpleDateFormat.format(calendar.getTime()));
            }
        },2017,01,01);
    datePickerDialog.show();
    }
    public boolean checkfild(){
        if(name.getText().toString().trim().length()==0){
            return false;
        }
        if(addr.getText().toString().trim().length()==0){
            return false;
        }
        if(date.getText().toString().trim().length()==0){
            return false;
        }
        if(encodedImage.trim().length()==0){
            return false;
        }
        return true;
    }
    private final ActivityResultLauncher<Intent> pickImage=registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), result->{
                if(result.getResultCode()==RESULT_OK){
                    if(result.getData()!=null){
                        Uri imageUri=result.getData().getData();
                        try {
                            InputStream inputStream=getContentResolver().openInputStream(imageUri);
                            Bitmap bitmap= BitmapFactory.decodeStream(inputStream);
                            imguser.setImageBitmap(bitmap);
                            textaddimg.setVisibility(View.GONE);
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
}