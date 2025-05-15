package com.example;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AdicionarItemHandler implements RequestHandler<Map<String, Object>, Map<String, Object>> {

    private static final String TABLE_NAME = "ListaDeMercado";

    @Override
    public Map<String, Object> handleRequest(Map<String, Object> input, Context context) {
        String name = (String) input.get("name");
        String date = (String) input.get("date");

        if (name == null || date == null) {
            return ResponseUtils.buildResponse(400, "Parâmetros obrigatórios: name e date");
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

        DynamoUtils.getClient().putItem(DynamoUtils.buildPutItemRequest(TABLE_NAME, item));

        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("PK", pk);
        responseBody.put("SK", sk);
        responseBody.put("name", name);
        responseBody.put("status", "todo");
        responseBody.put("createdAt", createdAt);

        return ResponseUtils.buildResponse(201, responseBody);
    }
}



