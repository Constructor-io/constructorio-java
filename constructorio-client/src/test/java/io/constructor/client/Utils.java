package io.constructor.client;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.logging.HttpLoggingInterceptor.Level;

/** Static functions to help with testing */
public class Utils {

    private static MediaType bodyType = MediaType.parse("application/json");

    /**
     * @return a ConstructorItem
     */
    public static ConstructorItem createProductItem() {
        String name = "Product" + UUID.randomUUID().toString().replaceAll("[\\s\\-()]", "");
        String id = name;
        String url = "https://constructor.io/products/" + name;

        HashMap<String, List<Object>> facets = new HashMap<String, List<Object>>();
        facets.put("color", Arrays.<Object>asList("blue", "red", 123));

        HashMap<String, Object> metadata = new HashMap<String, Object>();
        metadata.put("brand", "abc");

        HashMap<String, String> complexMetadataField = new HashMap<String, String>();
        complexMetadataField.put("key1", "val1");
        complexMetadataField.put("key2", "val2");
        metadata.put("complexMetadataField", complexMetadataField);

        ConstructorItem item = new ConstructorItem(id, name);
        item.setUrl(url);
        item.setFacets(facets);
        item.setMetadata(metadata);
        return item;
    }

    /**
     * @return a ConstructorVariation
     */
    public static ConstructorVariation createProductVariation(String itemId) {
        String name = "Variation" + UUID.randomUUID().toString().replaceAll("[\\s\\-()]", "");
        String id = name;
        String url = "https://constructor.io/variations/" + name;

        HashMap<String, List<Object>> facets = new HashMap<String, List<Object>>();
        facets.put("color", Arrays.<Object>asList("blue", "red", 123));

        HashMap<String, Object> metadata = new HashMap<String, Object>();
        metadata.put("brand", "abc");

        HashMap<String, String> complexMetadataField = new HashMap<String, String>();
        complexMetadataField.put("key1", "val1");
        complexMetadataField.put("key2", "val2");
        metadata.put("complexMetadataField", complexMetadataField);

        ConstructorVariation variation = new ConstructorVariation(id, itemId, name);
        variation.setUrl(url);
        variation.setUrl(url);
        variation.setFacets(facets);
        variation.setMetadata(metadata);
        return variation;
    }

    /**
     * @param statusCode the http status code
     * @param bodyText   the body
     * @return an HTTP response
     */
    public static Response createResponse(int statusCode, String bodyText) {
        Request request = new Request.Builder().url("https://example.com").build();
        ResponseBody body = ResponseBody.create(bodyType, bodyText);
        Response response = new Response.Builder()
                .request(request)
                .protocol(Protocol.HTTP_1_1)
                .code(statusCode)
                .body(body)
                .message("")
                .build();

        return response;
    }

    /**
     * @param filename the name of the file under resources
     * @return JSON from a file as a string
     */
    public static String getTestResource(String filename) throws Exception {
        Path path = Paths.get("src/test/resources/" + filename);
        byte[] bytes = Files.readAllBytes(path);
        String string = new String(bytes, "UTF-8");
        return string;
    }

    /** Enable http logging for all requests */
    public static void enableHTTPLogging() {
        HttpLoggingInterceptor logger = new HttpLoggingInterceptor();
        logger.setLevel(Level.NONE);
        OkHttpClient client = ConstructorIO.getHttpClient();
        OkHttpClient newClient = client.newBuilder().addInterceptor(logger).build();
        ConstructorIO.setHttpClient(newClient);
    }

    /**
     * Creates a JSON string for an item.
     *
     * @param id    the item ID
     * @param value the item display value
     * @param url   the item URL
     * @return JSON string representation
     */
    private static String itemToJson(String id, String value, String url) {
        return String.format(
                "{\"id\":\"%s\",\"value\":\"%s\",\"data\":{\"url\":\"%s\"}}", id, value, url);
    }

    /**
     * Creates a JSON string for a variation.
     *
     * @param id     the variation ID
     * @param itemId the parent item ID
     * @param value  the variation display value
     * @param url    the variation URL
     * @return JSON string representation
     */
    private static String variationToJson(String id, String itemId, String value, String url) {
        return String.format(
                "{\"id\":\"%s\",\"item_id\":\"%s\",\"value\":\"%s\",\"data\":{\"url\":\"%s\"}}",
                id, itemId, value, url);
    }

    /**
     * Creates a JSON string for an item group.
     *
     * @param id       the group ID
     * @param value    the group display value
     * @param parentId the parent group ID
     * @return JSON string representation
     */
    private static String itemGroupToJson(String id, String value, String parentId) {
        return String.format(
                "{\"id\":\"%s\",\"value\":\"%s\",\"data\":{\"parent_id\":\"%s\"}}",
                id, value, parentId);
    }

    /**
     * Generates a unique ID for test data.
     *
     * @param prefix the prefix for the ID
     * @return a unique ID string
     */
    private static String generateId(String prefix) {
        return prefix + UUID.randomUUID().toString().substring(0, 8);
    }

    /**
     * Creates a temporary JSON file containing an array of items.
     *
     * @param count the number of items to generate
     * @return a temporary File with .json extension
     * @throws IOException if file creation fails
     */
    public static File createItemsJsonFile(int count) throws IOException {
        File file = File.createTempFile("items", ".json");
        file.deleteOnExit();

        StringBuilder sb = new StringBuilder();
        sb.append("[\n");
        for (int i = 0; i < count; i++) {
            String id = generateId("item");
            String value = "Product " + (i + 1);
            String url = "https://example.com/" + id;
            sb.append("  ").append(itemToJson(id, value, url));
            if (i < count - 1) {
                sb.append(",");
            }
            sb.append("\n");
        }
        sb.append("]");

        try (FileWriter writer = new FileWriter(file)) {
            writer.write(sb.toString());
        }
        return file;
    }

    /**
     * Creates a temporary JSONL file containing items (one per line).
     *
     * @param count the number of items to generate
     * @return a temporary File with .jsonl extension
     * @throws IOException if file creation fails
     */
    public static File createItemsJsonlFile(int count) throws IOException {
        File file = File.createTempFile("items", ".jsonl");
        file.deleteOnExit();

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < count; i++) {
            String id = generateId("item");
            String value = "Product " + (i + 1);
            String url = "https://example.com/" + id;
            sb.append(itemToJson(id, value, url)).append("\n");
        }

        try (FileWriter writer = new FileWriter(file)) {
            writer.write(sb.toString());
        }
        return file;
    }

    /**
     * Creates a temporary JSON file containing an array of variations.
     *
     * @param count the number of variations to generate
     * @return a temporary File with .json extension
     * @throws IOException if file creation fails
     */
    public static File createVariationsJsonFile(int count) throws IOException {
        File file = File.createTempFile("variations", ".json");
        file.deleteOnExit();

        StringBuilder sb = new StringBuilder();
        sb.append("[\n");
        for (int i = 0; i < count; i++) {
            String id = generateId("var");
            String itemId = "item" + ((i % 3) + 1); // Rotate through item1, item2, item3
            String value = "Variation " + (i + 1);
            String url = "https://example.com/" + id;
            sb.append("  ").append(variationToJson(id, itemId, value, url));
            if (i < count - 1) {
                sb.append(",");
            }
            sb.append("\n");
        }
        sb.append("]");

        try (FileWriter writer = new FileWriter(file)) {
            writer.write(sb.toString());
        }
        return file;
    }

    /**
     * Creates a temporary JSONL file containing variations (one per line).
     *
     * @param count the number of variations to generate
     * @return a temporary File with .jsonl extension
     * @throws IOException if file creation fails
     */
    public static File createVariationsJsonlFile(int count) throws IOException {
        File file = File.createTempFile("variations", ".jsonl");
        file.deleteOnExit();

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < count; i++) {
            String id = generateId("var");
            String itemId = "item" + ((i % 3) + 1); // Rotate through item1, item2, item3
            String value = "Variation " + (i + 1);
            String url = "https://example.com/" + id;
            sb.append(variationToJson(id, itemId, value, url)).append("\n");
        }

        try (FileWriter writer = new FileWriter(file)) {
            writer.write(sb.toString());
        }
        return file;
    }

    /**
     * Creates a temporary JSONL file containing item groups (one per line).
     *
     * @param count the number of item groups to generate
     * @return a temporary File with .jsonl extension
     * @throws IOException if file creation fails
     */
    public static File createItemGroupsJsonlFile(int count) throws IOException {
        File file = File.createTempFile("item_groups", ".jsonl");
        file.deleteOnExit();

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < count; i++) {
            String id = generateId("group");
            String value = "Group " + (i + 1);
            String parentId = "root";
            sb.append(itemGroupToJson(id, value, parentId)).append("\n");
        }

        try (FileWriter writer = new FileWriter(file)) {
            writer.write(sb.toString());
        }
        return file;
    }

    /**
     * Creates a temporary file with an invalid .txt extension for testing
     * validation.
     *
     * @return a temporary File with .txt extension
     * @throws IOException if file creation fails
     */
    public static File createInvalidExtensionFile() throws IOException {
        File file = File.createTempFile("items", ".txt");
        file.deleteOnExit();

        try (FileWriter writer = new FileWriter(file)) {
            writer.write("This is a text file with invalid extension for catalog upload testing.");
        }
        return file;
    }

    /**
     * Creates a temporary file with no extension for testing validation.
     *
     * @return a temporary File with no extension
     * @throws IOException if file creation fails
     */
    public static File createNoExtensionFile() throws IOException {
        File tempFile = File.createTempFile("items", ".tmp");
        File noExtFile = new File(tempFile.getParent(), "items_" + UUID.randomUUID().toString().substring(0, 8));
        tempFile.renameTo(noExtFile);
        noExtFile.deleteOnExit();

        try (FileWriter writer = new FileWriter(noExtFile)) {
            writer.write("This file has no extension for testing validation.");
        }
        return noExtFile;
    }
}
