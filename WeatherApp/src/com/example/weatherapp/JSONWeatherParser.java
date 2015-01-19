package com.example.weatherapp;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class JSONWeatherParser {

	public static WeeklyWeather getWeather(String data) throws JSONException  {
		WeeklyWeather weather = new WeeklyWeather();
		int numOfDays = 7;

		ArrayList<Integer> date = new ArrayList<Integer>();
		ArrayList<String> city = new ArrayList<String>();
		ArrayList<String> desc = new ArrayList<String>();
		ArrayList<String> icon = new ArrayList<String>();
		ArrayList<Float> temp = new ArrayList<Float>();
		ArrayList<Float> maxTemp = new ArrayList<Float>();
		ArrayList<Float> minTemp = new ArrayList<Float>();



		JSONObject jObj = new JSONObject(data);

		if(getInt("cod", jObj) == 404)
		{
			return null;
		}
		JSONObject sysObj = getObject("city", jObj);
		String name =  getString("name", sysObj)+ " , " + getString("country", sysObj) ;
		for(int i = 0;i<numOfDays;i++)
		{
			city.add(name);
		}
		weather.setCity(city);
		Log.d("test"," data ........" +city);
		JSONArray list = jObj.getJSONArray("list");
		for(int i = 0;i<list.length();i++)
		{
			JSONObject listdt = list.getJSONObject(i);
			date.add(getInt("dt", listdt));

			JSONObject tempList = listdt.getJSONObject("temp");
			temp.add(getFloat("day", tempList));
			maxTemp.add(getFloat("max", tempList));
			minTemp.add(getFloat("min", tempList));

			JSONArray weatherArr = listdt.getJSONArray("weather");
			JSONObject weatherList = weatherArr.getJSONObject(0);
			icon.add(getString("icon", weatherList));
			desc.add(getString("description", weatherList));



		}
		weather.setDate(date);
		weather.setDescr(desc);
		weather.setIcon(icon);
		weather.setMaxTemp(maxTemp);
		weather.setMinTemp(minTemp);
		weather.setTemp(temp);
		Log.d("test"," data ........" + weather.getCity());
		Log.d("test"," data ........" + weather.getDate());
		Log.d("test"," data ........" + weather.getDescr());
		Log.d("test"," data ........" + weather.getIcon());
		Log.d("test"," data ........" + weather.getMaxTemp());
		Log.d("test"," data ........" + weather.getMinTemp());
		Log.d("test"," data ........" + weather.getTemp());
		return weather;
	}


	private static JSONObject getObject(String tagName, JSONObject jObj)  throws JSONException {
		JSONObject subObj = jObj.getJSONObject(tagName);
		return subObj;
	}

	private static String getString(String tagName, JSONObject jObj) throws JSONException {
		return jObj.getString(tagName);
	}

	private static float  getFloat(String tagName, JSONObject jObj) throws JSONException {
		return (float) jObj.getDouble(tagName);
	}

	private static int  getInt(String tagName, JSONObject jObj) throws JSONException {
		return jObj.getInt(tagName);
	}

}
