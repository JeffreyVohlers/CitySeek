package cityseek.activity;

public class Location {

	private String name;
	private String address;
	private double latitude;
	private double longitude;

	public final String NAME = "name";
	public final String ADDRESS = "address";
	public final String LATITUDE = "latitude";
	public final String LONGITUDE = "longitude";

	public Location() {
	}

	public Location(String name, String address, double latitude,
			double longitude) {
		this.name = name;
		this.address = address;
		this.latitude = latitude;
		this.longitude = longitude;
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
}
