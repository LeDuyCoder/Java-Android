package com.example.vocabularyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Screen_remove_vocabulary extends AppCompatActivity {

    private long backPressedTime;
    private Toast backToast;


    ListView listvoc;
    ListVocaAdapter adapter;
    ArrayList<OBJ_Vocabulary> voc = new ArrayList<OBJ_Vocabulary>();
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_remove_vocabulary);

        btn = (Button) findViewById(R.id.btn_out);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Screen_remove_vocabulary.this, MainActivity.class);
                if(voc.size() > 0) {
                    Gson gson = new Gson();
                    intent.putExtra("data_vocabulary", gson.toJson(voc));
                }else{
                    intent.putExtra("data_vocabulary", "None");
                }
                startActivity(intent);
                finish();
            }
        });

        listvoc = (ListView) findViewById(R.id.list_vocabulary);

        Intent intent = getIntent();

        Gson gson = new Gson();
        String text = intent.getStringExtra("data_vocabulary");
        System.out.println(text);
        if(text.equals("None") || text.equals("[]")) {
            voc.add(new OBJ_Vocabulary("None", "None", "None"));
        }else{
            Type obj_type = new TypeToken<ArrayList<OBJ_Vocabulary>>() {}.getType();
            this.voc = gson.fromJson(text, obj_type);
        }

        adapter = new ListVocaAdapter(Screen_remove_vocabulary.this, R.layout.layout_listview, voc);
        listvoc.setAdapter(adapter);

        listvoc.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Dialog_remove("Thông Báo", "Bạn có muốn xóa từ: " + voc.get(i).getEng(), i);

                return false;
            }
        });
    }

    private void Dialog_remove(String title, String content, int vitri){
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle(title);
        dialog.setMessage(content);
        dialog.setIcon(R.mipmap.icon);
        dialog.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                voc.remove(vitri);
                adapter.notifyDataSetChanged();
            }
        });
        dialog.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //
            }
        });
        dialog.setCancelable(false);

        dialog.show();
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