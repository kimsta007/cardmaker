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
	
	public String createCard(String xOrient, String eType, String rName, String rEmail) throws Exception {
		try {
			PreparedStatement ps = conn.prepareStatement("Insert into cs509db.card values(?,?,?,?)");
			ps.setNull(1,0);
			ps.setInt (2, Integer.parseInt(xOrient));
			ps.setInt(3, Integer.parseInt(eType));
			ps.setInt(4, getRecipientID(rName, rEmail));			
			ps.execute();
			ps.close();
			return "Card created.";
		} catch (Exception ex) {
			throw new Exception("Failed to create card." + ex.getMessage());
		}
	}
	
	public String deleteCard(String rName, String eType, String rEmail) throws Exception {
		try {
			PreparedStatement ps = 
				conn.prepareStatement("delete from cs509db.card where recipientID = " + getRecipientID(rName, rEmail) + " and eventTypeID = " + 
			Integer.parseInt(eType) + ";");
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
	
	private void dumby() {
		
	}
	
	public void addRecipient() {
		
	}
	
	public void deleteRecipient() {
		
	}
	
	public void updateRecipient() {
		
	}
	
	public void addTextElement() {
		
	}
	
	public void deleteTextElement() {
		
	}

	public void updateTextElement() {
		
	}
	
	public void addImageElement() {
		
	}
	
	public void updateImageElement() {
		
	}
	
	public void deleteImageElement() {
		
	}
}
