package com.comynt.launa;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class Receiver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) 
    {
        Intent serviceIntent = new Intent(context,ServiceData.class);
        if(!ServiceData.isRunning())
        	context.startService(serviceIntent);
    }
}
