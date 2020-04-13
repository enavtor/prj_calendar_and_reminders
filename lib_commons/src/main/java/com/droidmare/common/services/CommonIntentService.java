package com.droidmare.common.services;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.droidmare.common.utils.ServiceUtils;

//Common intent service declaration
//@author Eduardo on 24/05/2019.

public class CommonIntentService extends IntentService {

    protected String COMMON_TAG;

    public CommonIntentService(String serviceName) {
        super(serviceName);
    }

    @Override
    public void onHandleIntent(Intent dataIntent) {

        Log.d(COMMON_TAG, "onHandleIntent");

        ServiceUtils.initService(this);
    }
}