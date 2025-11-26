package io.constructor.client.models;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/** Constructor.io Sort Options Response ... uses Gson/Reflection to load data in */
public class SortOptionsResponse {

    @SerializedName("total_count")
    private int totalCount;

    @SerializedName("sort_options")
    private List<SortOption> sortOptions;

    /**
     * @return the total count
     */
    public int getTotalCount() {
        return totalCount;
    }

    /**
     * @param totalCount the total count to set
     */
    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    /**
     * @return the sort options
     */
    public List<SortOption> getSortOptions() {
        return sortOptions;
    }

    /**
     * @param sortOptions the sort options to set
     */
    public void setSortOptions(List<SortOption> sortOptions) {
        this.sortOptions = sortOptions;
    }
}
