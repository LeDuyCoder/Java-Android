package com.example.vocabularyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;

public class Screen_check_vocabulary extends AppCompatActivity {

    private ArrayList<OBJ_Vocabulary> List_Vocabulary = new ArrayList<OBJ_Vocabulary>();
    private long backPressedTime;
    private Toast backToast;
    int point = 100;
    int check = 20;
    int type_check;

    OBJ_Vocabulary voc_check;
    Button btn_out, btn_continue, btn_send;
    EditText editText;
    TextView textViewVocabluary, textViewPoint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_check_vocabulary);

        textViewPoint = (TextView) findViewById(R.id.textView8);
        textViewVocabluary = (TextView) findViewById(R.id.textView6);
        btn_out = (Button) findViewById(R.id.button5);
        btn_send = (Button) findViewById(R.id.button7);
        btn_continue = (Button) findViewById(R.id.button6);

        textViewPoint.setText("" + point);

        btn_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                continue_start();
            }
        });

        btn_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Screen_check_vocabulary.this, MainActivity.class);
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

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editText = (EditText) findViewById(R.id.editTextTextPersonName);
                if(editText.getText().toString().equals("")) {
                    ShowDialogNotication();
                }else{
                    check_vocabulary();
                }
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

        start_check();
    }

    private void ShowDialogNotication(){
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Thông Báo");
        dialog.setMessage("Không được để trống");
        dialog.setIcon(R.mipmap.icon);
        dialog.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //code
            }
        });
        dialog.setCancelable(false);

        dialog.show();
    }

    private void dialog_tb(){
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Thông Báo");
        dialog.setMessage("Tổng điểm: " + this.point);
        dialog.setIcon(R.mipmap.icon);
        dialog.setPositiveButton("Thử lại", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                check = 20;
                point = 100;
                textViewPoint.setText("" + point);
                start_check();
            }
        });
        dialog.setCancelable(false);

        dialog.show();
    }

    private void start_check(){
        if(this.check > 0) {
            if (this.point > 0) {
                Random random = new Random();
                this.type_check = random.nextInt(3);
                voc_check = List_Vocabulary.get(random.nextInt(List_Vocabulary.size()));

                if (this.type_check == 0) {
                    textViewVocabluary.setText(voc_check.getVietnamese());
                } else if (this.type_check == 1) {
                    textViewVocabluary.setText(voc_check.getEng());
                } else {
                    textViewVocabluary.setText("Âm nhấn của: " + voc_check.getEng());
                }
            } else {
                this.dialog_tb();
            }
        }else{
            this.dialog_tb();
        }
    }

    private void check_vocabulary(){
        editText = findViewById(R.id.editTextTextPersonName);
        String txt_data_user =  editText.getText().toString().toLowerCase();
        if(this.type_check == 0){
            if(txt_data_user.equals(voc_check.getEng())){
                textViewVocabluary.setText(voc_check.getEng());
                textViewVocabluary.setTextColor(Color.parseColor("#00e600"));
            }else{
                textViewVocabluary.setText(voc_check.getEng());
                textViewVocabluary.setTextColor(Color.parseColor("#ff0000"));
                check_reduce_point();
            }
        }else if (this.type_check == 1){
            if(txt_data_user.equals(voc_check.getVietnamese())){
                textViewVocabluary.setText(voc_check.getVietnamese());
                textViewVocabluary.setTextColor(Color.parseColor("#00e600"));
            }else{
                textViewVocabluary.setText(voc_check.getVietnamese());
                textViewVocabluary.setTextColor(Color.parseColor("#ff0000"));
                check_reduce_point();
            }
        }else {
            if(txt_data_user.equals(voc_check.getNegative())){
                textViewVocabluary.setText(voc_check.getNegative());
                textViewVocabluary.setTextColor(Color.parseColor("#00e600"));
            }else{
                textViewVocabluary.setText(voc_check.getNegative());
                textViewVocabluary.setTextColor(Color.parseColor("#ff0000"));
                check_reduce_point();
            }
        }
        editText.setText("");
        btn_send.setVisibility(View.INVISIBLE);
        btn_continue.setVisibility(View.VISIBLE);
        this.check -= 1;
    }

    private void check_reduce_point(){
        if(this.point > 0){
            if(this.point - 10 >= 0){
                this.point -= 10;
            }else{
                this.point = 0;
            }
        }
        textViewPoint.setText("" + this.point);
    }

    private void continue_start(){
        textViewVocabluary.setTextColor(Color.parseColor("#000000"));
        btn_send.setVisibility(View.VISIBLE);
        btn_continue.setVisibility(View.INVISIBLE);
        start_check();
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