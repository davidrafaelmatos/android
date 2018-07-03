package com.cm.davidmatos.androidfinalapp;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.ResultReceiver;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.cm.davidmatos.androidfinalapp.Utils.Constants;
import com.cm.davidmatos.androidfinalapp.Utils.FetchCoordinatesIntentService;
import com.cm.davidmatos.androidfinalapp.Utils.Utils;
import com.cm.davidmatos.androidfinalapp.WS.Carro;
import com.cm.davidmatos.androidfinalapp.WS.WS;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class dbViagem extends AppCompatActivity {

    EditText txtOrigem, txtDestino, txtLugares;
    Spinner spCarros;
    List<Carro> listaCarros = new ArrayList<>();
    String[] lCarros;
    Button btnCancelar, btnCalcular;
    Context context;
    Dialog myDialog;
    AddressResultReceiver mResultReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.db_viagem);

        context = this;
        myDialog = new Dialog(this);
        mResultReceiver = new AddressResultReceiver(new Handler());

        txtOrigem = findViewById(R.id.txtOrigem);
        txtDestino = findViewById(R.id.txtDestino);
        txtLugares = findViewById(R.id.txtLugares);
        spCarros = findViewById(R.id.spCarros);
        btnCancelar = findViewById(R.id.btnCancelar);
        btnCalcular = findViewById(R.id.btnCalcular);

        loadCarros();

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), dbMain.class);
                startActivity(intent);
            }
        });

        btnCalcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((txtOrigem.getText().toString().equals("") || txtOrigem.getText().toString().isEmpty()) &&
                        (txtDestino.getText().toString().equals("") || txtDestino.getText().toString().isEmpty()) &&
                        (txtLugares.getText().toString().equals("") || txtLugares.getText().toString().isEmpty())){
                    showErrorPopUp("Todos os campos tem que estar prenchidos");
                } else {
                    startIntentCoordinatesService("origem", txtOrigem.getText().toString());
                    startIntentCoordinatesService("destino", txtDestino.getText().toString());
                    Utils.quantidadeLugares = Integer.parseInt(txtLugares.getText().toString());
                    showSuccessPopUp("A calcular o seu percurso");
                }
            }
        });

    }

    private void loadCarros(){

        Response.Listener<JSONObject> objMarca = new Response.Listener<JSONObject>() {

            JSONArray Result;
            JSONObject Carro;
            Boolean Get;

            @Override
            public void onResponse(JSONObject response) {

                try {
                    Get = response.getBoolean("Get");
                    if (Get) {
                        try {
                            Result = response.getJSONArray("Result");
                            lCarros = new String[Result.length()];
                            for (int i = 0; i < Result.length(); i++){
                                Carro = Result.getJSONObject(i);
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
                                lCarros[i] = Carro.getString("marca") + " " + Carro.getString("modelo");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                loadSpinnerCarros();
            }
        };
        Response.ErrorListener errorListenerMarca = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        };
        WS.getInstance(context).GetCarroByIdUser(objMarca, errorListenerMarca);

    }

    private void loadSpinnerCarros() {
        ArrayAdapter<String> adapterCombustivel = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, lCarros);
        spCarros.setAdapter(adapterCombustivel);
        spCarros.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Utils.idCarro = listaCarros.get(position).getIdCarro();
                System.out.println(listaCarros.get(position).getIdCarro());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                showErrorPopUp("Tem que selecionar um veiculo");
            }
        });
    }

    private void showSuccessPopUp(String successMessage){
        TextView btnClose;
        TextView txtDescricaoSuccessPopUp;
        final Button btnErrorPopUp;

        myDialog.setContentView(R.layout.sucess_popup);

        btnClose = (TextView) myDialog.findViewById(R.id.btnClose);
        btnErrorPopUp = (Button) myDialog.findViewById(R.id.btnErrorPopUp);
        txtDescricaoSuccessPopUp = (TextView) myDialog.findViewById(R.id.txtDescricaoSuccessPopUp);

        txtDescricaoSuccessPopUp.setText(successMessage);

        btnErrorPopUp.setVisibility(View.INVISIBLE);

        Timer buttonTimer = new Timer();
        buttonTimer.schedule(new TimerTask() {

            @Override
            public void run() {
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        btnErrorPopUp.setVisibility(View.VISIBLE);
                    }
                });
            }
        }, 3000);


        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
                Intent intent = new Intent(context, dbViagem2.class);
                startActivity(intent);
            }
        });

        btnErrorPopUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
                Intent intent = new Intent(context, dbViagem2.class);
                startActivity(intent);
            }
        });
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();
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
                if (resultData.getString(Constants.TIPO).equals("origem")){
                    LatLng origem = new LatLng(resultData.getDouble(Constants.LATITUDE),
                            resultData.getDouble(Constants.LONGITUDE));
                    Utils.origemCoord = origem;
                    Utils.origemNome = txtOrigem.getText().toString();
                } else {
                    LatLng destino = new LatLng(resultData.getDouble(Constants.LATITUDE),
                                                resultData.getDouble(Constants.LONGITUDE));
                    Utils.destinoCoord = destino;
                    Utils.destinoNome = txtDestino.getText().toString();
                }
            }

        }
    }

    protected void startIntentCoordinatesService (String tipo, String aux) {
        Intent intent = new Intent(this, FetchCoordinatesIntentService.class);
        intent.putExtra(Constants.LOCATION_DATA_EXTRA, aux);
        intent.putExtra(Constants.RECEIVER, mResultReceiver);
        intent.putExtra(Constants.TIPO, tipo);
        startService(intent);
    }
}
