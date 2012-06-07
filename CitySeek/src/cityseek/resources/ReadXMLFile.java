package cityseek.resources;

import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;

import android.content.res.XmlResourceParser;
import android.util.Log;
import cityseek.activity.Location;

public class ReadXMLFile {

	public static List<Location> getPinsForSeeker(XmlResourceParser seekerFile) {
		List<Location> pins = new ArrayList<Location>();
		try {
			seekerFile.next();
			int eventType = seekerFile.getEventType();
			Location currentPin = new Location();
			while (eventType != XmlPullParser.END_DOCUMENT) {
				switch (eventType) {
				case XmlPullParser.START_DOCUMENT:
					break;
				case XmlPullParser.START_TAG:
					String tagName = seekerFile.getName();
					if (tagName.equalsIgnoreCase(currentPin.ADDRESS)) {
						currentPin.setAddress(seekerFile.nextText());
					} else if (tagName.equalsIgnoreCase(currentPin.LATITUDE)) {
						String s = seekerFile.nextText();
						currentPin.setLatitude(Double.parseDouble(s));
					} else if (tagName.equalsIgnoreCase(currentPin.LONGITUDE)) {
						String t = seekerFile.nextText();
						currentPin.setLongitude(Double.parseDouble(t));
					} else if (tagName.equalsIgnoreCase(currentPin.NAME)) {
						currentPin.setName(seekerFile.nextText());
					}
					break;
				case XmlPullParser.END_TAG:
					String name = seekerFile.getName();
					if (name.equalsIgnoreCase("pin")) {
						pins.add(currentPin);
						currentPin = new Location();
					}
					break;
				}
				eventType = seekerFile.next();
			}
			return pins;
		} catch (Exception e) {
			Log.e("input_method", e.getStackTrace().toString());
			return null;
		}
	}

}