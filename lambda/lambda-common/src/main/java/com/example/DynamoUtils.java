package com.example;

import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;
import software.amazon.awssdk.services.dynamodb.model.GetItemRequest;
import software.amazon.awssdk.services.dynamodb.model.DeleteItemRequest;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.regions.Region;

import java.util.Map;

public class DynamoUtils {
    private static final Region REGION = Region.SA_EAST_1;
    private static DynamoDbClient client;

    public static DynamoDbClient getClient() {
        if (client == null) {
            client = DynamoDbClient.builder()
                    .region(REGION)
                    .build();
        }
        return client;
    }

    public static PutItemRequest buildPutItemRequest(String tableName, Map<String, AttributeValue> item) {
        return PutItemRequest.builder()
                .tableName(tableName)
                .item(item)
                .build();
    }

    public static GetItemRequest buildGetItemRequest(String tableName, Map<String, AttributeValue> key) {
        return GetItemRequest.builder()
                .tableName(tableName)
                .key(key)
                .build();
    }

    public static DeleteItemRequest buildDeleteItemRequest(String tableName, Map<String, AttributeValue> key) {
        return DeleteItemRequest.builder()
                .tableName(tableName)
                .key(key)
                .build();
    }
}





