package io.constructor.client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import com.google.gson.Gson;
import io.constructor.client.models.SortOption;
import io.constructor.client.models.SortOptionsResponse;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class SortOptionsResponseTest {
    @Rule public ExpectedException thrown = ExpectedException.none();

    @Test
    public void sortOptionsResponseDeserialization() throws Exception {
        String json =
                "{"
                        + "\"total_count\": 2,"
                        + "\"sort_options\": ["
                        + "  {"
                        + "    \"display_name\": \"Preis\","
                        + "    \"path_in_metadata\": \"price_min\","
                        + "    \"position\": null,"
                        + "    \"hidden\": true,"
                        + "    \"sort_by\": \"price\","
                        + "    \"sort_order\": \"ascending\""
                        + "  },"
                        + "  {"
                        + "    \"display_name\": \"Preis\","
                        + "    \"path_in_metadata\": \"price_min\","
                        + "    \"position\": 1,"
                        + "    \"hidden\": false,"
                        + "    \"sort_by\": \"price\","
                        + "    \"sort_order\": \"descending\""
                        + "  }"
                        + "]"
                        + "}";

        SortOptionsResponse response = new Gson().fromJson(json, SortOptionsResponse.class);
        assertNotNull(response);
        assertEquals(response.getTotalCount(), 2);
        assertNotNull(response.getSortOptions());
        assertEquals(response.getSortOptions().size(), 2);

        // Check first sort option
        SortOption first = response.getSortOptions().get(0);
        assertEquals(first.getDisplayName(), "Preis");
        assertEquals(first.getPathInMetadata(), "price_min");
        assertNull(first.getPosition());
        assertEquals(first.getHidden(), Boolean.TRUE);
        assertEquals(first.getSortBy(), "price");
        assertEquals(first.getSortOrder(), SortOption.SortOrder.ascending);

        // Check second sort option
        SortOption second = response.getSortOptions().get(1);
        assertEquals(second.getDisplayName(), "Preis");
        assertEquals(second.getPathInMetadata(), "price_min");
        assertEquals(second.getPosition(), Integer.valueOf(1));
        assertEquals(second.getHidden(), Boolean.FALSE);
        assertEquals(second.getSortBy(), "price");
        assertEquals(second.getSortOrder(), SortOption.SortOrder.descending);
    }

    @Test
    public void sortOptionsResponseWithEmptyList() throws Exception {
        String json = "{" + "\"total_count\": 0," + "\"sort_options\": []" + "}";

        SortOptionsResponse response = new Gson().fromJson(json, SortOptionsResponse.class);
        assertNotNull(response);
        assertEquals(response.getTotalCount(), 0);
        assertNotNull(response.getSortOptions());
        assertEquals(response.getSortOptions().size(), 0);
    }

    @Test
    public void sortOptionsResponseSettersWork() {
        SortOptionsResponse response = new SortOptionsResponse();
        response.setTotalCount(5);

        java.util.ArrayList<SortOption> sortOptions = new java.util.ArrayList<SortOption>();
        SortOption sortOption = new SortOption("price", SortOption.SortOrder.ascending);
        sortOption.setDisplayName("Test");
        sortOptions.add(sortOption);

        response.setSortOptions(sortOptions);

        assertEquals(response.getTotalCount(), 5);
        assertEquals(response.getSortOptions().size(), 1);
        assertEquals(response.getSortOptions().get(0).getDisplayName(), "Test");
    }
}
