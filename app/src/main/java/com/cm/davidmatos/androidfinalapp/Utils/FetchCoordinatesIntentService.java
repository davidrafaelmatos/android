package com.cm.davidmatos.androidfinalapp.Utils;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.support.annotation.Nullable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class FetchCoordinatesIntentService extends IntentService {

    protected ResultReceiver mReceiver;

    public FetchCoordinatesIntentService() {
        super("FetchCoordinatesIntentService");
    }

    private void deliverResultToReceiver(int resultCode, ArrayList resultado){

        Bundle bundle = new Bundle();
        if(resultado.size() > 0) {
            bundle.putDouble(Constants.LATITUDE, Double.valueOf(resultado.get(0).toString()));
            bundle.putDouble(Constants.LONGITUDE, Double.valueOf(resultado.get(1).toString()));
        }
        mReceiver.send(resultCode, bundle);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        String errorMessage = "";

        String myLocation = intent.getStringExtra(Constants.LOCATION_DATA_EXTRA);
        mReceiver = intent.getParcelableExtra(Constants.RECEIVER);

        List<Address> addresses = null;

        try{
            addresses = geocoder.getFromLocationName(myLocation, 5);
        } catch (IOException e) {
            e.printStackTrace();
            errorMessage = "service nao disponivel";
        }

        if (addresses == null || addresses.size() == 0) {
            if (errorMessage.isEmpty()){
                errorMessage = "Endereço nao encontrado";
            }
            deliverResultToReceiver(Constants.FAILURE_RESULT, new ArrayList());
        } else {
            Address address = addresses.get(0);
            double latitude = address.getLatitude();
            double longitude = address.getLongitude();

            ArrayList resultado = new ArrayList();
            resultado.add(latitude);
            resultado.add(longitude);
            deliverResultToReceiver(Constants.SUCCESS_RESULT, resultado);
        }
    }
}
