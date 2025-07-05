package com.ringlayer.seanergy;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class ProfilMemberActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil_member);
    }

    @Override
    protected void onStart() {
        super.onStart();
        try {
            Button home;
            Button about;

            home = (Button) findViewById(R.id.button_home_profil);
            about = (Button) findViewById(R.id.button_about_profil);


            home.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    LoadBeranda();
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
                    Intent msgIntent = new Intent(ProfilMemberActivity.this, MainActivity.class);
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
                    Intent msgIntent = new Intent(ProfilMemberActivity.this, SistemActivity.class);
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
