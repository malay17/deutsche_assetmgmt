

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import assetmanager.Application;
import assetmanager.AssetManagerController;
import assetmanager.ResultData;
import assetmanager.Shop;
import assetmanager.ShopAddress;
import junit.framework.Assert;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
public class AssetManagerTest {

	@Autowired
	AssetManagerController assetManagerController;

	@Test
	public void testShopAddition() throws Exception {
		Shop shop1 = new Shop("Shop1",new ShopAddress("1","LU2 1AA"));
		
		assetManagerController.addShop(shop1);
		
		Assert.assertTrue(AssetManagerController.shopsMap.size() == 1);
	}

	@Test
	public void testDuplicateShopAddition() throws Exception {
		Shop shop1 = new Shop("Shop1",new ShopAddress("1","LU2 1AA"));
		Shop shop2 = new Shop("Shop1",new ShopAddress("2","LU2 1BB"));
		
	
		assetManagerController.addShop(shop1);
		assetManagerController.addShop(shop2);
		Assert.assertTrue(AssetManagerController.shopsMap.size() == 1);
	}
	
	@Test
	public void testMultipleShopAddition() throws Exception {
		Shop shop1 = new Shop("Shop1",new ShopAddress("1","LU2 1AA"));
		Shop shop2 = new Shop("Shop2",new ShopAddress("2","LU1 1AA"));
		Shop shop3 = new Shop("Shop3",new ShopAddress("3","LU3 1AA"));
		
		assetManagerController.addShop(shop1);
		assetManagerController.addShop(shop2);
		assetManagerController.addShop(shop3);
		Assert.assertTrue(AssetManagerController.shopsMap.size() == 3);
	}
	
	@Test
	public void testGoogleAPIOnAddition() throws Exception {
		Shop shop1 = new Shop("Shop1",new ShopAddress("1","LU2 1AA"));
		
		assetManagerController.addShop(shop1);
		
		Assert.assertTrue(AssetManagerController.shopsMap.get("Shop1").getShopAddress().getLatitude() != 0);
		Assert.assertTrue(AssetManagerController.shopsMap.get("Shop1").getShopAddress().getLongitude() != 0);
	}
	
	@Test
	public void testDistanceCalculator() throws Exception {
		double distance = assetManagerController.calcDistance(0, 1, 0, 1);
		
		Assert.assertTrue(distance != 0);
		
	}


	@Test
	public void testGetShop() throws Exception {
		
		ResultData result = assetManagerController.getShop(1, 1);
		
		Assert.assertTrue(result.getShop() != null);
		Assert.assertTrue(result.getShop().getShopName().equals("Shop1"));
	}

	
}
