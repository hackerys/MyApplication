package com.jansen.myapplication.okhttp;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

/**
 * Created Jansen on 2016/5/4.
 */
public class OkHttpTest extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        OkHttpClient oc = new OkHttpClient();
        final Request mRequest = new Request.Builder()
                .url("http://www.btime.com/?from=gjl")
                .build();
        Call mCall = oc.newCall(mRequest);
        mCall.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) throws IOException {
                Log.e("onResponse", response.toString());
                Log.e("onResponse", new String(response.body().bytes()));
            }
        });

    }
}
