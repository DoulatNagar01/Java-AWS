package ApiGateway;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
        import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
        import com.amazonaws.services.dynamodbv2.document.DynamoDB;
        import com.amazonaws.services.dynamodbv2.document.Item;
        import com.amazonaws.services.dynamodbv2.document.Table;
        import com.amazonaws.services.dynamodbv2.document.spec.PutItemSpec;
        import com.amazonaws.services.lambda.runtime.Context;
        import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
        import org.json.simple.JSONObject;
        import org.json.simple.parser.JSONParser;

        import java.io.*;
        import java.util.HashMap;

public class ApiTest implements RequestStreamHandler {
    private final String DYNAMO_TABLE = "DoulatTableTest01";
    @Override
    public void handleRequest(InputStream input, OutputStream output, Context context) throws IOException {
        OutputStreamWriter writer = new OutputStreamWriter(output);
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        JSONParser parser = new JSONParser();
        JSONObject responseObject = new JSONObject();
        JSONObject responseBody = new JSONObject();

        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.defaultClient();
        DynamoDB dynamoDB = new DynamoDB(client);

        int id;
        Item resItem = null;
        context.getLogger().log("Complied the code");

        try {
            JSONObject reqObject = (JSONObject) parser.parse(reader);
            // path parameters
            if(reqObject.get("pathParameters")!=null){
                JSONObject pps = (JSONObject)reqObject.get("pathParameters");
                if(pps.get("id")!=null){
                    id = Integer.parseInt((String)pps.get("id"));
                    resItem = dynamoDB.getTable(DYNAMO_TABLE).getItem("id",id);
                }
            }

//queryStringParameters
            else if(reqObject.get("queryStringParameters")!=null){
                JSONObject qsp = (JSONObject)reqObject.get("queryStringParameters");
                if(qsp.get("id")!=null){
                    id = Integer.parseInt((String)qsp.get("id"));
                    resItem = dynamoDB.getTable(DYNAMO_TABLE).getItem("id",id);
                }
            }
            if(resItem!=null){
                Product product = new Product(resItem.toJSON());
                responseBody.put("product",product);
                responseObject.put("statusCode",200);
            }else{
                responseBody.put("message","No Items Found");
                responseObject.put("statusCode",404);
            }
            responseObject.put("body",responseBody.toString());

        } catch (Exception e){
            context.getLogger().log("ERROR : "+e.getMessage());
        }
        writer.write(responseObject.toString());
        reader.close();
        writer.close();
    }

    public void handlePutRequest(InputStream input,OutputStream output, Context context) throws IOException{
        OutputStreamWriter writer = new OutputStreamWriter(output);
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        JSONParser parser = new JSONParser();
        JSONObject responseObject = new JSONObject();
        JSONObject responseBody = new JSONObject();

        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.defaultClient();
        DynamoDB dynamoDB = new DynamoDB(client);

        try {
            JSONObject reqObject = (JSONObject) parser.parse(reader);
            if(reqObject.get("body")!=null){
                Product product = new Product((String) reqObject.get("body"));
                dynamoDB.getTable(DYNAMO_TABLE).putItem(new PutItemSpec().withItem(new Item()
                        .withNumber("id",product.getId())
                        .withString("name",product.getName())
                        .withNumber("price",product.getPrice())));
                responseBody.put("message","new Item created/updated");
                responseObject.put("statusCode",200);
                responseObject.put("body",responseBody.toString());
            }
        } catch (Exception e){
            responseObject.put("statusCode",400);
            responseObject.put("error",e.getMessage());
        }
        writer.write(responseObject.toString());
        reader.close();
        writer.close();
    }
}
