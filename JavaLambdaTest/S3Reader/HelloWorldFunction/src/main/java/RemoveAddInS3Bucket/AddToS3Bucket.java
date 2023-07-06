package RemoveAddInS3Bucket;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CopyObjectRequest;
import com.amazonaws.services.s3.model.CopyObjectResult;
import com.amazonaws.services.s3.model.S3Event;

public class AddToS3Bucket implements RequestHandler<S3Event,Object> {
    private static final AmazonS3 s3Client = AmazonS3ClientBuilder.defaultClient();
    String sourceBucketName = "doulat-dms-lambda-zip";
    String objectKey ="HelloWorldFunction.jar";
    String destinationBucketName = "doulat-dms-lambda-test";
    String destinationKey = "CopyItem/HelloWorldFunction.jar";
    @Override
    public Object handleRequest(S3Event input, Context context) {
        try {
            boolean objectExit = s3Client.doesObjectExist(sourceBucketName, objectKey);
            if (!objectExit){
                return "Object is not present in the S3 Bucket";
            }
            CopyObjectRequest copyObjectRequest = new CopyObjectRequest(sourceBucketName, objectKey, destinationBucketName, destinationKey);
            CopyObjectResult result = s3Client.copyObject(copyObjectRequest);
            return "Obejct is copy to destination"+"\n"+ result.getETag();
        }
        catch (Exception e){
            return System.err;
        }
    }
}
