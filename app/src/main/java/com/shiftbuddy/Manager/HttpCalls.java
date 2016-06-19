package com.shiftbuddy.Manager;

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

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.shiftbuddy.bo.MoverVehicle;
import com.shiftbuddy.bo.Shipment;

import java.io.IOException;
import java.net.ConnectException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpCalls {

    final static String TAG = HttpCalls.class.getSimpleName();

    OkHttpClient client = new OkHttpClient();
    String u_name;
    String p_word;
    Context ctx;

    Shipment shipment = new Shipment();
    MoverVehicle moverVehicle = new MoverVehicle();

    public void login(String u_name,String p_word,Context context) throws IOException {

        this.u_name = u_name;
        this.p_word = p_word;
        this.ctx = context;
        loginThread.start();
    }

    public void stopLoginThread() {
        loginThread = new Thread();
    }

    public void updateShipmentInDatabase(Shipment shipment,Context context) {

        this.ctx = context;
        this.shipment = shipment;
        shipmentThread.start();
    }

    public void updateMoverVehicleDetails(MoverVehicle moverVehicle, Context context) {
        this.ctx = context;
        this.moverVehicle = moverVehicle;
        moverThread.start();
    }

    public void registerUser(String u_name, String p_word, Context context) {

        this.u_name = u_name;
        this.p_word = p_word;
        this.ctx = context;
        registerThread.start();
    }

    //Query login table with user details from login screen
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
                loginThread = new Thread();
            } catch (Exception e) {
                e.printStackTrace();
                Intent loginIntent = new Intent(Constants.BROADCAST_LOGIN_JSON);
                loginIntent.putExtra(Constants.CREDENTIALS_FOR_USER, Constants.INERNET_ERROR);
                ctx.sendBroadcast(loginIntent);
                loginThread = new Thread();
            }

        }
    };

    //Update shipment details into shipment table in the database
    Thread shipmentThread = new Thread() {

        @Override
        public void run()
        {
            try {

            } catch (Exception e) {
                shipmentThread = new Thread();
            }
        }
    };

    //Register user details in the database
    Thread registerThread = new Thread() {

        @Override
        public void run()
        {
            try {

            } catch (Exception e) {
                registerThread = new Thread();
            }
        }
    };

    //Update mover details in the mover database
    Thread moverThread = new Thread() {

        @Override
        public void run()
        {
            try {

            } catch (Exception e) {
                moverThread = new Thread();
            }
        }
    };
}


