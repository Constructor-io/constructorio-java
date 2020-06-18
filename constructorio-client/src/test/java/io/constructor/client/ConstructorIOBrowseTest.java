package io.constructor.client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import io.constructor.client.models.BrowseResponse;

public class ConstructorIOBrowseTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    /*@Test
    public void createBrowseResponseShouldReturnAResult() throws Exception {
        String string = Utils.getTestResource("response.browse.peanut.json");
        BrowseResponse response = ConstructorIO.createBrowseResponse(string);
        assertEquals("browse facets exist", response.getResponse().getFacets().size(), 4);
        assertEquals("browse facet [Brand] exists", response.getResponse().getFacets().get(0).getName(), "Brand");
        assertEquals("browse facet [Claims] exists", response.getResponse().getFacets().get(1).getName(), "Claims");
        assertEquals("browse facet [Price] exists", response.getResponse().getFacets().get(2).getName(), "Price");
        assertEquals("browse facet [Rating] exists", response.getResponse().getFacets().get(3).getName(), "Rating");
        assertEquals("browse groups exist", response.getResponse().getGroups().size(), 3);
        assertEquals("browse group [Dairy] exists", response.getResponse().getGroups().get(0).getDisplayName(), "Dairy");
        assertEquals("browse group [Dairy] children exist", response.getResponse().getGroups().get(0).getChildren().size(), 3);
        assertEquals("browse results exist", response.getResponse().getResults().size(), 24);
        assertEquals("total number of results", (int)response.getResponse().getTotalNumberOfResults(), 562);
        assertTrue("browse result id exists", response.getResultId() != null);
    }*/

    @Test
    public void BrowseShouldReturnAResult() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", "key_dKjn8oS8czBw7Ebv", true, null);
        UserInfo userInfo = new UserInfo(3, "c62a-2a09-faie");
        BrowseRequest request = new BrowseRequest("Color", "Blue");
        BrowseResponse response = constructor.browse(request, userInfo);
        assertEquals("browse results exist", response.getResponse().getResults().size(), 30);
        assertEquals("browse results count as expected", (int)response.getResponse().getTotalNumberOfResults(), 562);
        assertTrue("browse result id exists", response.getResultId() != null);
    }

    @Test
    public void BrowseShouldReturnAResultWithFivePerPage() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", "key_dKjn8oS8czBw7Ebv", true, null);
        UserInfo userInfo = new UserInfo(3, "c62a-2a09-faie");
        BrowseRequest request = new BrowseRequest("Color", "Blue");
        request.setResultsPerPage(5);
        BrowseResponse response = constructor.browse(request, userInfo);
        assertEquals("browse results exist", response.getResponse().getResults().size(), 5);
        assertEquals("browse results count as expected", (int)response.getResponse().getTotalNumberOfResults(), 562);
        assertTrue("browse result id exists", response.getResultId() != null);
    }

    @Test
    public void BrowseShouldReturnAResultWithLastPage() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", "key_dKjn8oS8czBw7Ebv", true, null);
        UserInfo userInfo = new UserInfo(3, "c62a-2a09-faie");
        BrowseRequest request = new BrowseRequest("Color", "Blue");
        request.setPage(4);
        BrowseResponse response = constructor.browse(request, userInfo);
        assertEquals("browse results exist", response.getResponse().getResults().size(), 14);
        assertEquals("browse results count as expected", (int)response.getResponse().getTotalNumberOfResults(), 562);
        assertTrue("browse result id exists", response.getResultId() != null);
    }

    @Test
    public void BrowseShouldReturnAResultWithBrowseSuggestionsSection() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", "key_dKjn8oS8czBw7Ebv", true, null);
        UserInfo userInfo = new UserInfo(3, "c62a-2a09-faie");
        BrowseRequest request = new BrowseRequest("Color", "Blue");
        request.setSection("Browse Suggestions");
        BrowseResponse response = constructor.browse(request, userInfo);
        assertEquals("browse results exist", response.getResponse().getResults().size(), 17);
        assertEquals("browse results count as expected", (int)response.getResponse().getTotalNumberOfResults(), 17);
        assertTrue("browse result id exists", response.getResultId() != null);
    }

    @Test
    public void BrowseShouldReturnAResultWithGroupId() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", "key_dKjn8oS8czBw7Ebv", true, null);
        UserInfo userInfo = new UserInfo(3, "c62a-2a09-faie");
        BrowseRequest request = new BrowseRequest("Color", "Blue");
        request.setGroupId("431");
        BrowseResponse response = constructor.browse(request, userInfo);
        assertEquals("browse results exist", response.getResponse().getResults().size(), 30);
        assertEquals("browse results count as expected", (int)response.getResponse().getTotalNumberOfResults(), 95);
        assertTrue("browse result id exists", response.getResultId() != null);
    }

    @Test
    public void BrowseShouldReturnAResultWithBrandFacets() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", "key_dKjn8oS8czBw7Ebv", true, null);
        UserInfo userInfo = new UserInfo(3, "c62a-2a09-faie");
        BrowseRequest request = new BrowseRequest("Color", "Blue");
        request.getFacets().put("Brand", Arrays.asList("Back to Nature", "Barbara's"));
        BrowseResponse response = constructor.browse(request, userInfo);
        assertEquals("browse results exist", response.getResponse().getResults().size(), 2);
        assertEquals("browse results count as expected", (int)response.getResponse().getTotalNumberOfResults(), 2);
        assertTrue("browse result id exists", response.getResultId() != null);
    }

    @Test
    public void BrowseShouldReturnAResultWithNullUserInfo() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", "key_dKjn8oS8czBw7Ebv", true, null);
        BrowseRequest request = new BrowseRequest("Color", "Blue");
        BrowseResponse response = constructor.browse(request, null);
        assertEquals("browse results exist", response.getResponse().getResults().size(), 30);
        assertEquals("browse results count as expected", (int)response.getResponse().getTotalNumberOfResults(), 562);
        assertTrue("browse result id exists", response.getResultId() != null);
    }
}