package io.constructor.client;

import io.constructor.client.models.SortOption;

/** Constructor.io Sort Option Request */
public class SortOptionRequest {
    private SortOption sortOption;
    private String section;

    /**
     * Creates a sort option request
     *
     * @param sortOption the sort option to be created/updated
     * @param section the section to which the sort option belongs
     */
    public SortOptionRequest(SortOption sortOption, String section) {
        if (sortOption == null) {
            throw new IllegalArgumentException("sortOption is required");
        }
        if (section == null) {
            throw new IllegalArgumentException("section is required");
        }

        this.sortOption = sortOption;
        this.section = section;
    }

    /**
     * Creates a sort option request with default section "Products"
     *
     * @param sortOption the sort option to be created/updated
     */
    public SortOptionRequest(SortOption sortOption) {
        this(sortOption, "Products");
    }

    /**
     * @return the sort option
     */
    public SortOption getSortOption() {
        return sortOption;
    }

    /**
     * @param sortOption the sort option to set
     */
    public void setSortOption(SortOption sortOption) {
        this.sortOption = sortOption;
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
}
