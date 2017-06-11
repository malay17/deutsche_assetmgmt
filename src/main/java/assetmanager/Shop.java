package assetmanager;

public class Shop {

	private String shopName;
	private ShopAddress shopAddress;
	
	public Shop(){
		super();
	}
	public Shop(String shopName, ShopAddress shopAddress){
		this.shopName = shopName;
		this.shopAddress = shopAddress;
	}
	
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	public ShopAddress getShopAddress() {
		return shopAddress;
	}
	public void setShopAddress(ShopAddress shopAddress) {
		this.shopAddress = shopAddress;
	}
	
	
}
