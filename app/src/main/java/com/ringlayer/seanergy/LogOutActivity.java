package com.ringlayer.seanergy;

import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

public class LogOutActivity extends AppCompatActivity {
    FileSystemOp fso = new FileSystemOp();
    private Toast mToastToShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_out);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent msgIntent = new Intent(LogOutActivity.this, LoginActivity.class);
                startActivity(msgIntent);

            }
        },2000);
    }

    @Override
    protected void onStart() {
        try {
            super.onStart();
            showToast("Proses login, mohon tunggu ...", 8);
            fso.DeleteFile(Environment.getExternalStorageDirectory().getAbsolutePath() + "/jsale/sess.dat");

        }
        catch(Exception e) {
            Log.e("error", e.toString());
        }
    }

    private void showToast(String msg_to_display, int second_to_disp) {
        int toastDurationInMilliSeconds = second_to_disp * 1000;
        mToastToShow = Toast.makeText(LogOutActivity.this, msg_to_display, Toast.LENGTH_LONG);
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
}
