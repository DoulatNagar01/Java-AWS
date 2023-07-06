package RemoveAddInS3Bucket;

        import com.amazonaws.services.lambda.runtime.Context;
        import com.amazonaws.services.lambda.runtime.LambdaLogger;
        import com.amazonaws.services.lambda.runtime.RequestHandler;
        import com.amazonaws.services.lambda.runtime.events.S3Event;
        import com.amazonaws.services.lambda.runtime.events.models.s3.S3EventNotification;
        import com.amazonaws.services.s3.AmazonS3;
        import com.amazonaws.services.s3.AmazonS3ClientBuilder;
        import com.amazonaws.services.s3.model.CopyObjectRequest;
        import com.amazonaws.services.s3.model.CopyObjectResult;
        import com.amazonaws.services.s3.model.S3Object;
        import com.amazonaws.services.s3.model.S3ObjectInputStream;

public class TriggedWithLambda implements RequestHandler<S3Event,String> {
    @Override
    public String handleRequest(S3Event input, Context context) {
        LambdaLogger logger = context.getLogger();
        AmazonS3 client = AmazonS3ClientBuilder.defaultClient();
        for(S3EventNotification.S3EventNotificationRecord s3Entity:input.getRecords()){
            String sourceBucketName = "doulat-dms-lambda-test";
            String sourceObjectKey = s3Entity.getS3().getObject().getKey();
            logger.log(sourceObjectKey);
            String destinationBucketName = "doulat-dms-lambda-zip";
            S3Object s3Object = client.getObject(sourceBucketName,sourceObjectKey);
            S3ObjectInputStream s3ObjectInputStream = s3Object.getObjectContent();
            CopyObjectRequest copyObjectRequest = new CopyObjectRequest(sourceBucketName,sourceObjectKey,destinationBucketName, sourceObjectKey);
            CopyObjectResult copyObjectResult = client.copyObject(copyObjectRequest);
            System.out.println("Copy object response: " + copyObjectResult);
        }
        return "success";
    }
}