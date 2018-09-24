package io.constructor.client;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import org.json.JSONObject;

public class AutocompleteResponseTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void newWithNullShouldFail() throws Exception {
        thrown.expect(IllegalArgumentException.class);
        new AutocompleteResponse(null);
    }

    @Test
    public void newWithJSONShouldReturnResponse() throws Exception {
        String string = Utils.getTestResource("response.autocomplete.peanut.json");
        JSONObject json = new JSONObject(string);
        AutocompleteResponse response = new AutocompleteResponse(json);
        assertEquals("search suggestions exist", response.getSections().get("Search Suggestions").size(), 8);
        assertEquals("product suggestions exist", response.getSections().get("Products").size(), 6);
        assertTrue("result id exists", response.getResultId() != null);
    }
}
