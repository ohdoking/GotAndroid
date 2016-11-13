package com.minder.gotandroid.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.minder.gotandroid.R;
import com.minder.gotandroid.api.ApiManager;
import com.minder.gotandroid.api.NullApiManager;
import com.minder.gotandroid.api.SeoulApiManager;
import com.minder.gotandroid.db.MyDB;
import com.minder.gotandroid.gps.GpsInfo;
import com.minder.gotandroid.list.SwipeActivity;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * Created by ohdok on 2016-11-13.
 */
public class SettingActivity extends Activity {

	MyDB myDB;
	Intent retIntent;

	GpsInfo gpsInfo;
	public ApiManager apiManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);
		retIntent = new Intent();

		myDB = new MyDB(getApplicationContext());

		RelativeLayout layout_mail = (RelativeLayout) findViewById(R.id.layout_mail);
		RelativeLayout layout_tutorial = (RelativeLayout) findViewById(R.id.layout_tutorial);
		RelativeLayout layout_data = (RelativeLayout) findViewById(R.id.layout_data);
		RelativeLayout layout_movie = (RelativeLayout) findViewById(R.id.layout_movie);
		RelativeLayout layout_made = (RelativeLayout) findViewById(R.id.layout_made);


		ImageButton logoBtn = (ImageButton) findViewById(R.id.logoBtn);
		ImageButton mailBtn = (ImageButton) findViewById(R.id.mailBtn);
		ImageButton tutorialBtn = (ImageButton) findViewById(R.id.tutorialBtn);
		ImageButton movieBtn = (ImageButton) findViewById(R.id.movieBtn);
		ToggleButton dataSwitch = (ToggleButton) findViewById(R.id.dataSwitch);

		TextView tv_currentVersion = (TextView) findViewById(R.id.tv_currentVersion);
//		TextView tv_newestVersion = (TextView) findViewById(R.id.tv_newestVersion);

		PackageManager manager = this.getPackageManager();
		try {
			PackageInfo info = manager.getPackageInfo(this.getPackageName(), 0);
			//String packageName = info.packageName;
			//int versionCode = info.versionCode;
			String versionName = info.versionName;

			tv_currentVersion.setText("v "+versionName);
//			tv_newestVersion.setText("v "+versionName);

		} catch (PackageManager.NameNotFoundException e) {
			// TODO Auto-generated catch block
		}



		if (getUsingApi() == true) {
			dataSwitch.setChecked(true);
		} else {
			dataSwitch.setChecked(false);
		}

		LayoutInflater inflater = (LayoutInflater) getActionBar().getThemedContext()
				.getSystemService(LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.setting_actionbar, null);

		ImageButton iv_back = (ImageButton) view.findViewById(R.id.iv_back);

		ActionBar.LayoutParams params = new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT,
				ActionBar.LayoutParams.MATCH_PARENT, Gravity.LEFT);
		getActionBar().setDisplayShowTitleEnabled(false);
		getActionBar().setIcon(new ColorDrawable(getResources().getColor(android.R.color.transparent)));
		getActionBar().setDisplayShowCustomEnabled(true);
		getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ffffff")));
		getActionBar().setCustomView(view, params);

		layout_mail.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Uri uri = Uri.parse("mailto:sksms4687@naver.com");
				Intent it = new Intent(Intent.ACTION_SENDTO, uri);
				startActivity(Intent.createChooser(it, "Choose an Email client"));
			}
		});

		layout_tutorial.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent it = new Intent(SettingActivity.this, ViewPagerActivity.class);
				startActivity(it);
			}
		});

		layout_movie.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent it = new Intent(Intent.ACTION_VIEW, Uri.parse("https://youtu.be/7z0ETQUgYDQ"));
				startActivity(it);
			}
		});

		layout_made.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(SettingActivity.this, MadeActivity.class);
				startActivity(i);
			}
		});


		iv_back.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});

		logoBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent it = new Intent(SettingActivity.this, SwipeActivity.class);
				startActivity(it);
			}
		});

		mailBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Uri uri = Uri.parse("mailto:sksms4687@naver.com");
				Intent it = new Intent(Intent.ACTION_SENDTO, uri);
				startActivity(Intent.createChooser(it, "Choose an Email client"));

			}
		});

		tutorialBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent it = new Intent(SettingActivity.this, ViewPagerActivity.class);
				startActivity(it);
			}
		});

		movieBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent it = new Intent(Intent.ACTION_VIEW, Uri.parse("https://youtu.be/7z0ETQUgYDQ"));
				startActivity(it);
			}
		});

		dataSwitch.setOnCheckedChangeListener(new Switch.OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (getUsingApi() == true) {
					saveUsingApi(false);
					myDB.deleteTable();
					retIntent.putExtra("usingApi", "false");
				} else {
					saveUsingApi(true);

					if (getMylogcation() == null) {
						apiManager = new NullApiManager();
					} else if (getMylogcation().equals("����Ư����") || getMylogcation().equals("Seoul")) {
						apiManager = new SeoulApiManager(SettingActivity.this, 1);
					}
					apiManager.getApi();
				}

			}

		});

	}

	private String getMylogcation() {
		String cityName = null;
		gpsInfo = new GpsInfo(SettingActivity.this);
		// GPS �������� ��������
		if (gpsInfo.isGetLocation()) {

			double latitude = gpsInfo.getLatitude();
			double longitude = gpsInfo.getLongitude();

			/*---------- ���ø� �������� ----------- */
			Geocoder gcd = new Geocoder(getBaseContext(), Locale.getDefault());
			List<Address> addresses;
			try {
				addresses = gcd.getFromLocation(latitude, longitude, 1);
				if (addresses.size() > 0)
					System.out.println(addresses.get(0).getLocality());
				cityName = addresses.get(0).getAdminArea();
			} catch (IOException e) {
				e.printStackTrace();
			}

			String s = longitude + "\n" + latitude + "\n\n������ ���� ���ø� : " + cityName;

			Log.i("ohdokingLocation", s);

		} else {
			// GPS �� �����Ҽ� �����Ƿ�
			// gpsInfo.showSettingsAlert();
		}
		return cityName;
	}

	/**
	 * SharedPrefernce
	 *
	 * @return
	 */

	// api �޾ƿ��� ����
	private boolean getUsingApi() {
		SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
		return pref.getBoolean("usingApi", false);

	}

	// api �޾ƿ��� ���� �����ϱ�
	private void saveUsingApi(Boolean value) {
		SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
		SharedPreferences.Editor editor = pref.edit();
		editor.putBoolean("usingApi", value);
		editor.commit();
	}

}
