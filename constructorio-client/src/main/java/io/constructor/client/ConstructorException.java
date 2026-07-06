package io.constructor.client;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class ConstructorException extends Exception {
    private Integer errorCode;
    private Map<String, List<String>> headers = Collections.<String, List<String>>emptyMap();

    public ConstructorException(String msg) {
        super(msg);
    }

    public ConstructorException(Exception e) {
        super(e);
        if (e instanceof ConstructorException) {
            this.errorCode = ((ConstructorException) e).getErrorCode();
            this.headers = ((ConstructorException) e).getHeaders();
        }
    }

    public ConstructorException(String msg, Integer code) {
        super(msg);
        this.errorCode = code;
    }

    public Integer getErrorCode() {
        return this.errorCode;
    }

    /**
     * @return the HTTP response headers, or an empty map if not available
     */
    public Map<String, List<String>> getHeaders() {
        return this.headers;
    }

    /**
     * @param headers the HTTP response headers to set. If null, defaults to an empty map.
     */
    public void setHeaders(Map<String, List<String>> headers) {
        this.headers = (headers != null) ? headers : Collections.<String, List<String>>emptyMap();
    }
}
