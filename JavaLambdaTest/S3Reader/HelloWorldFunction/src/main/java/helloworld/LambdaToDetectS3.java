package helloworld;


import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectSummary;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

// Whenever you insert the file into the s3Bucket program will detect
// and show the BucketName , FileName (Which you insert into the S3Bucket)
// and the Filedata (the data which is present in the file).
public class LambdaToDetectS3 implements RequestHandler<S3Event, String> {
    private final AmazonS3 s3Clint = AmazonS3ClientBuilder.defaultClient();

    @Override
    public String handleRequest(S3Event s3event, Context context) {
        List<String> fileContents = new ArrayList<>();
        try {
            for (com.amazonaws.services.lambda.runtime.events.models.s3.S3EventNotification.S3EventNotificationRecord record
                    : s3event.getRecords()) {
                com.amazonaws.services.lambda.runtime.events.models.s3.S3EventNotification.S3Entity entity = record.getS3();
                String bucketName = entity.getBucket().getName();
                String objectKey = entity.getObject().getKey();
                // Process the bucket name and object key as needed
                System.out.println("Bucket Name: " + bucketName);
                System.out.println("Object Key: " + objectKey);
                List<S3ObjectSummary> objects = s3Clint.listObjects(bucketName).getObjectSummaries();
                for (S3ObjectSummary object: objects) {
                    S3Object s3Object= s3Clint.getObject(bucketName, object.getKey());
                    BufferedReader reader = new BufferedReader(new InputStreamReader(s3Object.getObjectContent()));
                    String line;
                    try {
                        while ((line = reader.readLine()) != null){
                            fileContents.add(line);
                        }
                        reader.close();
                    } catch (IOException e){
                        e.printStackTrace();
                    }
                }
                System.out.println("fileContents : " + fileContents );
            }
        } catch (Exception e) {
            // Handle any exceptions appropriately
            e.printStackTrace();
        }


        return "Success";
    }
}
