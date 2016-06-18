package com.shiftbuddy.Manager;

import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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

    /*OkHttpClient client = new OkHttpClient();
    String u_name;
    String p_word;
    Context ctx;*/

    public Manager() {
    }

    /*public Manager(String text, View layout) {
        openAuthenticationSnackbar(text, layout);
    }*/

    public static void openAuthenticationSnackbar(String text,View layout) {
        Snackbar snackbar = Snackbar.make(layout, text, Snackbar.LENGTH_SHORT);
        snackbar.show();
    }

    public static boolean verifyDate(String pickupDate, String deliverDate) {
        boolean returnVal = false;
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        Calendar c = Calendar.getInstance();
        Date currentDate = new Date();
        Date pickUpConvertedDate = new Date();
        Date deliveryConvertedDate = new Date();
        try {
            currentDate = dateFormat.parse(dateFormat.format(c.getTime()));
            pickUpConvertedDate = dateFormat.parse(pickupDate);
            deliveryConvertedDate = dateFormat.parse(deliverDate);
            if((pickUpConvertedDate.equals(deliveryConvertedDate) || pickUpConvertedDate.before(deliveryConvertedDate))
                  && (!pickUpConvertedDate.before(currentDate) && !deliveryConvertedDate.before(currentDate))  ) {
                returnVal = true;
            } else if (pickUpConvertedDate.after(deliveryConvertedDate)) {
                returnVal = false;
            } else if(pickUpConvertedDate.before(currentDate) || deliveryConvertedDate.before(currentDate)) {
                returnVal = false;
            }
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Log.d(TAG, "Pickup @ "+pickUpConvertedDate +"and delivery @"+ deliveryConvertedDate);
        return returnVal;
    }

    public static boolean verifyAddress(String fromAddress, String toAddress) {
        return false;
    }
}

/*public void authenticateUser(final String username, final String password,final Context context) throws IOException {
        u_name = username;
        p_word = password;
        ctx = context;
        loginThread.start();
    }

    public void stopLoginThread() {
        Log.d(TAG,"Login thread stopped");
        loginThread = new Thread();
    }*/

/*Thread loginThread = new Thread() {

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
    };*/
