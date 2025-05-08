package com.example;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.*;

import java.util.HashMap;
import java.util.Map;

public class DeletarItemHandler implements RequestHandler<Map<String, Object>, Map<String, Object>> {

    private static final String TABLE_NAME = "ListaDeMercado";

    @Override
    public Map<String, Object> handleRequest(Map<String, Object> input, Context context) {
        String date = (String) input.get("date");
        String itemId = (String) input.get("itemId");

        if (date == null || itemId == null) {
            return response(400, "Parâmetros obrigatórios: date e itemId");
        }

        String pk = "LIST#" + date.replace("-", "");
        String sk = "ITEM#" + itemId;

        DynamoDbClient ddb = DynamoDbClient.create();

        Map<String, AttributeValue> key = new HashMap<>();
        key.put("PK", AttributeValue.fromS(pk));
        key.put("SK", AttributeValue.fromS(sk));

        try {
            ddb.deleteItem(DeleteItemRequest.builder()
                    .tableName(TABLE_NAME)
                    .key(key)
                    .build());

            return response(200, "Item removido com sucesso (ou já não existia).");

        } catch (DynamoDbException e) {
            return response(500, "Erro ao remover item: " + e.getMessage());
        }
    }

    private Map<String, Object> response(int statusCode, Object body) {
        Map<String, Object> response = new HashMap<>();
        response.put("statusCode", statusCode);
        response.put("body", body);
        return response;
    }
}
