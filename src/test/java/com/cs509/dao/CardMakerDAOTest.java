package com.cs509.dao;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.cs509.utils.DBUtils;

@RunWith(MockitoJUnitRunner.class)
public class CardMakerDAOTest {
    @Mock 
    CardMakerDAO dao;
	
    @Test
	public void testDBConn() {
		java.sql.Connection conn;		
		try {
			conn = DBUtils.connect();
			assertEquals(false, conn.isClosed());
			conn.close();
			assertEquals(true, conn.isClosed());
		} catch (Exception e) {
		}
	}
    
    @Test
    public void listAllCardsTest() throws Exception {
    	assertNotNull(dao.listAllCards());
    }

}
