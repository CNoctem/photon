package com.konishy.photon.meta;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeType;

import java.io.IOException;

public class Json {

    JsonNode rootNode;

    public Json(String json) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        rootNode = mapper.readTree(json);
    }

    public JsonNode get(String... path) {
        //TODO only works for depth 2
        JsonNode root = rootNode.get(path[0]);
        return getSub(root, path[1]);
    }

    private JsonNode getSub(JsonNode root, String name) {
        for (JsonNode node : root) {
            if (node != null) {
                JsonNode sub = node.get(name);
                if (sub !=  null) {
                    return sub;
                }
            }
        }
        return null;
    }

}

