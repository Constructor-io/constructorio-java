package io.constructor.client;

/** Constructor.io Sort Parameters - shared by requests that support sorting */
public class SortParams {

    private String sortBy;
    private boolean sortAscending;

    /** Creates sort parameters with no sortBy and ascending order */
    public SortParams() {
        this.sortBy = null;
        this.sortAscending = true;
    }

    /**
     * @param sortBy the sortBy to set
     * @param sortAscending true for ascending order, false for descending
     */
    public SortParams(String sortBy, boolean sortAscending) {
        this.sortBy = sortBy;
        this.sortAscending = sortAscending;
    }

    /**
     * @param sortBy the sortBy to set
     */
    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    /**
     * @return the sortBy
     */
    public String getSortBy() {
        return sortBy;
    }

    /**
     * @param sortAscending the sortAscending to set
     */
    public void setSortAscending(boolean sortAscending) {
        this.sortAscending = sortAscending;
    }

    /**
     * @return the sortAscending
     */
    public boolean getSortAscending() {
        return sortAscending;
    }
}
