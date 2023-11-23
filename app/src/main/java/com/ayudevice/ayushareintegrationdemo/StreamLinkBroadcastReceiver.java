package com.ayudevice.ayushareintegrationdemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/*
* Implement broadcast receiver to receive online streaming url.
*/
public class StreamLinkBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent != null) {
            String liveStreamLink = intent.getStringExtra("liveStreamLink");
        }
    }
}
