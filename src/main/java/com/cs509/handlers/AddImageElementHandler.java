package com.cs509.handlers;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import com.cs509.dao.CardMakerDAO;
import com.cs509.utils.ImageParser;
import com.cs509.utils.JSONUtils;
import com.cs509.utils.ParseJSON;
import com.cs509.utils.S3Utils;
import com.google.gson.Gson;

public class AddImageElementHandler implements RequestStreamHandler {

	ParseJSON parserUtils;
	Map<String, String> parsedValues;
	CardMakerDAO dao;
	JSONUtils myUtils;
	
	public AddImageElementHandler() {
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
		
		ImageParser parser = new ImageParser();  
		try {
			String fileName = parsedValues.get("imageName");
			String base64String = parsedValues.get("base64String");
			String xOrient = parsedValues.get("xOrient");
			String yOrient = parsedValues.get("yOrient");
			String width = parsedValues.get("width");
			String height = parsedValues.get("height");
			String pageID = parsedValues.get("pageID");
			String cardID = parsedValues.get("cardID");	    
			String url = new S3Utils().uploadImage(parser.decodeToImage(base64String), fileName);
			this.formatResponse(new Gson().toJson(dao.addImageElement(url, xOrient, yOrient, width, height, pageID, cardID)), 200);		    
	    } catch (Exception e) {
			this.formatResponse(new Gson().toJson(e.getMessage()), 400);
		}
	
		OutputStreamWriter writer = new OutputStreamWriter(output, "UTF-8"); 
		writer.write(myUtils.getResponseJson().toJSONString());  
		writer.close();		
	}
}
