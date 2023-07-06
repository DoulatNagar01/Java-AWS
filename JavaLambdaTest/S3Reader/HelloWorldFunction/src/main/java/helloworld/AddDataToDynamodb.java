package helloworld;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class AddDataToDynamodb implements RequestHandler<String,String> {
    AmazonDynamoDB client = AmazonDynamoDBClientBuilder.defaultClient();
    DynamoDB dynamoDB = new DynamoDB(client);

    @Override
    public String handleRequest(String input, Context context) {
        String tableName  = "doulatDmstest";
        Table table = dynamoDB.getTable(tableName);
        Item item = new Item().withPrimaryKey("keyId","akashay").withString("accessid","mewara");
        table.putItem(item);
        System.out.println("item:" + item);
        return tableName;
    }
}
