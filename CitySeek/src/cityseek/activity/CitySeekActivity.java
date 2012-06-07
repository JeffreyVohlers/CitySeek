package cityseek.activity;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import cityseek.overlays.OurLocationOverlay;
import cityseek.overlays.PinsOverlay;
import cityseek.resources.ReadXMLFile;
import cityseekers.activity.R;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;

public class CitySeekActivity extends MapActivity {
	MapView mapView;
	MapController mc;

	private LocationManager locationManager;
	private LocationListener locationListener;

	private static final double LATITUDE = 35.80997;
	private static final double LONGITUDE = -78.70270;
	private static final double RADIUS = 0.001;
	private static final double UPPER_LATITUDE = LATITUDE + RADIUS;
	private static final double LOWER_LATITUDE = LATITUDE - RADIUS;
	private static final double UPPER_LONGITUDE = LONGITUDE + RADIUS;
	private static final double LOWER_LONGITUDE = LONGITUDE - RADIUS;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		initMapView();
		initMyLocation();
		initPointsOfInterest();
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		locationListener = new GPSLocationListener();
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
				3000, 5, locationListener);
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

	private void initPointsOfInterest() {
		Drawable drawable = new BitmapDrawable(Bitmap.createScaledBitmap(
				((BitmapDrawable) getResources()
						.getDrawable(R.drawable.redflag)).getBitmap(), 40, 40,
				false));
		PinsOverlay pinOverlay = new PinsOverlay(drawable, this);
		List<cityseek.activity.Location> pins = ReadXMLFile
				.getPinsForSeeker(this.getResources().getXml(R.xml.arthurpins));
		for (int p = 0; p < pins.size(); p++) {
			cityseek.activity.Location pin = pins.get(p);
			OverlayItem item = new OverlayItem(new GeoPoint(
					(int) (pin.getLatitude() * 1E6),
					(int) (pin.getLongitude() * 1E6)), pin.getName(),
					pin.getAddress());
			pinOverlay.addOverlay(item);
		}
		mapView.getOverlays().add(pinOverlay);
	}

	@Override
	protected void onResume() {
		super.onResume();
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
				3000, 5, locationListener);
	}

	@Override
	protected void onPause() {
		super.onPause();
		locationManager.removeUpdates(locationListener);
	}

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}

	public void newActivity() {
		Intent intent = new Intent(this, ChallengeActivity.class);
		startActivity(intent);
	}

	private class GPSLocationListener implements LocationListener {

		public void onLocationChanged(Location location) {

			if (location.getLongitude() >= LOWER_LONGITUDE
					&& location.getLongitude() <= UPPER_LONGITUDE
					&& location.getLatitude() >= LOWER_LATITUDE
					&& location.getLatitude() <= UPPER_LATITUDE) {
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