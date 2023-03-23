package io.constructor.client;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class NaturalLanguageSearchRequestTest {

  @Rule public ExpectedException thrown = ExpectedException.none();

  @Test
  public void newWithNullQueryShouldFail() throws Exception {
    thrown.expect(IllegalArgumentException.class);
    new NaturalLanguageSearchRequest(null);
  }

  @Test
  public void newShouldReturnSearchRequest() throws Exception {
    String query = "peanut";
    NaturalLanguageSearchRequest request = new NaturalLanguageSearchRequest(query);
    assertEquals(request.getQuery(), query);
  }

  @Test
  public void newShouldReturnDefaultProperties() throws Exception {
    String query = "peanut";
    NaturalLanguageSearchRequest request = new NaturalLanguageSearchRequest(query);
    assertEquals(request.getQuery(), query);
    assertEquals(request.getSection(), "Products");
    assertEquals(request.getPage(), 1);
    assertEquals(request.getResultsPerPage(), 30);
  }

  @Test
  public void settersShouldSet() throws Exception {
    String query = "peanut";
    NaturalLanguageSearchRequest request = new NaturalLanguageSearchRequest(query);
    Map<String, List<String>> facets = new HashMap<String, List<String>>();
    facets.put("Flavors", Arrays.asList("Honey Roasted", "Dry Roasted", "Unsalted"));

    request.setQuery("airline tickets");
    request.setSection("Search Suggestions");
    request.setPage(3);
    request.setResultsPerPage(50);

    assertEquals(request.getQuery(), "airline tickets");
    assertEquals(request.getSection(), "Search Suggestions");
    assertEquals(request.getPage(), 3);
    assertEquals(request.getResultsPerPage(), 50);
  }
}
