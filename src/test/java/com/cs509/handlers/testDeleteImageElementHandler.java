package com.cs509.handlers;

import com.amazonaws.util.json.Jackson;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.gson.JsonObject;
import org.junit.Assert;
import org.junit.Test;

import java.io.*;

public class testDeleteImageElementHandler {
    @Test
    public void test() throws IOException {
        JsonObject data  = new JsonObject();
        int imageID = 99;
        data.addProperty("imageID", imageID);

        DeleteImageElementHandler handler = new DeleteImageElementHandler();
        String str = data.toString();

        InputStream input = new ByteArrayInputStream(str.getBytes());
        OutputStream output = new ByteArrayOutputStream();
        testContext ctx = new testContext();
        handler.handleRequest(input,output, ctx);

        JsonNode outputNode = Jackson.fromJsonString(output.toString(), JsonNode.class);
        String code  = outputNode.get("statusCode").asText();
        Assert.assertEquals("200", code);
    }
}
