package com.droidmare.common.models;

//A class containing constants that define common values related to the events:
//@author Eduardo on 02/10/2019.

public class ConstantValues {

    //Definition of the different types of possible events:
    public static final String ACTIVITY_EVENT_TYPE = "ACTIVITY";
    public static final String MEDICATION_EVENT_TYPE = "MEDICATION";
    public static final String PERSONAL_EVENT_TYPE = "PERSONAL";
    public static final String DOCTOR_EVENT_TYPE = "DOCTOR";
    public static final String TEXTNOFEEDBACK_EVENT_TYPE = "TEXTNOFEEDBACK";
    public static final String STIMULUS_EVENT_TYPE = "STIMULUS";

    //Integer value for each type of repetition (daily, weekly, on alternate days, etc):
    public static final int DAILY_REPETITION = 0;
    public static final int ALTERNATE_REPETITION = 1;
    public static final int WEEKLY_REPETITION = 2;
    public static final int MONTHLY_REPETITION = 3;
    public static final int ANNUAL_REPETITION = 4;

    //Repetition type default string:
    static final String DEFAULT_REPETITION_TYPE = "{\"type\":1,\"config\":\"[1]\"}";

    //ConstantValues where the event json string will be stored within an intent:
    public static final String EVENT_JSON_FIELD = "eventJsonString";

    //Strings that store the name of the different fields that the intent and jsons containing the event info will have:
    public static final String EVENT_ID_FIELD = "eventId";
    public static final String EVENT_USER_FIELD = "userId";
    public static final String EVENT_TYPE_FIELD = "eventType";
    public static final String EVENT_DESCRIPTION_FIELD = "eventText";

    public static final String EVENT_START_DATE_FIELD = "eventStartDate";
    public static final String EVENT_MINUTE_FIELD = "eventMinute";
    public static final String EVENT_HOUR_FIELD = "eventHour";
    public static final String EVENT_DAY_FIELD = "eventDay";
    public static final String EVENT_MONTH_FIELD = "eventMonth";
    public static final String EVENT_YEAR_FIELD = "eventYear";

    public static final String EVENT_PREV_ALARMS_FIELD = "eventPrevAlarms";
    public static final String EVENT_REP_INTERVAL_FIELD = "eventRepInterval";
    public static final String EVENT_REPETITION_TYPE_FIELD = "eventRepType";
    public static final String EVENT_REPETITION_STOP_FIELD = "eventRepStopDate";

    public static final String EVENT_PENDING_OP_FIELD = "eventPendingOp";
    public static final String EVENT_LAST_UPDATE_FIELD = "eventLastUpdate";
    public static final String EVENT_TIMEOUT_FIELD = "eventTimeOut";

    //Strings that store the fields used by the reminders application:
    public static final String PACKAGE_NAME = "packageToLaunch";
    public static final String ACTIVITY_NAME = "activityToLaunch";
    public static final String DELETE_ALARM_OP = "deleteAlarm";
    public static final String PREV_ALARM_MILLIS = "prevAlarmMillis";
    public static final String IS_FIRST_RECEIVED = "isFirsReceived";
    public static final String CURRENT_REPETITION = "currentRepetition";
    public static final String NEXT_REPETITION = "nextRepetition";
}
