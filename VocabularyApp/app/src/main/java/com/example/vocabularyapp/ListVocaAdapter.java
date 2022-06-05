package com.example.vocabularyapp;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class ListVocaAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private List<OBJ_Vocabulary> list;

    public ListVocaAdapter(Context context, int layout, List<OBJ_Vocabulary> list) {
        this.context = context;
        this.layout = layout;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
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

        TextView English = (TextView) view.findViewById(R.id.English);
        TextView Vietnamese = (TextView) view.findViewById(R.id.VietNamese);
        TextView Negative = (TextView) view.findViewById(R.id.Negative);
        TextView Spelling = (TextView) view.findViewById(R.id.Spelling);

        OBJ_Vocabulary obj_vocabulary = list.get(i);
        English.setText(obj_vocabulary.getEng());
        Vietnamese.setText(obj_vocabulary.getVietnamese());
        Negative.setText(obj_vocabulary.getNegative());
        Spelling.setText(obj_vocabulary.getSpelling());

        return view;
    }
}
