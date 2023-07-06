package helloworld;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import java.util.HashMap;
import java.util.Map;

public class CurdDynamodb implements RequestHandler<Object, String>{

        @Override
        public String handleRequest(Object input, Context context) {
                AmazonDynamoDB amazonClient = AmazonDynamoDBClientBuilder.standard().build();
                String tableName  = "DoulatTableTest";
                DynamoDB dynamoDB = new DynamoDB(amazonClient);
                ScanRequest request = new ScanRequest(tableName);
//                Table table = new Table(amazonClient, tableName);
                //For Scan A Perticular row ---------
//                Table table = new Table(amazonClient, tableName);
//                GetItemSpec spec = new GetItemSpec().withPrimaryKey("id",7,"first_name","Clerc");
//                Item item = table.getItem(spec);
//                String name = item.getString("first_name");
//                String result = "Name"+name;
//                System.out.println(result);
                // Scan a table --------
//                ScanRequest scanRequest = new ScanRequest(tableName);
//                ScanResult scanResult = amazonClient.scan(scanRequest);
//                for (Map<String, AttributeValue> item : scanResult.getItems()){
//                        System.out.println(item);
//                }
                // filter scan --------
//                Map<String,AttributeValue> expressionAttributeValue = new HashMap<String,AttributeValue>();
//                // assign
//                expressionAttributeValue.put(":val",new AttributeValue().withN("5"));
//                ScanRequest request = new ScanRequest()
//                        .withTableName(tableName)
//                        .withFilterExpression("id = :val") // compare with the id
//                        .withProjectionExpression("email") // what we needed
//                        .withExpressionAttributeValues(expressionAttributeValue);
//                ScanResult result = amazonClient.scan(request);
//                for (Map<String, AttributeValue> item : result.getItems()) {
//                        System.out.println(item); // output
//                }
//                scanTable(amazonClient,request);
                FilterTable(amazonClient,request);
//               DeleteTable(tableName,dynamoDB);
                return "Success";
        }
//        private ScanResult scanTable(AmazonDynamoDB amazonClient, ScanRequest request){
//                System.out.println(amazonClient.scan(request));
//                return amazonClient.scan(request);
//        }
        private void FilterTable(AmazonDynamoDB amazonClient, ScanRequest request){
                try{
                        Map<String,AttributeValue> expressionAttributeValue = new HashMap<String,AttributeValue>();
                        expressionAttributeValue.put(":val",new AttributeValue().withN("5"));
                        request.withFilterExpression("id = :val").withProjectionExpression("email")
                                .withExpressionAttributeValues(expressionAttributeValue);
                        System.out.println(amazonClient.scan(request));
                } catch (Exception e){
                        System.out.println("Failed to Filter the table");
                        e.printStackTrace(System.err);
                }
        }
//        private static void DeleteTable(String tableName, DynamoDB dynamoDB) {
//                try {
//                        Table table = dynamoDB.getTable(tableName);
//                        table.delete();
//                        System.out.println("Waiting for " + tableName + " to be deleted...this may take a while...");
//                        table.waitForDelete();
//                }
//                catch (Exception e) {
//                        System.err.println("Failed to delete table " + tableName);
//                        e.printStackTrace(System.err);
//                }
//        }

}
