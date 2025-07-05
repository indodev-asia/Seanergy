package com.ringlayer.seanergy;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class LupaActivity extends AppCompatActivity {
    Button Lupa;
    SendPostRequest spr = new SendPostRequest();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lupa);
    }

    @Override
    protected void onStart() {
        super.onStart();
        try {
            Button home;
            Button profil;
            Button about;
            Typeface fontAwesomeFont = Typeface.createFromAsset(getAssets(), "fontawesome-webfont.ttf");


            home = (Button) findViewById(R.id.button_home_lupa);
            profil = (Button) findViewById(R.id.button_profil_lupa);
            about = (Button) findViewById(R.id.button_about_lupa);

            home.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    LoadBeranda();
                }
            });
            profil.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    LoadProfilMember();
                }
            });
            about.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    LoadTentang();
                }
            });

            Lupa.setTypeface(fontAwesomeFont);
            Lupa = (Button) findViewById(R.id.tombol_lupa_lupa);
            Lupa.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Lupa();
                }
            });
        }
        catch (Exception e) {
            Log.e("error", e.toString());
        }
    }

    private void Lupa() {
        try {

        }
        catch (Exception e) {
            Log.e("error", e.toString());
        }
    }

    private void LoadBeranda() {
        try {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent msgIntent = new Intent(LupaActivity.this, MainActivity.class);
                    startActivity(msgIntent);
                    finish();

                }
            },5000);
        }
        catch (Exception e) {
            Log.e("error", e.toString());
        }
    }


    private void LoadProfilMember() {
        try {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent msgIntent = new Intent(LupaActivity.this, ProfilMemberActivity.class);
                    startActivity(msgIntent);
                    finish();

                }
            },5000);
        }
        catch (Exception e) {
            Log.e("error", e.toString());
        }
    }


    private void LoadTentang() {
        try {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent msgIntent = new Intent(LupaActivity.this, AboutActivity.class);
                    startActivity(msgIntent);
                    finish();

                }
            },5000);
        }
        catch (Exception e) {
            Log.e("error", e.toString());
        }
    }
}
