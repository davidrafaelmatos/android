package com.cm.davidmatos.androidfinalapp;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.ResultReceiver;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.cm.davidmatos.androidfinalapp.Utils.Constants;
import com.cm.davidmatos.androidfinalapp.Utils.FetchCoordinatesIntentService;
import com.cm.davidmatos.androidfinalapp.Utils.GetDirectionsData;
import com.cm.davidmatos.androidfinalapp.Utils.Utils;
import com.cm.davidmatos.androidfinalapp.WS.Carro;
import com.cm.davidmatos.androidfinalapp.WS.Proposta;
import com.cm.davidmatos.androidfinalapp.WS.User;
import com.cm.davidmatos.androidfinalapp.WS.Viagem;
import com.cm.davidmatos.androidfinalapp.WS.WS;
import com.cm.davidmatos.androidfinalapp.listViewAdapter.qbViagemAdapter;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.jackandphantom.blurimage.BlurImage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class qbViagem extends AppCompatActivity implements OnMapReadyCallback {

    ImageView blurViewQBViagemTitle;
    ImageView blurViewQBViagemOrigem;
    ImageView blurViewQBViagemDestino;
    Context context;
    ListView listViagem;
    EditText txtDestino;
    EditText txtOrigem;
    Button btnPesquisar;
    List<Viagem> listaViagem = new ArrayList<>();
    qbViagemAdapter adapter;
    Dialog myDialog;
    AddressResultReceiver mResultReceiver;
    Proposta p = new Proposta();
    Viagem v = new Viagem();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qb_viagem);

        myDialog = new Dialog(this);
        context = this;
        hideNavigationBar();

        mResultReceiver = new AddressResultReceiver(new Handler());

        listViagem = (ListView) findViewById(R.id.listViagem);

        txtOrigem = (EditText) findViewById(R.id.txtOrigem);
        txtDestino = (EditText) findViewById(R.id.txtDestino);

        btnPesquisar = (Button) findViewById(R.id.btnPesquisar);
        btnPesquisar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!txtDestino.getText().toString().equals("") && !txtOrigem.getText().toString().equals("")){
                    Toast.makeText(context, "entrou btn", Toast.LENGTH_SHORT).show();
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

    @Override
    public void onMapReady(GoogleMap googleMap) {

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
            JSONArray ViagemProposta;
            JSONObject Proposta;
            JSONObject Viagem;


            @Override
        public void onResponse(JSONObject response) {
            listaViagem.removeAll(listaViagem);
            try {
                get = response.getBoolean("get");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (get){
                try {
                    ViagemProposta = response.getJSONArray("result");
                    for (int i = 0; i < ViagemProposta.length(); i++){
                        System.out.println(i);
                        Proposta = ViagemProposta.getJSONObject(i).getJSONObject("Proposta");
                        Viagem = ViagemProposta.getJSONObject(i).getJSONObject("Viagem");
                        Viagem viagem = new Viagem(
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

                        if (!Proposta.getBoolean("get")){
                            if (viagem.getDestinoNome().toLowerCase().contains(txtDestino.getText().toString().toLowerCase()) &&
                                    viagem.getOrigemNome().toLowerCase().contains(txtOrigem.getText().toString().toLowerCase())) {
                                listaViagem.add(viagem);
                            }
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                adapter = new qbViagemAdapter(getApplicationContext(), listaViagem);
                listViagem.setAdapter(adapter);
            } else {
                showErrorPopUp("Nenhum historico encontrado");
            }
        }
    };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                showErrorPopUp(String.valueOf(error));
            }
        };

        WS.getInstance(context).GetviagensProposta(obj, errorListener);

        listViagem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showPopUp(listaViagem.get(position));
                p.setFkViagem(listaViagem.get(position).getIdViagem());
            }
        });

        hideNavigationBar();

    }

    private void showPopUp (Viagem vi) {

        myDialog.setContentView(R.layout.custum_popup_qb_viagens);

        loadData(vi, myDialog);
        fillEntryViagem(vi, myDialog);

        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();

    }

    private void getDirections(MapView mMap, Viagem vi, Context context) {

        StringBuilder sb;
        Object[] dataTransfer = new Object[4];

        sb = new StringBuilder();
        sb.append("https://maps.googleapis.com/maps/api/directions/json?");
        sb.append("origin=" + vi.getOrigemCoordLat() + "," + vi.getOrigemCoordLong());
        sb.append("&destination=" + vi.getDestinoCoordLat() + "," + vi.getDestinoCoordLong());
        sb.append("&key=" + Utils.ApiKey);

        GetDirectionsData getDirectionsData = new GetDirectionsData(context);
        dataTransfer[0] = mMap;
        dataTransfer[1] = sb.toString();
        dataTransfer[2] = new LatLng(Double.parseDouble(vi.getOrigemCoordLat()), Double.parseDouble(vi.getOrigemCoordLong()));
        dataTransfer[3] = new LatLng(Double.parseDouble(vi.getDestinoCoordLat()), Double.parseDouble(vi.getDestinoCoordLong()));

        getDirectionsData.execute(dataTransfer);

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

    private void showSuccessPopUp(String successMessage){
        TextView btnClose;
        TextView txtDescricaoSuccessPopUp;
        Button btnErrorPopUp;

        myDialog.setContentView(R.layout.sucess_popup);

        btnClose = (TextView) myDialog.findViewById(R.id.btnClose);
        btnErrorPopUp = (Button) myDialog.findViewById(R.id.btnErrorPopUp);
        txtDescricaoSuccessPopUp = (TextView) myDialog.findViewById(R.id.txtDescricaoSuccessPopUp);

        txtDescricaoSuccessPopUp.setText(successMessage);

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
                carregaLista();
            }
        });
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();
    }

    private void loadData(Viagem vi, Dialog dM) {
        final User u = new User();
        final Carro c = new Carro();
        final Dialog dialog = dM;

        // Loads User
        Response.Listener<JSONObject> objUser = new Response.Listener<JSONObject>() {
            Boolean get;
            JSONObject user;


            @Override
            public void onResponse(JSONObject response) {
                try {
                    get = response.getBoolean("Get");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (get){
                    try {
                        user = response.getJSONArray("result").getJSONObject(0);
                        u.setNome(user.getString("nome"));
                        u.setEmail(user.getString("email"));
                        fillEntryUser(dialog, u);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    showErrorPopUp("Nenhum utilizador encontrado");
                }
            }
        };

        Response.ErrorListener errorListenerUser = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        };
        WS.getInstance(context).GetUserById(vi.getFkUser(), objUser, errorListenerUser);

        // Loasd Car
        Response.Listener<JSONObject> objCarro = new Response.Listener<JSONObject>() {
            Boolean get;
            JSONObject carro;


            @Override
            public void onResponse(JSONObject response) {
                try {
                    get = response.getBoolean("Get");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (get){
                    try {
                        carro = response.getJSONArray("Result").getJSONObject(0);
                        c.setMarca(carro.getString("marca"));
                        c.setModelo(carro.getString("modelo"));
                        fillEntryCarro(dialog, c);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    showErrorPopUp("Nenhum carro encontrado");
                }
            }
        };

        Response.ErrorListener errorListenerCarro = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        };
        WS.getInstance(context).GetCarroById(vi.getFkCar(), objCarro, errorListenerCarro);

    }

    private void fillEntryViagem(Viagem vi, Dialog dm){

        TextView btnClose;
        Button btnQBViagemCancelar;
        Button btnQBViagemProposta;
        TextView txtPopUpOrigem, txtPopUpDestino, txtPopUpData;

        btnClose = (TextView) dm.findViewById(R.id.btnClose);
        btnQBViagemCancelar = (Button) dm.findViewById(R.id.btnQBViagemCancelar);
        btnQBViagemProposta = (Button) dm.findViewById(R.id.btnQBViagemProposta);

        txtPopUpOrigem = (TextView) dm.findViewById(R.id.txtPopUpOrigem);
        txtPopUpDestino = (TextView) dm.findViewById(R.id.txtPopUpDestino);
        txtPopUpData = (TextView) dm.findViewById(R.id.txtPopUpData);

        txtPopUpOrigem.setText(vi.getOrigemNome());
        txtPopUpDestino.setText(vi.getDestinoNome());
        txtPopUpData.setText(vi.getDataViagem());

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
                hideNavigationBar();
            }
        });

        btnQBViagemCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });

        btnQBViagemProposta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startIntentCoordinatesService();
            }
        });
    }

    private void fillEntryCarro(Dialog dm, Carro c){

        TextView txtPopUpVeiculo;

        txtPopUpVeiculo = (TextView) dm.findViewById(R.id.txtPopUpVeiculo);

        txtPopUpVeiculo.setText(c.getMarca() + " " + c.getModelo());
    }

    private void fillEntryUser(Dialog dm, User u){

        TextView txtPopUpUtilizador;

        txtPopUpUtilizador = (TextView) dm.findViewById(R.id.txtPopUpUser);

        txtPopUpUtilizador.setText(u.getNome());

    }

    class AddressResultReceiver extends ResultReceiver {

        public AddressResultReceiver(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {

            if (resultData.containsKey(Constants.RESULT_DATA_KEY)) {
                String mAddressOut = resultData.getString(Constants.RESULT_DATA_KEY);
            }
            if (resultData.containsKey(Constants.LATITUDE)) {
                p.setEstado(1);
                p.setFkUser(Utils.idUser);
                p.setOrigemCoordLat(String.valueOf(resultData.getDouble(Constants.LATITUDE)));
                p.setOrigemCoordLong(String.valueOf(resultData.getDouble(Constants.LONGITUDE)));
                p.setOrigemNome(txtOrigem.getText().toString());
                addProposta();
            }

        }
    }

    protected void startIntentCoordinatesService () {
        Intent intent = new Intent(this, FetchCoordinatesIntentService.class);
        intent.putExtra(Constants.LOCATION_DATA_EXTRA, txtOrigem.getText().toString());
        intent.putExtra(Constants.RECEIVER, mResultReceiver);
        System.out.println(txtOrigem.getText().toString() + " " + mResultReceiver);
        startService(intent);
    }

    private void addProposta() {

        Response.Listener<JSONObject> obj = new Response.Listener<JSONObject>() {
            Boolean post;

            @Override
            public void onResponse(JSONObject response) {
                try {
                    post = response.getBoolean("Post");
                    System.out.println(post);
                } catch (JSONException e) {
                    e.printStackTrace();
                    showErrorPopUp("error on JSON onResponse " + String.valueOf(e));
                }
                if (post){
                    showSuccessPopUp("A sua proposta foi adicionada com sucesso");
                } else {
                    showErrorPopUp("Nao foi possivel adicionar a sua proposta");
                }
            }

        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                showErrorPopUp(String.valueOf(error));
            }
        };

        WS.getInstance(context).newProposta(p, obj, errorListener);


    }

}
