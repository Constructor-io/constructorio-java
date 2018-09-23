package io.constructor.client;

import static org.junit.Assert.assertTrue;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ConstructorIOSearchTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void SearchShouldReturnAResult() throws Exception {
      ConstructorIO constructor = new ConstructorIO("", "key_K2hlXt5aVSwoI1Uw", true, null);
      UserInfo userInfo = new UserInfo(3, "c62a-2a09-faie");
      SearchRequest request = new SearchRequest("corn");
      SearchResponse result = constructor.search(request, userInfo);
      assertTrue("Search succeeds", result.getResultId() != null);
    }

    @Test
    public void SearchShouldReturnAResultWithNullUserInfo() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", "key_K2hlXt5aVSwoI1Uw", true, null);
        SearchRequest request = new SearchRequest("corn");
        SearchResponse result = constructor.search(request, null);
        assertTrue("Search succeeds", result.getResultId() != null);
    }

}
