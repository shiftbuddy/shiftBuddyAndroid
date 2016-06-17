package com.shiftbuddy.com.shiftbuddy.Manager;


import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpCalls {

    OkHttpClient client = new OkHttpClient();

    public int login(String u_name,String p_word) throws IOException {

        RequestBody formBody = new FormBody.Builder()
                .add("txtUsername", u_name)
                .add("txtPassword",p_word)
                .build();

        Request request = new Request.Builder()
                .url(Constants.LOGIN)
                .post(formBody)
                .build();

        Response response = client.newCall(request).execute();

        return response.code();
    }
}


