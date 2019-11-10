package com.droidmare.common.views.activities;

import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;

import com.droidmare.common.utils.ToastUtils;

public class CommonActivity extends AppCompatActivity {

    protected String canonicalName;

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {

        if (ToastUtils.cancelCurrentToast()) return true;

        else return super.dispatchKeyEvent(event);
    }
}