package io.constructor.client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import io.constructor.client.models.SearchResponse;

public class ConstructorIOSearchTestAsync {

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
              // TODO Auto-generated method stub
            }

            @Override
            public void onResponse(final SearchResponse response) throws ConstructorException {
                assertEquals("search results exist", response.getResponse().getResults().size(), 30);
                assertEquals("search results count as expected", (int)response.getResponse().getTotalNumberOfResults(), 104);
                assertTrue("search result id exists", response.getResultId() != null);
              };
        });
    }
}