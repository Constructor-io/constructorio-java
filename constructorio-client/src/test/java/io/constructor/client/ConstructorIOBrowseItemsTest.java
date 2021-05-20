package io.constructor.client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import io.constructor.client.models.BrowseResponse;
import io.constructor.client.models.FilterGroup;

public class ConstructorIOBrowseItemsTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void BrowseItemsShouldReturnAResult() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", "key_dKjn8oS8czBw7Ebv", true, null);
        UserInfo userInfo = new UserInfo(3, "c62a-2a09-faie");
        List<String> ids = Arrays.asList("altea-scarf-1550252-green", "guess-bag-49210-black");
        BrowseItemsRequest request = new BrowseItemsRequest(ids);
        BrowseResponse response = constructor.browseItems(request, userInfo);
        assertTrue("browse items results exist", response.getResponse().getResults().size() > 0);
        assertTrue("browse items total results count should be equal to length of supplied ids", (int)response.getResponse().getTotalNumberOfResults() == ids.size());
        assertTrue("browse items result id exists", response.getResultId() != null);
    }

    @Test
    public void BrowseItemsAsJSONShouldReturnAResult() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", "key_dKjn8oS8czBw7Ebv", true, null);
        UserInfo userInfo = new UserInfo(3, "c62a-2a09-faie");
        List<String> ids = Arrays.asList("altea-scarf-1550252-green", "guess-bag-49210-black");
        BrowseItemsRequest request = new BrowseItemsRequest(ids);
        String response = constructor.browseItemsAsJSON(request, userInfo);
        assertTrue("browse items results exist", response.length() > 0);
    }

    @Test
    public void BrowseItemsShouldReturnAResultWithFivePerPage() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", "key_dKjn8oS8czBw7Ebv", true, null);
        UserInfo userInfo = new UserInfo(3, "c62a-2a09-faie");
        List<String> ids = Arrays.asList("altea-scarf-1550252-green", "guess-bag-49210-black", "sun68-pullover-15256-cream", "us-polo-sneaker-resly-blue", "sun68-sweater-15249-pink", "damico-ring-SAU0004-bronze");
        BrowseItemsRequest request = new BrowseItemsRequest(ids);
        request.setResultsPerPage(5);
        BrowseResponse response = constructor.browseItems(request, userInfo);
        assertTrue("browse items results exist and count is five", response.getResponse().getResults().size() == 5);
        assertTrue("browse items total results count should be equal to length of supplied ids", (int)response.getResponse().getTotalNumberOfResults() == ids.size());
        assertTrue("browse items result id exists", response.getResultId() != null);
    }

    @Test
    public void BrowseItemsShouldReturnAResultWithDifferentPage() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", "key_dKjn8oS8czBw7Ebv", true, null);
        UserInfo userInfo = new UserInfo(3, "c62a-2a09-faie");
        List<String> ids = Arrays.asList("altea-scarf-1550252-green", "guess-bag-49210-black", "sun68-pullover-15256-cream", "us-polo-sneaker-resly-blue", "sun68-sweater-15249-pink", "damico-ring-SAU0004-bronze");
        BrowseItemsRequest request = new BrowseItemsRequest(ids);
        request.setPage(2);
        request.setResultsPerPage(3);
        BrowseResponse response = constructor.browseItems(request, userInfo);
        assertTrue("browse items results exist", response.getResponse().getResults().size() == ids.size() / 2);
        assertTrue("browse items total results count should be greater than 0", (int)response.getResponse().getTotalNumberOfResults() == ids.size());
        assertTrue("browse items result id exists", response.getResultId() != null);
    }

    @Test
    public void BrowseItemsShouldReturnAResultWithGroupId() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", "key_dKjn8oS8czBw7Ebv", true, null);
        UserInfo userInfo = new UserInfo(3, "c62a-2a09-faie");
        List<String> ids = Arrays.asList("altea-scarf-1550252-green", "guess-bag-49210-black", "sun68-pullover-15256-cream", "us-polo-sneaker-resly-blue", "sun68-sweater-15249-pink", "damico-ring-SAU0004-bronze");
        BrowseItemsRequest request = new BrowseItemsRequest(ids);
        request.setGroupId("sale");
        BrowseResponse response = constructor.browseItems(request, userInfo);
        assertTrue("browse items results exist", response.getResponse().getResults().size() > 0);
        assertTrue("browse items total results count should be greater than 0", (int)response.getResponse().getTotalNumberOfResults() > 0);
        assertTrue("browse items result id exists", response.getResultId() != null);
    }

    @Test
    public void BrowseItemsShouldReturnAResultWithBrandFacets() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", "key_dKjn8oS8czBw7Ebv", true, null);
        UserInfo userInfo = new UserInfo(3, "c62a-2a09-faie");
        List<String> ids = Arrays.asList("us-polo-sneaker-resly-blue");
        BrowseItemsRequest request = new BrowseItemsRequest(ids);
        request.getFacets().put("Brand", Arrays.asList("Uspolo"));
        BrowseResponse response = constructor.browseItems(request, userInfo);
        assertTrue("browse items results exist", response.getResponse().getResults().size() > 0);
        assertTrue("browse items total results count should be greater than 0", (int)response.getResponse().getTotalNumberOfResults() > 0);
        assertTrue("browse items result id exists", response.getResultId() != null);
    }

    @Test
    public void BrowseItemsShouldReturnAResultWithVariations() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", "key_dKjn8oS8czBw7Ebv", true, null);
        UserInfo userInfo = new UserInfo(3, "c62a-2a09-faie");
        List<String> ids = Arrays.asList("altea-scarf-1550252-green");
        BrowseItemsRequest request = new BrowseItemsRequest(ids);
        BrowseResponse response = constructor.browseItems(request, userInfo);
        assertTrue("browse items results exist", response.getResponse().getResults().size() > 0);
        assertEquals("browse items result [variations] exists", response.getResponse().getResults().get(0).getVariations().size(), 1);
        assertEquals("browse items result variation [facets] exists", response.getResponse().getResults().get(0).getVariations().get(0).getData().getFacets().size(), 8);
        assertEquals("browse items result variation [value] exists", response.getResponse().getResults().get(0).getVariations().get(0).getValue(), "Scarf Altea green");
        assertEquals("browse items result variation [variation id] exists", response.getResponse().getResults().get(0).getVariations().get(0).getData().getVariationId(), "A0E20000000252B");
        assertEquals("browse items result variation [url] exists", response.getResponse().getResults().get(0).getVariations().get(0).getData().getUrl(), "https://demo.commercetools.com/en/altea-scarf-1550252-green.html");
        assertTrue("browse items total results count should be greater than 0", (int)response.getResponse().getTotalNumberOfResults() > 0);
        assertTrue("browse items result id exists", response.getResultId() != null);
    }

    @Test
    public void BrowseItemsShouldReturnAResultWithSortOptions() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", "key_dKjn8oS8czBw7Ebv", true, null);
        UserInfo userInfo = new UserInfo(3, "c62a-2a09-faie");
        List<String> ids = Arrays.asList("altea-scarf-1550252-green");
        BrowseItemsRequest request = new BrowseItemsRequest(ids);
        BrowseResponse response = constructor.browseItems(request, userInfo);
        assertEquals("browse items result [sort options] exists", response.getResponse().getSortOptions().size(), 1);
        assertEquals("browse items result sort option [display name] exists", response.getResponse().getSortOptions().get(0).getDisplayName(), "Relevance");
        assertEquals("browse items result sort option [sort by] exists", response.getResponse().getSortOptions().get(0).getSortBy(), "relevance");
        assertEquals("browse items result sort option [sort order] exists", response.getResponse().getSortOptions().get(0).getSortOrder(), "descending");
        assertEquals("browse items result sort option [status] exists", response.getResponse().getSortOptions().get(0).getStatus(), "selected");
        assertTrue("browse items total results count should be greater than 0", (int)response.getResponse().getTotalNumberOfResults() > 0);
        assertTrue("browse items result id exists", response.getResultId() != null);
    }

    @Test
    public void BrowseItemsShouldReturnAResultWithNullUserInfo() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", "key_dKjn8oS8czBw7Ebv", true, null);
        List<String> ids = Arrays.asList("altea-scarf-1550252-green");
        BrowseItemsRequest request = new BrowseItemsRequest(ids);
        BrowseResponse response = constructor.browseItems(request, null);
        assertTrue("browse items results exist", response.getResponse().getResults().size() > 0);
        assertTrue("browse items total results count should be greater than 0", (int)response.getResponse().getTotalNumberOfResults() > 0);
        assertTrue("browse items result id exists", response.getResultId() != null);
    }

    @Test
    public void BrowseItemsShouldReturnAResultWithNewApiKeySet() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", "thiskeydoesnotexist", true, null);
        constructor.setApiKey("key_dKjn8oS8czBw7Ebv");
        List<String> ids = Arrays.asList("altea-scarf-1550252-green");
        BrowseItemsRequest request = new BrowseItemsRequest(ids);
        BrowseResponse response = constructor.browseItems(request, null);
        assertTrue("browse items results exist", response.getResponse().getResults().size() > 0);
        assertTrue("browse items total results count should be greater than 0", (int)response.getResponse().getTotalNumberOfResults() > 0);
        assertTrue("browse items result id exists", response.getResultId() != null);
    }

    @Test
    public void BrowseItemsShouldReturnAResultWithMaxGroupsDepthOf3() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", "key_dKjn8oS8czBw7Ebv", true, null);
        UserInfo userInfo = new UserInfo(3, "c62a-2a09-faie");
        List<String> ids = Arrays.asList("mu-shirt-linda-F108-beige");
        BrowseItemsRequest request = new BrowseItemsRequest(ids);
        request.getFormatOptions().put("groups_max_depth", "3");
        BrowseResponse response = constructor.browseItems(request, userInfo);
        FilterGroup root = response.getResponse().getGroups().get(0);
        FilterGroup firstGen = root.getChildren().get(0);
        FilterGroup secondGen = firstGen.getChildren().get(0);
        FilterGroup thirdGen = secondGen.getChildren().get(0);
        assertEquals("browse items result [root] exists", root.getGroupId(), "all");
        assertEquals("browse items result [firstGen] exists", firstGen.getGroupId(), "women");
        assertEquals("browse items result [secondGen] exists", secondGen.getGroupId(), "women|clothing");
        assertEquals("browse items result [thirdGen] exists", thirdGen.getGroupId(), "women|clothing|shirts");
        assertTrue("browse items result id exists", response.getResultId() != null);
    }

    @Test
    public void BrowseItemsShouldReturnAResultWithMaxGroupsDepthOf1() throws Exception {
        ConstructorIO constructor = new ConstructorIO("", "key_dKjn8oS8czBw7Ebv", true, null);
        UserInfo userInfo = new UserInfo(3, "c62a-2a09-faie");
        List<String> ids = Arrays.asList("mu-shirt-linda-F108-beige");
        BrowseItemsRequest request = new BrowseItemsRequest(ids);
        request.getFormatOptions().put("groups_max_depth", "1");
        BrowseResponse response = constructor.browseItems(request, userInfo);
        FilterGroup root = response.getResponse().getGroups().get(0);
        FilterGroup firstGen = root.getChildren().get(0);
        assertEquals("browse items result [root] exists", root.getGroupId(), "all");
        assertEquals("browse items result [firstGen] exists", firstGen.getGroupId(), "women");
        assertEquals("browse items result [firstGen] children", firstGen.getChildren().size(), 0);
        assertTrue("browse items result id exists", response.getResultId() != null);
    }
}