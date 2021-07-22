package io.constructor.client;

import static org.junit.Assert.assertTrue;

import java.util.concurrent.Callable;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import static org.awaitility.Awaitility.*;
import static java.util.concurrent.TimeUnit.*;

import io.constructor.client.models.BrowseResponse;

public class ConstructorIOBrowseAsyncTest {
  
  private BrowseResponse responseResolved;

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
  public void BrowseShouldReturnAResult() throws Exception {
      ConstructorIO constructor = new ConstructorIO("", "ZqXaOfXuBWD4s3XzCI1q", true, null);
      UserInfo userInfo = new UserInfo(3, "c62a-2a09-faie");
      BrowseRequest request = new BrowseRequest("Color", "blue");
      constructor.browse(request, userInfo, new BrowseCallback() {
        @Override
        public void onFailure(final ConstructorException exception) {
        }
  
        @Override
        public void onResponse(final BrowseResponse response) {
          responseResolved = response;
        };
      });
      await().atMost(2, SECONDS).until(responseIsResolved());
      assertTrue("browse results exist", responseResolved.getResponse().getResults().size() > 0);
      assertTrue("browse total results count should be greater than 0", (int)responseResolved.getResponse().getTotalNumberOfResults() > 0);
      assertTrue("browse result id exists", responseResolved.getResultId() != null);
      responseResolved = null;
  }

  @Test
  public void BrowseShouldReturnAResultWithNewApiKeySet() throws Exception {
      ConstructorIO constructor = new ConstructorIO("", "thiskeydoesnotexist", true, null);
      constructor.setApiKey("ZqXaOfXuBWD4s3XzCI1q");
      BrowseRequest request = new BrowseRequest("Color", "blue");
      constructor.browse(request, null, new BrowseCallback() {
        @Override
        public void onFailure(final ConstructorException exception) {
        }
  
        @Override
        public void onResponse(final BrowseResponse response) {
          responseResolved = response;
        };
      });
      await().atMost(2, SECONDS).until(responseIsResolved());
      assertTrue("browse results exist", responseResolved.getResponse().getResults().size() > 0);
      assertTrue("browse total results count should be greater than 0", (int)responseResolved.getResponse().getTotalNumberOfResults() > 0);
      assertTrue("browse result id exists", responseResolved.getResultId() != null);
      responseResolved = null;
  }
}