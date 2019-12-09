package com.cs509.handlers;

import com.amazonaws.util.json.Jackson;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.gson.JsonObject;
import org.junit.Assert;
import org.junit.Test;

import java.io.*;

public class testUpdateTextElementHandler {
    @Test
    public void test() throws IOException {
        JsonObject data  = new JsonObject();
        String text = "test";
        int xOrient = 100;
        int yOrient = 100;
        int width = 200;
        int height = 200;
        String font = "Verdana";
        int fontSize = 12;
        int textID = 999;
        data.addProperty("text", text);
        data.addProperty("xOrient", xOrient);
        data.addProperty("yOrient", yOrient);
        data.addProperty("width", width);
        data.addProperty("height", height);
        data.addProperty("font", font);
        data.addProperty("fontSize", fontSize);
        data.addProperty("textID", textID);

        UpdateTextElementHandler Handler = new UpdateTextElementHandler();
        String str = data.toString();

        InputStream input = new ByteArrayInputStream(str.getBytes());
        OutputStream output = new ByteArrayOutputStream();
        testContext ctx = new testContext();
        Handler.handleRequest(input,output, ctx);

        JsonNode outputNode = Jackson.fromJsonString(output.toString(), JsonNode.class);
        String code  = outputNode.get("statusCode").asText();
        Assert.assertEquals("200", code);

    }
}
