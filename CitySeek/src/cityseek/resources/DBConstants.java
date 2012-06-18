package cityseek.resources;

import android.provider.BaseColumns;

public interface DBConstants extends BaseColumns {
	public static final String TABLE_NAME = "locations";

	public static final String _ID = "id";
	public static final String NAME = "name";
	public static final String LATITUDE = "latitude";
	public static final String LONGITUDE = "longitude";
	public static final String ADDRESS = "address";
	public static final String VISITED = "visited";
}