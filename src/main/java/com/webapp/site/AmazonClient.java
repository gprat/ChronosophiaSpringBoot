package com.webapp.site;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.GetObjectTaggingRequest;
import com.amazonaws.services.s3.model.GetObjectTaggingResult;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.ObjectTagging;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.amazonaws.services.s3.model.SetObjectTaggingRequest;
import com.amazonaws.services.s3.model.Tag;

@Service
public class AmazonClient {

    private AmazonS3 s3client;

    @Value("${amazonProperties.endpointUrl}")
    private String endpointUrl;
    @Value("${amazonProperties.bucketName}")
    private String bucketName;
    @Value("${amazonProperties.accessKey}")
    private String accessKey;
    @Value("${amazonProperties.secretKey}")
    private String secretKey;
@PostConstruct
    private void initializeAmazon() {
       AWSCredentials credentials = new BasicAWSCredentials(this.accessKey, this.secretKey);
       this.s3client = AmazonS3ClientBuilder
    		   .standard()
    		   .withCredentials(new AWSStaticCredentialsProvider(credentials))
    		   .withRegion("eu-west-3")
    		   .build();
}

	public void uploadJSON(String filename, String content, String description,String username) {
		s3client.putObject(bucketName, filename, content);
		List<Tag> newTags = new ArrayList<Tag>();
        newTags.add(new Tag("description", description));
        newTags.add(new Tag("username", username));
        s3client.setObjectTagging(new SetObjectTaggingRequest(bucketName, filename, new ObjectTagging(newTags)));
	}
	
	public List<FileContent> listofJson(String prefix) {
		ObjectListing listing = s3client.listObjects( bucketName, prefix);
		List<S3ObjectSummary> summaries = listing.getObjectSummaries();
		List<FileContent> contents = new ArrayList();

		while (listing.isTruncated()) {
		   listing = s3client.listNextBatchOfObjects (listing);
		   summaries.addAll (listing.getObjectSummaries());
		}
		
		for(S3ObjectSummary summary : summaries) {
			String filename;
			String description="";
			String username="";
			filename = summary.getKey();
			GetObjectTaggingRequest getTaggingRequest = new GetObjectTaggingRequest(bucketName, filename);
	        GetObjectTaggingResult getTagsResult = s3client.getObjectTagging(getTaggingRequest);
	        for(Tag tag : getTagsResult.getTagSet()) {
	        	if(tag.getKey().equals("description")) {
	        		description = tag.getValue();
	        	}
	        	if(tag.getKey().equals("username")) {
	        		username = tag.getValue();
	        	}
	        }
	        contents.add(new FileContent(filename, description, username));
		}
		return contents;
	}
	
	public InputStream getFile(String filename){
		InputStream input = null;
		S3Object fullObject = null;
		fullObject = s3client.getObject(new GetObjectRequest(bucketName, filename));
		if(fullObject!=null) {
			return fullObject.getObjectContent();
		}
		return null;
	}
}