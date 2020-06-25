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

    @Test
    public void createBrowseResponseShouldReturnAResult() throws Exception {
        String string = Utils.getTestResource("response.browse.color.blue.json");
        BrowseResponse response = ConstructorIO.createBrowseResponse(string);
        assertEquals("browse facets exist", response.getResponse().getFacets().size(), 7);
        assertEquals("browse facet [Brand] exists", response.getResponse().getFacets().get(0).getName(), "Brand");
        assertEquals("browse facet [Color] exists", response.getResponse().getFacets().get(1).getName(), "Color");
        assertEquals("browse facet [Gender] exists", response.getResponse().getFacets().get(2).getName(), "Gender");
        assertEquals("browse facet [Made In Italy] exists", response.getResponse().getFacets().get(3).getName(), "Made In Italy");
        assertEquals("browse groups exist", response.getResponse().getGroups().size(), 1);
        assertEquals("browse group [All] exists", response.getResponse().getGroups().get(0).getDisplayName(), "All");
        assertEquals("browse group [All] children exist", response.getResponse().getGroups().get(0).getChildren().size(), 5);
        assertEquals("browse results exist", response.getResponse().getResults().size(), 5);
        assertEquals("total number of results", (int)response.getResponse().getTotalNumberOfResults(), 562);
        assertTrue("browse result id exists", response.getResultId() != null);
    }

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
        request.setPage(19);
        BrowseResponse response = constructor.browse(request, userInfo);
        assertEquals("browse results exist", response.getResponse().getResults().size(), 22);
        assertEquals("browse results count as expected", (int)response.getResponse().getTotalNumberOfResults(), 562);
        assertTrue("browse result id exists", response.getResultId() != null);
    }

    @Test
    public void BrowseShouldReturnAResultWithGroupId() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", "key_dKjn8oS8czBw7Ebv", true, null);
        UserInfo userInfo = new UserInfo(3, "c62a-2a09-faie");
        BrowseRequest request = new BrowseRequest("Color", "Blue");
        request.setGroupId("sale");
        BrowseResponse response = constructor.browse(request, userInfo);
        assertEquals("browse results exist", response.getResponse().getResults().size(), 30);
        assertEquals("browse results count as expected", (int)response.getResponse().getTotalNumberOfResults(), 321);
        assertTrue("browse result id exists", response.getResultId() != null);
    }

    @Test
    public void BrowseShouldReturnAResultWithBrandFacets() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", "key_dKjn8oS8czBw7Ebv", true, null);
        UserInfo userInfo = new UserInfo(3, "c62a-2a09-faie");
        BrowseRequest request = new BrowseRequest("Color", "Blue");
        request.getFacets().put("Brand", Arrays.asList("Frankie", "Cycle"));
        BrowseResponse response = constructor.browse(request, userInfo);
        assertEquals("browse results exist", response.getResponse().getResults().size(), 17);
        assertEquals("browse results count as expected", (int)response.getResponse().getTotalNumberOfResults(), 17);
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

    @Test
    public void BrowseShouldReturnAResultWithCollectionId() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", "key_aXLmVpkVp4BX21Sw", true, "betaac.cnstrc.com");
        BrowseRequest request = new BrowseRequest("collection_id", "fresh-deals");
        BrowseResponse response = constructor.browse(request, null);
        assertEquals("browse results exist", response.getResponse().getResults().size(), 30);
        assertEquals("browse results count as expected", (int)response.getResponse().getTotalNumberOfResults(), 999);
        assertTrue("browse result id exists", response.getResultId() != null);
    }

    @Test
    public void BrowseShouldReturnAResultWithNewApiKeySet() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", "thiskeydoesnotexist", true, "betaac.cnstrc.com");
        constructor.setApiKey("key_aXLmVpkVp4BX21Sw");
        BrowseRequest request = new BrowseRequest("collection_id", "fresh-deals");
        BrowseResponse response = constructor.browse(request, null);
        assertEquals("browse results exist", response.getResponse().getResults().size(), 30);
        assertEquals("browse results count as expected", (int)response.getResponse().getTotalNumberOfResults(), 999);
        assertTrue("browse result id exists", response.getResultId() != null);
    }
}