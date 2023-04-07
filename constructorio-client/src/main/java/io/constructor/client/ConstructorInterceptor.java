package io.constructor.client;

import java.io.IOException;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/** Constructor.io Http Interceptor */
public class ConstructorInterceptor implements Interceptor {

    /** Build a new request with common headers and parameters */
    public Response intercept(Interceptor.Chain chain) throws IOException {
        Request originalRequest = chain.request();
        Request newRequest =
                originalRequest.newBuilder().addHeader("Content-Type", "application/json").build();

        return chain.proceed(newRequest);
    }
}
