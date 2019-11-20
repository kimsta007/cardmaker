package com.cs509.utils;

import java.io.File;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

public class S3Utils {
	Regions clientRegion;
	String bucketName;
	AmazonS3 s3Client; 
	String awsBucketURL = "https://cardmakerimages.s3.ca-central-1.amazonaws.com/";
	
	public S3Utils() {
		 clientRegion = Regions.CA_CENTRAL_1; 
	     bucketName = "cardmakerimages"; 
	     s3Client = AmazonS3ClientBuilder.standard().withRegion(clientRegion).build();	    
	}
	
	public String uploadImage(String fileName, String fileExtension, File imageFile) throws AmazonServiceException {
		 String fileObjKeyName = fileName;
	         // https://docs.aws.amazon.com/sdk-for-java/v1/developer-guide/setup-credentials.html	       
	         PutObjectRequest request = new PutObjectRequest(bucketName, fileObjKeyName, imageFile);
	         ObjectMetadata metadata = new ObjectMetadata();
	         metadata.setContentType("images/" + fileExtension);
	         metadata.addUserMetadata("x-amz-meta-title", fileName);
	         request.setMetadata(metadata);
	         s3Client.putObject(request);
	         return awsBucketURL + fileName;
     }
}
