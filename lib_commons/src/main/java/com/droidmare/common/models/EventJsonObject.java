package com.droidmare.common.models;

import android.util.Log;

import com.droidmare.common.utils.DateUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

//Model for an event json object (a specialization of JSONObject for building event json objects)
//@author Eduardo on 02/11/2019.

public class EventJsonObject extends JSONObject {

    private static final String TAG = EventJsonObject.class.getCanonicalName();

    //In order to internally mange the JSONException thrown by the JSONObject class constructor, the EventJsonObjects are generated by the following static method:
    public static EventJsonObject createEventJson(String jsonString) {

        EventJsonObject newEventJson;

        if (jsonString != null) try {
            newEventJson = new EventJsonObject(jsonString);

            //If the event json has its start date defined as a long, it must be stored as minute, hour, day, month and year separately:
            if (newEventJson.has(ConstantValues.EVENT_START_DATE_FIELD) && !newEventJson.has(ConstantValues.EVENT_HOUR_FIELD)) {
                long eventStartDate = newEventJson.getLong(ConstantValues.EVENT_START_DATE_FIELD);

                int[] dateArray = DateUtils.transformFromMillis(eventStartDate);

                newEventJson.put(ConstantValues.EVENT_HOUR_FIELD, dateArray[DateUtils.HOUR]);
                newEventJson.put(ConstantValues.EVENT_MINUTE_FIELD, dateArray[DateUtils.MINUTE]);
                newEventJson.put(ConstantValues.EVENT_DAY_FIELD, dateArray[DateUtils.DAY]);
                newEventJson.put(ConstantValues.EVENT_MONTH_FIELD, dateArray[DateUtils.MONTH]);
                newEventJson.put(ConstantValues.EVENT_YEAR_FIELD, dateArray[DateUtils.YEAR]);
            }
            
            //On the contrary, if the event has no start date defined as a long, it must be set:
            else {
                int[] dateArray = new int[5];

                dateArray[DateUtils.HOUR] = newEventJson.getInt(ConstantValues.EVENT_HOUR_FIELD);
                dateArray[DateUtils.MINUTE] = newEventJson.getInt(ConstantValues.EVENT_MINUTE_FIELD);
                dateArray[DateUtils.DAY] = newEventJson.getInt(ConstantValues.EVENT_DAY_FIELD);
                dateArray[DateUtils.MONTH] = newEventJson.getInt(ConstantValues.EVENT_MONTH_FIELD);
                dateArray[DateUtils.YEAR] = newEventJson.getInt(ConstantValues.EVENT_YEAR_FIELD);

                newEventJson.put(ConstantValues.EVENT_START_DATE_FIELD, DateUtils.getMillisFromArray(dateArray));
            }
        } catch (JSONException jsonException) {
            Log.e(TAG, "createEventJson. JSONException: " + jsonException.getMessage());
            return new EventJsonObject();
        }

        else newEventJson = new EventJsonObject();

        return newEventJson;
    }

    public EventJsonObject() { super(); }

    private EventJsonObject(String jsonString) throws JSONException {
        super(jsonString);
    }

    //The four following methods override the parent methods in order to internally mange the JSONException thrown by them:
    @Override
    public JSONObject put(String name, int value) {
        try {
            super.put(name, value);
        } catch (JSONException jsonException) {
            Log.e(TAG, "putInt. JSONException: " + jsonException.getMessage());
            return this;
        }
        return this;
    }

    @Override
    public JSONObject put(String name, long value) {
        try {
            super.put(name, value);
        } catch (JSONException jsonException) {
            Log.e(TAG, "putLong. JSONException: " + jsonException.getMessage());
            return this;
        }
        return this;
    }

    @Override
    public JSONObject put(String name, boolean value) {
        try {
            super.put(name, value);
        } catch (JSONException jsonException) {
            Log.e(TAG, "putBoolean. JSONException: " + jsonException.getMessage());
            return this;
        }
        return this;
    }

    @Override
    public JSONObject put(String name, Object value) {
        try {
            super.put(name, value);
        } catch (JSONException jsonException) {
            Log.e(TAG, "putObject. JSONException: " + jsonException.getMessage());
            return this;
        }
        return this;
    }

    //The four following methods are used to extract specific fields from the EventJsonObject, adding the capacity to set a default value to those fields in case they are not present:
    public int getInt(String name, int defaultValue) {

        int fieldValue = defaultValue;

        if (this.has(name)) try {
            fieldValue = this.getInt(name);
        } catch (JSONException jsonException) {
            Log.e(TAG, "getInt. JSONException: " + jsonException.getMessage());
            return fieldValue;
        }

        return fieldValue;
    }

    public long getLong(String name, long defaultValue) {

        long fieldValue = defaultValue;

        if (this.has(name)) try {
            fieldValue = this.getLong(name);
        } catch (JSONException jsonException) {
            Log.e(TAG, "getLong. JSONException: " + jsonException.getMessage());
            return fieldValue;
        }

        return fieldValue;
    }

    public boolean getBoolean(String name, Boolean defaultValue) {

        boolean fieldValue = defaultValue;

        if (this.has(name)) try {
            fieldValue = this.getBoolean(name);
        } catch (JSONException jsonException) {
            Log.e(TAG, "getBoolean. JSONException: " + jsonException.getMessage());
            return fieldValue;
        }

        return fieldValue;
    }

    public String getString(String name, String defaultValue) {

        String fieldValue;

        if (this.has(name)) try {
            fieldValue = this.getString(name);
            if (defaultValue.equals(ConstantValues.DEFAULT_REPETITION_TYPE) && fieldValue.equals(""))
                fieldValue = defaultValue;
        } catch (JSONException jsonException) {
            Log.e(TAG, "getString. JSONException: " + jsonException.getMessage());
            return defaultValue;
        }

        else fieldValue = defaultValue;

        return fieldValue;
    }

    //This method is used to obtain the JSONArray that defines the previous alarms for an event:
    public JSONArray getPreviousAlarmsArray() {

        JSONArray prevAlarmsArray;

        try {
            //The JSONArray is stored as a String, so it must be transformed before being returned:
            prevAlarmsArray = new JSONArray(this.getString(ConstantValues.EVENT_PREV_ALARMS_FIELD, "[]"));
        } catch (JSONException jse) {
            Log.e(TAG, "getPreviousAlarmsArray. JSONException: " + jse.getMessage());
            return new JSONArray();
        }

        return prevAlarmsArray;
    }

    //This method is used to obtain the JSONObject that defines the repetition tye and configuration for an event:
    public JSONObject getRepetitionTypeJson() {

        JSONObject repetitionType = new JSONObject();

        try {
            //The JSONObject is stored as a String, so it must be transformed before being returned:
            repetitionType = new JSONObject(this.getString(ConstantValues.EVENT_REPETITION_TYPE_FIELD, ConstantValues.DEFAULT_REPETITION_TYPE));
        } catch (JSONException jse) {
            Log.e(TAG, "getRepetitionTypeJson. JSONException: " + jse.getMessage());

        }

        return repetitionType;
    }

    //This method transforms the repetition config string, inside repetitionType, into an array list:
    public static ArrayList<Integer> getRepetitionConfigArray(String config) {

        ArrayList<Integer> configList = new ArrayList<>();

        //Since the variable config is an array wrapped inside a String, its length must be at least three characters: [x]:
        if (config.length() > 2) {

            //Before proceeding, all the blacks, in case there are any, must be replaced:
            config = config.replace(" ", "");

            //Now the final array is build by extracting and transforming each element within the config string:
            String [] configArray = config.substring(1, config.length() - 1).split(",");

            for (String configElement: configArray)
                configList.add(Integer.valueOf(configElement));
        }

        return configList;
    }

    //Method that returns the current repetition milliseconds for the reminder containing this EventJsonObject:
    public long getReminderMillis() {

        Calendar calendar = Calendar.getInstance();

        int minute = this.getInt(ConstantValues.EVENT_MINUTE_FIELD, -1);
        int hour = this.getInt(ConstantValues.EVENT_HOUR_FIELD, -1);
        int day = this.getInt(ConstantValues.EVENT_DAY_FIELD, -1);
        int month = this.getInt(ConstantValues.EVENT_MONTH_FIELD, -1);
        int year = this.getInt(ConstantValues.EVENT_YEAR_FIELD, -1);

        long currentRepetition = this.getLong(ConstantValues.NEXT_REPETITION, -1);

        if (currentRepetition != -1) {

            int[] auxDateArray = DateUtils.transformFromMillis(currentRepetition);

            minute = auxDateArray[DateUtils.MINUTE];
            hour = auxDateArray[DateUtils.HOUR];
            day = auxDateArray[DateUtils.DAY];
            month = auxDateArray[DateUtils.MONTH];
            year = auxDateArray[DateUtils.YEAR];
        }

        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.YEAR, year);

        return calendar.getTimeInMillis();
    }
}
