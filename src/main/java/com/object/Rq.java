package com.object;

import lombok.Getter;

import java.util.Map;

@Getter
public class Rq {
    private String actionName;
    private Map<String, String> params;

    public Rq(String cmd) {
        String[] cmdBits = cmd.split("\\?", 2);
        actionName = cmdBits[0].trim();
        String queryString = cmdBits.length > 1 ? cmdBits[1].trim() : "";

        params = Map.of();
        if (!queryString.isEmpty()) {
            String[] queryStringBits = queryString.split("&");
            for (String bit : queryStringBits) {
                String[] keyValue = bit.split("=", 2);
                if (keyValue.length == 2) {
                    params.put(keyValue[0].trim(), keyValue[1].trim());
                }
            }
        }
    }
}
