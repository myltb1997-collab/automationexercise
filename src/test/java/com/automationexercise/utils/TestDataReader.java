package com.automationexercise.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.MissingNode;

import java.io.IOException;
import java.io.InputStream;

public final class TestDataReader {
    private static final String RESOURCE_PATH = "testdata/test-data.json";
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final JsonNode ROOT = loadRoot();

    private TestDataReader() {
    }

    public static String getString(String path) {
        return requireNode(path).asText();
    }

    public static int getInt(String path) {
        JsonNode node = requireNode(path);
        if (node.canConvertToInt()) {
            return node.asInt();
        }
        return Integer.parseInt(node.asText());
    }

    public static boolean getBoolean(String path) {
        return requireNode(path).asBoolean();
    }

    public static JsonNode getNode(String path) {
        return requireNode(path);
    }

    private static JsonNode requireNode(String path) {
        JsonNode node = resolveNode(path);
        if (node.isMissingNode() || node.isNull()) {
            throw new IllegalArgumentException("Missing test data path: " + path);
        }
        return node;
    }

    private static JsonNode resolveNode(String path) {
        JsonNode current = ROOT;
        if (path == null || path.isBlank()) {
            return MissingNode.getInstance();
        }

        String[] parts = path.split("\\.");
        for (String part : parts) {
            if (current == null || current.isMissingNode() || current.isNull()) {
                return MissingNode.getInstance();
            }
            current = current.path(part);
        }
        return current == null ? MissingNode.getInstance() : current;
    }

    private static JsonNode loadRoot() {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        try (InputStream input = classLoader.getResourceAsStream(RESOURCE_PATH)) {
            if (input == null) {
                throw new IllegalStateException("Test data file not found: " + RESOURCE_PATH);
            }
            return MAPPER.readTree(input);
        } catch (IOException e) {
            throw new IllegalStateException("Failed to load test data from: " + RESOURCE_PATH, e);
        }
    }
}

