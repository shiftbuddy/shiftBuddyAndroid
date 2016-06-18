package com.shiftbuddy.com.shiftbuddy.Manager;


import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.shiftbuddy.com.shiftbuddy.bo.Shipment;

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

    public void login(String u_name,String p_word,Context context) throws IOException {

        this.u_name = u_name;
        this.p_word = p_word;
        this.ctx = context;
        loginThread.start();
    }

    public static void updateShipmentInDatabase(Shipment shipment) {

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
                loginThread = null;
            } catch (Exception e) {
                e.printStackTrace();
                Intent loginIntent = new Intent(Constants.BROADCAST_LOGIN_JSON);
                loginIntent.putExtra(Constants.CREDENTIALS_FOR_USER, Constants.INERNET_ERROR);
                ctx.sendBroadcast(loginIntent);
                loginThread = null;
            }

        }
    };
}


