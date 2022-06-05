package com.example.readnewsrss;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class NewApdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private ArrayList<News> NewsList;

    public NewApdapter(Context context, int layout, ArrayList<News> newsList) {
        this.context = context;
        this.layout = layout;
        NewsList = newsList;
    }

    @Override
    public int getCount() {
        return NewsList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(layout, null);

        ImageView img = (ImageView) view.findViewById(R.id.imageView);
        TextView txt1 = (TextView) view.findViewById(R.id.textViewTitle);
        TextView txt2 = (TextView) view.findViewById(R.id.textView2);

        News news = NewsList.get(i);

        txt1.setText(news.getTitle());
        txt2.setText(news.getDiscription());

        new Aystak(img).execute(news.getLinkimage());

        return view;
    }

    public class Aystak extends AsyncTask<String, Void, Bitmap>{
        Bitmap bitmap = null;

        private ImageView viewImage;

        public Aystak(ImageView viewImage) {
            this.viewImage = viewImage;
        }

        @Override
        protected Bitmap doInBackground(String... strings) {

            try {
                URL url = new URL(strings[0]);
                bitmap = BitmapFactory.decodeStream((InputStream) url.openConnection().getInputStream());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            viewImage.setImageBitmap(bitmap);
        }
    }
}
