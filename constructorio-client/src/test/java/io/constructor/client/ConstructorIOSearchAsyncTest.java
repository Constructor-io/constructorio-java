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

public class ConstructorIOSearchAsyncTest {
  
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
    final ConstructorIO constructor = new ConstructorIO("", "ZqXaOfXuBWD4s3XzCI1q", true, null);
    final UserInfo userInfo = new UserInfo(3, "c62a-2a09-faie");
    final SearchRequest request = new SearchRequest("peanut");
    constructor.search(request, userInfo, new SearchCallback() {
      @Override
      public void onFailure(final ConstructorException exception) {
      }

      @Override
      public void onResponse(final SearchResponse response) {
        responseResolved = response;
      };
    });
    await().atMost(5, SECONDS).until(responseIsResolved());
    assertEquals("search results exist", responseResolved.getResponse().getResults().size(), 1);
    assertEquals("search results count as expected", (int) responseResolved.getResponse().getTotalNumberOfResults(), 1);
    assertTrue("search result id exists", responseResolved.getResultId() != null);
    responseResolved = null;
  }

  @Test
  public void SearchShouldReturnAResultWithNewApiKeySet() throws Exception {
      ConstructorIO constructor = new ConstructorIO("", "thiskeydoesnotexist", true, null);
      constructor.setApiKey("ZqXaOfXuBWD4s3XzCI1q");
      SearchRequest request = new SearchRequest("item1");
      constructor.search(request, null, new SearchCallback() {
        @Override
        public void onFailure(final ConstructorException exception) {
        }
  
        @Override
        public void onResponse(final SearchResponse response) {
          responseResolved = response;
        };
      });
      await().atMost(5, SECONDS).until(responseIsResolved());
      assertEquals("search results exist", responseResolved.getResponse().getResults().size(), 9);
      assertEquals("search results count as expected", (int)responseResolved.getResponse().getTotalNumberOfResults(), 9);
      assertTrue("search result id exists", responseResolved.getResultId() != null);
      responseResolved = null;
  }
}