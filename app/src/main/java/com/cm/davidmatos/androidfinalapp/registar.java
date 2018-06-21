package com.cm.davidmatos.androidfinalapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.jackandphantom.blurimage.BlurImage;

public class registar extends AppCompatActivity {

    ImageView blurView;
    Button btnCancelar;
    Button btnRegistar;
    EditText txtNome;
    EditText txtUsername;
    EditText txtEmail;
    EditText txtPassword;
    EditText txtRepPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registar);
        hideNavigationBar();

        blurView = (ImageView) findViewById(R.id.blurView);

        BlurImage.with(getApplicationContext()).load(R.drawable.backgound).intensity(25).Async(true).into(blurView);

        txtNome = (EditText) findViewById(R.id.txtUsername);
        txtUsername = (EditText) findViewById(R.id.txtUsername);
        txtEmail = (EditText) findViewById(R.id.txtEmail);
        txtPassword = (EditText) findViewById(R.id.txtPassword);
        txtRepPassword = (EditText) findViewById(R.id.txtRepPassword);

        btnCancelar = (Button) findViewById(R.id.btnCancelar);
        btnRegistar = (Button) findViewById(R.id.btnRegistar);

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
                 if(txtNome.getText() == null || txtUsername.getText() == null || txtEmail.getText() == null || txtPassword.getText() == null || txtRepPassword.getText() == null){
                     //error
                 } else {
                     if( txtPassword.getText().equals(txtRepPassword.getText())){

                     } else {
                         //error
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
}
