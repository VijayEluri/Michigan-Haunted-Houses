package com.starprose.haunted;

import java.util.ArrayList;

public class Attractions {

	/** Variables */
	private ArrayList<String> name = new ArrayList<String>();
	private ArrayList<String> address = new ArrayList<String>();
	private ArrayList<String> city = new ArrayList<String>();
	private ArrayList<String> state = new ArrayList<String>();
	private ArrayList<String> zip = new ArrayList<String>();
	private ArrayList<String> phone = new ArrayList<String>();
	private ArrayList<Float> latitude = new ArrayList<Float>();
	private ArrayList<Float> longitude = new ArrayList<Float>();
	private ArrayList<String> website = new ArrayList<String>();

	/** In Setter method default it will return arraylist
	 *  change that to add  */

	public ArrayList<String> getName() {
		return name;
	}

	public void setName(String name) {
		this.name.add(name);
	}

	public ArrayList<String> getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website.add(website);
	}

	public ArrayList<String> getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address.add(address);
	}

	public ArrayList<String> getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city.add(city);
	}
	
	public ArrayList<String> getState() {
		return state;
	}

	public void setState(String state) {
		this.state.add(state);
	}
	
	public ArrayList<String> getZIP() {
		return zip;
	}

	public void setZIP(String zip) {
		this.zip.add(zip);
	}
	
	public ArrayList<String> getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone.add(phone);
	}
	
	public ArrayList<Float> getLatitude() {
		return latitude;
	}

	public void setLatitude(Float latitude) {
		this.latitude.add(latitude);
	}
	
	public ArrayList<Float> getLongitude() {
		return longitude;
	}

	public void setLongitude(Float longitude) {
		this.longitude.add(longitude);
	}

}
