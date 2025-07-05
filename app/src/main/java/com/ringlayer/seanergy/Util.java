package com.ringlayer.seanergy;


import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.StrictMode;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

/**
 * Created by ringlayer on 24/11/18.
 */

public class Util {
    Activity mContext;

    public Util (Activity context)
    {
        mContext = context;

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy =
                    new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);}
    }

    public void askForPermission(String permission, Integer requestCode) {

        if (ContextCompat.checkSelfPermission(mContext, permission) != PackageManager.PERMISSION_GRANTED) {


            if (ActivityCompat.shouldShowRequestPermissionRationale(mContext, permission)) {
                ActivityCompat.requestPermissions(mContext, new String[]{permission}, requestCode);

            } else {

                ActivityCompat.requestPermissions(mContext, new String[]{permission}, requestCode);
            }
        }
    }
}
