package io.constructor.client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import com.google.gson.Gson;
import io.constructor.client.models.FilterFacet;
import io.constructor.client.models.FilterFacetOption;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class FiltersTest {

    @Rule public ExpectedException thrown = ExpectedException.none();

    @Test
    public void multipleFacet() throws Exception {
        String string = Utils.getTestResource("facet.multiple.json");
        FilterFacet facet = new Gson().fromJson(string, FilterFacet.class);
        assertEquals(facet.getDisplayName(), "Brand");
        assertEquals(facet.getName(), "Brand");
        assertEquals(facet.getOptions().size(), 4);
        assertEquals(facet.getType(), "multiple");
        assertNull(facet.getMax());
        assertNull(facet.getMin());
        assertNull(facet.getStatus());
    }

    @Test
    public void singleFacet() throws Exception {
        String string = Utils.getTestResource("facet.single.json");
        FilterFacet facet = new Gson().fromJson(string, FilterFacet.class);
        assertEquals(facet.getDisplayName(), "Rating");
        assertEquals(facet.getName(), "Rating");
        assertEquals(facet.getOptions().size(), 4);
        assertEquals(facet.getType(), "single");
        assertNull(facet.getMax());
        assertNull(facet.getMin());
        assertNull(facet.getStatus());
        assertEquals(facet.getData().size(), 2);
        assertEquals(facet.getData().get("foo"), "bar");
        assertEquals(facet.getData().get("bar"), 123.0);
        assertEquals(facet.getHidden(), true);
    }

    @Test
    public void rangeFacet() throws Exception {
        String string = Utils.getTestResource("facet.range.json");
        FilterFacet facet = new Gson().fromJson(string, FilterFacet.class);
        assertEquals(facet.getDisplayName(), "Price");
        assertEquals(facet.getName(), "Price");
        assertNull(facet.getOptions());
        assertEquals(facet.getType(), "range");
        assertEquals((double) facet.getMax(), 429, 0);
        assertEquals((double) facet.getMin(), 0, 0);
        assertEquals(facet.getStatus().size(), 0);
    }

    @Test
    public void hierarchicalFacet() throws Exception {
        String string = Utils.getTestResource("facet.hierarchical.json");
        FilterFacet facet = new Gson().fromJson(string, FilterFacet.class);
        assertEquals(facet.getDisplayName(), "Size");
        assertEquals(facet.getName(), "Size");
        assertEquals(facet.getType(), "hierarchical");
        assertEquals(facet.getOptions().get(0).getValue(), "Mens");
        assertEquals(facet.getOptions().get(0).getOptions().get(0).getValue(), "Mens/Small");
        assertNull(facet.getMax());
        assertNull(facet.getMin());
        assertNull(facet.getStatus());
    }

    @Test
    public void rangeFacetSelected() throws Exception {
        String string = Utils.getTestResource("facet.range.selected.json");
        FilterFacet facet = new Gson().fromJson(string, FilterFacet.class);
        assertEquals(facet.getDisplayName(), "Price");
        assertEquals(facet.getName(), "Price");
        assertNull(facet.getOptions());
        assertEquals(facet.getType(), "range");
        assertEquals((double) facet.getMax(), 429, 0);
        assertEquals((double) facet.getMin(), 0, 0);
        assertEquals(facet.getStatus().size(), 2);
    }

    @Test
    public void facetOption() throws Exception {
        String string = Utils.getTestResource("facetoption.json");
        FilterFacetOption facet = new Gson().fromJson(string, FilterFacetOption.class);
        assertEquals((double) facet.getCount(), 5, 0);
        assertEquals(facet.getData().size(), 0);
        assertEquals(facet.getDisplayName(), "Jif");
        assertEquals(facet.getStatus(), "");
        assertEquals(facet.getValue(), "Jif");
        assertEquals(facet.getRange()[0], "-inf");
        assertEquals(facet.getRange()[1], 10.0);
        Object[] range = {10, "inf"};
        facet.setRange(range);
        assertEquals(facet.getRange()[0], 10);
        assertEquals(facet.getRange()[1], "inf");
    }

    @Test
    public void facetOptionSelected() throws Exception {
        String string = Utils.getTestResource("facetoption.selected.json");
        FilterFacetOption facet = new Gson().fromJson(string, FilterFacetOption.class);
        assertEquals((double) facet.getCount(), 5, 0);
        assertEquals(facet.getData().size(), 0);
        assertEquals(facet.getDisplayName(), "Jif");
        assertEquals(facet.getStatus(), "selected");
        assertEquals(facet.getValue(), "Jif");
    }
}
