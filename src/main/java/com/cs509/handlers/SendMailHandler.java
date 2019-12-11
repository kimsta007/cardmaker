package com.cs509.handlers;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import com.amazonaws.services.simpleemail.model.Body;
import com.amazonaws.services.simpleemail.model.Content;
import com.amazonaws.services.simpleemail.model.Destination;
import com.amazonaws.services.simpleemail.model.Message;
import com.amazonaws.services.simpleemail.model.SendEmailRequest;
import com.cs509.utils.JSONUtils;
import com.cs509.utils.ParseJSON;
import com.google.gson.Gson;

public class SendMailHandler implements RequestStreamHandler{

    static final String FROM = "proakim@gmail.com";                                                      
    static final String SUBJECT = "Card Maker";
	ParseJSON parserUtils;
	Map<String, String> parsedValues;
	JSONUtils myUtils;

    public SendMailHandler() {
    	parserUtils = new ParseJSON();
		parsedValues = new HashMap<String, String>();
		myUtils = new JSONUtils();
    }
    
    @SuppressWarnings("unchecked")
	public void formatResponse(String jsonString, int statusCode) {
        myUtils.getResponseJson().put("body", jsonString);  
        myUtils.getResponseJson().put("statusCode", statusCode);
	}
    
	@Override
	public void handleRequest(InputStream input, OutputStream output,
			com.amazonaws.services.lambda.runtime.Context context) throws IOException {
		try {
	        parsedValues = parserUtils.jsonParser(input);
		} catch (Exception pe) {
			this.formatResponse(new Gson().toJson("Unable to process input"), 422);		
		}
		try {
			String contactName = parsedValues.get("contactName");
			String contactEmail = parsedValues.get("contactEmail");
			String cardURL = parsedValues.get("cardURL");
			StringBuilder mailBody = new StringBuilder();
			mailBody.append("Hi " + contactName + ", You have received an E-Card.");
			mailBody.append("Click the link to view the card: " + cardURL);
			
			Destination destination = new Destination().withToAddresses(new String[]{contactEmail});

	        Content subject = new Content().withData(SUBJECT);
	        Content textBody = new Content().withData(mailBody.toString());
	        Body body = new Body().withText(textBody);

	        Message message = new Message().withSubject(subject).withBody(body);

	        SendEmailRequest request = new SendEmailRequest().withSource(FROM).withDestination(destination).withMessage(message);

	        try {
	            AmazonSimpleEmailService client = AmazonSimpleEmailServiceClientBuilder.standard()
	               // .withCredentials(new AWSStaticCredentialsProvider(awsCreds))              
	                .withRegion("us-east-1")
	                .build();

	            // Send the email.
	            client.sendEmail(request);
	            this.formatResponse(new Gson().toJson("Email Sent!"), 200);		

	        } catch (Exception ex) {
	            this.formatResponse(new Gson().toJson(ex.getMessage()), 400);
	        }		
	    } catch (Exception e) {
			this.formatResponse(new Gson().toJson(e.getMessage()), 400);
		}
		OutputStreamWriter writer = new OutputStreamWriter(output, "UTF-8"); 
		writer.write(myUtils.getResponseJson().toJSONString());  
		writer.close();		
	}
}
