package io.constructor.client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import io.constructor.client.models.SearchResponse;

public class SearchResponseTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void createSearchResponseShouldReturnAResult() throws Exception {
        String string = Utils.getTestResource("response.search.peanut.json");
        SearchResponse response = ConstructorIO.createSearchResponse(string);
        assertEquals("search facets exist", response.getResponse().getFacets().size(), 3);
        assertEquals("search facet [Brand] exists", response.getResponse().getFacets().get(0).getName(), "Brand");
        assertEquals("search facet [Claims] exists", response.getResponse().getFacets().get(1).getName(), "Nutrition");
        assertEquals("search facet [Rating] exists", response.getResponse().getFacets().get(2).getName(), "Price");
        assertEquals("search groups exist", response.getResponse().getGroups().size(), 1);
        assertEquals("search group [Cookies, Snacks & Candy] exists", response.getResponse().getGroups().get(0).getChildren().get(0).getDisplayName(), "Cookies, Snacks & Candy");
        assertEquals("search results exist", response.getResponse().getResults().size(), 24);
        assertEquals("total number of results", (int)response.getResponse().getTotalNumberOfResults(), 301);
        assertTrue("search result id exists", response.getResultId() != null);
        assertTrue("request exists", response.getRequest() != null);
        assertEquals("request query exists", response.getRequest().get("term"), "peanut");
    }

    @Test
    public void createSearchResponseShouldReturnAResultWithVariations() throws Exception {
        String string = Utils.getTestResource("response.search.jacket.json");
        SearchResponse response = ConstructorIO.createSearchResponse(string);
        assertEquals("search facets exists", response.getResponse().getFacets().size(), 7);
        assertEquals("search groups exists", response.getResponse().getGroups().size(), 1);
        assertEquals("search results exists", response.getResponse().getResults().size(), 20);
        assertEquals("search result [id] exists", response.getResponse().getResults().get(0).getData().getId(), "bully-leather-jacket-251-grey");
        assertEquals("search result [variation id] exists", response.getResponse().getResults().get(0).getData().getVariationId(), "M0E20000000FBLO");
        assertEquals("search result [variations] exists", response.getResponse().getResults().get(0).getVariations().size(), 13);
        assertEquals("search result variation [facets] exists", response.getResponse().getResults().get(0).getVariations().get(0).getData().getFacets().size(), 8);
        assertEquals("search result variation [value] exists", response.getResponse().getResults().get(0).getVariations().get(0).getValue(), "Bully – Leather Jacket");
        assertEquals("search result variation [variation id] exists", response.getResponse().getResults().get(0).getVariations().get(0).getData().getVariationId(), "M0E20000000FBLO");
        assertEquals("search result variation [image url] exists", response.getResponse().getResults().get(0).getVariations().get(0).getData().getImageUrl(), "https://demo.commercetools.com/en/bully-leather-jacket-251-grey.html");
        assertEquals("search result variation [url] exists", response.getResponse().getResults().get(0).getVariations().get(0).getData().getUrl(), "https://demo.commercetools.com/en/bully-leather-jacket-251-grey.html");
        assertTrue("search result variation [metadata] exists", response.getResponse().getResults().get(0).getVariations().get(0).getData().getMetadata() != null);
        assertEquals("search result variation metadata [product type] exists", response.getResponse().getResults().get(0).getVariations().get(0).getData().getMetadata().get("productType"), "Apparel");
        assertEquals("search result variation metadata [price] exists", response.getResponse().getResults().get(0).getVariations().get(0).getData().getMetadata().get("price"), "411.25");
        assertEquals("total number of results", (int)response.getResponse().getTotalNumberOfResults(), 261);
        assertTrue("search result labels exist", response.getResponse().getResults().get(0).getLabels().get("is_sponsored"));
        assertTrue("search result id exists", response.getResultId() != null);
        assertTrue("request exists", response.getRequest() != null);
        assertEquals("request query exists", response.getRequest().get("term"), "jacket");
    }

    @Test
    public void createSearchResponseShouldReturnAResultWithSortOptions() throws Exception {
        String string = Utils.getTestResource("response.search.jacket.json");
        SearchResponse response = ConstructorIO.createSearchResponse(string);
        assertEquals("search result [sort options] exists", response.getResponse().getSortOptions().size(), 1);
        assertEquals("search result sort option [display name] exists", response.getResponse().getSortOptions().get(0).getDisplayName(), "Relevance");
        assertEquals("search result sort option [sort by] exists", response.getResponse().getSortOptions().get(0).getSortBy(), "relevance");
        assertEquals("search result sort option [sort order] exists", response.getResponse().getSortOptions().get(0).getSortOrder(), "descending");
        assertEquals("search result sort option [status] exists", response.getResponse().getSortOptions().get(0).getStatus(), "selected");
        assertEquals("total number of results", (int)response.getResponse().getTotalNumberOfResults(), 261);
        assertTrue("search result id exists", response.getResultId() != null);
        assertTrue("request exists", response.getRequest() != null);
        assertEquals("request query exists", response.getRequest().get("term"), "jacket");
    }
}