package com.cs509.handlers;
import com.amazonaws.util.json.Jackson;
import com.cs509.handlers.CreateCardHandler;
import com.cs509.handlers.testContext;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.gson.JsonObject;
import com.google.gson.Gson;


import org.junit.Test;
import org.junit.Assert;

import java.io.*;

public class testCreateCardHandler {
    @Test
    public void test() throws IOException {
        JsonObject data  = new JsonObject();
        int cOrient = 1;
        String rNameF = "test";
        String rEmail = "test@test.com";
        String eType = "testType";
        data.addProperty("cardOrientation",cOrient);
        data.addProperty("recipientName", rNameF);
        data.addProperty("recipientEmail", rEmail);
        data.addProperty("eventType", eType);

        CreateCardHandler Handler = new CreateCardHandler();
        String str = data.toString();

        InputStream input = new ByteArrayInputStream(str.getBytes());
        OutputStream output = new ByteArrayOutputStream();
        testContext ctx = new testContext();

        Handler.handleRequest(input, output, ctx);
        JsonNode outputNode = Jackson.fromJsonString(output.toString(), JsonNode.class);
        String code  = outputNode.get("statusCode").asText();
        Assert.assertEquals("200", code);

    }
}
