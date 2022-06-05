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

public class Learn_Vocabulary extends AppCompatActivity {

    private ArrayList<OBJ_Vocabulary> List_Vocabulary = new ArrayList<OBJ_Vocabulary>();
    private long backPressedTime;
    private Toast backToast;
    int type_check;

    OBJ_Vocabulary voc_check;
    Button btn_out, btn_send, btn_continue;
    TextView txtview;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn_vocabulary);

        txtview = (TextView) findViewById(R.id.textView6);
        btn_out = (Button) findViewById(R.id.button5);
        btn_send = (Button) findViewById(R.id.button7);
        btn_continue = (Button) findViewById(R.id.button6);

        btn_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                continue_start();
            }
        });

        btn_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Learn_Vocabulary.this, MainActivity.class);
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

    private void start_check(){
        Random random = new Random();
        this.type_check = random.nextInt(3);
        voc_check = List_Vocabulary.get(random.nextInt(List_Vocabulary.size()));

        if(this.type_check == 0){
           txtview.setText(voc_check.getVietnamese());
        }else if (this.type_check == 1){
            txtview.setText(voc_check.getEng());
        }else{
            txtview.setText("Âm nhấn của: " + voc_check.getEng());
        }
    }

    private void check_vocabulary(){
        editText = findViewById(R.id.editTextTextPersonName);
        String txt_data_user =  editText.getText().toString().toLowerCase();
        if(this.type_check == 0){
            if(txt_data_user.equals(voc_check.getEng())){
                txtview.setText(voc_check.getEng());
                txtview.setTextColor(Color.parseColor("#00e600"));
                Log.e("System", "Đúng");
            }else{
                txtview.setText(voc_check.getEng());
                txtview.setTextColor(Color.parseColor("#ff0000"));
                Log.e("System", "Sai");
            }
        }else if (this.type_check == 1){
            if(txt_data_user.equals(voc_check.getVietnamese())){
                txtview.setText(voc_check.getVietnamese());
                txtview.setTextColor(Color.parseColor("#00e600"));
                Log.e("System", "Đúng");
            }else{
                txtview.setText(voc_check.getVietnamese());
                txtview.setTextColor(Color.parseColor("#ff0000"));
                Log.e("System", "Sai");
            }
        }else {
            if(txt_data_user.equals(voc_check.getNegative())){
                txtview.setText(voc_check.getNegative());
                txtview.setTextColor(Color.parseColor("#00e600"));
            }else{
                txtview.setText(voc_check.getNegative());
                txtview.setTextColor(Color.parseColor("#ff0000"));
            }
        }
        editText.setText("");
        btn_send.setVisibility(View.INVISIBLE);
        btn_continue.setVisibility(View.VISIBLE);
        //this.start_check();
    }

    private void continue_start(){
        txtview.setTextColor(Color.parseColor("#000000"));
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