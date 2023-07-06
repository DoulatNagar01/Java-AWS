package helloworld;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.S3Event;

public class mainLambda implements RequestHandler<S3Event,Boolean> {

    @Override
    public Boolean handleRequest(S3Event input, Context context) {
        LambdaLogger logger = context.getLogger();
        logger.log("Hello doulat"+input);
        System.out.println("Working in the aws serverless");
        return true;
    }
}
