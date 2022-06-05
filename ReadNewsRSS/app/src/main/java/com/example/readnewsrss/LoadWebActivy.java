package com.example.readnewsrss;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class LoadWebActivy extends AppCompatActivity {

    WebView wv;
    private long backPressedTime;
    private Toast backToast;
    CardView cardviewBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_web_activy);

        cardviewBack = (CardView) findViewById(R.id.cardviewback);

        Intent intent = getIntent();
        String link = intent.getStringExtra("linkWebNew");
        wv = (WebView) findViewById(R.id.webView);
        wv.loadUrl(link);
        wv.setWebViewClient(new WebViewClient());

        cardviewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(LoadWebActivy.this, MainActivity.class);
                startActivity(intent1);
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