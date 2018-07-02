package com.cm.davidmatos.androidfinalapp.listViewAdapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cm.davidmatos.androidfinalapp.R;
import com.cm.davidmatos.androidfinalapp.WS.User;
import com.cm.davidmatos.androidfinalapp.WS.Viagem;

import java.util.List;


public class dbHistoricoUserAdapter extends BaseAdapter {

    private Context mContext;
    private List<User> mLista;

    public dbHistoricoUserAdapter(Context mContext, List<User> mLista) {
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
        return mLista.get(position).getIdUser();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = View.inflate(mContext, R.layout.listview_db_historico_user, null);
        TextView lblOrigemDestino = (TextView) v.findViewById(R.id.lblOrigemDestino);
        TextView lblData = (TextView) v.findViewById(R.id.lblData);
        TextView lblEstadoPagamento = (TextView) v.findViewById(R.id.lblEstadoPagamento);

        User u = mLista.get(position);

        lblOrigemDestino.setText(u.getNome());
        if (u.getEstado() == 1) {
            lblData.setText("Pago");
        } else {
            lblData.setText("Por pagar");
        }

        v.setTag(u.getIdUser());
        return v;
    }
}
