package cityseek.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import cityseek.resources.DBConstants;
import cityseek.resources.LocationData;
import cityseek.resources.ReadXMLFile;

public class MenuActivity extends Activity {
	private LocationData locationData;
	private List<Location> pins;

	private static String[] FROMALL = { DBConstants.NAME, DBConstants.LATITUDE,
			DBConstants.LONGITUDE, DBConstants.ADDRESS, DBConstants.VISITED };

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu);
		locationData = new LocationData(this);
		try {
			Cursor cursor = getLocations();
			if (cursor.getCount() < 2) {
				initArthurDatabase();
				cursor = getLocations();
			}
			pins = buildLocationsFromCursor(cursor);
		} finally {
			locationData.close();
		}
		ImageView myImg = (ImageView) findViewById(R.id.goseek);
		myImg.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				startNext((ArrayList<Location>) pins);
			}
		});
	}

	private List<cityseek.activity.Location> buildLocationsFromCursor(
			Cursor cursor) {
		int nameIndex = cursor.getColumnIndexOrThrow(DBConstants.NAME);
		int addressIndex = cursor.getColumnIndexOrThrow(DBConstants.ADDRESS);
		int longitudeIndex = cursor
				.getColumnIndexOrThrow(DBConstants.LONGITUDE);
		int latitudeIndex = cursor.getColumnIndexOrThrow(DBConstants.LATITUDE);
		int visitedIndex = cursor.getColumnIndexOrThrow(DBConstants.VISITED);
		List<cityseek.activity.Location> pins = new ArrayList<Location>();
		while (cursor.moveToNext()) {
			pins.add(new Location(cursor.getString(nameIndex), cursor
					.getString(addressIndex), cursor.getDouble(latitudeIndex),
					cursor.getDouble(longitudeIndex), new Boolean(cursor
							.getString(visitedIndex))));
		}
		return pins;
	}

	public void startNext(ArrayList<Location> pins) {
		Intent intent = new Intent(this, CitySeekActivity.class);
		intent.putParcelableArrayListExtra("pinList", pins);// **************TODO******************
		startActivity(intent);
	}

	private void initArthurDatabase() {
		List<cityseek.activity.Location> xmlPins = ReadXMLFile
				.getPinsForSeeker(this.getResources().getXml(R.xml.arthurpins));
		for (int p = 0; p < xmlPins.size(); p++) {
			addLocation(xmlPins.get(p));
		}
	}

	private void addLocation(Location pin) {
		SQLiteDatabase db = locationData.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(DBConstants.NAME, pin.getName());
		values.put(DBConstants.ADDRESS, pin.getAddress());
		values.put(DBConstants.LATITUDE, pin.getLatitude());
		values.put(DBConstants.LONGITUDE, pin.getLongitude());
		values.put(DBConstants.VISITED, pin.isVisited());
		db.insertOrThrow(DBConstants.TABLE_NAME, null, values);
	}

	private Cursor getLocations() {
		SQLiteDatabase db = locationData.getReadableDatabase();
		Cursor cursor = db.query(DBConstants.TABLE_NAME, FROMALL, null, null,
				null, null, DBConstants._ID + " DESC");
		startManagingCursor(cursor);
		return cursor;
	}
}
