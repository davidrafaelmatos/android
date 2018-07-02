package com.cm.davidmatos.androidfinalapp;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.cm.davidmatos.androidfinalapp.Utils.Utils;
import com.cm.davidmatos.androidfinalapp.WS.BoleiaViagem;
import com.cm.davidmatos.androidfinalapp.WS.WS;
import com.cm.davidmatos.androidfinalapp.listViewAdapter.qbHistoricoAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class dbHistorico extends AppCompatActivity {

    qbHistoricoAdapter adapter;
    Context context;
    List<BoleiaViagem> listBoleiaViagem = new ArrayList<>();
    List<BoleiaViagem> aux = new ArrayList<>();
    Dialog myDialog;
    ListView listHistorico;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qb_historico);

        myDialog = new Dialog(this);
        context = this;

        listHistorico = (ListView) findViewById(R.id.listHistorico);

        carregaLista();

        hideNavigationBar();
    }

    @Override
    protected void onResume() {
        super.onResume();
        hideNavigationBar();
    }

    private void hideNavigationBar() {
        this.getWindow().getDecorView()
                .setSystemUiVisibility(
                        View.SYSTEM_UI_FLAG_FULLSCREEN |
                                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY |
                                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
                                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                );
    }

    private void carregaLista(){

        hideNavigationBar();

        // Loads User
        Response.Listener<JSONObject> objUser = new Response.Listener<JSONObject>() {
            Boolean get;
            JSONArray BoleiaViagens;
            JSONObject Boleia;
            JSONObject Viagem;


            @Override
            public void onResponse(JSONObject response) {
                listBoleiaViagem.removeAll(listBoleiaViagem);
                try {
                    get = response.getBoolean("get");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (get){
                    try {
                        BoleiaViagens = response.getJSONArray("result");
                        for (int i = 0; i < BoleiaViagens.length(); i++){

                            Boleia = BoleiaViagens.getJSONObject(i).getJSONObject("Boleia");
                            Viagem = BoleiaViagens.getJSONObject(i).getJSONObject("Viagem");
                            BoleiaViagem bv = new BoleiaViagem(Boleia.getInt("idBoleia"), Boleia.getInt("estadoPagamento"), Viagem.getString("origemNome"), Viagem.getString("destinoNome"), Viagem.getString("dataViagem"));

                            listBoleiaViagem.add(i, bv);
                            }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    adapter = new qbHistoricoAdapter(getApplicationContext(), listBoleiaViagem);
                    listHistorico.setAdapter(adapter);
                } else {
                    showErrorPopUp("Nenhum historico encontrado");
                }
            }
        };

        Response.ErrorListener errorListenerUser = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        };
        WS.getInstance(context).GetboleiaByIdUserDetalhe(Utils.idUser, objUser, errorListenerUser);
    }

    private void showErrorPopUp(String errorMessage){
        TextView btnClose;
        TextView txtDescricaoErrorPopUp;
        Button btnErrorPopUp;

        myDialog.setContentView(R.layout.error_popup);

        btnClose = (TextView) myDialog.findViewById(R.id.btnClose);
        btnErrorPopUp = (Button) myDialog.findViewById(R.id.btnErrorPopUp);
        txtDescricaoErrorPopUp = (TextView) myDialog.findViewById(R.id.txtDescricaoErrorPopUp);

        txtDescricaoErrorPopUp.setText(errorMessage);

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });

        btnErrorPopUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();
    }

}
