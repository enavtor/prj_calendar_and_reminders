package com.droidmare.common.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.droidmare.common.R;

//utils for creating custom Toasts
//@author Eduardo on 09/07/2018.

public class ToastUtils {

    private static Toast toast = null;

    private static CountDownTimer toastCountDown;

    private static final int DEFAULT_TOAST_DURATION = 5;

    public static void makeCustomToast(final Context context, final String text) {
        makeCustomToast(context, text, DEFAULT_TOAST_DURATION);
    }

    //Method that creates and shows a Toast with a specific text size and duration (in seconds):
    public static void makeCustomToast(final Context context, final String text, final int seconds) {

        //If a toast is being displayed when a new toast is going to be shown, the first toast must be canceled, as well as its countdown:
        cancelCurrentToast();

        //The toast must always be created on the UI thread to avoid application malfunctions:
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            public void run() {
                toast = Toast.makeText(context, text, Toast.LENGTH_LONG);

                toast.setGravity(Gravity.CENTER, 0, 0);

                LinearLayout toastLayout = (LinearLayout) toast.getView();

                toastLayout.setBackground(null);

                Resources res = context.getResources();

                TextView toastTextView = (TextView) toastLayout.getChildAt(0);

                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) toastTextView.getLayoutParams();

                params.width = res.getDimensionPixelSize(R.dimen.dpi_600dp);

                int padding = res.getDimensionPixelSize(R.dimen.dpi_20dp);

                toastTextView.setPadding(padding, padding, padding, padding);

                toastTextView.setBackground(context.getDrawable(R.drawable.toast_background));

                toastTextView.setTextSize(ImageUtils.scalePixelValue(context, context.getResources().getDimensionPixelOffset(R.dimen.dpi_20sp)));

                toastTextView.setTextColor(Color.WHITE);

                //A countdown to display the toast with the specified duration is set:
                toastCountDown = new CountDownTimer(seconds * 1000, 1000) {

                    public void onTick(long millisUntilFinished) {
                        if (toast != null) toast.show();
                    }

                    public void onFinish() {

                        if (toast != null) {
                            toast.cancel();
                            toast = null;
                        }

                        toastCountDown = null;
                    }
                };

                //Now the toast is shown and the countdown is started:
                toast.show();
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O)
                    toastCountDown.start();
            }
        });
    }

    //Method that cancels the current toast, hiding it as soon as it is executed:
    public static boolean cancelCurrentToast() {

        boolean toastCanceled;

        if (toastCountDown != null) {
            toastCountDown.cancel();
            toastCountDown = null;
        }

        if (toastCanceled = toast != null) {
            toast.cancel();
            toast = null;
        }

        return toastCanceled;
    }
}