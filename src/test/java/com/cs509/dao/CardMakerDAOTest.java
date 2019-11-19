package com.cs509.dao;

import static org.junit.Assert.*;
import org.junit.Test;

public class CardMakerDAOTest {

	@Test
	public void testGetRecientID() {
		CardMakerDAO dx = new CardMakerDAO();
		assertEquals(10, dx.addTwoNums());
	}

}
