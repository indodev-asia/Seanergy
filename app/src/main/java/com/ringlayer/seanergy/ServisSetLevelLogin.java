package com.ringlayer.seanergy;

import android.app.IntentService;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

/**
 * Created by ringlayer on 01/05/19.
 */

public class ServisSetLevelLogin extends IntentService {
    public static final String PARAM_OUT_MSG = "";
    private FileSystemOp fso = new FileSystemOp();
    public ServisSetLevelLogin() {
        super("ServisSetLevelLogin");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        try {


        }
        catch (Exception e) {
            Log.e("error", e.toString());
        }
    }

    @Override
    public IBinder onBind(Intent i) {
        return null;
    }

}
