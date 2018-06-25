package com.cm.davidmatos.androidfinalapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.cm.davidmatos.androidfinalapp.Utils.Utils;
import com.cm.davidmatos.androidfinalapp.WS.WS;
import com.jackandphantom.blurimage.BlurImage;

import org.json.JSONException;
import org.json.JSONObject;

public class login extends AppCompatActivity {

    ImageView blurView;
    Button btnRegistar;
    Button btnLogin;
    Context context;
    EditText txtUsername;
    EditText txtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        hideNavigationBar();
        context = this;

        blurView = (ImageView) findViewById(R.id.blurView);
        BlurImage.with(getApplicationContext()).load(R.drawable.backgound).intensity(25).Async(true).into(blurView);

        txtUsername = (EditText) findViewById(R.id.txtUsername);
        txtPassword = (EditText) findViewById(R.id.txtPassword);

        btnRegistar = (Button) findViewById(R.id.btnRegistarRegistar);
        btnRegistar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(login.this, registar.class);
                login.this.startActivity(myIntent);
            }
        });

        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
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

    private void login() {

        Response.Listener<JSONObject> obj = new Response.Listener<JSONObject>() {
            Boolean Login;
            String nome;
            int id;

            @Override
            public void onResponse(JSONObject response) {
                try {
                    Login = response.getBoolean("Login");
                    nome = response.getString("nome");
                    id = response.getInt("id");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (Login){
                    Utils.idUser = id;
                    Intent intent = new Intent(login.this, main.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(context, "dados incorretos", Toast.LENGTH_LONG);
                }
            }
        };
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, String.valueOf(error), Toast.LENGTH_LONG).show();
            }
        };
        WS.getInstance(context).Login(txtUsername.getText().toString(), txtPassword.getText().toString(), obj, errorListener);
    }
}
