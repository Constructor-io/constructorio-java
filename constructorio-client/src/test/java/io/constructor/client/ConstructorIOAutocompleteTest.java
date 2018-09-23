package io.constructor.client;

import static org.junit.Assert.assertTrue;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ConstructorIOAutocompleteTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void autocompleteShouldReturnAResult() throws Exception {
      ConstructorIO constructor = new ConstructorIO("YSOxV00F0Kk2R0KnPQN8", "ZqXaOfXuBWD4s3XzCI1q", true, null);
      UserInfo userInfo = new UserInfo(3, "c62a-2a09-faie");
      AutocompleteResponse result = constructor.autocomplete("Stanley", userInfo);
      assertTrue("autocomplete succeeds", result.getResultId() != null);
    }

    @Test
    public void autocompleteShouldReturnAResultWithNullUserInfo() throws Exception {
        ConstructorIO constructor = new ConstructorIO("YSOxV00F0Kk2R0KnPQN8", "ZqXaOfXuBWD4s3XzCI1q", true, null);
        AutocompleteResponse result = constructor.autocomplete("Stanley", null);
        assertTrue("autocomplete succeeds", result.getResultId() != null);
    }

}
