package com.shtvsolution.common.views.activities;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shtvsolution.common.R;
import com.shtvsolution.common.services.CommonUserData;
import com.shtvsolution.common.utils.STBUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class CommonMainActivity extends CommonActivity {

    protected BitmapDrawable defaultAvatar;

    protected RelativeLayout loadingLayout;
    private TextView loadingText;

    protected static final int IR_RED = 0;
    protected static final int IR_GREEN = 1;
    protected static final int IR_YELLOW= 2;
    protected static final int IR_BLUE = 3;

    protected RelativeLayout irRedButton;
    private TextView irRedText;

    protected RelativeLayout irGreenButton;
    private TextView irGreenText;

    protected RelativeLayout irYellowButton;
    private TextView irYellowText;

    protected RelativeLayout irBlueButton;
    private TextView irBlueText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main_common);

        irRedButton = findViewById(R.id.ir_red_button);
        irRedText = findViewById(R.id.ir_red_text);

        irGreenButton = findViewById(R.id.ir_green_button);
        irGreenText = findViewById(R.id.ir_green_text);

        irYellowButton = findViewById(R.id.ir_yellow_button);
        irYellowText = findViewById(R.id.ir_yellow_text);

        irBlueButton = findViewById(R.id.ir_blue_button);
        irBlueText = findViewById(R.id.ir_blue_text);

        findViewById(R.id.virtual_back_layout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_BACK));
                dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_BACK));
            }
        });

        loadingLayout = findViewById(R.id.layout_loading);
        loadingText = findViewById(R.id.text_layout_loading);

        defaultAvatar = (BitmapDrawable) getDrawable(R.drawable.photo);

        setVersionNumber();
    }

    protected void setIrButtonText(int irCode, String irText) {
        RelativeLayout irButton = null;

        switch (irCode) {
            case IR_RED:
                irButton = irRedButton;
                irRedText.setText(irText);
                break;
            case IR_GREEN:
                irButton = irGreenButton;
                irGreenText.setText(irText);
                break;
            case IR_YELLOW:
                irButton = irYellowButton;
                irYellowText.setText(irText);
                break;
            case IR_BLUE:
                irButton = irBlueButton;
                irBlueText.setText(irText);
                break;
        }

        if (irButton != null) irButton.setVisibility(View.VISIBLE);
    }

    protected void includeLayout(int containerId, int layoutToInclude) {
        ViewStub viewStub = findViewById(containerId);
        viewStub.setLayoutResource(layoutToInclude);
        viewStub.inflate();
    }

    //Method that displays the loading screen whenever it is called:
    public void displayLoadingScreen(final String textToDisplay) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                loadingText.setText(textToDisplay);
                loadingLayout.setVisibility(View.VISIBLE);
            }
        });
    }
    //Method that hides the loading screen whenever it is called:
    public void hideLoadingScreen() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                loadingLayout.setVisibility(View.GONE);
            }
        });
    }

    //Method that configures the user information view:
    public void setUserInformation () {
        CommonUserData.readSharedPrefs(getApplicationContext(), CommonUserData.class.getCanonicalName());

        final ImageView avatar = findViewById(R.id.user_photo);
        final TextView name = findViewById(R.id.user_name);
        final TextView id = findViewById(R.id.user_id);

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (CommonUserData.getUserId() != null) {
                    avatar.setImageBitmap(CommonUserData.getDecodedAvatar());
                    name.setText(CommonUserData.getUserName());
                    id.setText(CommonUserData.getUserNickname());
                }

                else {
                    avatar.setImageBitmap(defaultAvatar.getBitmap());
                    name.setText(getString(R.string.no_user));
                    id.setText(getString(R.string.no_id));
                }
            }
        });
    }

    //Method for setting the date text displayed on the upper right corner of the application:
    public void setDateText () {

        final Calendar calendar = Calendar.getInstance();
        long time =  calendar.getTimeInMillis();
        Locale localeDate = Locale.getDefault();

        SimpleDateFormat simpleDate = new SimpleDateFormat(getString(R.string.date),localeDate);
        String date = simpleDate.format(time);

        final String upperDate = date.substring(0,1).toUpperCase() + date.substring(1);

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                TextView dateText = findViewById(R.id.txt_date);
                dateText.setText(upperDate);
            }
        });
    }

    protected void setVersionNumber(){
        TextView appVersion = findViewById(R.id.version_number);
        appVersion.append(STBUtils.getAppVersionName(getApplicationContext()));
    }
}