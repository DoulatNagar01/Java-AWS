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

public class FileReadInS3 implements RequestHandler<Void, List<String>> {
    private final AmazonS3 s3Client = AmazonS3ClientBuilder.defaultClient();
    private static final String bucketName = "lambda-s3-test-1";

    @Override
    public List<String> handleRequest(Void input, Context context) {
        List<String> fileContants = new ArrayList<>();
        List<S3ObjectSummary> objects = s3Client.listObjects("lambda-s3-test-1")
                .getObjectSummaries();
        for (S3ObjectSummary object: objects) {
            S3Object s3Object = s3Client.getObject("lambda-s3-test-1",object.getKey());
            BufferedReader reader = new BufferedReader(new InputStreamReader(s3Object.getObjectContent()));
            String line;
            try {
                while((line = reader.readLine()) != null){
                    fileContants.add(line);
                }
                reader.close();
            } catch (IOException e){
                e.printStackTrace();
            }
        }
        return fileContants;
    }
}
