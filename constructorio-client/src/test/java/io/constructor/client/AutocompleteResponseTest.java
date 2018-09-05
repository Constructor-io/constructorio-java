package io.constructor.client;

import static org.junit.Assert.assertTrue;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;

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
      Path path = Paths.get("src/test/java/io/constructor/client/response.autocomplete.peanut.json");
      byte[] bytes = Files.readAllBytes(path);
      String string = new String(bytes, "UTF-8");
      JSONObject json = new JSONObject(string);
      AutocompleteResponse response = new AutocompleteResponse(json);
      assertTrue("new succeeds", response.getResultId() != null);
    }
}
