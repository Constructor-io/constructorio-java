package io.constructor.client;

import static java.util.concurrent.TimeUnit.*;
import static org.awaitility.Awaitility.*;
import static org.junit.Assert.assertTrue;

import io.constructor.client.models.BrowseResponse;
import java.util.concurrent.Callable;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ConstructorIOBrowseAsyncTest {

    private BrowseResponse responseResolved;
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
    public void BrowseShouldReturnAResult() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", apiKey, true, null);
        UserInfo userInfo = new UserInfo(3, "c62a-2a09-faie");
        BrowseRequest request = new BrowseRequest("Color", "Blue");
        constructor.browse(
                request,
                userInfo,
                new BrowseCallback() {
                    @Override
                    public void onFailure(final ConstructorException exception) {}

                    @Override
                    public void onResponse(final BrowseResponse response) {
                        responseResolved = response;
                    }
                    ;
                });
        await().atMost(2, SECONDS).until(responseIsResolved());
        assertTrue("browse results exist", responseResolved.getResponse().getResults().size() > 0);
        assertTrue(
                "browse total results count should be greater than 0",
                (int) responseResolved.getResponse().getTotalNumberOfResults() > 0);
        assertTrue("browse result id exists", responseResolved.getResultId() != null);
        responseResolved = null;
    }

    @Test
    public void BrowseShouldReturnAResultWithNewApiKeySet() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", "thiskeydoesnotexist", true, null);
        constructor.setApiKey(apiKey);
        BrowseRequest request = new BrowseRequest("Color", "Blue");
        constructor.browse(
                request,
                null,
                new BrowseCallback() {
                    @Override
                    public void onFailure(final ConstructorException exception) {}

                    @Override
                    public void onResponse(final BrowseResponse response) {
                        responseResolved = response;
                    }
                    ;
                });
        await().atMost(2, SECONDS).until(responseIsResolved());
        assertTrue("browse results exist", responseResolved.getResponse().getResults().size() > 0);
        assertTrue(
                "browse total results count should be greater than 0",
                (int) responseResolved.getResponse().getTotalNumberOfResults() > 0);
        assertTrue("browse result id exists", responseResolved.getResultId() != null);
        responseResolved = null;
    }
}
