package com.droidmare.common.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.droidmare.common.utils.ServiceUtils;

//App's data deleter service declaration
//@author Eduardo on 24/05/2018.
public class CommonService extends Service {

    protected String TAG;

    @Override
    public void onCreate() {

        Log.d(TAG, "onCreate");

        ServiceUtils.initService(this);
    }

    @Override
    public void onDestroy() {

        Log.d(TAG, "Service destroyed");

        super.onDestroy();
    }

    @Override @Nullable
    public IBinder onBind(Intent intent) { return null; }
}