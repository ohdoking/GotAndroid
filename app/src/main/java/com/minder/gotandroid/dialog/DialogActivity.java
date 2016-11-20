package com.minder.gotandroid.dialog;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.minder.gotandroid.R;
import com.minder.gotandroid.db.MyDB;

public class DialogActivity extends Activity implements OnClickListener {

	private ImageButton regionBtn, cat1, cat2, cat3, cat4, cat5, alarmBtn, memoBtn;
	private String category;
	Button okBtn, cancelBtn;
	int check;
	MyDB db;
	String name;
	Intent resultIntent;

	int categoryId1 = 0;
	int categoryId2 = 0;
	int categoryId3 = 0;
	int categoryId4 = 0;
	int categoryId5 = 0;

	ArrayList<String> categoryList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_view);
		resultIntent = new Intent();

		Set<String> hashset = getPreferences();
		List<String> list = new ArrayList<String>(hashset);
		categoryList = new ArrayList<String>();

		categoryId1 = 1;
		categoryId2 = 2;
		categoryId3 = 3;
		categoryId4 = 4;
		categoryId5 = 5;


		db = new MyDB(getApplicationContext());

		cat1 = (ImageButton) findViewById(R.id.cate1);
		cat2 = (ImageButton) findViewById(R.id.cate2);
		cat3 = (ImageButton) findViewById(R.id.cate3);
		cat4 = (ImageButton) findViewById(R.id.cate4);
		cat5 = (ImageButton) findViewById(R.id.cate5);

		for (String category : list) {
			if (category.equals("sight")) {
				cat1.setImageResource(R.drawable.writeicon1);
				categoryList.add("sight");
				categoryId1 = 0;
			} else if (category.equals("festival")) {
				cat2.setImageResource(R.drawable.writeicon2);
				categoryList.add("festival");
				categoryId2 = 0;
			} else if (category.equals("food")) {
				cat3.setImageResource(R.drawable.writeicon3);
				categoryList.add("food");
				categoryId3 = 0;
			} else if (category.equals("exhibition")) {
				cat4.setImageResource(R.drawable.writeicon4);
				categoryList.add("exhibition");
				categoryId4 = 0;
			} else if (category.equals("etc")) {
				cat5.setImageResource(R.drawable.writeicon5);
				categoryList.add("etc");
				categoryId5 = 0;
			}
		}

		cat1.setOnClickListener(this);
		cat2.setOnClickListener(this);
		cat3.setOnClickListener(this);
		cat4.setOnClickListener(this);
		cat5.setOnClickListener(this);

		okBtn = (Button) findViewById(R.id.okBtn);
		okBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (categoryId1 == 1 && categoryId2 == 2 && categoryId3 == 3 && categoryId4 == 4 && categoryId5 == 5) {
				} else {
					try {
						savePreferences(categoryList);
						resultIntent.putExtra("filter", categoryList);
						setResult(Activity.RESULT_OK, resultIntent);
						finish();
					} catch (RuntimeException e) {
					}
				}
			}
		});

		cancelBtn = (Button) findViewById(R.id.deleteBtn);
		cancelBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});

	}

	// �� �ҷ�����
	private Set getPreferences() {
		SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
		Set<String> hash = new HashSet<String>();
		hash.add("sight");
		hash.add("festival");
		hash.add("food");
		hash.add("exhibition");
		hash.add("etc");

		return pref.getStringSet("categoryList", hash);
	}

	// �� �����ϱ�
	private void savePreferences(ArrayList<String> categoryList) {
		SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
		SharedPreferences.Editor editor = pref.edit();

		// Retrieve the values

		// Set the values
		Set<String> set = new HashSet<String>(categoryList);
		editor.putStringSet("categoryList", set);
		editor.commit();

	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
			case R.id.cate1:
				category = "sight";
				if (categoryId1 == 0) {
					cat1.setImageResource(R.drawable.inactive1);
					categoryId1 = 1;
					categoryList.remove(category);
				} else {
					cat1.setImageResource(R.drawable.writeicon1);
					categoryId1 = 0;
					categoryList.add(category);
				}

				check = 1;
				Toast.makeText(DialogActivity.this, "Sight", Toast.LENGTH_SHORT).show();
				break;
			case R.id.cate2:
				category = "festival";
				if (categoryId2 == 0) {
					cat2.setImageResource(R.drawable.inactive2);
					categoryId2 = 2;
					categoryList.remove(category);
				} else {
					cat2.setImageResource(R.drawable.writeicon2);
					categoryId2 = 0;
					categoryList.add(category);
				}
				check = 2;
				Toast.makeText(DialogActivity.this, "Festival", Toast.LENGTH_SHORT).show();
				break;
			case R.id.cate3:
				category = "food";
				if (categoryId3 == 0) {
					cat3.setImageResource(R.drawable.inactive3);
					categoryId3 = 3;
					categoryList.remove(category);
				} else {
					cat3.setImageResource(R.drawable.writeicon3);
					categoryId3 = 0;
					categoryList.add(category);
				}
				check = 3;
				Toast.makeText(DialogActivity.this, "Food", Toast.LENGTH_SHORT).show();
				break;
			case R.id.cate4:
				category = "exhibition";
				if (categoryId4 == 0) {
					cat4.setImageResource(R.drawable.inactive4);
					categoryId4 = 4;
					categoryList.remove(category);
				} else {
					cat4.setImageResource(R.drawable.writeicon4);
					categoryId4 = 0;
					categoryList.add(category);
				}
				check = 4;
				Toast.makeText(DialogActivity.this, "Exhibition", Toast.LENGTH_SHORT).show();
				break;
			case R.id.cate5:
				category = "etc";
				if (categoryId5 == 0) {
					cat5.setImageResource(R.drawable.inactive5);
					categoryId5 = 5;
					categoryList.remove(category);
				} else {
					cat5.setImageResource(R.drawable.writeicon5);
					categoryId5 = 0;
					categoryList.add(category);
				}
				check = 5;
				Toast.makeText(DialogActivity.this, "ETC", Toast.LENGTH_SHORT).show();
				break;
		}

	}
}