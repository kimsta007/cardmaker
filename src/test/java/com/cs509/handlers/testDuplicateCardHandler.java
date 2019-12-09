package com.cs509.handlers;

import com.amazonaws.util.json.Jackson;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.gson.JsonObject;
import org.junit.Assert;
import org.junit.Test;

import java.io.*;

public class testDuplicateCardHandler {
    @Test
    public void test() throws IOException {
        JsonObject data  = new JsonObject();
        int cOrient = 1;
        String rNameF = "test";
        String rEmail = "test@test.com";
        String eType = "testType";
        int cardID = 999;
        data.addProperty("cardOrientation",cOrient);
        data.addProperty("recipientName", rNameF);
        data.addProperty("recipientEmail", rEmail);
        data.addProperty("eventType", eType);
        data.addProperty("cardID", cardID);

        DuplicateCardHandler Handler = new DuplicateCardHandler();
        String str = data.toString();

        InputStream input = new ByteArrayInputStream(str.getBytes());
        OutputStream output = new ByteArrayOutputStream();
        testContext ctx = new testContext();

        Handler.handleRequest(input, output, ctx);
        JsonNode outputNode = Jackson.fromJsonString(output.toString(), JsonNode.class);
        String code  = outputNode.get("statusCode").asText();
        Assert.assertEquals("400", code);
    }
}
