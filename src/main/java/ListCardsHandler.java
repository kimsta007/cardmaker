import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import com.google.gson.Gson;

public class ListCardsHandler implements RequestStreamHandler {
	CardMakerDAO dao;
	JSONUtils myUtils;
	
	public ListCardsHandler() {
		dao = new CardMakerDAO();
		myUtils = new JSONUtils();
	}
	
	@SuppressWarnings("unchecked")
	public void formatResponse(String jsonString, int statusCode) {
        myUtils.getResponseJson().put("body", jsonString);  
        myUtils.getResponseJson().put("statusCode", statusCode);
	}
	
	@Override
	public void handleRequest(InputStream input, OutputStream output, Context context) throws IOException {

		try {
			this.formatResponse(new Gson().toJson(dao.listAllCards()), 200);
		} catch (Exception e) {
			this.formatResponse(new Gson().toJson(e.getMessage()), 400);
		}

		// compute proper response
        OutputStreamWriter writer = new OutputStreamWriter(output, "UTF-8"); 
        writer.write(myUtils.getResponseJson().toJSONString());  
        writer.close();
	}

}
