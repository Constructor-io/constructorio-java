package io.constructor.client.models;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/** Base class that provides access to HTTP response headers. */
public abstract class ResponseHeaders {

    private transient Map<String, List<String>> headers =
            Collections.<String, List<String>>emptyMap();

    /**
     * @return the HTTP response headers
     */
    public Map<String, List<String>> getHeaders() {
        return headers;
    }

    /**
     * @param headers the HTTP response headers to set. If null, defaults to an empty map.
     */
    public void setHeaders(Map<String, List<String>> headers) {
        this.headers = (headers != null) ? headers : Collections.<String, List<String>>emptyMap();
    }
}
