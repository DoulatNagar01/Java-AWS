package helloworld;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class lambdaFunction implements RequestHandler<String,String> {
    @Override
    public String handleRequest(String input, Context context) {
        System.out.println("Testing for deploy in aws lambda");
        return "Success";
    }
}
