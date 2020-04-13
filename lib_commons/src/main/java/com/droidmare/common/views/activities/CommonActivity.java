package com.droidmare.common.views.activities;

import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;

import com.droidmare.common.utils.ToastUtils;

//Common activity declaration
//@author Eduardo on 24/05/2019.

public class CommonActivity extends AppCompatActivity {

    protected String canonicalName;

    @Override
    //Whenever a key is pressed while a Toast is being displayed, the Toast must be hidden:
    public boolean dispatchKeyEvent(KeyEvent event) {

        if (ToastUtils.cancelCurrentToast()) return true;

        else return super.dispatchKeyEvent(event);
    }
}