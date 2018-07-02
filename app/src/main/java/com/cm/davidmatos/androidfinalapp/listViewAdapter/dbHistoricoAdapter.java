package com.cm.davidmatos.androidfinalapp.listViewAdapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cm.davidmatos.androidfinalapp.R;
import com.cm.davidmatos.androidfinalapp.WS.BoleiaViagem;
import com.cm.davidmatos.androidfinalapp.WS.Viagem;

import java.util.List;


public class dbHistoricoAdapter extends BaseAdapter {

    private Context mContext;
    private List<Viagem> mLista;

    public dbHistoricoAdapter(Context mContext, List<Viagem> mLista) {
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
        View v = View.inflate(mContext, R.layout.listview_db_historico, null);
        TextView lblOrigemDestino = (TextView) v.findViewById(R.id.lblOrigemDestino);
        TextView lblData = (TextView) v.findViewById(R.id.lblData);
        TextView lblEstadoPagamento = (TextView) v.findViewById(R.id.lblEstadoPagamento);

        Viagem vi = mLista.get(position);

        lblOrigemDestino.setText("De: " + vi.getOrigemNome() + " Para: " + vi.getDestinoNome());
        lblData.setText("Data: " + vi.getDataViagem());

        v.setTag(vi.getIdViagem());
        return v;
    }
}
