package com.ringlayer.seanergy;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class SplashActivity extends AppCompatActivity {
    public Util util =  new Util(SplashActivity.this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final Intent msgIntent;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                util.askForPermission(Manifest.permission.ACCESS_FINE_LOCATION, 0);
                util.askForPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, 1);
                util.askForPermission(Manifest.permission.READ_EXTERNAL_STORAGE, 2);
                util.askForPermission(Manifest.permission.ACCESS_COARSE_LOCATION, 3);
            }

            msgIntent = new Intent(this, ServisCekSesi.class);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startService(msgIntent);
                }
            },4000);
        }
        catch (Exception e) {
            Log.e("error", e.toString());
        }

    }

    private BroadcastReceiver br = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String full_sesi = "no session";
            final Context konteks = context;
            final Intent msgIntent;
            try {
                Bundle bundle = intent.getExtras();
                if (bundle != null) {
                    full_sesi = bundle.getString("FULL_SESI").trim();
                    Log.e("[+] Splash onReceive", full_sesi);
                    Log.e("[+] Splash onReceive", "Processing string");

                    if (full_sesi.contains("no session")) {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Intent msgIntent = new Intent(SplashActivity.this, LoginActivity.class);
                                startActivity(msgIntent);

                            }
                        },2000);

                    }
                    else {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Intent msgIntent = new Intent(SplashActivity.this, MainActivity.class);
                                startActivity(msgIntent);

                            }
                        },2000);
                    }

                    Log.e("[+] Splash onReceive", "Done");
                }
            }
            catch (Exception e) {
                Log.e("onReceive", e.toString());
                msgIntent = new Intent(konteks, LoginActivity.class);
                msgIntent.putExtra("FULL_SESI", full_sesi);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        konteks.startActivity(msgIntent);
                    }
                },3000);
            }
        }
    };


    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(br, new IntentFilter("1"));
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(br);
    }
}
