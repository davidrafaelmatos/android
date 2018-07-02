package com.cm.davidmatos.androidfinalapp;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.cm.davidmatos.androidfinalapp.Utils.Utils;
import com.cm.davidmatos.androidfinalapp.WS.BoleiaViagem;
import com.cm.davidmatos.androidfinalapp.WS.PropostaViagem;
import com.cm.davidmatos.androidfinalapp.WS.WS;
import com.cm.davidmatos.androidfinalapp.listViewAdapter.qbHistoricoAdapter;
import com.cm.davidmatos.androidfinalapp.listViewAdapter.qbPropostaAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class qbProposta extends AppCompatActivity {

    qbPropostaAdapter adapter;
    Context context;
    List<PropostaViagem> listPropostaViagem = new ArrayList<>();
    List<PropostaViagem> aux = new ArrayList<>();
    Dialog myDialog;
    ListView listProposta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qb_proposta);

        myDialog = new Dialog(this);
        context = this;

        listProposta = (ListView) findViewById(R.id.listPropostas);

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
            JSONArray PropostaViagem;
            JSONObject Proposta;
            JSONObject Viagem;


            @Override
            public void onResponse(JSONObject response) {
                listPropostaViagem.removeAll(listPropostaViagem);
                try {
                    get = response.getBoolean("get");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (get){
                    try {
                        PropostaViagem = response.getJSONArray("result");
                        for (int i = 0; i < PropostaViagem.length(); i++){

                            Proposta = PropostaViagem.getJSONObject(i).getJSONObject("Proposta");
                            Viagem = PropostaViagem.getJSONObject(i).getJSONArray("Viagem").getJSONObject(0);
                            PropostaViagem pv = new PropostaViagem(Proposta.getInt("idProposta"), Viagem.getString("origemNome"), Viagem.getString("destinoNome"), Viagem.getString("dataViagem"), Proposta.getInt("estado"));

                            listPropostaViagem.add(pv);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    adapter = new qbPropostaAdapter(getApplicationContext(), listPropostaViagem);
                    listProposta.setAdapter(adapter);
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
        WS.getInstance(context).GetPropostasViagem(Utils.idUser, objUser, errorListenerUser);
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
