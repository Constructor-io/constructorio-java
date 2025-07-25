package io.constructor.client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import io.constructor.client.models.BrowseResponse;
import java.util.Map;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class BrowseResponseTest {

    @Rule public ExpectedException thrown = ExpectedException.none();

    @Test
    public void createBrowseResponseShouldReturnAResult() throws Exception {
        String string = Utils.getTestResource("response.browse.color.blue.json");
        BrowseResponse response = ConstructorIO.createBrowseResponse(string);
        assertEquals("browse facets exist", response.getResponse().getFacets().size(), 7);
        assertEquals(
                "browse facet [Brand] exists",
                response.getResponse().getFacets().get(0).getName(),
                "Brand");
        assertEquals(
                "browse facet [Color] exists",
                response.getResponse().getFacets().get(1).getName(),
                "Color");
        assertEquals(
                "browse facet [Gender] exists",
                response.getResponse().getFacets().get(2).getName(),
                "Gender");
        assertEquals(
                "browse facet [Made In Italy] exists",
                response.getResponse().getFacets().get(3).getName(),
                "Made In Italy");
        assertEquals("browse groups exist", response.getResponse().getGroups().size(), 1);
        assertEquals(
                "browse group [All] exists",
                response.getResponse().getGroups().get(0).getDisplayName(),
                "All");
        assertEquals(
                "browse group [All] children exist",
                response.getResponse().getGroups().get(0).getChildren().size(),
                5);
        assertEquals("browse results exist", response.getResponse().getResults().size(), 5);
        assertEquals(
                "total number of results",
                (int) response.getResponse().getTotalNumberOfResults(),
                562);
        assertTrue(
                "browse result labels exists",
                (Boolean)
                        response.getResponse().getResults().get(0).getLabels().get("is_sponsored"));
        assertEquals(
                "browse result labels exists",
                (String) response.getResponse().getResults().get(0).getLabels().get("foo"),
                "bar");
        assertEquals(
                "browse result labels exists",
                (Double) response.getResponse().getResults().get(0).getLabels().get("baz"),
                (Double) 1.0);
        assertTrue("browse result id exists", response.getResultId() != null);
        assertTrue("request exists", response.getRequest() != null);
        assertEquals(
                "request filter value exists",
                response.getRequest().get("browse_filter_value"),
                "Blue");
    }

    @Test
    public void createBrowseResponseShouldReturnAResultWithVariations() throws Exception {
        String string = Utils.getTestResource("response.browse.color.blue.json");
        BrowseResponse response = ConstructorIO.createBrowseResponse(string);
        assertEquals("browse facets exists", response.getResponse().getFacets().size(), 7);
        assertEquals("browse groups exists", response.getResponse().getGroups().size(), 1);
        assertEquals("browse results exists", response.getResponse().getResults().size(), 5);
        assertEquals(
                "browse result [id] exists",
                response.getResponse().getResults().get(0).getData().getId(),
                "aspesi-coat-I502997385098-blue");
        assertEquals(
                "browse result [variation id] exists",
                response.getResponse().getResults().get(0).getData().getVariationId(),
                "M0E20000000ECTT");
        assertEquals(
                "browse result [variations] exists",
                response.getResponse().getResults().get(0).getVariations().size(),
                8);
        assertEquals(
                "browse result variation [facets] exists",
                response.getResponse()
                        .getResults()
                        .get(0)
                        .getVariations()
                        .get(0)
                        .getData()
                        .getFacets()
                        .size(),
                8);
        assertEquals(
                "browse result variation [value] exists",
                response.getResponse().getResults().get(0).getVariations().get(0).getValue(),
                "Coat Aspesi blue");
        assertEquals(
                "browse result variation [variation id] exists",
                response.getResponse()
                        .getResults()
                        .get(0)
                        .getVariations()
                        .get(0)
                        .getData()
                        .getVariationId(),
                "M0E20000000ECTT");
        assertEquals(
                "browse result variation [image url] exists",
                response.getResponse()
                        .getResults()
                        .get(0)
                        .getVariations()
                        .get(0)
                        .getData()
                        .getImageUrl(),
                "https://s3-eu-west-1.amazonaws.com/commercetools-maximilian/products/081200_1_large.jpg");
        assertEquals(
                "browse result variation [url] exists",
                response.getResponse()
                        .getResults()
                        .get(0)
                        .getVariations()
                        .get(0)
                        .getData()
                        .getUrl(),
                "https://demo.commercetools.com/en/aspesi-coat-I502997385098-blue.html");
        assertTrue(
                "browse result variation [metadata] exists",
                response.getResponse()
                                .getResults()
                                .get(0)
                                .getVariations()
                                .get(0)
                                .getData()
                                .getMetadata()
                        != null);
        assertEquals(
                "browse result variation metadata [product type] exists",
                response.getResponse()
                        .getResults()
                        .get(0)
                        .getVariations()
                        .get(0)
                        .getData()
                        .getMetadata()
                        .get("productType"),
                "Outerwear");
        assertEquals(
                "browse result variation metadata [price] exists",
                response.getResponse()
                        .getResults()
                        .get(0)
                        .getVariations()
                        .get(0)
                        .getData()
                        .getMetadata()
                        .get("price"),
                "536.25");
        assertEquals(
                "total number of results",
                (int) response.getResponse().getTotalNumberOfResults(),
                562);
        assertTrue("browse result id exists", response.getResultId() != null);
        assertTrue("request exists", response.getRequest() != null);
        assertEquals(
                "request filter value exists",
                response.getRequest().get("browse_filter_value"),
                "Blue");
    }

    @Test
    public void createBrowseResponseShouldReturnAResultWithSortOptions() throws Exception {
        String string = Utils.getTestResource("response.browse.color.blue.json");
        BrowseResponse response = ConstructorIO.createBrowseResponse(string);
        assertEquals(
                "browse result [sort options] exists",
                response.getResponse().getSortOptions().size(),
                4);
        assertEquals(
                "browse result sort option [display name] exists",
                response.getResponse().getSortOptions().get(0).getDisplayName(),
                "Relevance");
        assertEquals(
                "browse result sort option [sort by] exists",
                response.getResponse().getSortOptions().get(0).getSortBy(),
                "relevance");
        assertEquals(
                "browse result sort option [sort order] exists",
                response.getResponse().getSortOptions().get(0).getSortOrder(),
                "descending");
        assertEquals(
                "browse result sort option [status] exists",
                response.getResponse().getSortOptions().get(0).getStatus(),
                "selected");
        assertEquals(
                "total number of results",
                (int) response.getResponse().getTotalNumberOfResults(),
                562);
        assertTrue("browse result id exists", response.getResultId() != null);
        assertTrue("request exists", response.getRequest() != null);
        assertEquals(
                "request filter value exists",
                response.getRequest().get("browse_filter_value"),
                "Blue");
    }

    @Test
    public void createBrowseResponseShouldReturnAResultWithRefinedContent() throws Exception {
        String string = Utils.getTestResource("response.browse.color.blue.json");
        BrowseResponse response = ConstructorIO.createBrowseResponse(string);
        assertEquals(
                "browse result [refined content] exists",
                response.getResponse().getRefinedContent().size(),
                1);
        assertTrue(
                "browse result refined content [data] exists",
                response.getResponse().getRefinedContent().get(0).getData() != null);
        assertEquals(
                "browse result refined content data [body] exists",
                response.getResponse().getRefinedContent().get(0).getData().get("body"),
                "Content 1 Body");
        assertEquals(
                "browse result refined content data [header] exists",
                response.getResponse().getRefinedContent().get(0).getData().get("header"),
                "Content 1 Header");
        assertEquals(
                "browse result refined content data [altText] exists",
                response.getResponse().getRefinedContent().get(0).getData().get("altText"),
                "Content 1 desktop alt text");
        assertEquals(
                "browse result refined content data [ctaLink] exists",
                response.getResponse().getRefinedContent().get(0).getData().get("ctaLink"),
                "https://constructor.io/wp-content/uploads/2022/09/groceryshop-2022-r2.png");
        assertEquals(
                "browse result refined content data [ctaText] exists",
                response.getResponse().getRefinedContent().get(0).getData().get("ctaText"),
                "Content 1 CTA Button");
        assertEquals(
                "browse result refined content data [assetUrl] exists",
                response.getResponse().getRefinedContent().get(0).getData().get("assetUrl"),
                "https://constructor.io/wp-content/uploads/2022/09/groceryshop-2022-r2.png");
        assertEquals(
                "browse result refined content data [mobileAssetUrl] exists",
                response.getResponse().getRefinedContent().get(0).getData().get("mobileAssetUrl"),
                "https://constructor.io/wp-content/uploads/2022/09/groceryshop-2022-r2.png");
        assertEquals(
                "browse result refined content data [mobileAssetAltText] exists",
                response.getResponse()
                        .getRefinedContent()
                        .get(0)
                        .getData()
                        .get("mobileAssetAltText"),
                "Content 1 mobile alt text");
        assertTrue("browse result id exists", response.getResultId() != null);
        assertTrue("request exists", response.getRequest() != null);
    }

    @Test
    public void createBrowseResponseShouldReturnAResultWithLabels() throws Exception {
        String string = Utils.getTestResource("response.browse.color.blue.json");
        BrowseResponse response = ConstructorIO.createBrowseResponse(string);
        assertEquals(
                "search result [labels] exists",
                response.getResponse().getResults().get(0).getLabels().size(),
                6);
        assertEquals(
                "is_sponsored label exists",
                response.getResponse().getResults().get(0).getLabels().get("is_sponsored"),
                true);
        assertEquals(
                "__cnstrc_new_arrivals label exists and it's a nested object",
                ((Map<String, Object>)
                                response.getResponse()
                                        .getResults()
                                        .get(0)
                                        .getLabels()
                                        .get("__cnstrc_new_arrivals"))
                        .get("display_name"),
                "New Arrival");
    }

    @Test
    public void createBrowseResponseShouldReturnAResultWithIsSlotted() throws Exception {
        String string = Utils.getTestResource("response.browse.color.blue.json");
        BrowseResponse response = ConstructorIO.createBrowseResponse(string);
        assertEquals(
                "search result [labels] exists",
                response.getResponse().getResults().get(0).getIsSlotted(),
                true);
        assertEquals(
                "search result [labels] exists",
                response.getResponse().getResults().get(1).getIsSlotted(),
                false);
    }
}
