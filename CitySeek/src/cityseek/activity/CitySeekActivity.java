package cityseek.activity;

import android.os.Bundle;
import cityseek.resources.OurLocationOverlay;
import cityseekers.activity.R;

import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;

public class CitySeekActivity extends MapActivity {
	/** Called when the activity is first created. */
	MapView mapView;
	MapController mc;

	// private LocationManager locationManager;
	// private LocationListener locationListener;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		initMapView();
		initMyLocation();
		// locationManager = (LocationManager)
		// getSystemService(Context.LOCATION_SERVICE);
		// locationListener = new GPSLocationListener();
		// locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
		// 3000, 5, locationListener);
	}

	private void initMapView() {
		mapView = (MapView) findViewById(R.id.mapView);
		mc = mapView.getController();
		mc.setZoom(12);
		mapView.setBuiltInZoomControls(true);
	}

	private void initMyLocation() {
		final OurLocationOverlay overlay = new OurLocationOverlay(this, mapView);
		overlay.enableMyLocation();
		mapView.getOverlays().add(overlay);
	}

	@Override
	protected void onResume() {
		super.onResume();
		// locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
		// 3000, 5, locationListener);
	}

	@Override
	protected void onPause() {
		super.onPause();
		// locationManager.removeUpdates(locationListener);
	}

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}
}