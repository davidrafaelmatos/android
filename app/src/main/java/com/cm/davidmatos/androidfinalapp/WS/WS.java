package com.cm.davidmatos.androidfinalapp.WS;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;


import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class WS {

    private static WS ourInstance;
    public static synchronized WS getInstance(Context context) {
        if (ourInstance == null)
            ourInstance = new WS(context);

        return ourInstance;
    }

    private Context context;
    private RequestQueue requestQueue;

    private WS(Context context) {
        this.context = context;
        requestQueue = getRequestQueue();
    }

    private RequestQueue getRequestQueue() {
        if (requestQueue == null)
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());

        return requestQueue;
    }

    private String url = "http://davidmatos.pt/slimIOS/index.php";

    // -- --------------------------------------------------------------------------------------------------------------------------------
    // -- WS for User
    // -- --------------------------------------------------------------------------------------------------------------------------------

    // WS for Login
    public void Login(String username, String pass, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        Map<String, String> params = new HashMap<>();
        params.put("username", username);
        params.put("password", pass);

        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST, url + "/user/login",
                new JSONObject(params),
                listener,
                errorListener)
        {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type","application/json; charset=utf-8");
                headers.put("User-agent", System.getProperty("http.agent"));
                return headers;
            }
        };
        getRequestQueue().add(jsObjRequest);
    }

    // WS for Add a user
    public void newUser(User user, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        Map<String, String> u = new HashMap<>();
        u.put("nome", user.getNome().toString());
        u.put("username", user.getUsername().toString());
        u.put("password", user.getPassword().toString());
        u.put("email", user.getEmail().toString());
        u.put("estado", String.valueOf(user.getEstado()));
        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST, url + "/user/new",
                new JSONObject(u),
                listener,
                errorListener)
        {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type","application/json; charset=utf-8");
                headers.put("User-agent", System.getProperty("http.agent"));
                return headers;
            }
        };
        getRequestQueue().add(jsObjRequest);
    }


    // -- --------------------------------------------------------------------------------------------------------------------------------
    // -- WS for Viagens
    // -- --------------------------------------------------------------------------------------------------------------------------------

    public void GetViagensByDestino(Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET, url + "/viagens",
                null,
                listener,
                errorListener)
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type","application/json; charset=utf-8");
                headers.put("User-agent", System.getProperty("http.agent"));
                return headers;
            }
        };

        getRequestQueue().add(jsObjRequest);
    }



/*
    public void GetMarcas(Response.Listener<JSONArray> listener, Response.ErrorListener errorListener) {
        JsonArrayRequest jsObjRequest = new JsonArrayRequest(Request.Method.GET, url + "/marcas",
                null,
                listener,
                errorListener)
        {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type","application/json; charset=utf-8");
                headers.put("User-agent", System.getProperty("http.agent"));
                return headers;
            }
        };

        getRequestQueue().add(jsObjRequest);
    }

    public void GetModelos(Response.Listener<JSONArray> listener, Response.ErrorListener errorListener) {
        JsonArrayRequest jsObjRequest = new JsonArrayRequest(Request.Method.GET, url + "/modelos",
                null,
                listener,
                errorListener)
        {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type","application/json; charset=utf-8");
                headers.put("User-agent", System.getProperty("http.agent"));
                return headers;
            }
        };

        getRequestQueue().add(jsObjRequest);
    }

    public void GetDouBoleiasNotMine(int id, Response.Listener<JSONArray> listener, Response.ErrorListener errorListener) {
        JsonArrayRequest jsObjRequest = new JsonArrayRequest(Request.Method.GET, url + "/notmine/douboleia/" + id,
                null,
                listener,
                errorListener)
        {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type","application/json; charset=utf-8");
                headers.put("User-agent", System.getProperty("http.agent"));
                return headers;
            }
        };

        getRequestQueue().add(jsObjRequest);
    }

    public void GetCarrosUtilizador(int id, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET, url + "/my/cars/" + id,
                null,
                listener,
                errorListener)
        {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type","application/json; charset=utf-8");
                headers.put("User-agent", System.getProperty("http.agent"));
                return headers;
            }
        };

        getRequestQueue().add(jsObjRequest);
    }

    /*
    public void AddCarro(float consumos, String matricula, int fkModelo, int fkUtilizador, Listener<JSONObject> listener, ErrorListener errorListener) {
        Map<String, String> params = new HashMap<>();
        params.put("consumos", consumos + "");
        params.put("matricula", matricula);
        params.put("fkModelo", fkModelo + "");
        params.put("fkUtilizador", fkUtilizador + "");

        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST, url + "/carros/",
                new JSONObject(params),
                listener,
                errorListener)
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type","application/json; charset=utf-8");
                headers.put("User-agent", System.getProperty("http.agent"));
                return headers;
            }
        };

        getRequestQueue().add(jsObjRequest);
    }

    public void UpdateUtilizador(int id, String nome, String morada, String telemovel, Listener<JSONObject> listener, ErrorListener errorListener) {
        Map<String, String> params = new HashMap<>();
        params.put("nome", nome);
        params.put("morada", morada);
        params.put("telemovel", telemovel);

        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.PUT, url + "/user/" + id,
                new JSONObject(params),
                listener,
                errorListener)
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type","application/json; charset=utf-8");
                headers.put("User-agent", System.getProperty("http.agent"));
                return headers;
            }
        };

        getRequestQueue().add(jsObjRequest);
    }

    public void RemoveCarroUtilizador(int id, Listener<JSONObject> listener, ErrorListener errorListener) {
        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.DELETE, url + "/carros/" + id,
                null,
                listener,
                errorListener)
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type","application/json; charset=utf-8");
                headers.put("User-agent", System.getProperty("http.agent"));
                return headers;
            }
        };

        getRequestQueue().add(jsObjRequest);
    }

    public void GetLugares(Listener<JSONObject> listener, ErrorListener errorListener) {
        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET, url + "/lugares/",
                null,
                listener,
                errorListener)
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type","application/json; charset=utf-8");
                headers.put("User-agent", System.getProperty("http.agent"));
                return headers;
            }
        };

        getRequestQueue().add(jsObjRequest);
    }

    public void GetViagensUtilizadores(int id, Listener<JSONObject> listener, ErrorListener errorListener) {
        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET, url + "/viagens/uti/" + id,
                null,
                listener,
                errorListener)
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type","application/json; charset=utf-8");
                headers.put("User-agent", System.getProperty("http.agent"));
                return headers;
            }
        };

        getRequestQueue().add(jsObjRequest);
    }

    public void AddViagem(int origem, int destino, int fkCarro, String dia, String hora, Listener<JSONObject> listener, ErrorListener errorListener) {
        Map<String, String> params = new HashMap<>();
        params.put("origem", origem + "");
        params.put("destino", destino + "");
        params.put("fkCarro", fkCarro + "");
        params.put("concluida", false + "");
        params.put("dataSaida", dia + " " + hora);

        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST, url + "/viagens/",
                new JSONObject(params),
                listener,
                errorListener)
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type","application/json; charset=utf-8");
                headers.put("User-agent", System.getProperty("http.agent"));
                return headers;
            }
        };

        getRequestQueue().add(jsObjRequest);
    }

    public void GetMyViagens(int id, Listener<JSONObject> listener, ErrorListener errorListener) {
        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET, url + "/myviagens/" + id,
                null,
                listener,
                errorListener)
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type","application/json; charset=utf-8");
                headers.put("User-agent", System.getProperty("http.agent"));
                return headers;
            }
        };

        getRequestQueue().add(jsObjRequest);
    }
    */

}
