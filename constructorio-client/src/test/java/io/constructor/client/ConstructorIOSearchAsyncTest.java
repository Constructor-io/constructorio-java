package io.constructor.client;

import static java.util.concurrent.TimeUnit.*;
import static org.awaitility.Awaitility.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import io.constructor.client.models.SearchResponse;
import java.util.concurrent.Callable;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ConstructorIOSearchAsyncTest {

  private SearchResponse responseResolved;
  private String apiKey = System.getenv("TEST_REQUEST_API_KEY");

  private Callable<Boolean> responseIsResolved() {
    return new Callable<Boolean>() {
      public Boolean call() {
        return responseResolved != null;
      }
    };
  }

  @Rule public ExpectedException thrown = ExpectedException.none();

  @Test
  public void SearchShouldReturnAResult() throws Exception {
    final ConstructorIO constructor = new ConstructorIO("", apiKey, true, null);
    final UserInfo userInfo = new UserInfo(3, "c62a-2a09-faie");
    final SearchRequest request = new SearchRequest("peanut");
    constructor.search(
        request,
        userInfo,
        new SearchCallback() {
          @Override
          public void onFailure(final ConstructorException exception) {}

          @Override
          public void onResponse(final SearchResponse response) {
            responseResolved = response;
          }
          ;
        });
    await().atMost(5, SECONDS).until(responseIsResolved());
    assertEquals("search results exist", responseResolved.getResponse().getResults().size(), 1);
    assertEquals(
        "search results count as expected",
        (int) responseResolved.getResponse().getTotalNumberOfResults(),
        1);
    assertTrue("search result id exists", responseResolved.getResultId() != null);
    responseResolved = null;
  }

  @Test
  public void SearchShouldReturnAResultWithNewApiKeySet() throws Exception {
    ConstructorIO constructor = new ConstructorIO("", "thiskeydoesnotexist", true, null);
    constructor.setApiKey(apiKey);
    SearchRequest request = new SearchRequest("item1");
    constructor.search(
        request,
        null,
        new SearchCallback() {
          @Override
          public void onFailure(final ConstructorException exception) {}

          @Override
          public void onResponse(final SearchResponse response) {
            responseResolved = response;
          }
          ;
        });
    await().atMost(5, SECONDS).until(responseIsResolved());
    assertTrue("search results exist", responseResolved.getResponse().getResults().size() >= 5);
    assertTrue(
        "search results count as expected",
        (int) responseResolved.getResponse().getTotalNumberOfResults() >= 5);
    assertTrue("search result id exists", responseResolved.getResultId() != null);
    responseResolved = null;
  }
}
