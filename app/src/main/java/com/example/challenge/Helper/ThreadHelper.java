package com.example.challenge.Helper;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

public class ThreadHelper {
    public void ToastMessageHandler(Context context, String message){
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context,
                        message, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
