package com.example.marko.proba1;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by marko on 11.10.2017.
 */

public class ListKomitentAdapter  extends ArrayAdapter{
    List list=new ArrayList();
    public ListKomitentAdapter(@NonNull Context context, @LayoutRes int resource) {
        super(context, resource);
    }

    static class LayoutHandler{

        TextView ID,NAZIV,ADRESA;

    }
    @Override
    public void add(@Nullable Object object) {
        super.add(object);
        list.add(object);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Nullable
    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View row=convertView;
        LayoutHandler layoutHandler;
        if (row==null){
            LayoutInflater layoutInflater=(LayoutInflater)this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row=layoutInflater.inflate(R.layout.row_komitent,parent,false);
            layoutHandler=new LayoutHandler();
            layoutHandler.ID=(TextView) row.findViewById(R.id.IdTextView);
            layoutHandler.NAZIV=(TextView) row.findViewById(R.id.nazivTextView);
            layoutHandler.ADRESA=(TextView) row.findViewById(R.id.adresaTextView);
            row.setTag(layoutHandler);
        }else{
            layoutHandler=(LayoutHandler)row.getTag();


        }
        Komitent komitent=(Komitent) this.getItem(position);
        layoutHandler.ID.setText(komitent.getId());
        layoutHandler.NAZIV.setText(komitent.getCompanyName());
        layoutHandler.ADRESA.setText(komitent.getAddress());


        return row;

    }


}
