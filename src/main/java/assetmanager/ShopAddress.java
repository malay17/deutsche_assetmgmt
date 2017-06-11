package assetmanager;

public class ShopAddress {
	
	private String number;
	private String postCode;
	private double latitude;
	private double longitude;
	
	public ShopAddress(){
		super();
	}
	
	public ShopAddress(String number, String postCode){
		this.number = number;
		this.postCode = postCode;
	}
	
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getPostCode() {
		return postCode;
	}
	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	//returns address to be set to google maps api
	@Override
	public String toString() {
		return  number + " "+ postCode;
	}
	
	

}
