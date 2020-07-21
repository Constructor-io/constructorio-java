package io.constructor.client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.concurrent.Callable;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import static org.awaitility.Awaitility.*;
import static java.util.concurrent.TimeUnit.*;

import io.constructor.client.models.SearchResponse;

public class ConstructorIOSearchTestAsync {

  private SearchResponse responseResolved;

  private Callable<Boolean> responseIsResolved() {
    return new Callable<Boolean>() {
      public Boolean call() {
        return responseResolved != null;
      }
    };
  }

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Test
  public void SearchShouldReturnAResult() throws Exception {
    final ConstructorIO constructor = new ConstructorIO("", "key_K2hlXt5aVSwoI1Uw", true, null);
    final UserInfo userInfo = new UserInfo(3, "c62a-2a09-faie");
    final SearchRequest request = new SearchRequest("peanut");
    constructor.search(request, userInfo, new SearchCallback() {
      @Override
      public void onFailure(final ConstructorException exception) {
      }

      @Override
      public void onResponse(final SearchResponse response) throws ConstructorException {
        responseResolved = response;
      };
    });

    await().atMost(2, SECONDS).until(responseIsResolved());
    assertEquals("search results exist", responseResolved.getResponse().getResults().size(), 30);
    assertEquals("search results count as expected", (int) responseResolved.getResponse().getTotalNumberOfResults(),
        104);
    assertTrue("search result id exists", responseResolved.getResultId() != null);

    responseResolved = null;
  }
}