import org.json.simple.JSONObject;

public class JSONUtils {
	private JSONObject headerJson;
	private JSONObject responseJson;
	
	@SuppressWarnings("unchecked")
	public JSONUtils() {
		this.headerJson = new JSONObject();
		headerJson.put("Content-Type", "application/json");  
		headerJson.put("Access-Control-Allow-Methods", "GET,POST,DELETE,OPTIONS");
	    headerJson.put("Access-Control-Allow-Origin",  "*");	        
		this.responseJson = new JSONObject();
		responseJson.put("headers", headerJson);
	}
	
	public JSONObject getResponseJson() {
		return responseJson;
	}
}
