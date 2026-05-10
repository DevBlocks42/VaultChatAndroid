package com.devblocks42.vaultchat;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

public class Utils {

    public static String parseError(String json) {

        try {

            Gson gson = new Gson();
            Type type = new TypeToken<Map<String, List<String>>>() {}.getType();
            Map<String, List<String>> errors = gson.fromJson(json, type);
            StringBuilder sb = new StringBuilder();
            for (Map.Entry<String, List<String>> entry : errors.entrySet()) {
                String field = entry.getKey();
                List<String> messages = entry.getValue();
                for (String msg : messages) {
                    sb.append(msg).append("\n");
                }
            }
            return sb.toString();

        } catch (Exception e) {
            return json;
        }
    }
}
