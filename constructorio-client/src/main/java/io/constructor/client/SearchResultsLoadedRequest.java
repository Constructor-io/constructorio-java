package io.constructor.client;

import java.util.ArrayList;
import java.util.List;

/** Constructor.io Search Results Loaded (search_result_load) Tracking Request */
public class SearchResultsLoadedRequest {

    /** An item shown on the search results page */
    public static class Item {
        private String itemId;
        private String variationId;

        /**
         * @param itemId the item id
         */
        public Item(String itemId) {
            this(itemId, null);
        }

        /**
         * @param itemId the item id
         * @param variationId the variation id (optional)
         */
        public Item(String itemId, String variationId) {
            if (itemId == null) {
                throw new IllegalArgumentException("itemId is required");
            }
            this.itemId = itemId;
            this.variationId = variationId;
        }

        /**
         * @return the item id
         */
        public String getItemId() {
            return itemId;
        }

        /**
         * @return the variation id
         */
        public String getVariationId() {
            return variationId;
        }
    }

    private String term;
    private int resultCount;
    private String resultId;
    private List<Item> items;
    private Integer resultPage;
    private String section;
    private String url;

    /**
     * Creates a search results loaded tracking request
     *
     * @param term the search term the results were loaded for
     * @param resultCount the total number of results
     */
    public SearchResultsLoadedRequest(String term, int resultCount)
            throws IllegalArgumentException {
        if (term == null) {
            throw new IllegalArgumentException("term is required");
        }

        this.term = term;
        this.resultCount = resultCount;
        this.items = new ArrayList<Item>();
        this.url = "N/A";
    }

    /**
     * @return the term
     */
    public String getTerm() {
        return term;
    }

    /**
     * @param term the term to set
     */
    public void setTerm(String term) {
        this.term = term;
    }

    /**
     * @return the result count
     */
    public int getResultCount() {
        return resultCount;
    }

    /**
     * @param resultCount the result count to set
     */
    public void setResultCount(int resultCount) {
        this.resultCount = resultCount;
    }

    /**
     * @return the result id
     */
    public String getResultId() {
        return resultId;
    }

    /**
     * @param resultId the result id returned in the search response, used to tie this event to that
     *     response
     */
    public void setResultId(String resultId) {
        this.resultId = resultId;
    }

    /**
     * @return the items
     */
    public List<Item> getItems() {
        return items;
    }

    /**
     * @param items the items shown on the page (only the first 100 are sent)
     */
    public void setItems(List<Item> items) {
        this.items = items;
    }

    /**
     * @return the result page
     */
    public Integer getResultPage() {
        return resultPage;
    }

    /**
     * @param resultPage the page number of the results
     */
    public void setResultPage(Integer resultPage) {
        this.resultPage = resultPage;
    }

    /**
     * @return the section
     */
    public String getSection() {
        return section;
    }

    /**
     * @param section the section to set
     */
    public void setSection(String section) {
        this.section = section;
    }

    /**
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url the url of the page the results were loaded on
     */
    public void setUrl(String url) {
        this.url = url;
    }
}
