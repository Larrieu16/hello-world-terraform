package com.example;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.*;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
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
            return response(400, "Parâmetros obrigatórios: date e itemId");
        }

        String pk = "LIST#" + date.replace("-", "");
        String sk = "ITEM#" + itemId;

        DynamoDbClient ddb = DynamoDbClient.create();

        // Verifica se o item existe
        Map<String, AttributeValue> key = new HashMap<>();
        key.put("PK", AttributeValue.fromS(pk));
        key.put("SK", AttributeValue.fromS(sk));

        GetItemResponse getItemResponse = ddb.getItem(GetItemRequest.builder()
                .tableName(TABLE_NAME)
                .key(key)
                .build());

        if (!getItemResponse.hasItem()) {
            return response(404, "Item não encontrado");
        }

        // Prepara a expressão de atualização
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
                return response(400, "Status inválido. Use 'todo' ou 'done'.");
            }
            updateExpr.append("#s = :s, ");
            expressionValues.put(":s", AttributeValue.fromS(status));
            expressionNames.put("#s", "status");
            hasUpdates = true;
        }

        if (!hasUpdates) {
            return response(400, "Nenhum dado fornecido para atualização.");
        }

        // Remove a vírgula final
        String finalUpdateExpr = updateExpr.substring(0, updateExpr.length() - 2);

        // Executa a atualização
        UpdateItemRequest.Builder updateRequestBuilder = UpdateItemRequest.builder()
                .tableName(TABLE_NAME)
                .key(key)
                .updateExpression(finalUpdateExpr)
                .expressionAttributeValues(expressionValues)
                .expressionAttributeNames(expressionNames)
                .returnValues(ReturnValue.ALL_NEW);

        UpdateItemResponse updateResponse = ddb.updateItem(updateRequestBuilder.build());

        // Monta resposta com o item atualizado
        Map<String, AttributeValue> updatedItem = updateResponse.attributes();
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("PK", updatedItem.get("PK").s());
        responseBody.put("SK", updatedItem.get("SK").s());
        responseBody.put("name", updatedItem.get("name").s());
        responseBody.put("status", updatedItem.get("status").s());
        responseBody.put("createdAt", updatedItem.get("createdAt").s());

        return response(200, responseBody);
    }

    private Map<String, Object> response(int statusCode, Object body) {
        Map<String, Object> response = new HashMap<>();
        response.put("statusCode", statusCode);
        response.put("body", body);
        return response;
    }
}

