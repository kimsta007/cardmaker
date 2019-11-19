package com.cs509.utils;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class ParseJSON {

	BufferedReader reader;
	JSONParser parser;
	JSONObject jsonString;
	
	public int addTwoNums() {
		return 5 + 5;
	}
	
	public Map<String, String> jsonParser(InputStream stream) throws Exception {
			reader = new BufferedReader(new InputStreamReader(stream));
			parser = new JSONParser();
			jsonString = (JSONObject) parser.parse(reader);
			Map<String, String> map = new HashMap<>();
			
			for (Object key : jsonString.keySet()) {
		        String keyStr = (String)key;
		        Object keyvalue = jsonString.get(keyStr);
		        map.put(keyStr, keyvalue.toString());
			}
			return map;
		}
}

