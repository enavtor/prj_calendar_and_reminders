package com.droidmare.common.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.droidmare.common.views.activities.CommonMainActivity;

import java.lang.ref.WeakReference;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

//Service that implements the broadcast receiver for date changes and a daemon
//to ensure that the date text is always updated on the activities that have it:
//@author Eduardo on 28/02/2019.

public class CommonDateChecker extends CommonService {

    //Whether or not this is instantiated:
    public static boolean isInstantiated = false;

    //The number of milliseconds until the next change of day takes place:
    private long millisTillDateChange;

    //The timer that will schedule the timer task to update the date text:
    private Timer updateTimer;

    //The timer job that will update the date when at the end of the day:
    private TimerTask updateTask;

    //Receiver for time and date changes (TimeChangeReceiver is an inner class of this activity):
    private TimeChangeReceiver timeChangeReceiver;

    //Method that creates an appropriate intent for the time receiver and registers it:
    private void registerTimeReceiver () {
        timeChangeReceiver = new TimeChangeReceiver();

        IntentFilter timeIntent = new IntentFilter();

        timeIntent.addAction(Intent.ACTION_TIME_TICK);
        timeIntent.addAction(Intent.ACTION_TIMEZONE_CHANGED);
        timeIntent.addAction(Intent.ACTION_TIME_CHANGED);
        timeIntent.addAction(Intent.ACTION_DATE_CHANGED);

        registerReceiver(timeChangeReceiver, timeIntent);
    }

    //The following reference is used in order to update the activities' date (located within the header):
    private static WeakReference<CommonMainActivity> mainActivityReference;
    public static void setMainActivityReference(CommonMainActivity activity) {
        mainActivityReference = new WeakReference<>(activity);
    }

    @Override
    public void onCreate() {

        super.onCreate();

        isInstantiated = true;

        calculateMillisTillDateChange();

        registerTimeReceiver();
    }

    @Override
    public void onDestroy() {
        if (timeChangeReceiver != null)
            unregisterReceiver(timeChangeReceiver);

        isInstantiated = false;

        super.onDestroy();
    }

    //The following method creates an schedule to force the date update when a change of day takes place:
    private void createUpdateSchedule() {

        if (updateTimer != null) {
            updateTask.cancel();
            updateTask = null;
            updateTimer.purge();
            updateTimer.cancel();
            updateTimer = null;
        }

        updateTimer = new Timer();

        updateTask = new TimerTask() {
            @Override
            public void run() {
                calculateMillisTillDateChange();
            }
        };

        setActivitiesDate();

        updateTimer.schedule(updateTask, millisTillDateChange);
    }

    //This method is used to update the referenced activity's date text:
    public static void setActivitiesDate () {

        if (mainActivityReference != null && mainActivityReference.get() != null)
            mainActivityReference.get().setDateText();
    }

    //This method is used to force an update of the date view when the day changes:
    private void calculateMillisTillDateChange() {

        Calendar calendar = Calendar.getInstance();

        int secondsLeft = (60 - calendar.get(Calendar.SECOND)) % 60;
        int minutesLeft = (60 - calendar.get(Calendar.MINUTE)) % 60;
        int hoursLeft = 24 - calendar.get(Calendar.HOUR_OF_DAY);

        if (secondsLeft != 0) {
            if (minutesLeft == 0) minutesLeft = 59;
            else minutesLeft = minutesLeft - 1;
        }

        if ((secondsLeft != 0 || minutesLeft != 0) && hoursLeft != 0) hoursLeft = --hoursLeft;

        millisTillDateChange = (hoursLeft * 60 * 60 * 1000) + (minutesLeft * 60 * 1000) + (secondsLeft * 1000);

        createUpdateSchedule();
    }

    //Broadcast receiver for time and date changes:
    private class TimeChangeReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {

            final String action = intent.getAction();

            if (action != null && (action.equals(Intent.ACTION_TIME_CHANGED) || action.equals(Intent.ACTION_TIMEZONE_CHANGED) || action.equals(Intent.ACTION_DATE_CHANGED)))
                calculateMillisTillDateChange();
        }
    }
}