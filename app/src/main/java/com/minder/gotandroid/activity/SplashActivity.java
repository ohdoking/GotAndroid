package com.minder.gotandroid.activity;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.minder.gotandroid.R;
import com.minder.gotandroid.api.ApiManager;
import com.minder.gotandroid.api.NullApiManager;
import com.minder.gotandroid.api.SeoulEngApiManager;
import com.minder.gotandroid.gps.GpsInfo;


public class SplashActivity extends Activity {
    public ApiManager apiManager;
    GpsInfo gpsInfo;

    @Override
    public void onCreate(Bundle savedInstanceState) {
    	requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity2);
        

        if(getUsingApi() == true){
        	if(getMylogcation() == null){
        		apiManager = new NullApiManager();
        	}
	        else if(getMylogcation().equals("서울특별시") || getMylogcation().equals("Seoul")){
	        	apiManager = new SeoulEngApiManager(getApplicationContext(),0);
	        }
	        apiManager.getApi();
        }

        Handler hd = new Handler();
        hd.postDelayed(new Runnable() {

            @Override
            public void run() {
                finish();       // 3 초후 이미지를 닫아버림
            }
        }, 3000);
    }
    
    
    
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
           return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    private boolean getUsingApi(){
        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        return pref.getBoolean("usingApi", false);    
        
    }
    
    private String getMylogcation(){
    	String cityName = null;
    	gpsInfo = new GpsInfo(SplashActivity.this);

        if (gpsInfo.isGetLocation()) {

            double latitude = gpsInfo.getLatitude();
            double longitude = gpsInfo.getLongitude();

            Geocoder gcd = new Geocoder(getBaseContext(), Locale.getDefault());
            List<Address> addresses;
            try {
                addresses = gcd.getFromLocation(latitude,
                		longitude, 1);
                if (addresses.size() > 0)
                    System.out.println(addresses.get(0).getLocality());
                cityName = addresses.get(0).getAdminArea();
            } catch (IOException e) {
                e.printStackTrace();
            }
             
        }
		return cityName;
    }
    
}