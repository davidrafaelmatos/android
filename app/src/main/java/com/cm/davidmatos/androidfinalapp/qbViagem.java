package com.cm.davidmatos.androidfinalapp;

import android.content.Context;
import android.content.Intent;
import android.database.MatrixCursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.cm.davidmatos.androidfinalapp.Utils.Utils;
import com.cm.davidmatos.androidfinalapp.WS.Viagem;
import com.cm.davidmatos.androidfinalapp.WS.WS;
import com.jackandphantom.blurimage.BlurImage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class qbViagem extends AppCompatActivity {

    ImageView blurViewQBViagemTitle;
    ImageView blurViewQBViagemOrigem;
    ImageView blurViewQBViagemDestino;
    Context context;
    ListView listViagem;
    EditText txtDestino;
    EditText txtOrigem;
    Button btnPesquisar;
    ArrayList<Viagem> listaViagem = new ArrayList<>();
    SimpleCursorAdapter adater;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qb_viagem);

        context = this;
        hideNavigationBar();

        listViagem = (ListView) findViewById(R.id.listViagem);

        txtOrigem = (EditText) findViewById(R.id.txtOrigem);
        txtDestino = (EditText) findViewById(R.id.txtDestino);

        btnPesquisar = (Button) findViewById(R.id.btnPesquisar);
        btnPesquisar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!txtDestino.getText().toString().equals("") && !txtOrigem.getText().toString().equals("")){
                    carregaLista();
                } else {
                    Toast.makeText(context, "Todos os campos tem que estar preenchidos", Toast.LENGTH_LONG).show();
                }
            }
        });

        blurViewQBViagemTitle = (ImageView) findViewById(R.id.blurViewQBViagemTitle);
        BlurImage.with(getApplicationContext()).load(R.drawable.bg_boleia).intensity(10).Async(true).into(blurViewQBViagemTitle);

        blurViewQBViagemOrigem = (ImageView) findViewById(R.id.blurViewQBViagemOrigem);
        BlurImage.with(getApplicationContext()).load(R.drawable.bg_boleia).intensity(10).Async(true).into(blurViewQBViagemOrigem);

        blurViewQBViagemDestino = (ImageView) findViewById(R.id.blurViewQBViagemDestino);
        BlurImage.with(getApplicationContext()).load(R.drawable.bg_boleia).intensity(10).Async(true).into(blurViewQBViagemDestino);
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

        Response.Listener<JSONObject> obj = new Response.Listener<JSONObject>() {
            Boolean get;
            JSONArray viagens;

            @Override
            public void onResponse(JSONObject response) {
                try {
                    get = response.getBoolean("get");
                    viagens = response.getJSONArray("result");
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(context, "error JSON onResponse " + String.valueOf(e), Toast.LENGTH_LONG).show();
                }
                if (get){

                    MatrixCursor mc = new MatrixCursor(new String[] {"Viagem", "Info", /* etc*/}); // properties from the JSONObjects
                    for (int i = 0; i < viagens.length(); i++) {
                        JSONObject jo;
                        try {
                            jo = viagens.getJSONObject(i);
                            if (jo.getString("destinoNome").toLowerCase().contains(txtDestino.getText().toString().toLowerCase())) {
                                Viagem v = new Viagem(jo.getInt("idViagem"), jo.getString("origemNome"), jo.getString("origemCoordLat"), jo.getString("origemCoordLong"), jo.getString("destinoNome"),
                                        jo.getString("destinoCoordLat"), jo.getString("destinoCoordLong"), jo.getInt("fkCar"), jo.getInt("fkUser"), jo.getDouble("totalKm"), jo.getInt("estado"),
                                        jo.getInt("quantidadeLugares"), jo.getInt("lugaresDisponiveis"), jo.getString("dataViagem"));
                                listaViagem.add(v);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(context, "error try MC " + String.valueOf(e), Toast.LENGTH_LONG).show();
                        }
                    }

                } else {
                    Toast.makeText(context, "dados incorretos", Toast.LENGTH_LONG);
                }
            }

        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "OnErrorResponse " + String.valueOf(error), Toast.LENGTH_LONG).show();
            }
        };
        WS.getInstance(context).GetViagensByDestino(obj, errorListener);
    }

}
