package io.constructor.client;

import static org.junit.Assert.assertEquals;

import com.google.gson.Gson;

import io.constructor.client.models.FacetOptionConfiguration;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class FacetOptionConfigurationTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void facetOption() throws Exception {
        String string = Utils.getTestResource("facet.option.configuration.json");
        FacetOptionConfiguration config = new Gson().fromJson(string, FacetOptionConfiguration.class);
        assertEquals(config.getDisplayName(), "Jif");
        assertEquals(config.getValue(), "jif");
        assertEquals(config.getReplaceValueAlias(), true);
        assertEquals(config.getPosition(), 1);
        assertEquals(config.getData().size(), 1);
        assertEquals(config.getData().get("foo"), "bar");
    }
}
