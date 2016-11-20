package com.minder.gotandroid.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.minder.gotandroid.R;
import com.minder.gotandroid.db.MyDB;
import com.minder.gotandroid.dialog.DialogActivity;
import com.minder.gotandroid.dto.Dream;
import com.minder.gotandroid.list.ListAdapter;
import com.minder.gotandroid.list.ListViewSwipeGesture;
import com.minder.gotandroid.model.AlarmReceiver;
import com.minder.gotandroid.model.GPSTracker;
import com.minder.gotandroid.model.PushEvent;
import com.skp.Tmap.TMapMarkerItem;
import com.skp.Tmap.TMapPoint;
import com.skp.Tmap.TMapView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.minder.gotandroid.weather.api.APIRequest;
import com.minder.gotandroid.weather.common.PlanetXSDKConstants;
import com.minder.gotandroid.weather.common.PlanetXSDKException;
import com.minder.gotandroid.weather.common.RequestBundle;
import com.minder.gotandroid.weather.common.RequestListener;
import com.minder.gotandroid.weather.common.ResponseMessage;

/**
 * Created by ohdok on 2016-11-13.
 */
public class SwipeActivity extends Activity {

	public final int SETTING_ACTIVITY = 11;
	public final int DIALOG_ACTIVITY = 12;

	private ListView cmn_list_view;
	private ListAdapter listAdapter;
	private List<Dream> listdata;

	MyDB db;
	public static int splash = 0;

	public Map<Integer, Marker> markerList;
	private GoogleMap map;

	SearchView searchView;
	ImageView addtutorial;
	private CheckBox checkbox_ask;
	ProgressDialog pDialog;

	LinearLayout dummyLayer;
	private InputMethodManager imm;
	GPSTracker gpsTracker;


	private PendingIntent pendingIntent;

	private BackPressCloseHandler backPressCloseHandler;

	//	add tmap
	LinearLayout contentView;
	TMapView tmapview;


	// add weather
	TextView tvCounty;
	TextView tvTmax;
	TextView tvTmin;
	APIRequest api;
	RequestBundle requestBundle;
	String URL = "http://apis.skplanetx.com/weather/summary";
	Map<String, Object> param;
	String hndResult="";
	String resultCounty="";
	String resultCode="";
	String resultTmax="";
	String resultTmin="";
	ImageView imWeather;
	Handler msgHandler = new Handler() {
		public void dispatchMessage(Message msg) {
			// tvCounty.setText(resultCounty);
			tvTmax.setText(resultTmax);
			tvTmin.setText(resultTmin);
			Log.e("tag", resultCounty);
			switch (resultCounty) {
				case "도봉구":
					tvCounty.setText("Dobong-gu");
					break;
				case "노원구":
					tvCounty.setText("Nowon-gu");
					break;
				case "강북구":
					tvCounty.setText("Gangbuk-gu");
					break;
				case "성북구":
					tvCounty.setText("Seongbuk-gu");
					break;
				case "중랑구":
					tvCounty.setText("Jungnang-gu");
					break;
				case "동대문구":
					tvCounty.setText("Dongdaemun-gu");
					break;
				case "종로구":
					tvCounty.setText("Jongno-gu");
					break;
				case "은평구":
					tvCounty.setText("Eunpyeong-gu");
					break;
				case "서대문구":
					tvCounty.setText("Seodaemun-gu");
					break;
				case "중구":
					tvCounty.setText("Jung-gu");
					break;
				case "성동구":
					tvCounty.setText("Seongdong-gu");
					break;
				case "광진구":
					tvCounty.setText("Gwangjin-gu");
					break;
				case "마포구":
					tvCounty.setText("Mapo-gu");
					break;
				case "용산구":
					tvCounty.setText("Yongsan-gu");
					break;
				case "강동구":
					tvCounty.setText("Gangdong-gu");
					break;
				case "송파구":
					tvCounty.setText("Songpa-gu");
					break;
				case "강남구":
					tvCounty.setText("Gangnam-gu");
					break;
				case "서초구":
					tvCounty.setText("Seocho-gu");
					break;
				case "관악구":
					tvCounty.setText("Gwanak-gu");
					break;
				case "동작구":
					tvCounty.setText("Dongjak-gu");
					break;
				case "금천구":
					tvCounty.setText("Geumcheon-gu");
					break;
				case "영등포구":
					tvCounty.setText("Yeongdeungpo-gu");
					break;
				case "구로구":
					tvCounty.setText("Guro-gu");
					break;
				case "양천구":
					tvCounty.setText("Yangcheon-gu");
					break;
				case "강서구":
					tvCounty.setText("Gangseo-gu");
					break;
				default:
					tvCounty.setText("Jung-gu");
					break;
			}
			switch (resultCode) {
				case "SKY_D01":
					// 맑음
					Bitmap sky01 = BitmapFactory.decodeResource(getResources(),R.drawable.weather_01_1);
					imWeather.setImageBitmap(sky01);
					break;
				case "SKY_D02":
					// 구름조금
					Bitmap sky02 = BitmapFactory.decodeResource(getResources(),R.drawable.weather_02_1);
					imWeather.setImageBitmap(sky02);
					break;
				case "SKY_D03":
					// 구름많음
					Bitmap sky03 = BitmapFactory.decodeResource(getResources(),R.drawable.weather_03_1);
					imWeather.setImageBitmap(sky03);
					break;
				case "SKY_D04":
					// 흐림
					Bitmap sky04 = BitmapFactory.decodeResource(getResources(),R.drawable.weather_04);
					imWeather.setImageBitmap(sky04);
					break;
				case "SKY_D05":
					// 비
					Bitmap sky05 = BitmapFactory.decodeResource(getResources(),R.drawable.weather_05);
					imWeather.setImageBitmap(sky05);
					break;
				case "SKY_D06":
					// 눈
					Bitmap sky06 = BitmapFactory.decodeResource(getResources(),R.drawable.weather_06);
					imWeather.setImageBitmap(sky06);
					break;
				case "SKY_D07":
					// 비 또는 눈
					Bitmap sky07 = BitmapFactory.decodeResource(getResources(),R.drawable.weather_07);
					imWeather.setImageBitmap(sky07);
					break;
				default:
					break;
			}
		}
	};


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_swipe);

		contentView  = (LinearLayout)findViewById(R.id.dummyLayout);

		Intent alarmIntent = new Intent(SwipeActivity.this, AlarmReceiver.class);
		pendingIntent = PendingIntent.getBroadcast(SwipeActivity.this, 0,
				alarmIntent, 0);
		startAt10();
		db = new MyDB(getApplicationContext());

		imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

		if (splash == 0) {

			startActivity(new Intent(this, SplashActivity.class));
			splash++;
		}

		serviceStart();
		backPressCloseHandler = new BackPressCloseHandler(this);

		LayoutInflater inflater = (LayoutInflater) getActionBar()
				.getThemedContext().getSystemService(LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.main_actionbar, null);

		ImageButton settingImageView = (ImageButton) view
				.findViewById(R.id.settingImg);

		ActionBar.LayoutParams params = new ActionBar.LayoutParams(
				ActionBar.LayoutParams.MATCH_PARENT,
				ActionBar.LayoutParams.MATCH_PARENT, Gravity.LEFT);
		getActionBar().setDisplayShowTitleEnabled(false);
		getActionBar().setIcon(
				new ColorDrawable(getResources().getColor(
						android.R.color.transparent)));
		getActionBar().setDisplayShowCustomEnabled(true);
		getActionBar().setBackgroundDrawable(
				getResources().getDrawable(R.drawable.top_navi));
		getActionBar().setCustomView(view, params);

		cmn_list_view = (ListView) findViewById(R.id.cmn_list_view);
		dummyLayer = (LinearLayout) findViewById(R.id.dummyLayout);
		listdata = new ArrayList<Dream>();
		InitializeValues();
		// final ListViewSwipeGesture touchListener = new ListViewSwipeGesture(cmn_list_view, swipeListener, this);
		// touchListener.SwipeType = ListViewSwipeGesture.Double; // Set two

		settingImageView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(SwipeActivity.this, SettingActivity.class);
				startActivityForResult(i,SETTING_ACTIVITY);
			}
		});


		addtutorial = (ImageView) findViewById(R.id.addtutorial);
		if (cmn_list_view.getCount() == 0) {
			addtutorial.setVisibility(View.VISIBLE);
		} else {
			addtutorial.setVisibility(View.INVISIBLE);
		}

		cmn_list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
				dummyLayer.setVisibility(View.VISIBLE);
				imm.hideSoftInputFromWindow(searchView.getWindowToken(), 0);
				Dream dream = db.getDreamId(listdata.get(position).getId());

				//@TODO map move
				tmapview.setCenterPoint(dream.getLon(), dream.getLat(), true);
			}
		});


		chkGpsService();

		// tmap init
		tmapview = new TMapView(this);
		tmapview.setSKPMapApiKey(getResources().getString(R.string.t_map_key));

		contentView.removeAllViews();
		contentView.addView(tmapview, new LinearLayout.LayoutParams(RelativeLayout.LayoutParams.FILL_PARENT, RelativeLayout.LayoutParams.FILL_PARENT));

		initMap();

		// add weather
		commWithOpenApiServer();
		tvCounty = (TextView) findViewById(R.id.tvCounty);
		tvTmax = (TextView) findViewById(R.id.tvTmax);
		tvTmin = (TextView) findViewById(R.id.tvTmin);
		imWeather = (ImageView) findViewById(R.id.imWeather);
	}

	private boolean chkGpsService() {

		String gps = android.provider.Settings.Secure.getString(
				getContentResolver(),
				android.provider.Settings.Secure.LOCATION_PROVIDERS_ALLOWED);


		if (!(gps.matches(".*gps.*") && gps.matches(".*network.*"))) {

			AlertDialog.Builder gsDialog = new AlertDialog.Builder(this);
			gsDialog.setTitle(R.string.title_gps_setting);
			gsDialog.setMessage(R.string.memo_gps_setting);

			gsDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
				@Override
				public void onDismiss(DialogInterface dialog) {
					if (getUsingApi() == false) {
						Toast.makeText(getApplicationContext(), R.string.toast_api_on_off,Toast.LENGTH_LONG).show();
					}
				}
			});

			gsDialog.setPositiveButton(R.string.setting,
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							Intent intent = new Intent(
									android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
							intent.addCategory(Intent.CATEGORY_DEFAULT);
							startActivity(intent);
						}
					})
					.setNegativeButton(R.string.cancel,
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
													int which) {
									return;
								}
							}).create().show();
			return false;

		} else {
			if (getUsingApi() == false) {
				Toast.makeText(getApplicationContext(),R.string.toast_api_on_off,Toast.LENGTH_LONG).show();
			}
			return true;
		}
	}

	public void initMap() {

		if (tmapview != null) {

			gpsTracker = new GPSTracker(getApplicationContext());

			markerList = new HashMap<Integer, Marker>();

			if (!listdata.isEmpty()) {
				//@TODO clear add
				for (Dream dream : listdata) {

					int id = 0;

					if (dream.getCategory().equals("food")) {
						id = R.drawable.mapicon1;
					} else if (dream.getCategory().equals("event")) {
						id = R.drawable.mapicon2;

					} else if (dream.getCategory().equals("festival")) {

						id = R.drawable.mapicon3;
					} else if (dream.getCategory().equals("tour")) {

						id = R.drawable.mapicon4;
					} else {
						id = R.drawable.mapicon5;
					}

					Log.i("test123",dream.getLat()+"");
					TMapMarkerItem markeritem2 = new TMapMarkerItem();
					TMapPoint tpoint = new TMapPoint(dream.getLat(), dream.getLon());
					markeritem2.setTMapPoint(tpoint);
					Bitmap bitmap = BitmapFactory.decodeResource(getResources(),id);
					markeritem2.setIcon(bitmap);
					markeritem2.setID(dream.getId().toString());
					markeritem2.setPosition(0.5f,1.0f);
					markeritem2.setCanShowCallout(true);
					markeritem2.setName(dream.getTodo());


					markeritem2.setCalloutTitle(dream.getTodo());
					markeritem2.setCalloutSubTitle(dream.getMemo());
					tmapview.addMarkerItem(dream.getId().toString(), markeritem2);
				}
			}

			Location location = gpsTracker.getLocation();
			if (location != null) {
				// Current Location
				tmapview.setCenterPoint(location.getLongitude(), location.getLatitude(), true);
				tmapview.setTrackingMode(true);

			}
		}
	}

	private void InitializeValues() {

		Set<String> hashset = getPreferences();

		ArrayList<String> list = new ArrayList<String>(hashset);

		listdata.clear();
		listdata.addAll(db.getDreamCate(list));

		for (Dream string : listdata) {
			Log.i("ohdoking12", string.getCategory() + "//" + string.getTodo());
		}
		listAdapter = new ListAdapter(this, listdata);
		cmn_list_view.setAdapter(listAdapter);

	}

	private Set getPreferences() {
		SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
		Set<String> hash = new HashSet<String>();
		hash.add("tour");
		hash.add("festival");
		hash.add("event");
		hash.add("food");
		hash.add("etc");

		return pref.getStringSet("categoryList", hash);
	}

	public void serviceStart() {
		Intent i = new Intent(SwipeActivity.this, PushEvent.class);
		startService(i);
	}

	@Override
	public void onResume() {
		super.onResume();

		addtutorial = (ImageView) findViewById(R.id.addtutorial);

		db = new MyDB(getApplicationContext());
		InitializeValues();

		listAdapter.notifyDataSetChanged();
		if (cmn_list_view.getCount() == 0) {
			addtutorial.setVisibility(View.VISIBLE);
		} else {
			addtutorial.setVisibility(View.INVISIBLE);
		}
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);

		SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
		searchView = (SearchView) menu.findItem(R.id.action_search)
				.getActionView();

		searchView.setSearchableInfo(searchManager
				.getSearchableInfo(getComponentName()));
		searchView.setIconifiedByDefault(true);

		int searchImgId = getResources().getIdentifier(
				"android:id/search_button", null, null);
		ImageView v = (ImageView) searchView.findViewById(searchImgId);
		v.setImageResource(R.drawable.search_icon);

		int closeButtonId = getResources().getIdentifier(
				"android:id/search_close_btn", null, null);
		ImageView closeButtonImage = (ImageView) searchView
				.findViewById(closeButtonId);
		closeButtonImage.setImageResource(R.drawable.icon_close);

		int id = searchView.getContext().getResources()
				.getIdentifier("android:id/search_src_text", null, null);
		TextView textView = (TextView) searchView.findViewById(id);
		textView.setTextColor(Color.BLACK);

		int searchPlateId = searchView.getContext().getResources()
				.getIdentifier("android:id/search_plate", null, null);
		View searchPlateView = searchView.findViewById(searchPlateId);
		if (searchPlateView != null) {
			searchPlateView.setBackgroundDrawable(getResources().getDrawable(
					R.drawable.search_line));
		}
		SearchView.OnQueryTextListener textChangeListener = new SearchView.OnQueryTextListener() {
			@Override
			public boolean onQueryTextChange(String newText) {
				dummyLayer.setVisibility(View.GONE);
				listAdapter.filterData(newText);
				return true;
			}

			@Override
			public boolean onQueryTextSubmit(String query) {
				// this is your adapter that will be filtered
				dummyLayer.setVisibility(View.VISIBLE);
				Log.i("ohdokingQuery", query);
				listAdapter.filterData(query);
				imm.hideSoftInputFromWindow(searchView.getWindowToken(), 0);

				return true;
			}
		};

		SearchView.OnCloseListener cancel = new SearchView.OnCloseListener() {

			@Override
			public boolean onClose() {
				dummyLayer.setVisibility(View.VISIBLE);
				return false;
			}

		};
		searchView.setOnQueryTextListener(textChangeListener);
		searchView.setOnCloseListener(cancel);

		return super.onCreateOptionsMenu(menu);
	}

	/**
	 * On selecting action bar icons
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.action_location_found:

				Intent i = new Intent(SwipeActivity.this, DialogActivity.class);
				startActivityForResult(i, DIALOG_ACTIVITY);

				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public void onBackPressed() {
		if (!searchView.isIconified()) {
			searchView.onActionViewCollapsed();
			dummyLayer.setVisibility(View.VISIBLE);
		} else {
			backPressCloseHandler.onBackPressed();
		}
	}



	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
			case (DIALOG_ACTIVITY): {
				if (resultCode == Activity.RESULT_OK) {
					ArrayList<String> newText = data
							.getStringArrayListExtra("filter");

					listdata.clear();
					listdata.addAll(db.getDreamCate(newText));
					listAdapter.notifyDataSetChanged();

					initMap();
				}
				break;
			}
			case (SETTING_ACTIVITY): {
				listdata.clear();
				listdata.addAll(db.getAllDreams());
				initMap();
				if(db.getAllDreams().size() == 0){
					map.clear();
				}
				listAdapter.notifyDataSetChanged();
				break;
			}
		}
	}

	public void startAt10() {
		AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
		int interval = 24 * 60 * 60 * 1000;

		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 1);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);

		manager.setInexactRepeating(AlarmManager.RTC_WAKEUP,
				calendar.getTimeInMillis(), interval, pendingIntent);

	}

	private boolean getUsingApi() {
		SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
		return pref.getBoolean("usingApi", false);

	}

	public void showLoginDialog(String title) {
		final String markerTitle = title;
		AlertDialog.Builder builder = new AlertDialog.Builder(this);

		LayoutInflater inflater = getLayoutInflater();

		final View view = inflater.inflate(R.layout.activity_search_dialog,null);
		checkbox_ask = (CheckBox) view.findViewById(R.id.checkbox_ask);
		checkbox_ask.setChecked(false);

		builder.setView(view);

		builder.setNegativeButton(R.string.cancel,
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
					}
				});

		builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				if (checkbox_ask.isChecked()) {
					savePreferencesCheck(true);
				} else {
					savePreferencesCheck(false);
				}
                Uri uri = Uri.parse("http://www.google.com/#q="+ markerTitle + "&ie=utf8");

				Intent intent = new Intent(Intent.ACTION_VIEW, uri);
				startActivity(intent);
			}
		});

		builder.create();
		builder.show();
	}

	private boolean getPreferencesCheck() {
		SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
		return pref.getBoolean("check", false);
	}

	private void savePreferencesCheck(boolean b) {
		SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
		SharedPreferences.Editor editor = pref.edit();

		// Set the values
		editor.putBoolean("check", b);
		editor.commit();

	}

	class BackPressCloseHandler {
		private long backKeyPressedTime = 0;
		private Toast toast;

		private Activity activity;


		public BackPressCloseHandler(Activity context) {
			this.activity = context;
		}

		public void onBackPressed() {
			if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
				backKeyPressedTime = System.currentTimeMillis();
				showGuide();
				return;
			}
			if (System.currentTimeMillis() <= backKeyPressedTime + 2000) {
				activity.finish();
				moveTaskToBack(true);
				System.exit(0);
				android.os.Process.killProcess(android.os.Process.myPid());
				toast.cancel();
			}
		}

		private void showGuide() {
			toast = Toast.makeText(activity, R.string.toast_back, Toast.LENGTH_SHORT);
			toast.show();
		}
	}

	// add weather
	public void commWithOpenApiServer() {
		api = new APIRequest();
		APIRequest.setAppKey("d2401464-9537-30ef-985a-65d90e883c02");

		param = new HashMap<String, Object>();
		param.put("version","1");
		param.put("lat","37.5714000000");
		param.put("lon","126.9658000000");
		param.put("stnid","108");
		// requestBundle
		requestBundle = new RequestBundle();
		requestBundle.setUrl(URL);
		requestBundle.setParameters(param);
		requestBundle.setHttpMethod(PlanetXSDKConstants.HttpMethod.GET);
		requestBundle.setResponseType(PlanetXSDKConstants.CONTENT_TYPE.JSON);
		try {
			// async call
			api.request(requestBundle, requestListener);
		} catch (PlanetXSDKException e) {
			e.printStackTrace();
		}
	}
	// async call listener
	public RequestListener requestListener = new RequestListener() {
		@Override
		public void onPlanetSDKException(PlanetXSDKException e) {
			hndResult = e.toString();
			msgHandler.sendEmptyMessage(0);
		}
		@Override
		public void onComplete(ResponseMessage result) {
			// inform to messagehandler
			resultCounty = (String) result.getResultHashmap().get("county");
			resultCode = (String) result.getResultHashmap().get("today_code");
			resultTmin = (String) result.getResultHashmap().get("today_tmax");
			resultTmax = (String) result.getResultHashmap().get("today_tmin");
			msgHandler.sendEmptyMessage(0);
		}
	};

}
