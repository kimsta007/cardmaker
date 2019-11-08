import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import com.google.gson.Gson;

public class DuplicateCardHandler implements RequestStreamHandler {

	ParseJSON parserUtils;
	Map<String, String> parsedValues;
	CardMakerDAO dao;
	JSONUtils myUtils;
	
	public DuplicateCardHandler() {
		parserUtils = new ParseJSON();
		parsedValues = new HashMap<String, String>();
		dao = new CardMakerDAO();
		myUtils = new JSONUtils();
	}
	
	@SuppressWarnings("unchecked")
	public void formatResponse(String jsonString, int statusCode) {
        myUtils.getResponseJson().put("body", jsonString);  
        myUtils.getResponseJson().put("statusCode", statusCode);
	}
	
	@Override
	public void handleRequest(InputStream input, OutputStream output, Context context) throws IOException {
		// extract body from incoming HTTP POST request. If any error, then return 422 error
		try {
	        parsedValues = parserUtils.jsonParser(input);
		} catch (Exception pe) {
			this.formatResponse(new Gson().toJson("Unable to process input"), 422);		
		}

		try {
			String cOrient = parsedValues.get("cardOrientation");
			String rName = parsedValues.get("recipientName");
			String rEmail = parsedValues.get("recipientEmail");
			String eType = parsedValues.get("eventType");
			String cardID = parsedValues.get("cardID");
			this.formatResponse(new Gson().toJson(dao.duplicateCard(cardID, eType, cOrient, rName, rEmail)), 200);
		} catch (Exception e) {
			this.formatResponse(new Gson().toJson(e.getMessage()), 400);
		}
	
		OutputStreamWriter writer = new OutputStreamWriter(output, "UTF-8"); 
		writer.write(myUtils.getResponseJson().toJSONString());  
		writer.close();		
	}
}
