package assetmanager;

public class ResultData {

	private String result;
	private Shop shop;
	
	public ResultData(String result, Shop shop){
		this.result = result;
		this.shop = shop;		
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public Shop getShop() {
		return shop;
	}

	public void setShop(Shop shop) {
		this.shop = shop;
	}
	
	
	
}
