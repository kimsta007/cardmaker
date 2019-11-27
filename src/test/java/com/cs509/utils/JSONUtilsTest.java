package com.cs509.utils;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class JSONUtilsTest {

	@Test
	public void testJsonUtils() {
		JSONUtils utils = new JSONUtils();
	    try {
	    	assertNotNull(utils.getResponseJson());
	    }catch(Exception e) {
	    	e.printStackTrace();
	    }
	}
}
