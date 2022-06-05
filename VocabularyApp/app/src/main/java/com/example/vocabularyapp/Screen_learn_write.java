package com.example.vocabularyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
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
import java.util.List;
import java.util.Random;

public class Screen_learn_write extends AppCompatActivity {

    private List<OBJ_Vocabulary> List_Vocabulary = new ArrayList<OBJ_Vocabulary>();
    private long backPressedTime;
    private Toast backToast;

    Button btn_back, btn_out, btn_next;
    TextView English, VietNamese, Negative, Spelling, NumberVoca;
    int MaxList, number = 0;
    AdView adView1;
    InterstitialAd interstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_learn_write);

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

        btn_out = (Button) findViewById(R.id.button11);
        btn_back = (Button) findViewById(R.id.button14);
        btn_next = (Button) findViewById(R.id.button15);
        NumberVoca = (TextView) findViewById(R.id.textView10);

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(number >= List_Vocabulary.size()){
                    number = 0;
                }else {
                    number += 1;
                }

                if(number >= List_Vocabulary.size()){
                    number = 0;
                    start_check(number);
                }else {
                    start_check(number);
                }

                String text_pape_number = number+1 + " / " + List_Vocabulary.size();
                NumberVoca.setText(text_pape_number);

            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(number == 0){
                    number = List_Vocabulary.size() - 1;
                }else{
                    number -= 1;
                }

                if(number < 0){
                    number = List_Vocabulary.size() - 1;
                    start_check(number);
                }else{
                    start_check(number);
                }

                String text_pape_number = number+1 + " / " + List_Vocabulary.size();
                NumberVoca.setText(text_pape_number);
            }
        });

        btn_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Screen_learn_write.this, MainActivity.class);
                if(List_Vocabulary.size() > 0) {
                    Gson gson = new Gson();
                    intent.putExtra("data_vocabulary", gson.toJson(List_Vocabulary));
                }else{
                    intent.putExtra("data_vocabulary", "None");
                }
                startActivity(intent);
                finish();
            }
        });

        Intent intent = getIntent();
        String text = intent.getStringExtra("data_vocabulary");
        if(text.equals("None") || text.equals("[]")) {
            //
        }else {
            Gson gson = new Gson();
            Type obj_type = new TypeToken<ArrayList<OBJ_Vocabulary>>() {}.getType();
            List_Vocabulary = gson.fromJson(text, obj_type);
        }

        String text_pape_number = number+1 + " / " + List_Vocabulary.size();
        NumberVoca.setText(text_pape_number);

        start_check(number);

    }

    public void StartAds(){
        AdRequest adRequest = new AdRequest.Builder().build();
        adView1.loadAd(adRequest);
    }

    public void start_check(int number){
        //MaxList = List_Vocabulary.size();
        OBJ_Vocabulary obj = List_Vocabulary.get(number);

        English = (TextView) findViewById(R.id.textView12);
        VietNamese = (TextView) findViewById(R.id.textView13);
        Negative = (TextView) findViewById(R.id.textView14);
        Spelling = (TextView) findViewById(R.id.textView15);

        English.setText(obj.getEng());
        VietNamese.setText("Nghĩa: " + obj.getVietnamese());
        Negative.setText("Ấm nhấn: " + obj.getNegative());
        Spelling.setText("Phiên âm: " + obj.getSpelling());
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