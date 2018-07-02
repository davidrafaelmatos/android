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


public class qbHistoricoAdapter extends BaseAdapter {

    private Context mContext;
    private List<BoleiaViagem> mLista;

    public qbHistoricoAdapter(Context mContext, List<BoleiaViagem> mLista) {
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
        return mLista.get(position).getIdBoleia();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = View.inflate(mContext, R.layout.listview_qb_historico, null);
        TextView lblOrigemDestino = (TextView) v.findViewById(R.id.lblOrigemDestino);
        TextView lblData = (TextView) v.findViewById(R.id.lblData);
        TextView lblEstadoPagamento = (TextView) v.findViewById(R.id.lblEstadoPagamento);

        BoleiaViagem bv = mLista.get(position);

        System.out.println("position: " + position + " estado: " + mLista.get(position).getEstadoPagamento());

        lblOrigemDestino.setText("De: " + bv.getOrigemNome() + " Para: " + bv.getDestinoNome());
        lblData.setText("Data: " + bv.getDataViagem());
        System.out.println("estadoPagamento: " + bv.getEstadoPagamento());
        if (bv.getEstadoPagamento() == 1){
            // foder tudo - bool para ter pagos
            lblEstadoPagamento.setText("Boleia Paga");
        } else {
            lblEstadoPagamento.setText("Boleia por Pagar");
        }

        v.setTag(bv.getIdBoleia());
        return v;
    }
}
