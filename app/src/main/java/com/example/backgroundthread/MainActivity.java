package com.example.backgroundthread;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.lang.ref.WeakReference;

public class MainActivity extends AppCompatActivity implements MyAsyncCallback {

    private TextView tvStatus;
    private TextView tvDesc;

    private final static String INPUT_STRING = "Halo Ini demo AsyncTask!!";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvStatus = findViewById(R.id.tv_status);
        tvDesc = findViewById(R.id.tv_desc);

        DemoAsync demoAsync = new DemoAsync(this);
        demoAsync.execute(INPUT_STRING);
    }

    @Override
    public void onPreExecute() {
        tvStatus.setText(R.string.status_pre);
        tvDesc.setText(INPUT_STRING);
    }

    @Override
    public void onPostExecute(String result) {
        tvStatus.setText(R.string.status_post);
        if (result != null) {
            tvDesc.setText(result);
        }
    }


    private static class DemoAsync extends AsyncTask<String, Void, String> {
        static final String LOG_ASYNC = "DemoAsync";
        WeakReference<MyAsyncCallback> myListener;

        DemoAsync(MyAsyncCallback myListener) {
            this.myListener = new WeakReference<>(myListener);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.d(LOG_ASYNC, "onPreExecute: play");

            MyAsyncCallback myListener = this.myListener.get();
            if (myListener != null) {
                myListener.onPreExecute();
            }
        }


        @Override
        protected String doInBackground(String... params) {
            Log.d(LOG_ASYNC, "doInBackground: play");

            String output = null;
            try {
                String input = params[0];
                output = input + " Selamat Belajar!!";
                Thread.sleep(2000);
            } catch (Exception e) {
                Log.d(LOG_ASYNC, "doInBackground: Error" + e.getMessage());
            }

            return output;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d(LOG_ASYNC, "onPostExecute: play");

            MyAsyncCallback myListener = this.myListener.get();
            if (myListener != null) {
                myListener.onPostExecute(s);
            }
        }
    }
}

interface MyAsyncCallback {
    void onPreExecute();

    void onPostExecute(String text);
}
