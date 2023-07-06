package helloworld;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectSummary;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class S3BucketReader implements RequestHandler<Void, List<String>> {
    private final AmazonS3 s3Client = AmazonS3ClientBuilder.defaultClient();
    private static final String BUCKET_NAME = "doulat-dms-lambda-test";

    @Override
    public List<String> handleRequest(Void input, Context context) {
        List<String> fileContents = new ArrayList<>();

        List<S3ObjectSummary> objects = s3Client.listObjects(BUCKET_NAME).getObjectSummaries();
        for (S3ObjectSummary object : objects) {
            S3Object s3Object = s3Client.getObject(BUCKET_NAME, object.getKey());
            BufferedReader reader = new BufferedReader(new InputStreamReader(s3Object.getObjectContent()));
            String line;
            try {
                while ((line = reader.readLine()) != null) {
                    fileContents.add(line);
                }
                reader.close();
            } catch (IOException e) {
                // Handle exception
                e.printStackTrace();
            }
        }
        return fileContents;
    }
}

