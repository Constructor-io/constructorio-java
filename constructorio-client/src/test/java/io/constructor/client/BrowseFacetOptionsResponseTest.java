package io.constructor.client;

import static org.junit.Assert.*;

import io.constructor.client.models.BrowseFacetOptionsResponse;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class BrowseFacetOptionsResponseTest {

  @Rule public ExpectedException thrown = ExpectedException.none();

  @Test
  public void createBrowseFacetOptionsResponseShouldReturnAResult() throws Exception {
    String string = Utils.getTestResource("response.browsefacetoptions.json");
    BrowseFacetOptionsResponse response = ConstructorIO.createBrowseFacetOptionsResponse(string);

    assertEquals("1 facet exists", 1, response.getResponse().getFacets().size());
    assertEquals(
        "result_id exists", "b776803b-5e00-4c92-b519-3ef58bf027f6", response.getResultId());
    assertEquals(
        "display_name exists", "Color", response.getResponse().getFacets().get(0).getDisplayName());
    assertEquals("name exists", "Color", response.getResponse().getFacets().get(0).getName());
    assertEquals("type exists", "multiple", response.getResponse().getFacets().get(0).getType());
    assertEquals(
        "4 options exists", 4, response.getResponse().getFacets().get(0).getOptions().size());
  }
}
