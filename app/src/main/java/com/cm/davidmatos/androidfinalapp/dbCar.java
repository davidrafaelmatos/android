package com.cm.davidmatos.androidfinalapp;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.cm.davidmatos.androidfinalapp.WS.Carro;
import com.cm.davidmatos.androidfinalapp.WS.WS;
import com.cm.davidmatos.androidfinalapp.listViewAdapter.dbCarAdapter;
import com.cm.davidmatos.androidfinalapp.listViewAdapter.dbHistoricoAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class dbCar extends AppCompatActivity {

    dbCarAdapter adapter;
    Context context;
    List<Carro> listaCarros = new ArrayList<>();
    Dialog myDialog;
    ListView listCarros;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.db_car);

        hideNavigationBar();

        myDialog = new Dialog(this);
        context = this;

        listCarros = (ListView) findViewById(R.id.listviewCar);

        carregaLista();

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
            JSONArray Carros;
            JSONObject Carro;

            @Override
            public void onResponse(JSONObject response) {
                listaCarros.removeAll(listaCarros);
                try {
                    get = response.getBoolean("Get");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (get){
                    try {
                        Carros = response.getJSONArray("Result");
                        for (int i = 0; i < Carros.length(); i++){
                            Carro = Carros.getJSONObject(i);

                            com.cm.davidmatos.androidfinalapp.WS.Carro c = new Carro(
                                    Carro.getInt("idCar"),
                                    Carro.getString("marca"),
                                    Carro.getString("modelo"),
                                    Carro.getInt("combustivel"),
                                    Carro.getDouble("consumo"),
                                    Carro.getInt("fkUser"),
                                    Carro.getInt("estado")
                            );

                            listaCarros.add(c);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    adapter = new dbCarAdapter(getApplicationContext(), listaCarros);
                    listCarros.setAdapter(adapter);
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
        WS.getInstance(context).GetCarroByIdUser(objUser, errorListenerUser);
        /*
        listCarros.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showPopUp(listViagem.get(position));
            }
        });
        */
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
