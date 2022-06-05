package com.example.vocabularyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button btn1, btn4, btn7, btn3;
    private ArrayList<OBJ_Vocabulary> List_Vocabulary = new ArrayList<OBJ_Vocabulary>();
    private long backPressedTime;
    private Toast backToast;
    AdView adView1;
    InterstitialAd interstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adView1 = (AdView) findViewById(R.id.ad_view1);
        MobileAds.initialize(this, "ca-app-pub-1041515516282066~3905521307");

        StartAds();

        interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId("ca-app-pub-1041515516282066/5214366293");
        interstitialAd.loadAd(new AdRequest.Builder().build());
        interstitialAd.setAdListener(new AdListener() {
            public void onAdLoaded() {
                if (interstitialAd.isLoaded()) {
                    interstitialAd.show();
                }
            }
        });


        btn1 = (Button) findViewById(R.id.button2);
        btn4 = (Button) findViewById(R.id.button4);
        btn7 = (Button) findViewById(R.id.button7);
        btn3 = (Button) findViewById(R.id.button3);

        //screen check vocabulary
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(List_Vocabulary.size() > 1) {
                    Intent intent = new Intent(MainActivity.this, Screen_check_vocabulary.class);
                    sendData(intent);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(MainActivity.this, "Vui lòng thêm từ vựng", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //screen learn vocabulary
        btn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Dialog dialog = new Dialog(MainActivity.this);
                dialog.setContentView(R.layout.layout_learn_vocabulary);

                Button btn_learn_normal = (Button) dialog.findViewById(R.id.button8);
                Button btn_learn_write = (Button) dialog.findViewById(R.id.button9);

                btn_learn_write.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        change_screen_1();
                    }
                });

                btn_learn_normal.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        change_screen_2();
                    }
                });
                dialog.show();
            }

            public void change_screen_1(){
                if(List_Vocabulary.size() > 1) {
                    Intent intent = new Intent(MainActivity.this, Learn_Vocabulary.class);
                    sendData(intent);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(MainActivity.this, "Vui lòng thêm từ vựng", Toast.LENGTH_SHORT).show();
                }
            }

            public void change_screen_2(){
                if(List_Vocabulary.size() > 1){
                    Intent intent = new Intent(MainActivity.this, Screen_learn_write.class);
                    sendData(intent);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(MainActivity.this, "Vui lòng thêm từ vựng", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //screen add vocabulary
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Screen_add_vocabulary.class);
                sendData(intent);
                startActivity(intent);
                finish();
            }
        });

        //screen remove vocabulary
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Screen_remove_vocabulary.class);
                sendData(intent);
                startActivity(intent);
                finish();
            }
        });
    }

    public void StartAds(){
        AdRequest adRequest = new AdRequest.Builder().build();
        adView1.loadAd(adRequest);
    }

    @Override
    protected void onResume() {
        super.onResume();

        Intent intent = getIntent();

        Gson gson = new Gson();
        String text = intent.getStringExtra("data_vocabulary");
        if (text != null){
            if (text.equals("None") == true) {
                //
            } else {
                Type obj_type = new TypeToken<ArrayList<OBJ_Vocabulary>>() {}.getType();
                this.List_Vocabulary = gson.fromJson(text, obj_type);
            }
        }

        System.out.println(this.List_Vocabulary.size());
    }

    private void sendData(Intent intent){
        if(List_Vocabulary.size() > 0) {
            Gson gson = new Gson();
            intent.putExtra("data_vocabulary", gson.toJson(List_Vocabulary));
        }else{
            intent.putExtra("data_vocabulary", "None");
        }
    }

    @Override
    public void onBackPressed() {
        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            backToast.cancel();
            super.onBackPressed();
            return;
        } else {
            backToast = Toast.makeText(getBaseContext(), "Press back again to exit", Toast.LENGTH_SHORT);
            backToast.show();
        }

        backPressedTime = System.currentTimeMillis();
    }
}