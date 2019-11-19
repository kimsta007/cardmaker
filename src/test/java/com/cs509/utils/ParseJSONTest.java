package com.cs509.utils;

import static org.junit.Assert.*;

import org.junit.Test;

public class ParseJSONTest {

	@Test
	public void test() {
		ParseJSON parseJson = new ParseJSON();
		assertEquals(10, parseJson.addTwoNums());
	}

}
