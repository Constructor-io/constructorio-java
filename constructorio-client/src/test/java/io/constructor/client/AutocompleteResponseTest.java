package io.constructor.client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import io.constructor.client.models.AutocompleteResponse;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class AutocompleteResponseTest {

    @Rule public ExpectedException thrown = ExpectedException.none();

    @Test
    public void createAutocompleteResponseShouldReturnAResult() throws Exception {
        String string = Utils.getTestResource("response.autocomplete.peanut.json");
        AutocompleteResponse response = ConstructorIO.createAutocompleteResponse(string);
        assertEquals(
                "search suggestions exist",
                response.getSections().get("Search Suggestions").size(),
                8);
        assertEquals("product suggestions exist", response.getSections().get("Products").size(), 6);
        assertTrue("result id exists", response.getResultId() != null);
        assertTrue("request exists", response.getRequest() != null);
        assertTrue(
                "labels exists",
                (Boolean)response.getSections().get("Products").get(0).getLabels().get("is_sponsored"));
        assertEquals("request query exists", response.getRequest().get("query"), "peanut");
    }

    @Test
    public void createAutocompleteResponseShouldReturnResultWithEmptySections() throws Exception {
        String string = Utils.getTestResource("response.autocomplete.emptysections.json");
        AutocompleteResponse response = ConstructorIO.createAutocompleteResponse(string);
        assertEquals(
                "search suggestions exist",
                response.getSections().get("Search Suggestions").size(),
                0);
        assertEquals("product suggestions exist", response.getSections().get("Products").size(), 0);
        assertTrue("result id exists", response.getResultId() != null);
        assertTrue("request exists", response.getRequest() != null);
        assertEquals("request query exists", response.getRequest().get("query"), "peanut");
    }

    @Test
    public void createAutocompleteResponseShouldReturnResultWithNoSections() throws Exception {
        String string = Utils.getTestResource("response.autocomplete.nosections.json");
        AutocompleteResponse response = ConstructorIO.createAutocompleteResponse(string);
        assertEquals(response.getSections().size(), 0);
        assertTrue("result id exists", response.getResultId() != null);
        assertTrue("request exists", response.getRequest() != null);
        assertEquals("request query exists", response.getRequest().get("query"), "peanut");
    }
}
