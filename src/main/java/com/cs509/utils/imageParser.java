package com.cs509.utils;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Base64;

import javax.imageio.ImageIO;

public class imageParser {
	
	public static BufferedImage decodeToImage(String imageString) {
        BufferedImage image = null;
        try {
        	byte[] imageByte = Base64.getDecoder().decode(imageString);
            ByteArrayInputStream inputStream = new ByteArrayInputStream(imageByte);
            image = ImageIO.read(inputStream);
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return image; //image to be uploaded to S3 bucket
    }
	
	public static String encodeToString(BufferedImage image, String type) {
        String imageString = null;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            ImageIO.write(image, type, outputStream);
            byte[] imageBytes = outputStream.toByteArray();
            imageString = new String(Base64.getEncoder().encode(imageBytes));
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return imageString; //Return String to Client
    }
}
