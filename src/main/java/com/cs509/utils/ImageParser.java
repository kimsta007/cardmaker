package com.cs509.utils;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Base64;

import javax.imageio.ImageIO;

public class ImageParser {
	
	public File decodeToImage(String imageString, String imageFileName) {
        BufferedImage image = null;
        File renamedImage = null;
        try {
        	byte[] imageByte = Base64.getDecoder().decode(imageString);
            ByteArrayInputStream inputStream = new ByteArrayInputStream(imageByte);
            image = ImageIO.read(inputStream);
            renamedImage = new File(imageFileName);
            String extension[] = imageFileName.split(".");
            ImageIO.write(image, extension[1], renamedImage);
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return renamedImage; //File to be uploaded to S3 bucket
    }
	
	//Mnnn need to think about you !
	public String encodeToString(BufferedImage image, String type) {
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
