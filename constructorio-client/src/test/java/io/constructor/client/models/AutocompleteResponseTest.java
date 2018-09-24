package io.constructor.client.models;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.google.gson.Gson;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import io.constructor.client.Utils;

public class AutocompleteResponseTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void newFromGsonShouldReturnResponse() throws Exception {
        String string = Utils.getTestResource("response.autocomplete.peanut.json");
        AutocompleteResponse response = new Gson().fromJson(string, AutocompleteResponse.class);
        assertEquals("search suggestions exist", response.getSections().get("Search Suggestions").size(), 8);
        assertEquals("product suggestions exist", response.getSections().get("Products").size(), 6);
        assertTrue("result id exists", response.getResultId() != null);
    }
}
