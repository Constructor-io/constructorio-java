package io.constructor.client;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Static functions to help with testing
 */
public class Utils {

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
      HttpUrl url = new HttpUrl.Builder()
          .scheme("https")
          .host("example.com")
          .build();

      Request request = new Request.Builder()
          .url(url)
          .build();

      ResponseBody body = ResponseBody.create(MediaType.get("application/json"), bodyText);
      
      Response response = new Response.Builder()
          .request(request)
          .protocol(Protocol.HTTP_1_1)
          .code(statusCode)
          .body(body)
          .build();

      return response;
    }

    /**
     * @param filename the name of the file under resources
     * @return JSON from a file as a string
     */
    public static String getResource(String filename) throws Exception {
        Path path = Paths.get("src/test/resources/" + filename);
        byte[] bytes = Files.readAllBytes(path);
        String string = new String(bytes, "UTF-8");
        return string;
    }
}