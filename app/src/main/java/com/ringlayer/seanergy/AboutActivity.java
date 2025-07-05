package com.ringlayer.seanergy;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class AboutActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
    }

    @Override
    protected void onStart() {
        super.onStart();
        try {
            Button home;
            Button profil;
            Button about;

            home = (Button) findViewById(R.id.button_home_about);
            profil = (Button) findViewById(R.id.button_profil_about);
            about = (Button) findViewById(R.id.button_about_about);

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
                    Intent msgIntent = new Intent(AboutActivity.this, MainActivity.class);
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
                    Intent msgIntent = new Intent(AboutActivity.this, ProfilMemberActivity.class);
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
                    Intent msgIntent = new Intent(AboutActivity.this, AboutActivity.class);
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
