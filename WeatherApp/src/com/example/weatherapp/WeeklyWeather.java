
package com.example.weatherapp;

import java.util.ArrayList;




public class WeeklyWeather {

	ArrayList<Integer> date = new ArrayList<Integer>();
	ArrayList<String> city = new ArrayList<String>();
	ArrayList<String> desc = new ArrayList<String>();
	ArrayList<String> icon = new ArrayList<String>();
	ArrayList<Float> temp = new ArrayList<Float>();
	ArrayList<Float> maxTemp = new ArrayList<Float>();
	ArrayList<Float> minTemp = new ArrayList<Float>();



	public ArrayList<Integer> getDate() {
		return date;
	}
	public void setDate(ArrayList<Integer> date) {
		this.date = date;
	}
	public ArrayList<String> getCity() {
		return city;
	}
	public void setCity(ArrayList<String> city) {
		this.city = city;
	}
	public ArrayList<String> getDescr() {
		return desc;
	}
	public void setDescr(ArrayList<String> desc) {
		this.desc = desc;
	}
	public ArrayList<String> getIcon() {
		return icon;
	}
	public void setIcon(ArrayList<String> icon) {
		this.icon = icon;
	}

	public ArrayList<Float> getTemp() {
		return temp;
	}
	public void setTemp(ArrayList<Float> temp) {
		this.temp = temp;
	}
	public ArrayList<Float> getMinTemp() {
		return minTemp;
	}
	public void setMinTemp(ArrayList<Float> minTemp) {
		this.minTemp = minTemp;
	}
	public ArrayList<Float> getMaxTemp() {
		return maxTemp;
	}
	public void setMaxTemp(ArrayList<Float> maxTemp) {
		this.maxTemp = maxTemp;


	}

}
