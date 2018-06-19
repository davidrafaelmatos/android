package com.cm.davidmatos.androidfinalapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.jackandphantom.blurimage.BlurImage;

public class login extends AppCompatActivity {

    ImageView blurView;
    Button btnRegistar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        blurView = (ImageView) findViewById(R.id.blurView);

        BlurImage.with(getApplicationContext()).load(R.drawable.backgound).intensity(25).Async(true).into(blurView);

        btnRegistar = (Button) findViewById(R.id.btnCancelar);
        btnRegistar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(login.this, registar.class);
                login.this.startActivity(myIntent);
            }
        });

    }
}
