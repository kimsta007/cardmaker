package com.cs509.handlers;

import com.amazonaws.util.json.Jackson;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.gson.JsonObject;
import org.junit.Assert;
import org.junit.Test;

import java.io.*;

public class testDeleteTextElementHandler {
    @Test
    public void test() throws IOException {
        JsonObject data  = new JsonObject();
        int textID = 999;
        data.addProperty("textID", textID);

        DeleteTextElementHandler handler = new DeleteTextElementHandler();
        OutputStream output = new ByteArrayOutputStream();

        testContext ctx = new testContext();
        String str = data.toString();
        InputStream input = new ByteArrayInputStream(str.getBytes());
        handler.handleRequest(input,output, ctx);

        JsonNode outputNode = Jackson.fromJsonString(output.toString(), JsonNode.class);
        String code  = outputNode.get("statusCode").asText();
        Assert.assertEquals("200", code);


    }
}
