package com.ringlayer.seanergy;

import android.app.IntentService;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

/**
 * Created by ringlayer on 01/05/19.
 */

public class ServisCekSesi extends IntentService {
    public static final String PARAM_IN_MSG = "";
    public static final String PARAM_OUT_MSG = "";
    private FileSystemOp fso = new FileSystemOp();
    public ServisCekSesi() {
        super("ServisCekSesi");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        try {
            fso.prepare_log_dir();


        }
        catch (Exception e) {
            Log.e("error", e.toString());
        }
    }

    @Override
    public IBinder onBind(Intent i) {
        return null;
    }


    private void publishResults(String full_sesi) {
        try {
            Log.e("[+] publishResults", full_sesi);
            Intent intent = new Intent("1");
            intent.putExtra("FULL_SESI", full_sesi);

            sendBroadcast(intent);
        }
        catch (Exception e) {
            Log.e("publishResults", e.toString());
        }

    }
}
