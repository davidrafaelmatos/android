package com.cm.davidmatos.androidfinalapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.jackandphantom.blurimage.BlurImage;

public class qbViagem extends AppCompatActivity {

    ImageView blurViewQBViagemTitle;
    ImageView blurViewQBViagemOrigem;
    ImageView blurViewQBViagemDestino;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qb_viagem);

        setBlurView();
        hideNavigationBar();

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

    private void setBlurView() {

    }
}
