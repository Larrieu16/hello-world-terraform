package com.example;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.regions.Region;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class FuncaoDoisHandler implements RequestHandler<Map<String, Object>, Map<String, Object>> {

    private static final String TABLE_NAME = "ListaDeMercado";

    @Override
    public Map<String, Object> handleRequest(Map<String, Object> input, Context context) {
        String name = (String) input.get("name");
        String date = (String) input.get("date");

        if (name == null || date == null) {
            return response(400, "Parâmetros obrigatórios: name e date");
        }

        String pk = "LIST#" + date.replace("-", "");
        String itemId = UUID.randomUUID().toString();
        String sk = "ITEM#" + itemId;
        String createdAt = Instant.now().toString();

        Map<String, AttributeValue> item = new HashMap<>();
        item.put("PK", AttributeValue.fromS(pk));
        item.put("SK", AttributeValue.fromS(sk));
        item.put("name", AttributeValue.fromS(name));
        item.put("status", AttributeValue.fromS("todo"));
        item.put("createdAt", AttributeValue.fromS(createdAt));


        DynamoDbClient ddb = DynamoDbClient.builder()
                .region(Region.SA_EAST_1)
                .build();


        ddb.putItem(PutItemRequest.builder()
                .tableName(TABLE_NAME)
                .item(item)
                .build());

        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("PK", pk);
        responseBody.put("SK", sk);
        responseBody.put("name", name);
        responseBody.put("status", "todo");
        responseBody.put("createdAt", createdAt);

        return response(201, responseBody);
    }

    private Map<String, Object> response(int statusCode, Object body) {
        Map<String, Object> response = new HashMap<>();
        response.put("statusCode", statusCode);
        response.put("body", body);
        return response;
    }
}


