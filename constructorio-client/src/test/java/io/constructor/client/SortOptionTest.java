package io.constructor.client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import com.google.gson.Gson;
import io.constructor.client.models.SortOption;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class SortOptionTest {
    @Rule public ExpectedException thrown = ExpectedException.none();

    @Test
    public void sortOptionDeserialization() throws Exception {
        String json =
                "{"
                        + "\"display_name\": \"Preis\","
                        + "\"path_in_metadata\": \"price_min\","
                        + "\"position\": null,"
                        + "\"hidden\": true,"
                        + "\"sort_by\": \"price\","
                        + "\"sort_order\": \"ascending\""
                        + "}";

        SortOption sortOption = new Gson().fromJson(json, SortOption.class);
        assertEquals(sortOption.getDisplayName(), "Preis");
        assertEquals(sortOption.getPathInMetadata(), "price_min");
        assertNull(sortOption.getPosition());
        assertEquals(sortOption.getHidden(), Boolean.TRUE);
        assertEquals(sortOption.getSortBy(), "price");
        assertEquals(sortOption.getSortOrder(), "ascending");
    }

    @Test
    public void sortOptionSerialization() throws Exception {
        SortOption sortOption = new SortOption();
        sortOption.setDisplayName("Preis");
        sortOption.setPathInMetadata("price_min");
        sortOption.setPosition(1);
        sortOption.setHidden(true);
        sortOption.setSortBy("price");
        sortOption.setSortOrder("descending");

        String json = new Gson().toJson(sortOption);

        // Verify the JSON contains the expected fields
        SortOption deserialized = new Gson().fromJson(json, SortOption.class);
        assertEquals(deserialized.getDisplayName(), "Preis");
        assertEquals(deserialized.getPathInMetadata(), "price_min");
        assertEquals(deserialized.getPosition(), Integer.valueOf(1));
        assertEquals(deserialized.getHidden(), Boolean.TRUE);
        assertEquals(deserialized.getSortBy(), "price");
        assertEquals(deserialized.getSortOrder(), "descending");
    }

    @Test
    public void sortOptionDefaultValues() {
        SortOption sortOption = new SortOption();
        assertNull("Display name should default to null", sortOption.getDisplayName());
        assertNull("Path in metadata should default to null", sortOption.getPathInMetadata());
        assertNull("Position should default to null", sortOption.getPosition());
        assertNull("Hidden should default to null", sortOption.getHidden());
        assertNull("Sort by should default to null", sortOption.getSortBy());
        assertNull("Sort order should default to null", sortOption.getSortOrder());
    }

    @Test
    public void sortOptionWithNullPosition() throws Exception {
        String json =
                "{"
                        + "\"display_name\": \"Name\","
                        + "\"sort_by\": \"name\","
                        + "\"sort_order\": \"ascending\","
                        + "\"position\": null,"
                        + "\"hidden\": false"
                        + "}";

        SortOption sortOption = new Gson().fromJson(json, SortOption.class);
        assertEquals(sortOption.getDisplayName(), "Name");
        assertNull(sortOption.getPosition());
        assertEquals(sortOption.getHidden(), Boolean.FALSE);
    }
}
