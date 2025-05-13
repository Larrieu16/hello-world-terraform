package com.example;

import java.util.HashMap;
import java.util.Map;

public class ResponseUtils {
    public static Map<String, Object> buildResponse(int statusCode, Object body) {
        Map<String, Object> response = new HashMap<>();
        response.put("statusCode", statusCode);
        response.put("body", body);
        return response;
    }
}
