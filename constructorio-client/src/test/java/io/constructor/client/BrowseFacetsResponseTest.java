package io.constructor.client;

import static org.junit.Assert.*;

import io.constructor.client.models.BrowseFacetsResponse;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class BrowseFacetsResponseTest {

  @Rule public ExpectedException thrown = ExpectedException.none();

  @Test
  public void createBrowseFacetOptionsResponseShouldReturnAResult() throws Exception {
    String string = Utils.getTestResource("response.browsefacets.json");
    BrowseFacetsResponse response = ConstructorIO.createBrowseFacetsResponse(string);

    assertTrue("total_num_results exists", response.getResponse().getTotalNumberOfResults() == 15);
    assertEquals(
        "result_id exists", "d3fe21ab-41eb-4f57-9e24-92cfaf5c89f1", response.getResultId());
    assertEquals("15 facets exist", 15, response.getResponse().getFacets().size());
    assertEquals(
        "display_name exists",
        "articleNumberManufacturer",
        response.getResponse().getFacets().get(0).getDisplayName());
    assertEquals(
        "name exists",
        "articleNumberManufacturer",
        response.getResponse().getFacets().get(0).getName());
    assertEquals("type exists", "multiple", response.getResponse().getFacets().get(0).getType());
  }
}
