package com.cs509.utils;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
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
	
	public String uploadImage(byte[] imageFile, String fileName) throws Exception {
		ObjectMetadata meta = new ObjectMetadata();
		meta.setContentLength(imageFile.length);
		InputStream inputStream = new ByteArrayInputStream(imageFile);
	    PutObjectRequest request = new PutObjectRequest(bucketName, fileName, inputStream, meta);
	    s3Client.putObject(request.withCannedAcl(CannedAccessControlList.PublicRead));
	    return awsBucketURL + fileName;
     }
}