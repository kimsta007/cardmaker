package com.cs509.handlers;

import static org.junit.Assert.*;

import org.junit.Test;

public class AddTextElementHandlerTest {

	@Test
	public void testAddTwoNumbers() {
		AddTextElementHandler handler = new AddTextElementHandler();
		assertEquals(10, handler.addTwoNums());
	}

}
