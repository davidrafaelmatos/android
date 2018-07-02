package com.cm.davidmatos.androidfinalapp;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.cm.davidmatos.androidfinalapp.Utils.Utils;
import com.cm.davidmatos.androidfinalapp.WS.BoleiaViagem;
import com.cm.davidmatos.androidfinalapp.WS.User;
import com.cm.davidmatos.androidfinalapp.WS.Viagem;
import com.cm.davidmatos.androidfinalapp.WS.WS;
import com.cm.davidmatos.androidfinalapp.listViewAdapter.dbHistoricoAdapter;
import com.cm.davidmatos.androidfinalapp.listViewAdapter.dbHistoricoUserAdapter;
import com.cm.davidmatos.androidfinalapp.listViewAdapter.qbHistoricoAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class dbHistorico extends AppCompatActivity {

    dbHistoricoAdapter adapter;
    dbHistoricoUserAdapter adapterUser;
    Context context;
    List<Viagem> listViagem = new ArrayList<>();
    List<Viagem> aux = new ArrayList<>();
    Dialog myDialog;
    ListView listHistorico;
    ListView listUsers;
    List<User> userList = new ArrayList<>();

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

    private void carregaLista() {

        hideNavigationBar();

        // Loads User
        Response.Listener<JSONObject> objUser = new Response.Listener<JSONObject>() {
            Boolean get;
            JSONArray Viagens;
            JSONObject Viagem;


            @Override
            public void onResponse(JSONObject response) {
                listViagem.removeAll(listViagem);
                try {
                    get = response.getBoolean("get");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (get){
                    try {
                        Viagens = response.getJSONArray("result");
                        for (int i = 0; i < Viagens.length(); i++){
                            Viagem = Viagens.getJSONObject(i);
                            com.cm.davidmatos.androidfinalapp.WS.Viagem vi = new com.cm.davidmatos.androidfinalapp.WS.Viagem(
                                    Viagem.getInt("idViagem"),
                                    Viagem.getString("origemNome"),
                                    Viagem.getString("origemCoordLat"),
                                    Viagem.getString("origemCoordLong"),
                                    Viagem.getString("destinoNome"),
                                    Viagem.getString("destinoCoordLat"),
                                    Viagem.getString("destinoCoordLong"),
                                    Viagem.getInt("fkCar"),
                                    Viagem.getInt("fkUser"),
                                    Viagem.getDouble("totalKm"),
                                    Viagem.getInt("estado"),
                                    Viagem.getInt("quantidadeLugares"),
                                    Viagem.getInt("lugaresDisponiveis"),
                                    Viagem.getString("dataViagem")
                            );

                            listViagem.add(vi);
                            }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    adapter = new dbHistoricoAdapter(getApplicationContext(), listViagem);
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
        WS.getInstance(context).GetViagensByIdUser(objUser, errorListenerUser);

        listHistorico.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showPopUp(listViagem.get(position));
            }
        });
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

    private void showPopUp (Viagem vi) {

        TextView btnClose;
        Button btnSair;
        myDialog.setContentView(R.layout.custum_popup_db_historico);

        btnClose = (TextView) myDialog.findViewById(R.id.btnClose);
        btnSair = (Button) myDialog.findViewById(R.id.btnSair);

        loadData(vi, myDialog);

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
                hideNavigationBar();
            }
        });

        btnSair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
                hideNavigationBar();
            }
        });

        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();

    }

    private void loadData(Viagem vi, Dialog mD) {

        final TextView lblViagem, lblVeiculo, lblKm;
        final ListView listviewDbUser;

        lblViagem = (TextView) mD.findViewById(R.id.lblViagem);
        lblVeiculo = (TextView) mD.findViewById(R.id.lblVeiculo);
        lblKm = (TextView) mD.findViewById(R.id.lblKm);
        listviewDbUser = (ListView) mD.findViewById(R.id.listviewDbUser);


        Response.Listener<JSONObject> objUser = new Response.Listener<JSONObject>() {
            Boolean get;
            JSONObject result;
            JSONObject Viagem;
            JSONObject Carro;
            JSONArray Users;
            JSONObject User;


            @Override
            public void onResponse(JSONObject response) {
                userList.removeAll(userList);
                try {
                    get = response.getBoolean("get");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (get){
                    try {
                        result = response.getJSONObject("result");
                        Viagem = result.getJSONObject("Viagem");
                        Carro = result.getJSONArray("Carro").getJSONObject(0);
                        Users = result.getJSONArray("User");

                        for (int i = 0; i < Users.length(); i++){
                            User = Users.getJSONArray(i).getJSONObject(0);
                            com.cm.davidmatos.androidfinalapp.WS.User u = new User(
                                    User.getInt("idUser"),
                                    User.getString("username"),
                                    User.getString("password"),
                                    User.getString("nome"),
                                    User.getString("email"),
                                    User.getInt("estado")
                            );

                            userList.add(u);
                        }

                        lblViagem.setText("De: " + Viagem.getString("origemNome") + " Para: " + Viagem.getString("destinoNome"));
                        lblVeiculo.setText(Carro.getString("marca") + " " + Carro.getString("modelo"));
                        lblKm.setText(Viagem.getDouble("totalKm") + " metros");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    adapterUser = new dbHistoricoUserAdapter(getApplicationContext(), userList);
                    listviewDbUser.setAdapter(adapterUser);
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
        WS.getInstance(context).GetViagemComposta(vi.getIdViagem(), objUser, errorListenerUser);

    }
}
