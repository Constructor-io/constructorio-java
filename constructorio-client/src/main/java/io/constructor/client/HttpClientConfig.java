package io.constructor.client;

public class HttpClientConfig {
    private int readTimeout;
    private int writeTimeout;
    private int connectTimeout;
    private int connectionPoolMaxIdleConnections;
    private int connectionPoolKeepAliveDuration;
    private int dispatcherMaxRequests;
    private int dispatcherMaxRequestsPerHost;

    HttpClientConfig() {
        this.readTimeout = 30000;
        this.writeTimeout = 30000;
        this.connectTimeout = 10000;
        this.connectionPoolMaxIdleConnections = 5;
        this.connectionPoolKeepAliveDuration = 300000;
        this.dispatcherMaxRequests = 64;
        this.dispatcherMaxRequestsPerHost = 5;
    }

    /**
     * @return the read timeout in milliseconds
     */
    public int getReadTimeout() {
        return readTimeout;
    }

    /**
     * @param readTimeout the read timeout for new connections in milliseconds
     */
    public void setReadTimeout(int readTimeout) {
        this.readTimeout = readTimeout;
    }

    /**
     * @return the write timeout in milliseconds
     */
    public int getWriteTimeout() {
        return writeTimeout;
    }

    /**
     * @param writeTimeout the write timeout for new connections in milliseconds
     */
    public void setWriteTimeout(int writeTimeout) {
        this.writeTimeout = writeTimeout;
    }

    /**
     * @return the connect timeout in milliseconds
     */
    public int getConnectTimeout() {
        return connectTimeout;
    }

    /**
     * @param connectTimeout the connect timeout for new connections in milliseconds
     */
    public void setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    /**
     * @return the maximum number of idle connections in the pool
     */
    public int getConnectionPoolMaxIdleConnections() {
        return connectionPoolMaxIdleConnections;
    }

    /**
     * @param connectionPoolMaxIdleConnections the maximum number of idle connections in the pool
     */
    public void setConnectionPoolMaxIdleConnections(int connectionPoolMaxIdleConnections) {
        this.connectionPoolMaxIdleConnections = connectionPoolMaxIdleConnections;
    }

    /**
     * @return the keep alive duration in milliseconds
     */
    public int getConnectionPoolKeepAliveDuration() {
        return connectionPoolKeepAliveDuration;
    }

    /**
     * @param connectionPoolKeepAliveDuration the time in milliseconds to keep the connection alive in the pool before closing it
     */
    public void setConnectionPoolKeepAliveDuration(int connectionPoolKeepAliveDuration) {
        this.connectionPoolKeepAliveDuration = connectionPoolKeepAliveDuration;
    }

    /**
     * @return the max requests to execute concurrently
     */
    public int getDispatcherMaxRequests() {
        return dispatcherMaxRequests;
    }

    /**
     * @param dispatcherMaxRequests the max requests to execute concurrently
     */
    public void setDispatcherMaxRequests(int dispatcherMaxRequests) {
        this.dispatcherMaxRequests = dispatcherMaxRequests;
    }

    /**
     * @return the max requests for each host to execute concurrently
     */
    public int getDispatcherMaxRequestsPerHost() {
        return dispatcherMaxRequestsPerHost;
    }

    /**
     * @param dispatcherMaxRequestsPerHost the max requests for each host to execute concurrently
     */
    public void setDispatcherMaxRequestsPerHost(int dispatcherMaxRequestsPerHost) {
        this.dispatcherMaxRequestsPerHost = dispatcherMaxRequestsPerHost;
    }
}
