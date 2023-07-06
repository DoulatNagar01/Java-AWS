package com.lambdatest1;

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.event.S3EventNotification;
import com.amazonaws.services.s3.model.S3Object;
//import com.amazonaws.services.s3.model.S3ObjectInputStream;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class LambdaS3Event {
    public String m1(){
        System.out.println("Mesg from the m1 method");
        return " from m1 method";
    }

//    private static final AmazonS3 s3Client = AmazonS3Client.builder()
//            .withCredentials(new DefaultAWSCredentialsProviderChain())
//            .build();
//
//    @Override
//    public Boolean handleRequest(S3Event input, Context context) {
//// check if we are getting any records
//        final LambdaLogger logger = context.getLogger();
//// if(input.getRecords().isEmpty()){
//// return false;
//// }
//        S3EventNotification notification = S3EventNotification.parseJson(input.toString());
//        for(S3EventNotification.S3EventNotificationRecord record: notification.getRecords()){
//            String bucketName = record.getS3().getBucket().getName();
//            String objectKey = record.getS3().getObject().getKey();
//            logger.log("Received S3 event notification for object: s3://" + bucketName + "/" + objectKey);
//
//            S3Object s3Object = s3Client.getObject(bucketName,objectKey);
//            S3ObjectInputStream inputStream = s3Object.getObjectContent();
//
////processing CSV - open CSV, Apache CSV
//            try(final BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))){
//                br.lines().skip(1)
//                        .forEach(line -> logger.log(line+"\n"));
//            } catch (IOException e){
//                logger.log("Error occurred in Lambda: "+e.getMessage());
//                return false;
//            }
//        }
//        return true;
    }

