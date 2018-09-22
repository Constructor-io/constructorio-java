package io.constructor.client;

import static org.junit.Assert.assertTrue;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import org.json.JSONObject;

public class SearchResponseTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void newWithNullShouldFail() throws Exception {
        thrown.expect(IllegalArgumentException.class);
        new SearchResponse(null);
    }

    @Test
    public void newWithJSONShouldReturnResponse() throws Exception {
      String string = Utils.getTestResource("response.search.peanut.json");
      JSONObject json = new JSONObject(string);
      SearchResponse response = new SearchResponse(json);
      assertTrue("new succeeds", response.getResultId() != null);
    }
}
