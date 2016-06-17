package com.shiftbuddy;

/**
 * @(#) ShiftBuddy
 * <p>
 * Copyright (C) ShiftBuddy, 2016
 * All rights reserved.
 * <p>
 * This software is the proprietary information of
 * shiftbuddy ("Confidential Information").
 * Author : Dinesh Vaithyalingam Gangatharan
 */

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.shiftbuddy.Layout.MyButton;
import com.shiftbuddy.com.shiftbuddy.Manager.BlurBuilder;
import com.shiftbuddy.com.shiftbuddy.Manager.Constants;
import com.shiftbuddy.com.shiftbuddy.Manager.Manager;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();
    MainActivity activity;
    LinearLayout mainActivityLayout;
    LinearLayout shipmentScreenOpen;
    EditText userName;
    EditText password;
    MyButton login;
    MyButton register;
    Manager manager = new Manager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        blurBackground();
        initListeners();
        setFilters();

    }

    private void setFilters() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constants.BROADCAST_LOGIN_JSON);
        registerReceiver(threadInfoReceiver, filter);
    }

    private void blurBackground() {
        Bitmap originalBitmap = BitmapFactory.decodeResource(getApplicationContext().getResources(),
                R.drawable.bg_app);
        Bitmap blurredBitmap = BlurBuilder.blur(activity, originalBitmap);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            mainActivityLayout.setBackground(new BitmapDrawable( getResources(), blurredBitmap));
        }
    }

    /**
     * Dispatch onResume() to fragments.  Note that for better inter-operation
     * with older versions of the platform, at the point of this call the
     * fragments attached to the activity are <em>not</em> resumed.  This means
     * that in some cases the previous state may still be saved, not allowing
     * fragment transactions that modify the state.  To correctly interact
     * with fragments in their proper state, you should instead override
     * {@link #onResumeFragments()}.
     */

    @Override
    protected void onResume() {
        super.onResume();
        //navigateScreen.setTextColor(Color.WHITE);
    }

    private void initListeners() {

        //User login
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (userName.getText() != null && password.getText() != null) {
                    if(! userName.getText().toString().equals("") && !password.getText().toString().equals("")) {
                        try {
                            manager.authenticateUser(userName.getText().toString(), password.getText().toString(),
                                    getApplicationContext());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        openAuthenticationSnackbar(Constants.USER_VALIDATION);
                    }
                } else {
                    openAuthenticationSnackbar(Constants.USER_VALIDATION);
                }
            }
        });

        //New user register
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (userName.getText() != null && password.getText() != null) {

                } else {
                    openAuthenticationSnackbar(Constants.USER_VALIDATION);
                }
            }
        });
    }

    //SnackBar to show user that he has to enter username and password
    private void openAuthenticationSnackbar(String text) {
        Snackbar snackbar = Snackbar
                .make(mainActivityLayout,
                        "Please enter valid username and password", Snackbar.LENGTH_SHORT);
        snackbar.show();
    }


    private void init() {

        mainActivityLayout = (LinearLayout) findViewById(R.id.mainActivityLayout);
        shipmentScreenOpen = (LinearLayout) findViewById(R.id.nextScreen);
        userName = (EditText) findViewById(R.id.userName);
        password = (EditText) findViewById(R.id.passWord);
        login = (MyButton) findViewById(R.id.login);
        register = (MyButton) findViewById(R.id.register);
        activity = MainActivity.this;
    }

    private final BroadcastReceiver threadInfoReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {

            manager.stopLoginThread();
            if (intent.getAction().equals(Constants.BROADCAST_LOGIN_JSON)) {
                try {
                    Bundle bundle = intent.getExtras();
                    if (null != bundle) {
                        if(!bundle.getString(Constants.CREDENTIALS_FOR_USER).equals("")) {
                            String userObj = bundle.getString(Constants.CREDENTIALS_FOR_USER);
                            Log.d(TAG,userObj);
                            if(!userObj.equals(Constants.WRONG_USER) && !userObj.equals(Constants.INERNET_ERROR)) {
                                //Open package sending screen
                                Intent myIntent = new Intent(MainActivity.this, SelectionActivity.class);
                                startActivity(myIntent);
                            } else if(userObj.equals(Constants.INERNET_ERROR)){
                                //Open snackBar because not connecting
                                openAuthenticationSnackbar(Constants.INERNET_ERROR);
                            } else {
                                //open snackBar for anything else
                                openAuthenticationSnackbar(Constants.USER_VALIDATION);
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    };

}
