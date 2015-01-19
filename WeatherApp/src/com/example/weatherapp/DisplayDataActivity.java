package com.example.weatherapp;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.json.JSONException;




import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class DisplayDataActivity extends Activity {
	String icon;
	TextView t1,t2,t3,t4,t5;
	ImageView imgView;
	private static String IMG_URL = "http://openweathermap.org/img/w/";
	private static String Append_URL = ".png";
	private ProgressBar spinner;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_data);
		spinner = (ProgressBar)findViewById(R.id.progressBar1);
		int date = getIntent().getIntExtra("date", -1);
		String city = getIntent().getStringExtra("city");
		float temp = getIntent().getFloatExtra("temp", -1);
		float max = getIntent().getFloatExtra("max", -1);
		float min = getIntent().getFloatExtra("min",-1);
		icon = getIntent().getStringExtra("icon");
		String desc = getIntent().getStringExtra("desc");
		t1 = (TextView)findViewById(R.id.textView1);
		t2 = (TextView)findViewById(R.id.textView2);
		t3 = (TextView)findViewById(R.id.textView3);
		t4 = (TextView)findViewById(R.id.textView4);
		t5 = (TextView)findViewById(R.id.textView5);
		imgView = (ImageView)findViewById(R.id.imageView1);
		long time = date* (long)1000;
		Date d = new Date(time);
		SimpleDateFormat formatter = new SimpleDateFormat("MM/DD/yyyy", Locale.getDefault());
		String dateString = formatter.format(d );
		t1.setText(t1.getText()+ " " + city + " on " + dateString);
		t2.setText(desc);
		t3.setText("Temperature: " + temp + " degrees" );
		t4.setText("Max Temperature: " + max + " degrees" );
		t5.setText("Min Temperature: " + min + " degrees" );
		spinner.setVisibility(View.VISIBLE);
		new DownloadImageTask((ImageView) findViewById(R.id.imageView1))
		.execute(IMG_URL+icon+Append_URL);
	}




	private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
		ImageView bmImage;

		public DownloadImageTask(ImageView bmImage) {
			this.bmImage = bmImage;
		}

		protected Bitmap doInBackground(String... urls) {
			String urldisplay = urls[0];
			Bitmap mIcon11 = null;
			try {
				InputStream in = new java.net.URL(urldisplay).openStream();
				mIcon11 = BitmapFactory.decodeStream(in);
			} catch (Exception e) {
				Log.e("Error", e.getMessage());
				e.printStackTrace();
			}
			return mIcon11;
		}

		protected void onPostExecute(Bitmap result) {
			spinner.setVisibility(View.GONE);
			bmImage.setImageBitmap(result);
		}


	}
}
