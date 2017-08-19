package com.example.keshavjoshi.firebase;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.github.jlmd.animatedcircleloadingview.AnimatedCircleLoadingView;

public class splash extends Activity {

    private AnimatedCircleLoadingView animatedCircleLoadingView;
    Context context=this;
    TextView KESHAV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        KESHAV=(TextView)findViewById(R.id.textView7);
        animatedCircleLoadingView = (AnimatedCircleLoadingView)findViewById(R.id.circle_loading_view);
        startLoading();
        startPercentMockThread();

    }

    private void startLoading() {
        animatedCircleLoadingView.startDeterminate();
    }

    private void startPercentMockThread() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1500);
                    for (int i = 0; i <= 100; i++) {
                        Thread.sleep(65);
                        changePercent(i);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Intent intent = new Intent(context, login.class);
                startActivity(intent);
                finish();

            }
        };
        new Thread(runnable).start();
    }

    private void changePercent(final int percent) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                animatedCircleLoadingView.setPercent(percent);
            }
        });

    }

    public void resetLoading() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                animatedCircleLoadingView.resetLoading();
            }
        });
    }

}