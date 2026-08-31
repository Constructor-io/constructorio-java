package io.constructor.client;

/** Constructor.io Browse Request */
public class BrowseRequest extends BrowseSearchBaseRequest {

    private String filterName;
    private String filterValue;

    /**
     * Creates a browse request
     *
     * @param filterName filter name to display results from
     * @param filterValue filter value to display results from
     */
    public BrowseRequest(String filterName, String filterValue) throws IllegalArgumentException {
        super();
        if (filterName == null) {
            throw new IllegalArgumentException("filterName is required");
        }
        if (filterValue == null) {
            throw new IllegalArgumentException("filterValue is required");
        }
        this.filterName = filterName;
        this.filterValue = filterValue;
    }

    /**
     * @param filterName the filterName to set
     */
    public void setFilterName(String filterName) {
        this.filterName = filterName;
    }

    /**
     * @return the filterName
     */
    public String getFilterName() {
        return filterName;
    }

    /**
     * @param filterValue the filterValue to set
     */
    public void setFilterValue(String filterValue) {
        this.filterValue = filterValue;
    }

    /**
     * @return the filterValue
     */
    public String getFilterValue() {
        return filterValue;
    }
}
