package com.example;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.*;
import software.amazon.awssdk.services.dynamodb.model.UpdateItemRequest;
import software.amazon.awssdk.services.dynamodb.model.UpdateItemResponse;

import java.util.HashMap;
import java.util.Map;

public class AtualizarItemHandler implements RequestHandler<Map<String, Object>, Map<String, Object>> {

    private static final String TABLE_NAME = "ListaDeMercado";

    @Override
    public Map<String, Object> handleRequest(Map<String, Object> input, Context context) {
        String date = (String) input.get("date");
        String itemId = (String) input.get("itemId");
        String name = (String) input.get("name");
        String status = (String) input.get("status");

        if (date == null || itemId == null) {
            return ResponseUtils.buildResponse(400, "Parâmetros obrigatórios: date e itemId");
        }

        String pk = "LIST#" + date.replace("-", "");
        String sk = "ITEM#" + itemId;

        Map<String, AttributeValue> key = Map.of(
                "PK", AttributeValue.fromS(pk),
                "SK", AttributeValue.fromS(sk)
        );

        DynamoDbClient ddb = DynamoUtils.getClient();
        GetItemResponse getItemResponse = ddb.getItem(DynamoUtils.buildGetItemRequest(TABLE_NAME, key));

        if (!getItemResponse.hasItem()) {
            return ResponseUtils.buildResponse(404, "Item não encontrado");
        }

        Map<String, AttributeValue> expressionValues = new HashMap<>();
        Map<String, String> expressionNames = new HashMap<>();
        StringBuilder updateExpr = new StringBuilder("SET ");
        boolean hasUpdates = false;

        if (name != null) {
            updateExpr.append("#n = :n, ");
            expressionValues.put(":n", AttributeValue.fromS(name));
            expressionNames.put("#n", "name");
            hasUpdates = true;
        }

        if (status != null) {
            if (!status.equalsIgnoreCase("todo") && !status.equalsIgnoreCase("done")) {
                return ResponseUtils.buildResponse(400, "Status inválido. Use 'todo' ou 'done'.");
            }
            updateExpr.append("#s = :s, ");
            expressionValues.put(":s", AttributeValue.fromS(status));
            expressionNames.put("#s", "status");
            hasUpdates = true;
        }

        if (!hasUpdates) {
            return ResponseUtils.buildResponse(400, "Nenhum dado fornecido para atualização.");
        }

        String finalUpdateExpr = updateExpr.substring(0, updateExpr.length() - 2);

        UpdateItemRequest updateRequest = UpdateItemRequest.builder()
                .tableName(TABLE_NAME)
                .key(key)
                .updateExpression(finalUpdateExpr)
                .expressionAttributeValues(expressionValues)
                .expressionAttributeNames(expressionNames)
                .returnValues(ReturnValue.ALL_NEW)
                .build();

        UpdateItemResponse updateResponse = ddb.updateItem(updateRequest);

        Map<String, AttributeValue> updatedItem = updateResponse.attributes();
        Map<String, Object> responseBody = Map.of(
                "PK", updatedItem.get("PK").s(),
                "SK", updatedItem.get("SK").s(),
                "name", updatedItem.get("name").s(),
                "status", updatedItem.get("status").s(),
                "createdAt", updatedItem.get("createdAt").s()
        );

        return ResponseUtils.buildResponse(200, responseBody);
    }
}

