package assetmanager;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.maps.GaeRequestHandler;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.GeocodingResult;

@RestController
public class AssetManagerController {

	//Map to store all shops in memory, name of the shop acts as the key
	public static Map<String,Shop> shopsMap = new ConcurrentHashMap<String,Shop>();


	@RequestMapping(method = RequestMethod.POST, value="/shops")
	public ResultData addShop( @RequestBody Shop inputData) {

		String result;
		Shop resultShop;


		//Call Google API to locate longitude and latitude
		GeoApiContext context = new GeoApiContext(new GaeRequestHandler()).setApiKey("AIzaSyC8FPgSoJU8oKdtaxUxqtFVeRGdh4B5ta0");

		GeocodingResult[] results = null;
		results = GeocodingApi.newRequest(context).address(inputData.getShopName()+" "+inputData.getShopAddress().toString()).awaitIgnoreError();
		//GeocodingApiRequest apiRequest = GeocodingApi.geocode(context,"1600 Amphitheatre Parkway Mountain View, CA 94043");
		//results = apiRequest.awaitIgnoreError();

		if(results != null){
			inputData.getShopAddress().setLatitude(results[0].geometry.location.lat);
			inputData.getShopAddress().setLongitude(results[0].geometry.location.lng);
		}


		/*Two scenarios :
		 * 1. The shop already exists -- It will be replaced by new value, old value will be returned
		 * 2. New shop - It will be added to the map
		 */
		//Added synchronization to avoid parallel updates
		synchronized(shopsMap){
			resultShop = shopsMap.put(inputData.getShopName(), inputData);
		}
		if(null == resultShop){
			result = "New shop successfully added to the system";
		}
		else{
			result = "This shop already exists in the system, data replaced by new values. Old shop details shown here";
		}

		System.out.println("Total objects in map = "+shopsMap.size());

		return new ResultData(result,resultShop);
	}


	@RequestMapping(method = RequestMethod.GET, value="/shops")
	public ResultData getShop(@RequestParam(value="latitude") double latitude,@RequestParam(value="longitude") double longitude) {

		String result = null;
		Shop resultShop = null;

		double shortestDistance = Double.MAX_VALUE;

		if(shopsMap.size() != 0){
			result = "Below is closest shop from your location";

			for(Shop shop : shopsMap.values() ){
				double distance = calcDistance(latitude, shop.getShopAddress().getLatitude(), longitude, shop.getShopAddress().getLongitude());
				if(distance < shortestDistance){
					shortestDistance = distance;
					resultShop = shop;
				}
			}
		}
		else{
			result = "No shops found in system";
			resultShop = null;
		}

		return new ResultData(result,resultShop);

	}

	public static double calcDistance(double lat1, double lat2, double lon1,
			double lon2) {

		final int R = 6371; // Radius of the earth

		double latDistance = Math.toRadians(lat2 - lat1);
		double lonDistance = Math.toRadians(lon2 - lon1);
		double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
				+ Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
				* Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		double distance = R * c * 1000; // convert to meters


		return distance;
	}


}
