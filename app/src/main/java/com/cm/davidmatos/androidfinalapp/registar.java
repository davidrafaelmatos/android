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
import com.cm.davidmatos.androidfinalapp.WS.User;
import com.cm.davidmatos.androidfinalapp.WS.WS;
import com.jackandphantom.blurimage.BlurImage;

import org.json.JSONException;
import org.json.JSONObject;

public class registar extends AppCompatActivity {

    ImageView blurView;
    Button btnCancelar;
    Button btnRegistar;
    EditText txtNome;
    EditText txtUsername;
    EditText txtEmail;
    EditText txtPassword;
    EditText txtRepPassword;
    Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registar);
        hideNavigationBar();
        context = this;

        blurView = (ImageView) findViewById(R.id.blurView);
        BlurImage.with(getApplicationContext()).load(R.drawable.backgound).intensity(25).Async(true).into(blurView);

        txtNome = (EditText) findViewById(R.id.txtUsername);
        txtUsername = (EditText) findViewById(R.id.txtUsername);
        txtEmail = (EditText) findViewById(R.id.txtEmail);
        txtPassword = (EditText) findViewById(R.id.txtPassword);
        txtRepPassword = (EditText) findViewById(R.id.txtRepPassword);

        btnCancelar = (Button) findViewById(R.id.btnCancelar);
        btnRegistar = (Button) findViewById(R.id.btnRegistarRegistar);

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(registar.this, login.class);
                registar.this.startActivity(intent);
            }
        });


        btnRegistar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("entrou butao click");
                 if(txtNome.getText().toString().equals("") || txtUsername.getText().toString().equals("") || txtEmail.getText().toString().equals("") || txtPassword.getText().toString().equals("") || txtRepPassword.getText().toString().equals("")){
                     Toast.makeText(context, "Todos os campos tem que estar preenchidos", Toast.LENGTH_LONG).show();
                     System.out.println("entrou erro");
                 } else {
                     if( txtPassword.getText().toString().equals(txtRepPassword.getText().toString())){
                         System.out.println("entrou  registar");
                         registar();
                     } else {
                         System.out.println("entrou erro pass");
                         Toast.makeText(context, "As palavras Chaves não são iguais", Toast.LENGTH_LONG).show();
                     }
                 }
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

    private void registar() {

        Response.Listener<JSONObject> obj = new Response.Listener<JSONObject>() {
            Boolean Add;
            int id;

            @Override
            public void onResponse(JSONObject response) {
                try {
                    Add = response.getBoolean("Add");
                    id = response.getInt("id");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (Add){
                    Utils.idUser = id;
                    Intent intent = new Intent(registar.this, main.class);
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
        User u = new User(-1, txtUsername.getText().toString(), txtPassword.getText().toString(), txtNome.getText().toString(), txtEmail.getText().toString(), 1);
        WS.getInstance(context).newUser(u, obj, errorListener);
    }
}
