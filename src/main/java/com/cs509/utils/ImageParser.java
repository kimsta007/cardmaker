package com.cs509.utils;

import java.io.ByteArrayInputStream;
import java.util.Base64;

public class ImageParser {
	
	public byte[]  decodeToImage(String imageString) throws Exception {
        	byte[] imageByte = Base64.getDecoder().decode(imageString);
            ByteArrayInputStream inputStream = new ByteArrayInputStream(imageByte);       
            inputStream.close();
        return imageByte; //File to be uploaded to S3 bucket
    }
}
