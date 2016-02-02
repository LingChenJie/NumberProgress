package com.app.githubapp;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

public class MainActivity extends Activity {

    private NumberProgressBar mNumberProgressBar;
    private long mProgress;
    private long mMaxProgress;

    Handler myHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 100:
                    long progress = (long) msg.obj;
                    mNumberProgressBar.setProgress(progress);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMaxProgress = 100;

        mNumberProgressBar = (NumberProgressBar) findViewById(R.id.progress_bar);
        mNumberProgressBar.setMax(mMaxProgress);

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (mProgress < mMaxProgress) {
                    try {
                        mProgress++;
                        Message msg = new Message();
                        msg.what = 100;
                        msg.obj = mProgress;
                        myHandler.sendMessage(msg);
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
}
