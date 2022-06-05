package com.example.readnewsrss;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ActivityHotNew extends AppCompatActivity {

    ListView listView;
    ArrayList<News> arrayListNews = new ArrayList<>();
    NewApdapter apdapter;
    private long backPressedTime;
    private Toast backToast;
    CardView cardviewBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hot_new);

        listView = (ListView) findViewById(R.id.listViews);
        cardviewBack = (CardView) findViewById(R.id.cardviewback);

        Intent intent = getIntent();
        String link = intent.getStringExtra("link");

        new asynTakNews().execute(link);

        apdapter = new NewApdapter(this, R.layout.newslayout, arrayListNews);
        listView.setAdapter(apdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent1 = new Intent(ActivityHotNew.this, LoadWebActivy.class);
                intent1.putExtra("linkWebNew", arrayListNews.get(i).getLinkNew());
                startActivity(intent1);
                finish();
            }
        });

        cardviewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(ActivityHotNew.this, MainActivity.class);
                startActivity(intent1);
                overridePendingTransition(R.anim.changeactivity1, R.anim.changeactivity2);
                finish();
            }
        });
    }

    public class asynTakNews extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... strings) {
            StringBuffer stringBuffer = new StringBuffer();
            try {
                URL url = new URL(strings[0]);
                InputStreamReader inputStreamReader = new InputStreamReader(url.openConnection().getInputStream());
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String line = "";
                while ((line = bufferedReader.readLine()) != null){
                    stringBuffer.append(line + "\n");
                }

                bufferedReader.close();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return stringBuffer.toString();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            XMLDOMParser parser = new XMLDOMParser();
            Document document = parser.getDocument(s);
            NodeList notelist = (NodeList) document.getElementsByTagName("item");
            NodeList nodeListdescription = document.getElementsByTagName("description");
            String LinkImage = null;

            for (int i = 0; i < notelist.getLength(); i++){
                Element element = (Element) notelist.item(i);
                String cdata = nodeListdescription.item(i + 1).getTextContent();
                Pattern p = Pattern.compile("<img[^>]+src\\s*=\\s*['\"]([^'\"]+)['\"][^>]*>");
                Matcher matcher = p.matcher(cdata);
                if(matcher.find()){
                    LinkImage = matcher.group(1);
                    arrayListNews.add(new News(parser.getValue(element, "title"), parser.getValue(element, "pubDate"), LinkImage, parser.getValue(element, "link")));
                }
            }

            apdapter.notifyDataSetChanged();
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