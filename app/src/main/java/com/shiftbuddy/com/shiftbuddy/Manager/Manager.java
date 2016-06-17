package com.shiftbuddy.com.shiftbuddy.Manager;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.io.IOException;
import java.net.ConnectException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

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

public class Manager {

    public static final String TAG = Manager.class.getSimpleName();

    OkHttpClient client = new OkHttpClient();
    String u_name;
    String p_word;
    Context ctx;

    public Manager() {
    }

    public void authenticateUser(final String username, final String password,final Context context) throws IOException {
        u_name = username;
        p_word = password;
        ctx = context;
        loginThread.start();
    }

    public void stopLoginThread() {
        Log.d(TAG,"Login thread stopped");
        loginThread = new Thread();
    }

    Thread loginThread = new Thread() {

        @Override
        public void run()
        {
            try  {
                Log.d(TAG, "Login thread started");
                RequestBody formBody = new FormBody.Builder()
                        .add("txtUsername", u_name)
                        .add("txtPassword",p_word)
                        .build();

                Request request = new Request.Builder()
                        .url(Constants.LOGIN)
                        .post(formBody)
                        .build();

                Response response = client.newCall(request).execute();
                if(response.isSuccessful()) {
                    //JSONObject login = new JSONObject(response.body().string().toString());
                    Intent loginIntent = new Intent(Constants.BROADCAST_LOGIN_JSON);
                    loginIntent.putExtra(Constants.CREDENTIALS_FOR_USER, response.body().string().toString());
                    ctx.sendBroadcast(loginIntent);
                } else {
                    Log.d(TAG,"Issue with response: "+response.code());
                }
            } catch (ConnectException ce) {
                ce.printStackTrace();
                Intent loginIntent = new Intent(Constants.BROADCAST_LOGIN_JSON);
                loginIntent.putExtra(Constants.CREDENTIALS_FOR_USER, Constants.INERNET_ERROR);
                ctx.sendBroadcast(loginIntent);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    };




}
