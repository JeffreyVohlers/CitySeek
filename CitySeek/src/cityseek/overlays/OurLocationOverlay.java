package cityseek.overlays;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.location.Location;
import cityseekers.activity.R;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;

public class OurLocationOverlay extends MyLocationOverlay {

	MapView mapView;
	Bitmap uRHere;
	Point currentPoint = new Point();

	boolean centerOnCurrentLocation = true;
	int height;
	int width;

	public OurLocationOverlay(Context context, MapView mapView) {
		super(context, mapView);
		this.mapView = mapView;
		uRHere = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
				context.getResources(), R.drawable.urhere), 30, 30, false); // TODO:
																			// Change
																			// size
																			// and
																			// R
																			// lookup
	}

	@Override
	protected void drawMyLocation(Canvas canvas, MapView mapView,
			Location lastFix, GeoPoint myLocation, long when) {
		if (this.height == 0 || this.width == 0) {
			this.height = mapView.getHeight();
			this.width = mapView.getWidth();
		}
		mapView.getProjection().toPixels(myLocation, currentPoint);
		canvas.drawBitmap(uRHere, currentPoint.x, currentPoint.y - 40, null); // TODO:
																				// Change
																				// proportions
	}

	@Override
	public synchronized void onLocationChanged(Location location) {
		super.onLocationChanged(location);
		if (mapView != null && centerOnCurrentLocation) {
			mapView.getController().animateTo(getMyLocation());
		}
	}

}