package io.constructor.client;

import static org.junit.Assert.*;

import io.constructor.client.models.SortOption;
import io.constructor.client.models.SortOptionsResponse;
import java.util.ArrayList;
import org.json.JSONObject;
import org.junit.AfterClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ConstructorIOSortOptionsTest {

    private static String token = System.getenv("TEST_API_TOKEN");
    private static String apiKey = System.getenv("TEST_CATALOG_API_KEY");
    private static ArrayList<String> sortOptionsToCleanup = new ArrayList<>();

    private void addSortOptionToCleanupArray(String sortBy, String sortOrder, String section) {
        if (section == null) {
            section = "Products";
        }
        sortOptionsToCleanup.add(sortBy + "|" + sortOrder + "|" + section);
    }

    private void addSortOptionToCleanupArray(String sortBy, String sortOrder) {
        addSortOptionToCleanupArray(sortBy, sortOrder, "Products");
    }

    @AfterClass
    public static void cleanupSortOptions() throws ConstructorException {
        ConstructorIO constructor = new ConstructorIO(token, apiKey, true, null);

        for (String sortOptionKey : sortOptionsToCleanup) {
            String[] parts = sortOptionKey.split("\\|");
            String sortBy = parts[0];
            String sortOrder = parts[1];
            String section = parts[2];

            try {
                constructor.deleteSortOptions(sortBy, sortOrder, section);
            } catch (ConstructorException e) {
                System.err.println(
                        "Warning: Failed to clean up sort option: "
                                + sortBy
                                + " "
                                + sortOrder);
            }
        }
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void createSortOptionShouldReturn() throws Exception {
        ConstructorIO constructor = new ConstructorIO(token, apiKey, true, null);

        SortOption sortOption = new SortOption();
        sortOption.setDisplayName("Test Price Sort");
        sortOption.setPathInMetadata("test_price");
        sortOption.setHidden(false);
        sortOption.setSortBy("test_price");
        sortOption.setSortOrder("ascending");

        SortOptionRequest request = new SortOptionRequest(sortOption, "Products");

        String response = constructor.createSortOption(request);
        JSONObject jsonObj = new JSONObject(response);

        assertEquals(jsonObj.get("display_name"), "Test Price Sort");
        assertEquals(jsonObj.get("sort_by"), "test_price");
        assertEquals(jsonObj.get("sort_order"), "ascending");
        assertEquals(jsonObj.get("path_in_metadata"), "test_price");
        assertEquals(jsonObj.get("hidden"), false);

        addSortOptionToCleanupArray("test_price", "ascending");
    }

    @Test(expected = ConstructorException.class)
    public void testCreateSortOptionWithNullRequest() throws Exception {
        ConstructorIO constructor = new ConstructorIO(token, apiKey, true, null);
        constructor.createSortOption(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateSortOptionWithNullSortOption() throws Exception {
        ConstructorIO constructor = new ConstructorIO(token, apiKey, true, null);
        SortOptionRequest request = new SortOptionRequest(null, "Products");
        constructor.createSortOption(request);
    }

    @Test
    public void testCreateSortOptionWithDifferentSection() throws Exception {
        ConstructorIO constructor = new ConstructorIO(token, apiKey, true, null);

        SortOption sortOption = new SortOption();
        sortOption.setDisplayName("Custom Section Sort");
        sortOption.setPathInMetadata("custom_field");
        sortOption.setSortBy("custom_field");
        sortOption.setSortOrder("descending");
        sortOption.setHidden(true);

        SortOptionRequest request = new SortOptionRequest(sortOption, "Search Suggestions");

        String response = constructor.createSortOption(request);
        JSONObject jsonObj = new JSONObject(response);

        assertEquals("custom_field", jsonObj.getString("sort_by"));
        assertEquals("descending", jsonObj.getString("sort_order"));

        addSortOptionToCleanupArray("custom_field", "descending", "Search Suggestions");
    }

    @Test
    public void testUpdateSortOption() throws Exception {
        ConstructorIO constructor = new ConstructorIO(token, apiKey, true, null);

        // Create a sort option first
        SortOption createOption = new SortOption();
        createOption.setDisplayName("Original Name");
        createOption.setPathInMetadata("update_test");
        createOption.setSortBy("update_test");
        createOption.setSortOrder("ascending");
        createOption.setHidden(false);

        constructor.createSortOption(new SortOptionRequest(createOption, "Products"));

        // Update the sort option
        SortOption updateOption = new SortOption();
        updateOption.setDisplayName("Updated Name");
        updateOption.setPathInMetadata("update_test_v2");
        updateOption.setSortBy("update_test");
        updateOption.setSortOrder("ascending");
        updateOption.setHidden(true);
        updateOption.setPosition(5);

        SortOptionRequest updateRequest = new SortOptionRequest(updateOption, "Products");
        String updateResponse = constructor.updateSortOption(updateRequest);
        JSONObject jsonObj = new JSONObject(updateResponse);

        assertEquals("Updated Name", jsonObj.getString("display_name"));
        assertEquals("update_test_v2", jsonObj.getString("path_in_metadata"));
        assertEquals(true, jsonObj.getBoolean("hidden"));
        assertEquals(5, jsonObj.getInt("position"));

        addSortOptionToCleanupArray("update_test", "ascending");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUpdateSortOptionWithoutSortBy() throws Exception {
        ConstructorIO constructor = new ConstructorIO(token, apiKey, true, null);

        SortOption sortOption = new SortOption();
        sortOption.setDisplayName("Test");
        sortOption.setSortOrder("ascending");

        SortOptionRequest request = new SortOptionRequest(sortOption, "Products");
        constructor.updateSortOption(request);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUpdateSortOptionWithoutSortOrder() throws Exception {
        ConstructorIO constructor = new ConstructorIO(token, apiKey, true, null);

        SortOption sortOption = new SortOption();
        sortOption.setDisplayName("Test");
        sortOption.setSortBy("test_field");

        SortOptionRequest request = new SortOptionRequest(sortOption, "Products");
        constructor.updateSortOption(request);
    }

    @Test
    public void testDeleteSortOptionWithSortByAndSortOrder() throws Exception {
        ConstructorIO constructor = new ConstructorIO(token, apiKey, true, null);

        // Create a sort option first
        SortOption sortOption = new SortOption();
        sortOption.setDisplayName("Delete Test");
        sortOption.setPathInMetadata("delete_test");
        sortOption.setSortBy("delete_test");
        sortOption.setSortOrder("ascending");

        constructor.createSortOption(new SortOptionRequest(sortOption, "Products"));

        // Delete the sort option
        // DELETE endpoint returns 204 with no body
        String deleteResponse = constructor.deleteSortOptions("delete_test", "ascending", "Products");

        // Verify that the response is empty (204 No Content)
        assertTrue(
                "DELETE should return empty response",
                deleteResponse == null || deleteResponse.trim().isEmpty());
    }

    @Test
    public void testDeleteSortOptionWithDefaultSection() throws Exception {
        ConstructorIO constructor = new ConstructorIO(token, apiKey, true, null);

        // Create a sort option first
        SortOption sortOption = new SortOption();
        sortOption.setDisplayName("Delete Default Section");
        sortOption.setPathInMetadata("delete_default");
        sortOption.setSortBy("delete_default");
        sortOption.setSortOrder("descending");

        constructor.createSortOption(new SortOptionRequest(sortOption, "Products"));

        // Delete the sort option using default section
        // DELETE endpoint returns 204 with no body
        String deleteResponse = constructor.deleteSortOptions("delete_default", "descending");

        // Verify that the response is empty (204 No Content)
        assertTrue(
                "DELETE should return empty response",
                deleteResponse == null || deleteResponse.trim().isEmpty());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDeleteSortOptionWithEmptySortBy() throws Exception {
        ConstructorIO constructor = new ConstructorIO(token, apiKey, true, null);
        constructor.deleteSortOptions("", "ascending", "Products");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDeleteSortOptionWithEmptySortOrder() throws Exception {
        ConstructorIO constructor = new ConstructorIO(token, apiKey, true, null);
        constructor.deleteSortOptions("test", "", "Products");
    }

    @Test
    public void testRetrieveSortOptions() throws Exception {
        ConstructorIO constructor = new ConstructorIO(token, apiKey, true, null);

        SortOptionsResponse response = constructor.retrieveSortOptions();

        assertNotNull(response);
        assertNotNull(response.getSortOptions());
        assertTrue(response.getTotalCount() >= 0);
    }

    @Test
    public void testRetrieveSortOptionsWithSection() throws Exception {
        ConstructorIO constructor = new ConstructorIO(token, apiKey, true, null);

        SortOptionsResponse response = constructor.retrieveSortOptions("Products", null);

        assertNotNull(response);
        assertNotNull(response.getSortOptions());
        assertTrue(response.getTotalCount() >= 0);
    }

    @Test
    public void testRetrieveSortOptionsWithFilter() throws Exception {
        ConstructorIO constructor = new ConstructorIO(token, apiKey, true, null);

        // Create a sort option to ensure there's something to retrieve
        SortOption sortOption = new SortOption();
        sortOption.setDisplayName("Retrieve Filter Test");
        sortOption.setPathInMetadata("retrieve_filter_test");
        sortOption.setSortBy("retrieve_filter_test");
        sortOption.setSortOrder("ascending");

        constructor.createSortOption(new SortOptionRequest(sortOption, "Products"));
        addSortOptionToCleanupArray("retrieve_filter_test", "ascending");

        // Retrieve with filter
        SortOptionsResponse response = constructor.retrieveSortOptions("Products", "retrieve_filter_test");

        assertNotNull(response);
        assertNotNull(response.getSortOptions());

        // If there are results, verify they match the filter
        if (response.getTotalCount() > 0) {
            for (SortOption option : response.getSortOptions()) {
                assertEquals("retrieve_filter_test", option.getSortBy());
            }
        }
    }

    @Test
    public void testSortOptionDefaultValues() {
        SortOption sortOption = new SortOption();
        assertNull("Position should default to null", sortOption.getPosition());
        assertNull("Hidden should default to null", sortOption.getHidden());
        assertNull("Display name should default to null", sortOption.getDisplayName());
    }
}
