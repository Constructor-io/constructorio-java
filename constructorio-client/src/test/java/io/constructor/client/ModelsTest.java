package io.constructor.client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import com.google.gson.Gson;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import io.constructor.client.models.SearchFacet;
import io.constructor.client.models.SearchFacetOption;

public class ModelsTest {

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Test
  public void multipleFacet() throws Exception {
    String string = Utils.getTestResource("facet.multiple.json");
    SearchFacet facet = new Gson().fromJson(string, SearchFacet.class);
    assertEquals(facet.getDisplayName(), "Brand");
    assertEquals(facet.getName(), "Brand");
    assertEquals(facet.getOptions().size(), 4);
    assertEquals(facet.getType(), "multiple");
    assertNull(facet.getMax());
    assertNull(facet.getMin());
  }

  @Test
  public void singleFacet() throws Exception {
    String string = Utils.getTestResource("facet.single.json");
    SearchFacet facet = new Gson().fromJson(string, SearchFacet.class);
    assertEquals(facet.getDisplayName(), "Rating");
    assertEquals(facet.getName(), "Rating");
    assertEquals(facet.getOptions().size(), 4);
    assertEquals(facet.getType(), "single");
    assertNull(facet.getMax());
    assertNull(facet.getMin());
  }

  @Test
  public void rangeFacet() throws Exception {
    String string = Utils.getTestResource("facet.range.json");
    SearchFacet facet = new Gson().fromJson(string, SearchFacet.class);
    assertEquals(facet.getDisplayName(), "Price");
    assertEquals(facet.getName(), "Price");
    assertNull(facet.getOptions());
    assertEquals(facet.getType(), "range");
    assertEquals((int)facet.getMax(), 429);
    assertEquals((int)facet.getMin(), 0);
  }

  @Test
  public void facetOption() throws Exception {
    String string = Utils.getTestResource("facetoption.json");
    SearchFacetOption facet = new Gson().fromJson(string, SearchFacetOption.class);
    assertEquals((int)facet.getCount(), 5);
    assertEquals(facet.getData().size(), 0);
    assertEquals(facet.getDisplayName(), "Jif");
    assertEquals(facet.getStatus(), "");
    assertEquals(facet.getValue(), "Jif");
  }

  @Test
  public void facetOptionSelected() throws Exception {
    String string = Utils.getTestResource("facetoption.selected.json");
    SearchFacetOption facet = new Gson().fromJson(string, SearchFacetOption.class);
    assertEquals((int)facet.getCount(), 5);
    assertEquals(facet.getData().size(), 0);
    assertEquals(facet.getDisplayName(), "Jif");
    assertEquals(facet.getStatus(), "selected");
    assertEquals(facet.getValue(), "Jif");
  }
}