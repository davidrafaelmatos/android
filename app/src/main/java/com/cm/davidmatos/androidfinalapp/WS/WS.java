package com.cm.davidmatos.androidfinalapp.WS;

import android.app.VoiceInteractor;
import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.cm.davidmatos.androidfinalapp.Utils.MySingleton;
import com.cm.davidmatos.androidfinalapp.Utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class WS {

    public HashMap<Integer, Carro> getCarro(int id){

        HashMap<Integer, Carro> listCarro = new HashMap<Integer, Carro>();

        String url = "http://davidmatos.pt/slimIOS/index.php/carro/" + String.valueOf(id);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray arr = response.getJSONArray(Carro);
                    for (int i = 0; i < arr.length(); i++) {
                        JSONObject obj = arr.getJSONObject(i);

                    }
                } catch (JSONException ex) {
                    //error
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

       // MySingleton.getInstance(login).addToRequestQueue(jsonObjectRequest);

        return listCarro;
    }


}
