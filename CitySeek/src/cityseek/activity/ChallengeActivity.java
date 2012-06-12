package cityseek.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.ImageView;

public class ChallengeActivity extends Activity {

	private static final double TARGET_LATITUDE = 100.500000;
	private static final double TARGET_LONGITUDE = 50.500000;
	private static final double UPPER_LATITUDE = TARGET_LATITUDE + 0.000025;
	private static final double LOWER_LATITUDE = TARGET_LATITUDE - 0.000025;
	private static final double UPPER_LONGITUDE = TARGET_LONGITUDE + 0.000025;
	private static final double LOWER_LONGITUDE = TARGET_LONGITUDE - 0.000025;

	private LocationListener locationListener;
	private LocationManager locationManager;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.challenge);
		ImageView image = (ImageView) findViewById(R.id.art);

		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

		locationListener = new GPSListener();

		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
				3000, 3, locationListener);
	}

	public void newActivity() {

		Intent intent = new Intent(this, QuizActivity.class);
		startActivity(intent);
	}

	private class GPSListener implements LocationListener {

		public void onLocationChanged(Location location) {

			if (location.getLatitude() <= UPPER_LATITUDE
					&& location.getLatitude() >= LOWER_LATITUDE
					&& location.getLongitude() <= UPPER_LONGITUDE
					&& location.getLongitude() >= LOWER_LONGITUDE) {
				newActivity();
			}
		}

		public void onProviderDisabled(String provider) {
			// TODO Auto-generated method stub

		}

		public void onProviderEnabled(String provider) {
			// TODO Auto-generated method stub

		}

		public void onStatusChanged(String provider, int status, Bundle extras) {
			// TODO Auto-generated method stub

		}
	}
}
