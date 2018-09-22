package io.constructor.client;

import static org.junit.Assert.assertTrue;

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
      assertTrue("new succeeds", response.getResultId() != null);
    }
}
