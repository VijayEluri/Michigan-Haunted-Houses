package com.starprose.haunted;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class HauntedXMLHandler extends DefaultHandler {

	Boolean currentElement = false;
	String currentValue = null;
	public static Attractions Attractions = null;

	public static Attractions getAttractions() {
		return Attractions;
	}

	public static void setAttractions(Attractions Attractions) {
		HauntedXMLHandler.Attractions = Attractions;
	}

	/** Called when tag starts ( ex:- <name>AndroidPeople</name>
	 * -- <name> )*/
	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {

		currentElement = true;

		if (localName.equals("document"))
		{
			/** Start */
			Attractions = new Attractions();
		} 

	}

	/** Called when tag closing ( ex:- <name>AndroidPeople</name>
	 * -- </name> )*/
	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {

		currentElement = false;

		/** set value */
		if (localName.equalsIgnoreCase("name"))
			Attractions.setName(currentValue);
		else if (localName.equalsIgnoreCase("website"))
			Attractions.setWebsite(currentValue);
		else if (localName.equalsIgnoreCase("address"))
			Attractions.setAddress(currentValue);
		else if (localName.equalsIgnoreCase("city"))
			Attractions.setCity(currentValue);
		else if (localName.equalsIgnoreCase("state"))
			Attractions.setState(currentValue);
		else if (localName.equalsIgnoreCase("zip"))
			Attractions.setZIP(currentValue);
		else if (localName.equalsIgnoreCase("phone"))
			Attractions.setPhone(currentValue);
		else if (localName.equalsIgnoreCase("latitude"))
			Attractions.setLatitude(Float.parseFloat(currentValue));
		else if (localName.equalsIgnoreCase("longitude"))
			Attractions.setLongitude(Float.parseFloat(currentValue));

	}

	/** Called to get tag characters ( ex:- <name>AndroidPeople</name>
	 * -- to get AndroidPeople Character ) */
	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {

		if (currentElement) {
			currentValue = new String(ch, start, length);
			currentElement = false;
		}

	}

	
}
