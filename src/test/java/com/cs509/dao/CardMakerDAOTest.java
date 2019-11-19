package com.cs509.dao;

import static org.junit.Assert.*;

import org.junit.Test;

import com.cs509.utils.DBUtils;

public class CardMakerDAOTest {

	@Test
	public void testGetRecientID() {
		java.sql.Connection conn;		
		try {
			conn = DBUtils.connect();
			assertEquals(false, conn.isClosed());
			conn.close();
			assertEquals(true, conn.isClosed());
		} catch (Exception e) {
		}
	}

}
