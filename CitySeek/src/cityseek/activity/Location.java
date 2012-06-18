package cityseek.activity;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

public class Location implements Parcelable {

	private String name;
	private String address;
	private double latitude;
	private double longitude;
	private boolean visited;

	public final String NAME = "name";
	public final String ADDRESS = "address";
	public final String LATITUDE = "latitude";
	public final String LONGITUDE = "longitude";

	public Location() {
	}

	/**
	 * This should only be used to decompress a parcel containing a list of
	 * Locations
	 * 
	 * @param source
	 */
	public Location(Parcel source) {
		Log.v("Tag", "Unparcelling");
		name = source.readString();
		latitude = source.readDouble();
		longitude = source.readDouble();
		address = source.readString();
		visited = source.readByte() == 1; // visited == true if byte == 1
	}

	public Location(String name, String address, double latitude,
			double longitude) {
		this.name = name;
		this.address = address;
		this.latitude = latitude;
		this.longitude = longitude;
		this.visited = false;
	}

	public Location(String name, String address, double latitude,
			double longitude, boolean visited) {
		this.name = name;
		this.address = address;
		this.latitude = latitude;
		this.longitude = longitude;
		this.visited = visited;
	}

	public String getName() {
		return name;
	}

	public String getAddress() {
		return address;
	}

	public double getLatitude() {
		return latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public boolean isVisited() {
		return visited;
	}

	public void setVisited(boolean visited) {
		this.visited = visited;
	}

	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	public void writeToParcel(Parcel dest, int flags) {
		Log.v("Tag", "writeToParcel..." + flags);
		dest.writeString(name);
		dest.writeDouble(latitude);
		dest.writeDouble(longitude);
		dest.writeString(address);
		dest.writeByte((byte) (visited ? 1 : 0)); // if visited == true, byte ==
													// 1
	}

	/**
	 * It will be required during un-marshaling data stored in a Parcel
	 */
	public static final Parcelable.Creator<Location> CREATOR = new Parcelable.Creator<Location>() {
		public Location createFromParcel(Parcel in) {
			return new Location(in);
		}

		public Location[] newArray(int size) {
			return new Location[size];
		}
	};

}
