package com.object;

import lombok.Getter;

import java.util.List;

@Getter
public class Rq {
    private String actionName;
    private List<String> params;

    public Rq(String cmd) {
        String[] cmdBits = cmd.split(" ", 2);
        actionName = cmdBits[0].trim();
        String queryString = cmdBits.length > 1 ? cmdBits[1].trim() : "";

        if (!queryString.isEmpty()) {
            params = List.of(queryString.split(" "));
        } else {
            params = List.of();
        }
    }

    public String getParam(int index, String defaultValue) {
        try {
            return params.get(index);
        } catch (IndexOutOfBoundsException e) {
            return defaultValue;
        }
    }

    public int getParamAsInt(int index, int defaultValue) {
        try {
            return Integer.parseInt(getParam(index, String.valueOf(defaultValue)));
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }
}
