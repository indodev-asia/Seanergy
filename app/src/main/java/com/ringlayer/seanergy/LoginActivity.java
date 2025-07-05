package com.ringlayer.seanergy;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {
    SendPostRequest spr = new SendPostRequest();
    public FileSystemOp fso = new FileSystemOp();
    public Util util =  new Util(LoginActivity.this);
    private Toast mToastToShow;
    EditText EUsername;
    EditText EPassword;
    Button Login;
    Button Lupa;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        try {
            Typeface fontAwesomeFont = Typeface.createFromAsset(getAssets(), "fontawesome-webfont.ttf");
            Login.setTypeface(fontAwesomeFont);
            Lupa.setTypeface(fontAwesomeFont);
            Login = (Button) findViewById(R.id.tombol_login);
            Login.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Login();
                }
            });

            Lupa = (Button) findViewById(R.id.tombol_lupa);
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

    @Override
    protected void onStart() {
        super.onStart();
        try {
            Button home;
            Button profil;
            Button about;

            home = (Button) findViewById(R.id.button_home_login);
            profil = (Button) findViewById(R.id.button_profil_login);
            about = (Button) findViewById(R.id.button_about_login);

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

    private void Lupa() {
        try {
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(LoginActivity.this, LupaActivity.class);
                    startActivity(intent);
                    finish();
                }
            },500);
        }
        catch (Exception e) {
            Log.e("error", e.toString());
        }
    }

    private void Login() {
        try {
            String Username = "";
            String Password = "";

            EUsername = (EditText) findViewById(R.id.username_login);
            EPassword = (EditText) findViewById(R.id.password_login);

            Username = EUsername.getText().toString();
            Password = EPassword.getText().toString();

            if ((Username.trim().length() < 2) || (Password.trim().length() < 2)) {
                showToast("Username dan password harus diisi", 3);
            }
            else {
                showToast("Proses login, mohon tunggu ...", 8);
                fso.DeleteFile(Environment.getExternalStorageDirectory().getAbsolutePath() + "/seanergy/sess.dat");

                String[] key_str = {"usernamex", "passwordx"};
                String[] val_str = {Username, Password};

                String login_res = spr._execute(getResources().getString(R.string.url_login),key_str, val_str);
                if (login_res.indexOf("sukses") != -1) {
                    _init_session(login_res);
                }
            }
        }
        catch (Exception e) {
            Log.e("error", e.toString());
        }
    }


    public void _init_session(String login_res) {
        try {
            boolean validses = login_res.indexOf("!!") !=-1? true: false;
            if (validses == true) {
                Log.e("[+] init_session", "got valid session");
                Intent msgIntent = new Intent(LoginActivity.this, ServisSetLevelLogin.class);
                msgIntent.putExtra("LOGIN_RES", login_res);
                startService(msgIntent);
                /* ok baby lets parse result into session */
                String[] res = login_res.split(Pattern.quote("!!"));
                String got_sess_str = res[1].trim();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent msgIntent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(msgIntent);

                    }
                },5000);
            }
            else {
                _popup( "Gagal menyimpan sesi login ! Setting izinkan permisi aplikasi 'Penyimpanan' untuk aplikasi ini !");
            }
        }
        catch(Exception e) {
            fso._do_log_debug(e, "_init_session");
        }
    }

    private void showToast(String msg_to_display, int second_to_disp) {
        int toastDurationInMilliSeconds = second_to_disp * 1000;
        mToastToShow = Toast.makeText(LoginActivity.this, msg_to_display, Toast.LENGTH_LONG);
        mToastToShow.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL, 0, 0);
        CountDownTimer toastCountDown;
        toastCountDown = new CountDownTimer(toastDurationInMilliSeconds, 1000 /*Tick duration*/) {
            public void onTick(long millisUntilFinished) {
                mToastToShow.show();
            }
            public void onFinish() {
                mToastToShow.cancel();
            }
        };

        mToastToShow.show();
        toastCountDown.start();
    }

    public void _popup(String pesan) {
        try {
            mToastToShow = Toast.makeText(LoginActivity.this, pesan, Toast.LENGTH_LONG);
            mToastToShow.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL, 0, 0);


            new Thread(new Runnable() {
                public void run() {
                    try {
                        Thread.sleep(4000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    mToastToShow.show();
                }
            }).start();

        }
        catch(Exception e) {
            Log.e("error", e.toString());
        }
    }


    private void LoadBeranda() {
        try {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent msgIntent = new Intent(LoginActivity.this, MainActivity.class);
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
                    Intent msgIntent = new Intent(LoginActivity.this, ProfilMemberActivity.class);
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
                    Intent msgIntent = new Intent(LoginActivity.this, SistemActivity.class);
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
