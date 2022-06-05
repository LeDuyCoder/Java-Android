package com.example.vocabularyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class Screen_add_vocabulary extends AppCompatActivity {

    private List<OBJ_Vocabulary> List_Vocabulary = new ArrayList<OBJ_Vocabulary>();
    private long backPressedTime;
    private Toast backToast;

    Button btn, btn_out;
    EditText et;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_add_vocabulary);

        btn_out = (Button) findViewById(R.id.button7);
        btn = (Button) findViewById(R.id.button);

        btn_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Screen_add_vocabulary.this, MainActivity.class);
                if(List_Vocabulary.size() > 1) {
                    Gson gson = new Gson();
                    intent.putExtra("data_vocabulary", gson.toJson(List_Vocabulary));
                    startActivity(intent);
                }else{
                    startActivity(intent);
                    Toast.makeText(Screen_add_vocabulary.this, "Thêm ít nhất 2 từ", Toast.LENGTH_SHORT).show();
                }
                finish();
            }
        });


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SetData();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        Intent intent = getIntent();
        String text = intent.getStringExtra("data_vocabulary");
        if(text.equals("None") || text.equals("[]")) {
            //
        }else {
            Gson gson = new Gson();
            Type obj_type = new TypeToken<ArrayList<OBJ_Vocabulary>>(){}.getType();
            List_Vocabulary = gson.fromJson(text, obj_type);
            if(List_Vocabulary.size() > 1) {
                String TextInput = "";

                for (OBJ_Vocabulary obj : List_Vocabulary) {
                    if(obj.getSpelling().equals("None")){
                        TextInput += obj.getEng() + "-" + obj.getVietnamese() + "-" + obj.getNegative() + "\n";
                    }
                    else {
                        TextInput += obj.getEng() + "-" + obj.getVietnamese() + "-" + obj.getNegative() + "-" + obj.getSpelling() +"\n";
                    }
                }
                et = (EditText) findViewById(R.id.editTextTextMultiLine);
                et.setText(TextInput);
            }
        }
    }

    /*cần làm lại phần setData xử lí dữ liệu cao hơn*/

    private void SetData() {
        et = (EditText) findViewById(R.id.editTextTextMultiLine);
        String txt = et.getText().toString();
        List_Vocabulary.clear();
        for (String vocabulary : txt.split("\n")) {
            String[] voc = vocabulary.replace("- ", "-").replace(" -", "-").split("-");
            if(voc.length == 3) {
                OBJ_Vocabulary obj_voc = new OBJ_Vocabulary(voc[0].toLowerCase(), voc[1].toLowerCase(), voc[2].toLowerCase());
                List_Vocabulary.add((OBJ_Vocabulary) obj_voc);
            }else if(voc.length >= 4){
                OBJ_Vocabulary obj_voc = new OBJ_Vocabulary(voc[0].toLowerCase(), voc[1].toLowerCase(), voc[2].toLowerCase());
                obj_voc.setSpelling(voc[3]);
                List_Vocabulary.add((OBJ_Vocabulary) obj_voc);
            }
        }
        Toast.makeText(this, "Đã cập nhật dữ liệu", Toast.LENGTH_SHORT).show();

    }

    private List<OBJ_Vocabulary> getDatas(){
        return List_Vocabulary;
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