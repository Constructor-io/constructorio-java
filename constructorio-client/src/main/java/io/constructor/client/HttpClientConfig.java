package io.constructor.client;

public class HttpClientConfig {
    private Integer readTimeout;
    private Integer writeTimeout;
    private Integer connectTimeout;
    private Integer connPoolMaxIdleConnections;
    private Integer connPoolKeepAliveDuration;
    private Integer dispatcherMaxRequests;
    private Integer dispatcherMaxRequestsPerHost;

    HttpClientConfig() {
        this.readTimeout = null;
        this.writeTimeout = null;
        this.connectTimeout = null;
        this.connPoolMaxIdleConnections = null;
        this.connPoolKeepAliveDuration = null;
        this.dispatcherMaxRequests = null;
        this.dispatcherMaxRequestsPerHost = null;
    }

    /**
     * @return the okhttp read timeout in milliseconds
     */
    public Integer getReadTimeout() {
        return readTimeout;
    }

    /**
     * @param readTimeout the read timeout for new connections in milliseconds
     */
    public void setReadTimeout(Integer readTimeout) {
        this.readTimeout = readTimeout;
    }

    /**
     * @return the write timeout in milliseconds
     */
    public Integer getWriteTimeout() {
        return writeTimeout;
    }

    /**
     * @param writeTimeout the write timeout for new connections in milliseconds
     */
    public void setWriteTimeout(Integer writeTimeout) {
        this.writeTimeout = writeTimeout;
    }

    /**
     * @return the connect timeout in milliseconds
     */
    public Integer getConnectTimeout() {
        return connectTimeout;
    }

    /**
     * @param connectTimeout the connect timeout for new connections in milliseconds
     */
    public void setConnectTimeout(Integer connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    /**
     * @return the maximum number of idle connections in the pool
     */
    public Integer getConnPoolMaxIdleConnections() {
        return connPoolMaxIdleConnections;
    }

    /**
     * @param connPoolMaxIdleConnections the maximum number of idle connections in the pool
     */
    public void setConnPoolMaxIdleConnections(Integer connPoolMaxIdleConnections) {
        this.connPoolMaxIdleConnections = connPoolMaxIdleConnections;
    }

    /**
     * @return the keep alive duration in millis
     */
    public Integer getConnPoolKeepAliveDuration() {
        return connPoolKeepAliveDuration;
    }

    /**
     * @param connPoolKeepAliveDuration the time in milliseconds to keep the connection alive in the pool before closing it
     */
    public void setConnPoolKeepAliveDuration(Integer connPoolKeepAliveDuration) {
        this.connPoolKeepAliveDuration = connPoolKeepAliveDuration;
    }

    /**
     * @return the max requests to execute concurrently
     */
    public Integer getDispatcherMaxRequests() {
        return dispatcherMaxRequests;
    }

    /**
     * @param dispatcherMaxRequests the max requests to execute concurrently
     */
    public void setDispatcherMaxRequests(Integer dispatcherMaxRequests) {
        this.dispatcherMaxRequests = dispatcherMaxRequests;
    }

    /**
     * @return the max requests for each host to execute concurrently
     */
    public Integer getDispatcherMaxRequestsPerHost() {
        return dispatcherMaxRequestsPerHost;
    }

    /**
     * @param dispatcherMaxRequestsPerHost the max requests for each host to execute concurrently
     */
    public void setDispatcherMaxRequestsPerHost(Integer dispatcherMaxRequestsPerHost) {
        this.dispatcherMaxRequestsPerHost = dispatcherMaxRequestsPerHost;
    }
}
