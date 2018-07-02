package com.cm.davidmatos.androidfinalapp.listViewAdapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cm.davidmatos.androidfinalapp.R;
import com.cm.davidmatos.androidfinalapp.WS.Carro;
import com.cm.davidmatos.androidfinalapp.WS.PropostaViagem;

import java.util.List;


public class dbCarAdapter extends BaseAdapter {

    private Context mContext;
    private List<Carro> mLista;

    public dbCarAdapter(Context mContext, List<Carro> mLista) {
        this.mContext = mContext;
        this.mLista = mLista;
    }

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
        return mLista.get(position).getIdCarro();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = View.inflate(mContext, R.layout.listview_qb_propostas, null);
        TextView lblOrigemDestino = (TextView) v.findViewById(R.id.lblOrigemDestino);
        TextView lblData = (TextView) v.findViewById(R.id.lblData);
        TextView lblEstadoPagamento = (TextView) v.findViewById(R.id.lblEstado);

        Carro c = mLista.get(position);

        lblOrigemDestino.setText(c.getMarca() + " " + c.getModelo());
        lblData.setText(c.getConsumo() + "l/100Km");
        if (c.getCombustivel() == 0){
            lblEstadoPagamento.setText("Gasolina");
        } else if (c.getCombustivel() == 1){
            lblEstadoPagamento.setText("Gasoleo");
        } else {
            lblEstadoPagamento.setText("Eletrico");
        }

        v.setTag(c.getIdCarro());
        return v;
    }
}
