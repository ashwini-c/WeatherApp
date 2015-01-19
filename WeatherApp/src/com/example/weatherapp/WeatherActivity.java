package com.example.weatherapp;


import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;



import org.json.JSONException;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class WeatherActivity extends Activity {

	Button search;
	EditText location;
	ListView list;
	WeatherAdapter adapter;
	WeeklyWeather weatherData = null;
	private ProgressBar spinner;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_weather);

		spinner = (ProgressBar)findViewById(R.id.progressBar1);

		list = (ListView)findViewById(R.id.listView1);
		search = (Button)findViewById(R.id.button1);
		location = (EditText)findViewById(R.id.editText1);
		search.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				String city = location.getEditableText().toString();
				if(city.isEmpty())
				{
					Toast.makeText(getApplicationContext(), "Please enter a city name!!!", Toast.LENGTH_SHORT).show();
				}
				else if (!isNetworkAvailable())
				{
					Toast.makeText(getApplicationContext(), "Please check your internet connection and try again!", Toast.LENGTH_SHORT).show();
				}
				else 
				{
					spinner.setVisibility(View.VISIBLE);
					JSONWeatherTask task = new JSONWeatherTask();
					task.execute(city);
				}

			}
		});

		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int pos, long arg3) {

				Intent intent = new Intent(getApplicationContext(), DisplayDataActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				intent.putExtra("date", weatherData.getDate().get(pos));
				intent.putExtra("city", weatherData.getCity().get(pos));
				intent.putExtra("temp", weatherData.getTemp().get(pos));
				intent.putExtra("max", weatherData.getMaxTemp().get(pos));
				intent.putExtra("min", weatherData.getMinTemp().get(pos));
				intent.putExtra("icon", weatherData.getIcon().get(pos));
				intent.putExtra("desc", weatherData.getDescr().get(pos));
				startActivity(intent);
			}
		});
	}


	private boolean isNetworkAvailable() {
		ConnectivityManager connectivityManager 
		= (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
		return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.weather, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}


	private class WeatherAdapter extends BaseAdapter
	{

		WeeklyWeather data = null;

		public WeatherAdapter(WeeklyWeather data) {
			this.data = data;
		}

		@Override
		public int getCount() {
			return 7;
		}

		@Override
		public Object getItem(int arg0) {
			return null;
		}

		@Override
		public long getItemId(int pos) {
			return pos;
		}

		@Override
		public View getView(int pos, View arg1, ViewGroup parent) {
			LayoutInflater inflater = getLayoutInflater();
			View v;
			v = inflater.inflate(R.layout.list_item, parent, false);
			TextView date,temp,desc;

			date = (TextView) v.findViewById(R.id.textView1);
			temp = (TextView) v.findViewById(R.id.textView2);
			desc=  (TextView)v.findViewById(R.id.textView3);
			long time = (data.getDate().get(pos))* (long)1000;
			Date d = new Date(time);
			SimpleDateFormat formatter = new SimpleDateFormat("MM/DD/yyyy", Locale.getDefault());
			String dateString = formatter.format(d );

			date.setText(dateString);
			temp.setText(""+data.getTemp().get(pos)+" degrees");
			desc.setText(data.getDescr().get(pos));


			return (v);
		}

	}
	private class JSONWeatherTask extends AsyncTask<String, Void, WeeklyWeather> {

		@Override
		protected WeeklyWeather doInBackground(String... params) {
			WeeklyWeather weather = new WeeklyWeather();
			String data = ( (new WeatherHttpClient()).getWeatherData(params[0]));
			Log.d("androidruntime"," data ........" + data);
			try {
				weather = JSONWeatherParser.getWeather(data);



			} catch (JSONException e) {				
				e.printStackTrace();
			}
			return weather;

		}




		@Override
		protected void onPostExecute(WeeklyWeather weather) {			
			super.onPostExecute(weather);
			if(weather == null)
			{
				spinner.setVisibility(View.GONE);
				Toast.makeText(getApplicationContext(), "Please enter a valid city name!!!", Toast.LENGTH_SHORT).show();
			}
			else
			{
				spinner.setVisibility(View.GONE);
				weatherData = weather;
				adapter = new WeatherAdapter(weather);
				list.setAdapter(adapter);
			}

		}







	}
}
