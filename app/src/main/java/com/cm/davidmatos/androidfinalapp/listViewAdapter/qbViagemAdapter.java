package com.cm.davidmatos.androidfinalapp.listViewAdapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.cm.davidmatos.androidfinalapp.WS.Viagem;

import java.util.List;

public class qbViagemAdapter extends BaseAdapter {


    private Context mContext;
    private List<Viagem> mLista;

    @Override
    public int getCount() {
        return mLista.size();
    }

    @Override
    public Object getItem(int position) {
        return mLista.get(position);
    }

    @Override
    public long getItemId(int position) {
        return mLista.get(position).getIdViagem();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       // View v = View.inflate(mContext, android.R.layout.listview_qb_viagem, null);
        return null;
    }
}
