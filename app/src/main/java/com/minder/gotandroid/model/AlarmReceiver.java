package com.minder.gotandroid.model;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.widget.Toast;

import com.minder.gotandroid.db.MyDB;

public class AlarmReceiver extends BroadcastReceiver {

	MyDB db;
    @Override
    public void onReceive(Context context, Intent intent) {
    	db = new MyDB(context);
    	
    	int i = db.refreshDreamInToday();
    	
    	
        // For our recurring task, we'll just display a message
//        Toast.makeText(context, "refresh count noti : " + String.valueOf(i), Toast.LENGTH_SHORT).show();
        
//        Vibrator vibe = (Vibrator)context.getSystemService(Context.VIBRATOR_SERVICE);         
//      vibe.vibrate(1000);  
        
    }
}