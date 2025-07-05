package com.ringlayer.seanergy;


import android.app.IntentService;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.StrictMode;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by ringlayer on 08/12/18.
 */

public class ServisGetPage extends IntentService {
    public static final String PARAM_OUT_MSG = "";
    public ServisGetPage() {
        super("ServisGetPage");
    }
    public FileSystemOp fso = new FileSystemOp();
    private Handler handler;

    @Override
    protected void onHandleIntent(Intent intent) {
        try {

            String call_lists = "";
            String notif_res = "";
            /* fetch dynamic pages */
            /*
            String base_url = getResources().getString(R.string.url_dinamis);
            //get about
            String tmp_about = getHtml(base_url + "about");
            if (tmp_about.length() > 3) {
                MainActivity.halaman_about = tmp_about;
                LoginActivity.halaman_about_out = tmp_about;
            }
            //get kontak
            String tmp_kontak = getHtml(base_url + "kontak");
            if (tmp_kontak.length() > 3) {
                MainActivity.halaman_kontak = tmp_kontak;
                LoginActivity.halaman_kontak_out = tmp_kontak;
            }
            //get faq
            String tmp_faq = getHtml(base_url + "faq");
            if (tmp_faq.length() > 3) {
                MainActivity.halaman_faq = tmp_faq;
                LoginActivity.halaman_faq_out = tmp_faq;
            }
            */
            /* eof fetch dynamic pages */
            publishResults();


        } catch (Exception e) {
            Log.e("[-]ohi getpage", e.toString());
        }
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        handler = new Handler();
        return super.onStartCommand(intent, flags, startId);
    }


    public String _ret_sess_str() {
        String sess_str = "";

        try {
            String fname = Environment.getExternalStorageDirectory().getAbsolutePath() + "/seanergy/sess.dat";
            File file = new File(fname);

            if (file.exists()) {
                sess_str = fso.read(fname).trim();
            }
        }
        catch (Exception e) {
            Log.e("_do_log_debug", e.toString());
        }

        return sess_str;
    }

    /* modified from https://stackoverflow.com/questions/2423498/how-to-get-the-html-source-of-a-page-from-a-html-link-in-android */
    public static String getHtml(String url) {
        try {
            if (android.os.Build.VERSION.SDK_INT > 9) {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
                StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                StrictMode.setVmPolicy(builder.build());
            }
            URLConnection connection = (new URL(url)).openConnection();
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            connection.connect();

            InputStream in = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder html = new StringBuilder();
            for (String line; (line = reader.readLine()) != null; ) {
                html.append(line);
            }
            in.close();

            return html.toString();
        }
        catch (Exception e) {
            Log.e("[-] getHtml",e.toString());
            return "";
        }
    }

    @Override
    public IBinder onBind(Intent i) {
        return null;
    }
    private void publishResults() {
        try {
            Log.e("[+] publishResults", "done");
            Intent intent = new Intent("1");
            intent.putExtra("ACT", "done");

            sendBroadcast(intent);
        }
        catch (Exception e) {
            Log.e("publishResults", e.toString());
        }

    }
}
