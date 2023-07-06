package helloworld;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class helloworld implements RequestHandler<String,String> {
    @Override
    public String handleRequest(String input, Context context) {
        System.out.println("Deploy Successfully");
        return "Success";
    }
}
