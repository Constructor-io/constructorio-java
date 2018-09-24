package io.constructor.client.models;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.google.gson.Gson;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import io.constructor.client.Utils;

public class SearchResponseTest { 

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void newWithJSONShouldReturnResponse() throws Exception {
        String string = Utils.getTestResource("response.search.peanut.json");
        SearchResponse response = new Gson().fromJson(string, SearchResponse.class);

        assertEquals("search facets exist", response.getResponse().getFacets().size(), 2);
        assertEquals("search facet [Brand] exists", response.getResponse().getFacets().get(0).getName(), "Brand");
        assertEquals("search facet [Claims] exists", response.getResponse().getFacets().get(1).getName(), "Claims");
        assertEquals("search groups exist", response.getResponse().getGroups().size(), 3);
        assertEquals("search group [Dairy] exists", response.getResponse().getGroups().get(0).getDisplayName(), "Dairy");
        assertEquals("search group [Dairy] children exist", response.getResponse().getGroups().get(0).getChildren().size(), 3);
        assertEquals("search results exist", response.getResponse().getResults().size(), 24);
        assertEquals("total number of results", response.getResponse().getTotalNumberOfResults(), 198);
        assertTrue("search result id exists", response.getResultId() != null);
    }
}
