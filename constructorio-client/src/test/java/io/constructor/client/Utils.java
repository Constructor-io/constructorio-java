package io.constructor.client;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.logging.HttpLoggingInterceptor.Level;

/**
 * Static functions to help with testing
 */
public class Utils {

    private static MediaType bodyType = MediaType.parse("application/json");

    /**
     * @return a ConstructorItem
     */
    public static ConstructorItem createProductItem() {
        String name = "Product" + UUID.randomUUID().toString().replaceAll("[\\s\\-()]", "");
        String url = "https://constructor.io/products/" + name;
        ConstructorItem item = new ConstructorItem(name);
        item.setUrl(url);
        return item;
    }

    /**
     * @param statusCode the http status code
     * @param bodyText the body
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

    /**
     * Enable http logging for all requests
     */
    public static void enableHTTPLogging() {
        HttpLoggingInterceptor logger = new HttpLoggingInterceptor();
        logger.setLevel(Level.NONE);
        OkHttpClient client = ConstructorIO.getClient();
        OkHttpClient newClient = client.newBuilder().addInterceptor(logger).build();
        ConstructorIO.setClient(newClient);
    }
}