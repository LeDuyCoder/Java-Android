package com.example.readnewsrss;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    
    CardView cd1, cd2, cd3, cd4, cd5, cd6, cd7, cd8;
    private long backPressedTime;
    private Toast backToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        cd1 = (CardView) findViewById(R.id.hotnews);
        cd2 = (CardView) findViewById(R.id.bongda);
        cd3 = (CardView) findViewById(R.id.anninh);
        cd4 = (CardView) findViewById(R.id.taichinh);
        cd5 = (CardView) findViewById(R.id.amthuc);
        cd6 = (CardView) findViewById(R.id.cntt);
        cd7 = (CardView) findViewById(R.id.suckhoe);
        cd8 = (CardView) findViewById(R.id.funny);

        cd1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ActivityHotNew.class);
                intent.putExtra("link", "https://cdn.24h.com.vn/upload/rss/tintuctrongngay.rss");
                startActivity(intent);
                overridePendingTransition(R.anim.changeactivity1, R.anim.changeactivity2);
                finish();
            }
        });

        cd2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ActivityHotNew.class);
                intent.putExtra("link", "https://cdn.24h.com.vn/upload/rss/bongda.rss");
                startActivity(intent);
                overridePendingTransition(R.anim.changeactivity1, R.anim.changeactivity2);
                finish();
            }
        });

        cd3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ActivityHotNew.class);
                intent.putExtra("link", "https://cdn.24h.com.vn/upload/rss/anninhhinhsu.rss");
                startActivity(intent);
                overridePendingTransition(R.anim.changeactivity1, R.anim.changeactivity2);
                finish();
            }
        });

        cd4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ActivityHotNew.class);
                intent.putExtra("link", "https://cdn.24h.com.vn/upload/rss/taichinhbatdongsan.rss");
                startActivity(intent);
                overridePendingTransition(R.anim.changeactivity1, R.anim.changeactivity2);
                finish();
            }
        });

        cd5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ActivityHotNew.class);
                intent.putExtra("link", "https://cdn.24h.com.vn/upload/rss/amthuc.rss");
                startActivity(intent);
                overridePendingTransition(R.anim.changeactivity1, R.anim.changeactivity2);
                finish();
            }
        });

        cd6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ActivityHotNew.class);
                intent.putExtra("link", "https://cdn.24h.com.vn/upload/rss/congnghethongtin.rss");
                startActivity(intent);
                overridePendingTransition(R.anim.changeactivity1, R.anim.changeactivity2);
                finish();
            }
        });

        cd7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ActivityHotNew.class);
                intent.putExtra("link", "https://cdn.24h.com.vn/upload/rss/suckhoedoisong.rss");
                startActivity(intent);
                overridePendingTransition(R.anim.changeactivity1, R.anim.changeactivity2);
                finish();
            }
        });

        cd8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ActivityHotNew.class);
                intent.putExtra("link", "https://cdn.24h.com.vn/upload/rss/cuoi24h.rss");
                startActivity(intent);
                overridePendingTransition(R.anim.changeactivity1, R.anim.changeactivity2);
                finish();
            }
        });
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