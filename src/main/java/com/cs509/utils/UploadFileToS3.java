package com.cs509.utils;

import java.io.File;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

public class UploadFileToS3 {
	
	public void uploadImage(String fileName, String fileExtension, File imageFile) {
		 Regions clientRegion = Regions.CA_CENTRAL_1; 
	     String bucketName = "cardmakerimages"; 
	     String fileObjKeyName = fileName;
	  
	     try {
	         // https://docs.aws.amazon.com/sdk-for-java/v1/developer-guide/setup-credentials.html
	         AmazonS3 s3Client = AmazonS3ClientBuilder.standard().withRegion(clientRegion).build();
	         PutObjectRequest request = new PutObjectRequest(bucketName, fileObjKeyName, imageFile);
	         ObjectMetadata metadata = new ObjectMetadata();
	         metadata.setContentType("images/" + fileExtension);
	         metadata.addUserMetadata("x-amz-meta-title", fileName);
	         request.setMetadata(metadata);
	         s3Client.putObject(request);
	     } catch (AmazonServiceException ex) {
	    	 ex.printStackTrace();
	     }
     }
}
