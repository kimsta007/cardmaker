package com.cs509.utils;

import static org.junit.Assert.assertNotNull;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Map;

import org.junit.Test;

public class ParseJSONTest {

	@Test
	public void testJsonParser() {
		String initialString = "{" + 
				"\"text\": \"Akim Ndlovu\"," + 
				"\"xOrient\": \"100\"," + 
				"\"yOrient\": \"100\"," + 
				"\"width\": \"200\"," + 
				"\"height\": \"200\"," + 
				"\"font\": \"Verdana\"," + 
				"\"fontSize\": \"12\"," + 
				"\"pageID\": \"2\"," + 
				"\"cardID\": \"44\"" + 
				"}";
	    InputStream testStream = new ByteArrayInputStream(initialString.getBytes());
		ParseJSON parser = new ParseJSON();
		try {
			Map<String, String> map = parser.jsonParser(testStream);
			assertNotNull(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
			
	}

}
