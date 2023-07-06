package ApiGateway;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import java.util.Map;

public class CallingApi implements RequestHandler<Map<String, Object>, String> {
    public String handleRequest(Map<String, Object> input, Context context) {
        // extract the payload from the input
        String payload = (String) input.get("body");
        // process the payload and return a response
        return "Hello " + payload + " from Lambda!";
    }
}
