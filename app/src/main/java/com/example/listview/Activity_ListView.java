package com.example.listview;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Activity_ListView extends AppCompatActivity {


	private static final String TAG = "BIKEDATA";
	ListView my_listview;
	private ConnectivityCheck check;
	private DownloadTask task1;
	String url;
	private List<BikeData> bikes;
	private SharedPreferences myPreference;
	private CustomAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);


		// Change title to indicate sort by
		setTitle("Sort by:");

		//listview that you will operate on
		my_listview = (ListView)findViewById(R.id.lv);

		url = "http://tetonsoftware.com/bikes/bikes.json";
		myPreference = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

		//toolbar

		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		android.support.v7.app.ActionBar actionBar = getSupportActionBar();

		//setupSimpleSpinner();

		task1 = new DownloadTask(this);
		check = new ConnectivityCheck();

		if(check.isNetworkReachableAlertUserIfNot(this)){
			task1.execute(url);
			setupSimpleSpinner();
			//setupListViewOnClickListener();

		}
		//setupListViewOnClickListener();

		//TODO call a thread to get the JSON list of bikes
		//TODO when it returns it should process this data with bindData
	}

	public void setupListViewOnClickListener() {
		//TODO you want to call my_listviews setOnItemClickListener with a new instance of android.widget.AdapterView.OnItemClickListener() {

		my_listview.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				//TODO alert Dialog with toString of BikeData
				AlertDialog.Builder dialog = new AlertDialog.Builder(Activity_ListView.this);
				dialog.setMessage(bikes.get(position).toString());
				dialog.show();

			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}
		});
	}

	Spinner spinner;
	/**
	 * create a data adapter to fill above spinner with choices(Company,Location and Price),
	 * bind it to the spinner
	 * Also create a OnItemSelectedListener for this spinner so
	 * when a user clicks the spinner the list of bikes is resorted according to selection
	 * dontforget to bind the listener to the spinner with setOnItemSelectedListener!
	 */
	private void setupSimpleSpinner() {
		spinner = (Spinner)findViewById(R.id.spinner);
		ArrayAdapter<CharSequence> sortBy = ArrayAdapter.createFromResource(this,R.array.sortable_fields,R.layout.spinner_item);
		spinner.setAdapter(sortBy);

		spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			public static final int SELECTED_ITEM = 0;

			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				if (parent.getChildAt(SELECTED_ITEM) != null) {
					((TextView) parent.getChildAt(SELECTED_ITEM)).setTextColor(Color.WHITE);
					Toast.makeText(Activity_ListView.this, (String) parent.getItemAtPosition(position), Toast.LENGTH_SHORT).show();


					if (parent.getItemAtPosition(position).equals("Model")) {
						Collections.sort(bikes, new ComparatorModel());
						adapter.notifyDataSetChanged();

					} else if (parent.getItemAtPosition(position).equals("Price")) {
						Collections.sort(bikes, new ComparatorPrice());
						adapter.notifyDataSetChanged();

					} else if (parent.getItemAtPosition(position).equals("Location")) {
						Collections.sort(bikes, new ComparatorLocation());
						adapter.notifyDataSetChanged();

					} else if (parent.getItemAtPosition(position).equals("Company")) {
						Collections.sort(bikes, new ComparatorCompany());
						adapter.notifyDataSetChanged();
					}


				}
			}


			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}
		});

	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();

		switch (id) {
			case R.id.action_settings:
				doSettings();
				doPrefChangeListener();
				return true;
			case R.id.refresh:
				doRefresh();
				return true;
		}
		return super.onOptionsItemSelected(item);
	}
	private void doRefresh(){
		//TODO ?
		DownloadTask taskRefresh = new DownloadTask(Activity_ListView.this);
		taskRefresh.execute(url);
	}
	private void doSettings() {
		//implement
		Intent intentSettings = new Intent(this,activityPreference.class);
		startActivity(intentSettings);
	}
	public void processJSON(String result){
		bikes = JSONHelper.parseAll(result);
		adapter = new CustomAdapter(this,bikes);
		my_listview.setAdapter(adapter);
		//setupListViewOnClickListener();


	}
	private SharedPreferences.OnSharedPreferenceChangeListener listener;
	private String prefUrl;
	public void doPrefChangeListener(){
		listener = new SharedPreferences.OnSharedPreferenceChangeListener(){

			@Override
			public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
				if(key.equals("listPref")){
					prefUrl = myPreference.getString("listPref","");
					url = prefUrl;
					Toast.makeText(Activity_ListView.this,"listPref ="+url, Toast.LENGTH_SHORT).show();
					if(check.isNetworkReachableAlertUserIfNot(Activity_ListView.this)){
						//task1.execute(url);
						DownloadTask taskPref = new DownloadTask(Activity_ListView.this);
						taskPref.execute(url);
					}

				}

			}
		};
		myPreference.registerOnSharedPreferenceChangeListener(listener);

	}


}
