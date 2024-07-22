package io.constructor.client;

import static org.junit.Assert.assertEquals;

import com.google.gson.Gson;

import io.constructor.client.models.FacetConfiguration;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;


public class FacetConfigurationTest {
    @Rule public ExpectedException thrown = ExpectedException.none();

    @Test
    public void multipleFacet() throws Exception {
        String string = Utils.getTestResource("facet.configuration.json");
        FacetConfiguration config = new Gson().fromJson(string, FacetConfiguration.class);
        assertEquals(config.getDisplayName(), "Brand");
        assertEquals(config.getName(), "brand");
        assertEquals(config.getOptions().size(), 1);
        assertEquals(config.getType(), "multiple");
        assertEquals(config.getSortOrder(), "relevance");
        assertEquals(config.getSortDescending(), true);
        assertEquals(config.getMatchType(), "any");
        assertEquals(config.getPosition(), 2);
        assertEquals(config.getHidden(), false);
        assertEquals(config.getIsProtected(), false);
        assertEquals(config.getCountable(), true);
        assertEquals(config.getOptionsLimit(), 300);
        assertEquals(config.getData().size(), 1);
        assertEquals(config.getData().get("foo"), "bar");
    }

    @Test
    public void rangeFacet() throws Exception {
        String string = Utils.getTestResource("facet.range.configuration.json");
        FacetConfiguration config = new Gson().fromJson(string, FacetConfiguration.class);
        assertEquals(config.getName(), "price");
        assertEquals(config.getType(), "range");
        assertEquals(config.getRangeType(), "static");
        assertEquals(config.getRangeFormat(), "boundaries");
        assertEquals(config.getRangeInclusive(), "above");
        assertEquals(config.getRangeLimits().size(), 2);
        
    }
}
