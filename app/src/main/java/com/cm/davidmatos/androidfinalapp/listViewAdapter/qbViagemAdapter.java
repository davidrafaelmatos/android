package com.cm.davidmatos.androidfinalapp.listViewAdapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.cm.davidmatos.androidfinalapp.R;
import com.cm.davidmatos.androidfinalapp.WS.Viagem;

import java.util.List;

public class qbViagemAdapter extends BaseAdapter {


    private Context mContext;
    private List<Viagem> mLista;

    public qbViagemAdapter(Context mContext, List<Viagem> mLista) {
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
        return mLista.get(position).getIdViagem();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = View.inflate(mContext, R.layout.listview_qb_viagem, null);
        TextView lblOrigemDestino = (TextView) v.findViewById(R.id.listviewQBViagemOrigemDestino);
        TextView lblData = (TextView) v.findViewById(R.id.listviewQBViagemData);
        TextView lblLugares = (TextView) v.findViewById(R.id.listviewQBViagemLugares);

        Viagem vi = mLista.get(position);

        lblOrigemDestino.setText("De: " + vi.getOrigemNome() + " Para: " + vi.getDestinoNome());
        lblData.setText("Data: " + vi.getDataViagem());
        lblLugares.setText("Lugares Disponivies: " + vi.getLugaresDisponiveis());

        v.setTag(vi.getIdViagem());
        return v;
    }
}
