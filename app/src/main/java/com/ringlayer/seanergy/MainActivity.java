package com.ringlayer.seanergy;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {
    Button home;
    Button about;
    Button profil;
    Button login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();
        try {
            Button profil;
            Button about;

            profil = (Button) findViewById(R.id.button_profil_main);
            about = (Button) findViewById(R.id.button_about_main);

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



    private void LoadTentang() {
        try {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent msgIntent = new Intent(MainActivity.this, AboutActivity.class);
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
                    Intent msgIntent = new Intent(MainActivity.this, ProfilMemberActivity.class);
                    startActivity(msgIntent);
                    finish();

                }
            },5000);
        }
        catch (Exception e) {
            Log.e("error", e.toString());
        }
    }

    private void LoadLogin() {
        try {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent msgIntent = new Intent(MainActivity.this, LoginActivity.class);
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
