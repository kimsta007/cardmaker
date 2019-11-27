package com.cs509.utils;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class DBUtilsTest {

	@Test
	public void testDBConnect() {		
	    try {
	    	assertNotNull(DBUtils.connect());
	    }catch(Exception e) {
	    	e.printStackTrace();
	    }
	}
}
