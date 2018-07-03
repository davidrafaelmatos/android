package com.cm.davidmatos.androidfinalapp;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.cm.davidmatos.androidfinalapp.Utils.GetDirectionsData;
import com.cm.davidmatos.androidfinalapp.Utils.Utils;
import com.cm.davidmatos.androidfinalapp.WS.Viagem;
import com.cm.davidmatos.androidfinalapp.WS.WS;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;
import org.json.JSONObject;

public class dbViagem2 extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Button btnCancelar, btnGuardar;
    Dialog myDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.db_viagem2);
        myDialog = new Dialog(this);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        btnCancelar = findViewById(R.id.btnCancelar);
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), dbMain.class);
                startActivity(intent);
            }
        });

        btnGuardar = findViewById(R.id.btnGuardar);
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Viagem vi = new Viagem(
                    1,
                        Utils.origemNome,
                        String.valueOf(Utils.origemCoord.latitude),
                        String.valueOf(Utils.origemCoord.longitude),
                        Utils.destinoNome,
                        String.valueOf(Utils.destinoCoord.latitude),
                        String.valueOf(Utils.destinoCoord.longitude),
                        Utils.idCarro,
                        Utils.idUser,
                        100,
                        1,
                        Utils.quantidadeLugares,
                        Utils.quantidadeLugares,
                        "03/07/2018 16:30"
                );
                addCar(vi);
            }
        });

    }

    private void addCar(Viagem viagem){
        Response.Listener<JSONObject> objMarca = new Response.Listener<JSONObject>() {
            JSONObject Post ;

            @Override
            public void onResponse(JSONObject response) {

                try {
                    Boolean get = response.getBoolean("Post");

                    if (get){
                        showSuccessPopUp("A Viagem foi adicionado com sucesso");
                    } else {
                        showErrorPopUp("Ocorreu um erro durante o registo da viagem");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        Response.ErrorListener errorListenerMarca = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        };
        WS.getInstance(getApplicationContext()).newViagem(viagem, objMarca, errorListenerMarca);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(Utils.origemCoord).title("Origem" + Utils.origemNome));
        mMap.addMarker(new MarkerOptions().position(Utils.destinoCoord).title("Destino" + Utils.destinoNome));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Utils.origemCoord, 18));

        getDirections(mMap, getApplicationContext());
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
                Intent intent = new Intent(getApplicationContext(), dbMain.class);
                startActivity(intent);
            }
        });

        btnErrorPopUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), dbMain.class);
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
                Intent intent = new Intent(getApplicationContext(), dbMain.class);
                startActivity(intent);
            }
        });

        btnErrorPopUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), dbMain.class);
                startActivity(intent);
            }
        });
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();
    }

    private void getDirections(GoogleMap mMap, Context context) {

        StringBuilder sb;
        Object[] dataTransfer = new Object[4];

        sb = new StringBuilder();
        sb.append("https://maps.googleapis.com/maps/api/directions/json?");
        sb.append("origin=" + Utils.origemCoord.latitude + "," + Utils.origemCoord.longitude);
        sb.append("&destination=" + Utils.destinoCoord.latitude + "," + Utils.destinoCoord.longitude);
        sb.append("&key=" + Utils.ApiKey);

        GetDirectionsData getDirectionsData = new GetDirectionsData(context);
        dataTransfer[0] = mMap;
        dataTransfer[1] = sb.toString();
        dataTransfer[2] = Utils.origemCoord;
        dataTransfer[3] = Utils.destinoCoord;

        getDirectionsData.execute(dataTransfer);

    }


}
