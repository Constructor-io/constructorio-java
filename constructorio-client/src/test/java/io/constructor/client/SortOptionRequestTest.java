package io.constructor.client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import io.constructor.client.models.SortOption;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class SortOptionRequestTest {
    @Rule public ExpectedException thrown = ExpectedException.none();

    @Test
    public void newSortOptionRequestShouldReturnWithDefaultSection() {
        SortOption sortOption = new SortOption();
        sortOption.setDisplayName("Price");
        sortOption.setSortBy("price");
        sortOption.setSortOrder("ascending");

        SortOptionRequest request = new SortOptionRequest(sortOption);
        assertNotNull(request);
        assertEquals(request.getSection(), "Products");
        assertEquals(request.getSortOption(), sortOption);
    }

    @Test
    public void newSortOptionRequestShouldReturnWithCustomSection() {
        SortOption sortOption = new SortOption();
        sortOption.setDisplayName("Price");
        sortOption.setSortBy("price");
        sortOption.setSortOrder("ascending");

        SortOptionRequest request = new SortOptionRequest(sortOption, "Search Suggestions");
        assertNotNull(request);
        assertEquals(request.getSection(), "Search Suggestions");
        assertEquals(request.getSortOption(), sortOption);
    }

    @Test
    public void sortOptionRequestSettersWork() {
        SortOption sortOption1 = new SortOption();
        sortOption1.setDisplayName("Price");
        sortOption1.setSortBy("price");

        SortOption sortOption2 = new SortOption();
        sortOption2.setDisplayName("Name");
        sortOption2.setSortBy("name");

        SortOptionRequest request = new SortOptionRequest(sortOption1);
        request.setSortOption(sortOption2);
        request.setSection("Custom Section");

        assertEquals(request.getSortOption(), sortOption2);
        assertEquals(request.getSection(), "Custom Section");
    }

    @Test
    public void newSortOptionRequestShouldThrowExceptionWithNullSortOption() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("sortOption is required");
        new SortOptionRequest(null);
    }

    @Test
    public void newSortOptionRequestShouldThrowExceptionWithNullSection() {
        SortOption sortOption = new SortOption();
        sortOption.setDisplayName("Price");

        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("section is required");
        new SortOptionRequest(sortOption, null);
    }
}
