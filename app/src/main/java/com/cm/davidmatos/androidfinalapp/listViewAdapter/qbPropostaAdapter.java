package com.cm.davidmatos.androidfinalapp.listViewAdapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cm.davidmatos.androidfinalapp.R;
import com.cm.davidmatos.androidfinalapp.WS.BoleiaViagem;
import com.cm.davidmatos.androidfinalapp.WS.PropostaViagem;
import com.cm.davidmatos.androidfinalapp.WS.Viagem;

import java.util.List;


public class qbPropostaAdapter extends BaseAdapter {

    private Context mContext;
    private List<PropostaViagem> mLista;

    public qbPropostaAdapter(Context mContext, List<PropostaViagem> mLista) {
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
        return mLista.get(position).getIdProposta();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = View.inflate(mContext, R.layout.listview_qb_propostas, null);
        TextView lblOrigemDestino = (TextView) v.findViewById(R.id.lblOrigemDestino);
        TextView lblData = (TextView) v.findViewById(R.id.lblData);
        TextView lblEstadoPagamento = (TextView) v.findViewById(R.id.lblEstado);

        PropostaViagem bv = mLista.get(position);

        lblOrigemDestino.setText("De: " + bv.getOrigemNome() + " Para: " + bv.getDestinoNome());
        lblData.setText("Data: " + bv.getDataViagem());
        if (bv.getEstado() == 1){
            lblEstadoPagamento.setText("Pendente");
        } else if (bv.getEstado() == 2){
            lblEstadoPagamento.setText("Aceite");
        } else {
            lblEstadoPagamento.setText("Recusado");
        }

        v.setTag(bv.getIdProposta());
        return v;
    }
}
