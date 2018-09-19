package io.constructor.client;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Constructor.io Http Interceptor
 */
public class ConstructorIOHttpInterceptor implements Interceptor {
     
  private String version;
  private String key;
  private String host;
  private String protocol;

  ConstructorIOHttpInterceptor(String version, String key, String host, String protocol) {
    this.version = version;
    this.key = key;
    this.host = host;
    this.protocol = protocol;
  }

  /**
   * Build a new request with common headers and parameters
   */
  public Response intercept(Interceptor.Chain chain) 
    throws IOException {

      Request originalRequest = chain.request();
      HttpUrl newUrl = originalRequest.url().newBuilder()
        .addQueryParameter("key", this.key)
        .addQueryParameter("version", this.version)
        .host(this.host)
        .scheme(this.protocol)
        .build();

      Request newRequest = originalRequest
        .newBuilder()
        .addHeader("Content-Type", "application/json")
        .url(newUrl)
        .build();

      return chain.proceed(newRequest);
  }
}
