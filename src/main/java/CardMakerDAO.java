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
	
	public ArrayList<HashMap<String, String>> createCard(String cOrient, String eType, String rName, String rEmail) throws Exception {
		try {
			PreparedStatement ps = conn.prepareStatement("Insert into cs509db.card values(?,?,?,?)");
			ps.setNull(1,0);
			ps.setInt (2, Integer.parseInt(cOrient));
			ps.setInt(3, Integer.parseInt(eType));
			ps.setInt(4, getRecipientID(rName, rEmail));			
			ps.execute();
			ps.close();
		} catch (Exception ex) {
			throw new Exception("Failed to create card." + ex.getMessage());
		}
			return listAllCards();
	}
	
	public int createDuplicateCard (String cOrient, String eType, String rName, String rEmail) throws Exception {
		int recipientID = 0;
		try {
			PreparedStatement ps = conn.prepareStatement("Insert into card values(?,?,?,?)");
			ps.setNull(1,0);
			ps.setInt (2, Integer.parseInt(cOrient));
			ps.setInt(3, Integer.parseInt(eType));
			recipientID = getRecipientID(rName, rEmail);
			ps.setInt(4, recipientID);			
			ps.execute();
			ps.close();
		} catch (Exception ex) {
			throw new Exception("Failed to create card duplicate." + ex.getMessage());
		}
		return getDuplicateCard(Integer.parseInt(cOrient), Integer.parseInt(eType), recipientID);
	}
	
	public int getDuplicateCard(int cOrient, int eType, int recipientID) throws Exception {
        int cardID = 0;
		PreparedStatement ps = conn.prepareStatement("SELECT cardID FROM card where cardOrientation = " + cOrient + " and"
        		+ " eventTypeID = " + eType + " and recipientID = " + recipientID + " order by cardID desc limit 1;");	    
        ResultSet resultSet = ps.executeQuery();	
        while (resultSet.next()) {
        	cardID = resultSet.getInt("cardID");
        }
        resultSet.close();
        ps.close();
        return cardID;
	}
	
	public String deleteCard(String cardID) throws Exception {
		try {
			PreparedStatement ps = 
				conn.prepareStatement("delete from card where cardID = " + Integer.parseInt(cardID) + ";");
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
				conn.prepareStatement("delete from recipient where recipientID = " + Integer.parseInt(recipientID) + ";");
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
				conn.prepareStatement("update recipient set recipientName = '" + rName + "', recipientSurname = '"+ rSurname +
						"', recipientEmail = '" + rEmail + "' where recipientID = " + Integer.parseInt(recipientID) + ";");
				ps.execute();
				ps.close();
			return "Recipient updated.";
		} catch (Exception ex) {
			throw new Exception("Failed to update recipient." + ex.getMessage());
		}
	}
	
	public HashMap<String, String> addTextElement(String text, String xOrient, String yOrient, String width, String height, String font, String fontSize,
			String pageID, String cardID) throws Exception {
		HashMap<String, String> textMap = new HashMap<String, String>();
		try {
			PreparedStatement ps = conn.prepareStatement("Insert into text values(?,?,?,?,?,?,?,?,?,?)");
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
		} catch (Exception ex) {
			throw new Exception("Failed to create Text Element." + ex.getMessage());
		}
		textMap.put("text", text);
		textMap.put("xOrient", xOrient);
		textMap.put("yOrient", yOrient);
		textMap.put("width", width);
		textMap.put("height", height);
		textMap.put("font", font);
		textMap.put("fontSize", fontSize);
		textMap.put("pageID", pageID);
		textMap.put("cardID", cardID);
		textMap.put("textID", Integer.toString(getTextElementByID(text, xOrient, yOrient, width, height, font, fontSize, pageID, cardID)));
		return textMap;
	}
	
	public int getTextElementByID(String text, String xOrient, String yOrient, String width, String height, String font, String fontSize,
			String pageID, String cardID) throws Exception {
        int elementID = 0;
		try {
		PreparedStatement ps = conn.prepareStatement(
        		"Select textID from text where text = '" + text + "' and xOrient = " + Integer.parseInt(xOrient) + " and yOrient = " + 
        				Integer.parseInt(yOrient) + " and width = " + Integer.parseInt(width) + " and height = " + Integer.parseInt(height) + 
        					" and font = '" + font + "' and fontSize = " + Integer.parseInt(fontSize) + " and pageID = " + Integer.parseInt(pageID) + 
        					" and cardID = " + Integer.parseInt(cardID) + " order by textID desc limit 1;"
        );	    
        ResultSet resultSet = ps.executeQuery();	
        while (resultSet.next()) {
        	elementID = resultSet.getInt("textID");        	
        }
        resultSet.close();
        ps.close();
		return elementID;
		} catch (Exception ex) {
			throw new Exception("Failed to locate textElement." + ex.getMessage());
		}
	}
	
	public int getImageElementByID(String name, String xOrient, String yOrient, String width, String height, String pageID, String cardID) 
			throws Exception {
        int elementID = 0;
		try {
		PreparedStatement ps = conn.prepareStatement(
        		"Select imageID from image where name = '" + name + "', xOrient = " + Integer.parseInt(xOrient) + ", yOrient = " + 
        				Integer.parseInt(yOrient) + ", width = " + Integer.parseInt(width) + ", height = " + Integer.parseInt(height) + 
        				 ", pageID = " + Integer.parseInt(pageID) + ", cardID = " + Integer.parseInt(cardID) + ";"
        );	    
        ResultSet resultSet = ps.executeQuery();	
        while (resultSet.next()) {
        	elementID = resultSet.getInt("imageID");        	
        }
        resultSet.close();
        ps.close();
		return elementID;
		} catch (Exception ex) {
			throw new Exception("Failed to locate imageElement." + ex.getMessage());
		}
	}
	
	public String deleteTextElement(String textID) throws Exception {
		try {
			PreparedStatement ps = 
				conn.prepareStatement("delete from text where textID = " + Integer.parseInt(textID) + ";");
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
				conn.prepareStatement("update text set text = '" + text + "', xOrient = "+ Integer.parseInt(xOrient) +
						", yOrient = " + Integer.parseInt(yOrient) + ", width = " + Integer.parseInt(width) + ", height = " + Integer.parseInt(height) + 
						", font = '" + font + "', fontSize = " + Integer.parseInt(fontSize) + " where textID = " + Integer.parseInt(textID) + ";");
				ps.execute();
				ps.close();
			return "Text Element Updated.";
		} catch (Exception ex) {
			throw new Exception("Failed to update Text." + ex.getMessage());
		}
	}
	
	public HashMap<String, String> addImageElement(String name, String xOrient, String yOrient, String width, String height, String pageID, String cardID) 
	throws Exception {
		HashMap<String, String> imageMap = new HashMap<String, String>();
		try {
			PreparedStatement ps = conn.prepareStatement("Insert into image values(?,?,?,?,?,?,?,?)");
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
		} catch (Exception ex) {
			throw new Exception("Failed to create Image Element." + ex.getMessage());
		}
		//Will not necessarily work like this is we store the image in S3 we probably need to be routing it to S3 and sending
		//back a base64 string not sure what will be returned yet
		imageMap.put("name", name);
		imageMap.put("xOrient", xOrient);
		imageMap.put("yOrient", yOrient);
		imageMap.put("width", width);
		imageMap.put("height", height);
		imageMap.put("pageID", pageID);
		imageMap.put("cardID", cardID);
		imageMap.put("imageID", Integer.toString(getImageElementByID(name, xOrient, yOrient, width, height, pageID, cardID)));
		return imageMap;
	}
	
	public String updateImageElement(String name, String xOrient, String yOrient, String width, String height, String imageID) throws Exception{
		try {
			PreparedStatement ps = 
				conn.prepareStatement("update image set name = " + name + ", xOrient = "+ Integer.parseInt(xOrient) +
						", yOrient = " + Integer.parseInt(yOrient) + ", width = " + Integer.parseInt(width) + ", height = " + Integer.parseInt(height) + 
						" where imageID = " + Integer.parseInt(imageID) + ";");
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
				conn.prepareStatement("delete from image where imageID = " + Integer.parseInt(imageID) + ";");
				ps.execute();
				ps.close();
			return "Image deleted.";
		} catch (Exception ex) {
			throw new Exception("Failed to delete Image." + ex.getMessage());
		}
	}
	
	public ArrayList<HashMap<String, String>> duplicateCard(String cardID, String eventTypeID, 
		String cardOrientation, String recipientName, String recipientEmail) throws Exception {
		int duplicateCardID = 0;
		try {
			duplicateCardID = this.createDuplicateCard(cardOrientation, eventTypeID, recipientName, recipientEmail);
			
		} catch (Exception ex) {
			throw new Exception("Failed to duplicate card." + ex.getMessage());
		}
		this.duplicateImages(Integer.parseInt(cardID), duplicateCardID);
		this.duplicateText(Integer.parseInt(cardID), duplicateCardID);
		return listAllCards(); 
	}
	
	public void duplicateImages(int cardID, int duplicateCardID) throws Exception {
		try {
		PreparedStatement ps = conn.prepareStatement("Select * from image where cardID = " + cardID + ";");	    
        ResultSet resultSet = ps.executeQuery();	
        while (resultSet.next()) {
        	updateDuplicateImage(resultSet.getInt("imageID"), duplicateCardID);        	
        }
        resultSet.close();
        ps.close();
		} catch (Exception ex) {
			throw new Exception("Failed to duplicate imageElement." + ex.getMessage());
		}
	}
	
	public void updateDuplicateImage(int imageID, int duplicateCardID) throws Exception {
		try {
			PreparedStatement ps = 
				conn.prepareStatement("update image set cardID = " + duplicateCardID + 
						" where imageID = " + imageID + ";");
				ps.execute();
				ps.close();
		} catch (Exception ex) {
			throw new Exception("Failed to duplicate Image." + ex.getMessage());
		}
	}
	
	public void duplicateText(int cardID, int duplicateCardID) throws Exception {
		try {
			PreparedStatement ps = conn.prepareStatement("Select * from text where cardID = " + cardID + ";");	    
	        ResultSet resultSet = ps.executeQuery();	
	        while (resultSet.next()) {
	    	    addTextElement(resultSet.getString("text"), resultSet.getString("xOrient"), resultSet.getString("yOrient"), 
	    	    		resultSet.getString("width"), resultSet.getString("height"), resultSet.getString("font"), resultSet.getString("fontSize"), 
	    	    			resultSet.getString("pageID"), String.valueOf(duplicateCardID));  
	        }
	        resultSet.close();
	        ps.close();
			} catch (Exception ex) {
				throw new Exception("Failed to duplicate textElement." + ex.getMessage());
			}
	}
	
	public String getCard(String cardID, String recipientID) {
	   return null;
	}
}
