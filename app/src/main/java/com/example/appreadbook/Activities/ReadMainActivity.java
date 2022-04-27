package com.example.appreadbook.Activities;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appreadbook.Model.Chap;
import com.example.appreadbook.Model.Info;
import com.example.appreadbook.R;
import com.example.appreadbook.databinding.ActivityReadMainBinding;

public class ReadMainActivity extends AppCompatActivity {
    ActivityReadMainBinding binding;
    Chap chap;
    Typeface tf;
    int color=Info.textColor;
    int bgcolor=Info.bgtextColor;
    int textsize=Info.texSize;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityReadMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        chap=(Chap)getIntent().getSerializableExtra("chap");
        String ten=(String)getIntent().getStringExtra("ten");
        binding.noidung.setText(chap.getContent());
        binding.tieude.setText(ten);
        binding.tenchap.setText("Chap "+chap.getChapso());
        binding.backchap.setOnClickListener(v->onBackPressed());
        if(Info.darkread==true){
            binding.getRoot().setBackgroundColor(Color.BLACK);
            binding.noidung.setBackgroundColor(Color.BLACK);
            binding.noidung.setTextColor(Color.WHITE);
        }

        binding.optiontext.setOnClickListener(v->{
            PopupMenu popupMenu=new PopupMenu(this,binding.optiontext);
            popupMenu.inflate(R.menu.menu_text);
            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem menuItem) {
                    switch (menuItem.getItemId()) {
                        case R.id.sizeColor:
                            opentDialogSizeColor();
                            break;
                        case R.id.font:
                            opentDialogFont();
                            break;
                        case R.id.backcolor:
                           // Toast.makeText(ReadMainActivity.this,"chay duoc",Toast.LENGTH_LONG).show();
                            opentDialogbgcolor();
                            break;
                       default:
                            break;
                    }
                    return true;
                }
            });
            popupMenu.show();
        });

    }
    private void opentDialogSizeColor(){
        final Dialog dialog=new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.fragment_size_text);
        Window window=dialog.getWindow();
        if(window==null){
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams windowatrribute=window.getAttributes();
        windowatrribute.gravity= Gravity.CENTER;
        window.setAttributes(windowatrribute);
        dialog.setCancelable(true);
        RadioGroup rdg;
        TextView textsizes;
        SeekBar size;
        Button yes;
        Button no;

        rdg=dialog.findViewById(R.id.color);
        textsizes=dialog.findViewById(R.id.textsize);
        size=dialog.findViewById(R.id.size);
        yes=dialog.findViewById(R.id.yes);
        no=dialog.findViewById(R.id.no);
        rdg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch(i){
                    case R.id.green:
                        binding.noidung.setTextColor(Color.GREEN);
                        color=Color.GREEN;
                        break;
                    case R.id.blue:
                        binding.noidung.setTextColor(Color.BLUE);
                        color=Color.BLUE;
                        break;
                    case R.id.black:
                        binding.noidung.setTextColor(Color.BLACK);
                        color=Color.BLACK;
                        break;
                    case R.id.white:
                        binding.noidung.setTextColor(Color.WHITE);
                        color=Color.WHITE;
                        break;
                    default:
                        break;
                }
            }
        });
        size.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                binding.noidung.setTextSize(i);
                textsizes.setText(String.valueOf(i));
                textsize=i;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Info.textColor=color;
                Info.texSize=textsize;
                dialog.dismiss();
            }
        });
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.noidung.setTextColor(Info.textColor);
                binding.noidung.setTextSize(Info.texSize);
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    private void opentDialogFont(){
        final Dialog dialog=new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.fragment_font);
        Window window=dialog.getWindow();
        if(window==null){
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
        WindowManager.LayoutParams windowatrribute=window.getAttributes();
        windowatrribute.gravity= Gravity.CENTER;
        window.setAttributes(windowatrribute);
        dialog.setCancelable(true);
        RadioGroup grfont;
        Button yes;
        Button no;
        yes=dialog.findViewById(R.id.yes);
        no=dialog.findViewById(R.id.no);
        grfont=dialog.findViewById(R.id.grfont);
        grfont.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                switch(i){
                    case R.id.alex:
                        Toast.makeText(ReadMainActivity.this,"alex",Toast.LENGTH_LONG).show();
                        tf=Typeface.createFromAsset(getAssets(),"fonts/AlexBrush-Regular.ttf");
                        break;
                    case R.id.arizo:
                        Toast.makeText(ReadMainActivity.this,"alex",Toast.LENGTH_LONG).show();
                        tf=Typeface.createFromAsset(getAssets(),"fonts/Arizonia-Regular.ttf");
                        break;
                    case R.id.chuck:
                        Toast.makeText(ReadMainActivity.this,"chuck",Toast.LENGTH_LONG).show();
                        tf=Typeface.createFromAsset(getAssets(),"fonts/ChunkFive-Regular.otf");
                        break;
                    case R.id.lobster:
                        Toast.makeText(ReadMainActivity.this,"lobster",Toast.LENGTH_LONG).show();
                        tf=Typeface.createFromAsset(getAssets(),"fonts/Lobster_1.3.otf");
                        break;
                    case R.id.pacifi:
                        Toast.makeText(ReadMainActivity.this,"pacifi",Toast.LENGTH_LONG).show();
                        tf=Typeface.createFromAsset(getAssets(),"fonts/Pacifico.ttf");
                        break;
                    case R.id.roboto:
                        Toast.makeText(ReadMainActivity.this,"roboto",Toast.LENGTH_LONG).show();
                        tf=Typeface.createFromAsset(getAssets(),"fonts/Roboto-ThinItalic.ttf");
                        break;
                    default:
                        break;
                }
            }
        });
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                binding.noidung.setTypeface(tf);
                dialog.dismiss();
            }
        });
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog.dismiss();
            }
        });

        dialog.show();
    }
    private void opentDialogbgcolor(){
        final Dialog dialog=new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.fragment_color_bg);
        Window window=dialog.getWindow();
        if(window==null){
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams windowatrribute=window.getAttributes();
        windowatrribute.gravity= Gravity.CENTER;
        window.setAttributes(windowatrribute);
        dialog.setCancelable(true);
        RadioGroup bggrcolor;
        Button bgyes;
        Button bgno;

        bggrcolor=dialog.findViewById(R.id.bgcolor);
        bgyes=dialog.findViewById(R.id.bgyes);
        bgno=dialog.findViewById(R.id.bgno);
        bggrcolor.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch(i){
                    case R.id.bggreen:
                        binding.scolltext.setBackgroundColor(Color.GREEN);
                        bgcolor=Color.GREEN;
                        break;
                    case R.id.bgblue:
                        binding.scolltext.setBackgroundColor(Color.BLUE);
                        bgcolor=Color.BLUE;
                        break;
                    case R.id.bgblack:
                        binding.scolltext.setBackgroundColor(Color.BLACK);
                        bgcolor=Color.BLACK;
                        break;
                    case R.id.bgwhite:
                        binding.scolltext.setBackgroundColor(Color.WHITE);
                        bgcolor=Color.WHITE;
                        break;
                    default:
                        break;
                }
            }
        });

        bgyes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Info.bgtextColor=bgcolor;
                dialog.dismiss();
            }
        });
        bgno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.scolltext.setBackgroundColor(Info.textColor);
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
