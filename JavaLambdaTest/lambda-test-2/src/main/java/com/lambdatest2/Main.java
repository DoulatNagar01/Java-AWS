package com.lambdatest2;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.s3.model.S3Event;

public class Main implements RequestHandler<S3Event, Boolean> {

    @Override
    public Boolean handleRequest(S3Event input, Context context) {
        System.out.println("calling from main method");
        return null;
    }
}
