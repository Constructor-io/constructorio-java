package io.constructor.client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import io.constructor.client.models.AutocompleteResponse;

public class ConstructorIOAutocompleteTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void autocompleteShouldReturnAResult() throws Exception {
        ConstructorIO constructor = new ConstructorIO("tok_AFQd9IKamjHXRsTp", "ZqXaOfXuBWD4s3XzCI1q", true, null);
        UserInfo userInfo = new UserInfo(3, "c62a-2a09-faie");
        AutocompleteRequest request = new AutocompleteRequest("Stanley");
        AutocompleteResponse response = constructor.autocomplete(request, userInfo);
        assertEquals("autocomplete product suggestions exist", response.getSections().get("Products").size(), 1);
        assertEquals("autocomplete search suggestions exist", response.getSections().get("Search Suggestions").size(), 1);
        assertTrue("autocomplete result id exists", response.getResultId() != null);
    }

    @Test
    public void autocompleteShouldReturnAResultWithNullUserInfo() throws Exception {
        ConstructorIO constructor = new ConstructorIO("tok_AFQd9IKamjHXRsTp", "ZqXaOfXuBWD4s3XzCI1q", true, null);
        AutocompleteRequest request = new AutocompleteRequest("Stanley");
        AutocompleteResponse response = constructor.autocomplete(request, null);
        assertEquals("autocomplete product suggestions exist", response.getSections().get("Products").size(), 1);
        assertEquals("autocomplete search suggestions exist", response.getSections().get("Search Suggestions").size(), 1);
        assertTrue("autocomplete result id exists", response.getResultId() != null);
    }

    @Test
    public void autocompleteShouldReturnAResultWithProductsOnly() throws Exception {
        ConstructorIO constructor = new ConstructorIO("tok_AFQd9IKamjHXRsTp", "ZqXaOfXuBWD4s3XzCI1q", true, null);
        AutocompleteRequest request = new AutocompleteRequest("Stanley");
        request.getResultsPerSection().put("Products", 10);
        AutocompleteResponse response = constructor.autocomplete(request, null);
        assertEquals("autocomplete product suggestions exist", response.getSections().get("Products").size(), 1);
        assertEquals("autocomplete search suggestions exist", response.getSections().get("Search Suggestions").size(), 0);
        assertTrue("autocomplete result id exists", response.getResultId() != null);
    }

    @Test
    public void autocompleteShouldReturnAResultWithSearchSuggestionsOnly() throws Exception {
        ConstructorIO constructor = new ConstructorIO("tok_AFQd9IKamjHXRsTp", "ZqXaOfXuBWD4s3XzCI1q", true, null);
        AutocompleteRequest request = new AutocompleteRequest("Stanley");
        request.getResultsPerSection().put("Search Suggestions", 10);
        AutocompleteResponse response = constructor.autocomplete(request, null);
        assertEquals("autocomplete product suggestions exist", response.getSections().get("Products").size(), 0);
        assertEquals("autocomplete search suggestions exist", response.getSections().get("Search Suggestions").size(), 1);
        assertTrue("autocomplete result id exists", response.getResultId() != null);
    }

    @Test
    public void autocompleteShouldReturnAResultWithNewApiKeySet() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", "thiskeydoesnotexist", true, null);
        constructor.setApiKey("ZqXaOfXuBWD4s3XzCI1q");
        AutocompleteRequest request = new AutocompleteRequest("Stanley");
        request.getResultsPerSection().put("Search Suggestions", 10);
        AutocompleteResponse response = constructor.autocomplete(request, null);
        assertEquals("autocomplete product suggestions exist", response.getSections().get("Products").size(), 0);
        assertEquals("autocomplete search suggestions exist", response.getSections().get("Search Suggestions").size(), 1);
        assertTrue("autocomplete result id exists", response.getResultId() != null);
    }
}
