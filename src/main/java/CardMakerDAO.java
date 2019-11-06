import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.sql.ResultSetMetaData;

public class CardMakerDAO {
	
	java.sql.Connection conn;
	
	public CardMakerDAO (){
		try  {
    		conn = DBUtils.connect();
    	} catch (Exception e) {
    		conn = null;
    	}
	}
	
	public int getRecipientID(String rName, String rEmail) throws Exception {
        int recipientID = 0;
		PreparedStatement ps = conn.prepareStatement("SELECT recipientID FROM cs509db.recipient where recipientName = '" + rName + "' and"
        		+ " recipientEmail = '" + rEmail + "';");	    
        ResultSet resultSet = ps.executeQuery();	
        while (resultSet.next()) {
        	recipientID = resultSet.getInt("recipientID");
        }
        resultSet.close();
        ps.close();
        return recipientID;
	}
	
	public ArrayList<HashMap<String, String>> createCard(String xOrient, String eType, String rName, String rEmail) throws Exception {
		try {
			PreparedStatement ps = conn.prepareStatement("Insert into cs509db.card values(?,?,?,?)");
			ps.setNull(1,0);
			ps.setInt (2, Integer.parseInt(xOrient));
			ps.setInt(3, Integer.parseInt(eType));
			ps.setInt(4, getRecipientID(rName, rEmail));			
			ps.execute();
			ps.close();
		} catch (Exception ex) {
			throw new Exception("Failed to create card." + ex.getMessage());
		}
			return listAllCards();
	}
	
	public String deleteCard(String cardID) throws Exception {
		try {
			PreparedStatement ps = 
				conn.prepareStatement("delete from cs509db.card where recipientID = " + Integer.parseInt(cardID) + ";");
				ps.execute();
				ps.close();
			return "Card deleted.";
		} catch (Exception ex) {
			throw new Exception("Failed to delete card." + ex.getMessage());
		}
	}
	
	public ArrayList<HashMap<String, String>> listAllRecipients() throws Exception {
		 try {
	            ArrayList<HashMap<String, String>> record = new ArrayList<HashMap<String, String>>();
	            PreparedStatement ps = conn.prepareStatement("SELECT * FROM cs509db.recipient;");	    
	            ResultSet resultSet = ps.executeQuery();	
	            ResultSetMetaData rsMetaData = resultSet.getMetaData();
	            while (resultSet.next()) {
	            	HashMap<String, String> resultsMap = new HashMap<String,String>();
	            	resultsMap.put(rsMetaData.getColumnName(0), resultSet.getString("recipientID"));
	            	resultsMap.put(rsMetaData.getColumnName(1), resultSet.getString("recipientName"));
	            	resultsMap.put(rsMetaData.getColumnName(2), resultSet.getString("recipientSurname"));	
	            	resultsMap.put(rsMetaData.getColumnName(3), resultSet.getString("recipientEmail"));
	            	record.add(resultsMap);
	            }
	            resultSet.close();
	            ps.close();
	            return record;
	        } catch (Exception e) {
	        	throw new Exception("Failed to list recipients " + e.getMessage());
	        }
	}
	
	public ArrayList<HashMap<String, String>> listAllCards() throws Exception {
		 try {  
	            ArrayList<HashMap<String, String>> record = new ArrayList<HashMap<String, String>>();
	            PreparedStatement ps = conn.prepareStatement(
	            		"Select cardID, cardOrientation, event, recipientName, recipientSurname, recipientEmail from card " + 
	            		"inner join recipient on card.recipientID = recipient.recipientID " + 
	            		"inner join event on card.eventTypeID = event.eventTypeID;"
	            		);	    
	            ResultSet resultSet = ps.executeQuery();	
	            ResultSetMetaData rsMetaData = resultSet.getMetaData();
	            while (resultSet.next()) {
	            	HashMap<String, String> resultsMap = new HashMap<String,String>();
	            	resultsMap.put(rsMetaData.getColumnName(1), resultSet.getString("cardID"));
	            	resultsMap.put(rsMetaData.getColumnName(2), resultSet.getString("cardOrientation"));
	            	resultsMap.put(rsMetaData.getColumnName(3), resultSet.getString("event"));	
	            	resultsMap.put(rsMetaData.getColumnName(4), resultSet.getString("recipientName"));
	            	resultsMap.put(rsMetaData.getColumnName(5), resultSet.getString("recipientSurname"));	
	            	resultsMap.put(rsMetaData.getColumnName(6), resultSet.getString("recipientEmail"));
	            	record.add(resultsMap);
	            }
	            resultSet.close();
	            ps.close();
	            return record;
	        } catch (Exception e) {
	        	throw new Exception("Failed to list recipients " + e.getMessage());
	        }
	}
	
	public String addRecipient(String rName, String rSurname, String rEmail) throws Exception {
		try {
			PreparedStatement ps = conn.prepareStatement("Insert into cs509db.recipient values(?,?,?,?)");
			ps.setNull(1,0);
			ps.setString(2, rName);
			ps.setString(3, rSurname);
			ps.setString(4, rEmail);			
			ps.execute();
			ps.close();
			return "Recipient Added.";
		} catch (Exception ex) {
			throw new Exception("Failed to add recipient."	+ ex.getMessage());
		}
	}
	
	public String deleteRecipient(String recipientID) throws Exception{
		try {
			PreparedStatement ps = 
				conn.prepareStatement("delete from cs509db.recipient where recipientID = " + Integer.parseInt(recipientID) + ";");
				ps.execute();
				ps.close();
			return "Recipient deleted.";
		} catch (Exception ex) {
			throw new Exception("Failed to delete recipient." + ex.getMessage());
		}
	}
	
	
	public String updateRecipient(String rName, String rSurname, String rEmail, String recipientID) throws Exception {
		try {
			PreparedStatement ps = 
				conn.prepareStatement("update cs509db.recipient set recipientName = '" + rName + "', recipientSurname = '"+ rSurname +
						"', recipientEmail = '" + rEmail + "' where recipientID = " + Integer.parseInt(recipientID) + ";");
				ps.execute();
				ps.close();
			return "Recipient updated.";
		} catch (Exception ex) {
			throw new Exception("Failed to update recipient." + ex.getMessage());
		}
	}
	
	//Return everything related to this element
	public String addTextElement(String text, String xOrient, String yOrient, String width, String height, String font, String fontSize,
			String pageID, String cardID) throws Exception {
		try {
			PreparedStatement ps = conn.prepareStatement("Insert into cs509db.text values(?,?,?,?,?,?,?,?,?,?)");
			ps.setNull(1,0);
			ps.setString (2, text);
			ps.setInt(3, Integer.parseInt(xOrient));
			ps.setInt(4, Integer.parseInt(yOrient));			
			ps.setInt(5, Integer.parseInt(width));
			ps.setInt(6, Integer.parseInt(height));			
			ps.setString(7, font);
			ps.setInt(8, Integer.parseInt(fontSize));			
			ps.setInt(9, Integer.parseInt(pageID));
			ps.setInt(10, Integer.parseInt(cardID));			
			ps.execute();
			ps.close();
			return "Text Inserted.";
		} catch (Exception ex) {
			throw new Exception("Failed to create card." + ex.getMessage());
		}
	}
	
	public String deleteTextElement(String textID) throws Exception {
		try {
			PreparedStatement ps = 
				conn.prepareStatement("delete from cs509db.text where textID = " + Integer.parseInt(textID) + ";");
				ps.execute();
				ps.close();
			return "Text deleted.";
		} catch (Exception ex) {
			throw new Exception("Failed to delete recipient." + ex.getMessage());
		}
	}

	public String updateTextElement(String text, String xOrient, String yOrient, String width, String height, String font, String fontSize,
			String textID) throws Exception{
		try {
			PreparedStatement ps = 
				conn.prepareStatement("update cs509db.text set name = " + text + ", xOrient = "+ Integer.parseInt(xOrient) +
						", yOrient = " + Integer.parseInt(yOrient) + ", width = " + Integer.parseInt(width) + ", height = " + Integer.parseInt(height) + 
						", font = '" + font + "', fontSize = " + Integer.parseInt(fontSize) + "where textID = " + Integer.parseInt(textID) + ";");
				ps.execute();
				ps.close();
			return "Text updated.";
		} catch (Exception ex) {
			throw new Exception("Failed to update Text." + ex.getMessage());
		}
	}
	
	//Return everything related to this element
	public String addImageElement(String name, String xOrient, String yOrient, String width, String height, String pageID, String cardID) 
	throws Exception {
		try {
			PreparedStatement ps = conn.prepareStatement("Insert into cs509db.text values(?,?,?,?,?,?,?,?,?,?)");
			ps.setNull(1,0);
			ps.setString (2, name);
			ps.setInt(3, Integer.parseInt(xOrient));
			ps.setInt(4, Integer.parseInt(yOrient));			
			ps.setInt(5, Integer.parseInt(width));
			ps.setInt(6, Integer.parseInt(height));			
			ps.setInt(7, Integer.parseInt(pageID));
			ps.setInt(8, Integer.parseInt(cardID));			
			ps.execute();
			ps.close();
			return "Image Inserted.";
		} catch (Exception ex) {
			throw new Exception("Failed to create card." + ex.getMessage());
		}
	}
	
	public String updateImageElement(String name, String xOrient, String yOrient, String width, String height, String imageID) throws Exception{
		try {
			PreparedStatement ps = 
				conn.prepareStatement("update cs509db.image set name = " + name + ", xOrient = "+ Integer.parseInt(xOrient) +
						", yOrient = " + Integer.parseInt(yOrient) + ", width = " + Integer.parseInt(width) + ", height = " + Integer.parseInt(height) + 
						"where imageID = " + Integer.parseInt(imageID) + ";");
				ps.execute();
				ps.close();
			return "Image updated.";
		} catch (Exception ex) {
			throw new Exception("Failed to update Image." + ex.getMessage());
		}
	}
	
	public String deleteImageElement(String imageID) throws Exception {
		try {
			PreparedStatement ps = 
				conn.prepareStatement("delete from cs509db.image where imageID = " + Integer.parseInt(imageID) + ";");
				ps.execute();
				ps.close();
			return "Image deleted.";
		} catch (Exception ex) {
			throw new Exception("Failed to delete Image." + ex.getMessage());
		}
	}
	
	//Get card with all its elements for reconstruction 
	public void getCard() {
		
	}
}
