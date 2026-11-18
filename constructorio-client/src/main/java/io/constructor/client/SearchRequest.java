package io.constructor.client;

/** Constructor.io Search Request */
public class SearchRequest extends SortablePlpRequestBase {

    private String query;
    private String collectionId;

    /**
     * Creates a search request
     *
     * @param query the term to return search results for
     */
    public SearchRequest(String query) throws IllegalArgumentException {
        if (query == null) {
            throw new IllegalArgumentException("query is required");
        }
        this.query = query;
    }

    /**
     * @param query the query to set
     */
    public void setQuery(String query) {
        this.query = query;
    }

    /**
     * @return the query
     */
    public String getQuery() {
        return query;
    }

    /**
     * @param collectionId the collectionId to set
     */
    public void setCollectionId(String collectionId) {
        this.collectionId = collectionId;
    }

    /**
     * @return the collectionId
     */
    public String getCollectionId() {
        return collectionId;
    }
}
