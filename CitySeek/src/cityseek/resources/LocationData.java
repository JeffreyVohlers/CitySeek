package cityseek.resources;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class LocationData extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "locations.db";
	private static final int DATABASE_VERSION = 1;

	public LocationData(Context ctx) {
		super(ctx, DATABASE_NAME, null, DATABASE_VERSION);

	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE " + DBConstants.TABLE_NAME + " ("
				+ DBConstants._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
				+ DBConstants.NAME + " TEXT NOT NULL, " + DBConstants.LATITUDE
				+ " DOUBLE, " + DBConstants.LONGITUDE + " DOUBLE, "
				+ DBConstants.ADDRESS + " TEXT NOT NULL, "
				+ DBConstants.VISITED + " BOOLEAN DEFAULT FALSE);");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + DBConstants.TABLE_NAME);
		onCreate(db);
	}

}