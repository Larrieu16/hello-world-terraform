package com.example;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import software.amazon.awssdk.services.dynamodb.model.*;

import java.util.Map;

public class DeletarItemHandler implements RequestHandler<Map<String, Object>, Map<String, Object>> {

    private static final String TABLE_NAME = "ListaDeMercado";

    @Override
    public Map<String, Object> handleRequest(Map<String, Object> input, Context context) {
        String date = (String) input.get("date");
        String itemId = (String) input.get("itemId");

        if (date == null || itemId == null) {
            return ResponseUtils.buildResponse(400, "Parâmetros obrigatórios: date e itemId");
        }

        String pk = "LIST#" + date.replace("-", "");
        String sk = "ITEM#" + itemId;

        Map<String, AttributeValue> key = Map.of(
                "PK", AttributeValue.fromS(pk),
                "SK", AttributeValue.fromS(sk)
        );

        try {
            DynamoUtils.getClient().deleteItem(DynamoUtils.buildDeleteItemRequest(TABLE_NAME, key));
            return ResponseUtils.buildResponse(200, "Item removido com sucesso (ou já não existia).");

        } catch (DynamoDbException e) {
            return ResponseUtils.buildResponse(500, "Erro ao remover item: " + e.getMessage());
        }
    }
}


