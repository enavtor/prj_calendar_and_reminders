package com.shtvsolution.common.services;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.shtvsolution.common.utils.ServiceUtils;

//App's data deleter service declaration
//@author Eduardo on 24/05/2018.
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