package io.constructor.client;

import static org.junit.Assert.*;

import com.google.gson.Gson;
import io.constructor.client.models.FacetConfigurationV2;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import org.junit.Test;

public class FacetConfigurationV2Test {

    @Test
    public void testDeserializeFacetConfigurationV2FromJson() throws Exception {
        String string = Utils.getTestResource("facet.configuration.v2.json");
        FacetConfigurationV2 facetConfiguration =
                new Gson().fromJson(string, FacetConfigurationV2.class);

        assertEquals("brand", facetConfiguration.getName());
        assertEquals("brand", facetConfiguration.getPathInMetadata());
        assertEquals("multiple", facetConfiguration.getType());
        assertEquals("Brand", facetConfiguration.getDisplayName());
        assertEquals("relevance", facetConfiguration.getSortOrder());
        assertEquals(true, facetConfiguration.getSortDescending());
        assertEquals("any", facetConfiguration.getMatchType());
        assertEquals(Integer.valueOf(2), facetConfiguration.getPosition());
        assertEquals(false, facetConfiguration.getHidden());
        assertEquals(false, facetConfiguration.getProtected());
        assertEquals(true, facetConfiguration.getCountable());
        assertEquals(Integer.valueOf(300), facetConfiguration.getOptionsLimit());
        assertNotNull(facetConfiguration.getData());
        assertEquals("bar", facetConfiguration.getData().get("foo"));
    }

    @Test
    public void testSerializeFacetConfigurationV2ToJson() {
        FacetConfigurationV2 facetConfiguration = new FacetConfigurationV2();
        facetConfiguration.setName("testFacet");
        facetConfiguration.setPathInMetadata("testFacet");
        facetConfiguration.setType("multiple");
        facetConfiguration.setDisplayName("Test Facet");
        facetConfiguration.setSortOrder("relevance");
        facetConfiguration.setSortDescending(true);
        facetConfiguration.setMatchType("any");
        facetConfiguration.setPosition(1);
        facetConfiguration.setHidden(false);
        facetConfiguration.setProtected(false);
        facetConfiguration.setCountable(true);
        facetConfiguration.setOptionsLimit(500);

        Map<String, Object> data = new HashMap<>();
        data.put("key", "value");
        facetConfiguration.setData(data);

        String json = new Gson().toJson(facetConfiguration);

        assertTrue(json.contains("\"name\":\"testFacet\""));
        assertTrue(json.contains("\"path_in_metadata\":\"testFacet\""));
        assertTrue(json.contains("\"type\":\"multiple\""));
        assertTrue(json.contains("\"display_name\":\"Test Facet\""));
        assertTrue(json.contains("\"sort_order\":\"relevance\""));
        assertTrue(json.contains("\"sort_descending\":true"));
        assertTrue(json.contains("\"match_type\":\"any\""));
        assertTrue(json.contains("\"position\":1"));
        assertTrue(json.contains("\"hidden\":false"));
        assertTrue(json.contains("\"protected\":false"));
        assertTrue(json.contains("\"countable\":true"));
        assertTrue(json.contains("\"options_limit\":500"));
    }

    @Test
    public void testFacetConfigurationV2WithRangeType() {
        FacetConfigurationV2 facetConfiguration = new FacetConfigurationV2();
        facetConfiguration.setName("priceFacet");
        facetConfiguration.setPathInMetadata("price");
        facetConfiguration.setType("range");
        facetConfiguration.setRangeType("static");
        facetConfiguration.setRangeFormat("boundaries");
        facetConfiguration.setRangeLimits(Arrays.<Number>asList(10, 25, 50, 100));

        assertEquals("range", facetConfiguration.getType());
        assertEquals("static", facetConfiguration.getRangeType());
        assertEquals("boundaries", facetConfiguration.getRangeFormat());
        assertEquals(4, facetConfiguration.getRangeLimits().size());
    }

    @Test
    public void testFacetConfigurationV2WithRangeInclusive() {
        FacetConfigurationV2 facetConfiguration = new FacetConfigurationV2();
        facetConfiguration.setRangeInclusive("above");

        assertEquals("above", facetConfiguration.getRangeInclusive());
    }

    @Test
    public void testFacetConfigurationV2Timestamps() {
        FacetConfigurationV2 facetConfiguration = new FacetConfigurationV2();
        facetConfiguration.setCreatedAt("2024-01-15T10:30:00Z");
        facetConfiguration.setUpdatedAt("2024-01-16T14:45:00Z");

        assertEquals("2024-01-15T10:30:00Z", facetConfiguration.getCreatedAt());
        assertEquals("2024-01-16T14:45:00Z", facetConfiguration.getUpdatedAt());
    }

    @Test
    public void testFacetConfigurationV2Request() {
        FacetConfigurationV2 config = new FacetConfigurationV2();
        config.setName("testFacet");
        config.setPathInMetadata("testFacet");

        FacetConfigurationV2Request request = new FacetConfigurationV2Request(config, "Products");

        assertEquals(config, request.getFacetConfiguration());
        assertEquals("Products", request.getSection());
    }

    @Test
    public void testFacetConfigurationV2RequestWithDefaultSection() {
        FacetConfigurationV2 config = new FacetConfigurationV2();
        config.setName("testFacet");
        config.setPathInMetadata("testFacet");

        FacetConfigurationV2Request request = new FacetConfigurationV2Request(config);

        assertEquals(config, request.getFacetConfiguration());
        assertEquals("Products", request.getSection());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFacetConfigurationV2RequestWithNullConfig() {
        new FacetConfigurationV2Request(null, "Products");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFacetConfigurationV2RequestWithNullSection() {
        FacetConfigurationV2 config = new FacetConfigurationV2();
        config.setName("testFacet");
        new FacetConfigurationV2Request(config, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFacetConfigurationV2RequestWithBlankSection() {
        FacetConfigurationV2 config = new FacetConfigurationV2();
        config.setName("testFacet");
        new FacetConfigurationV2Request(config, "   ");
    }

    @Test
    public void testFacetConfigurationsV2Request() {
        FacetConfigurationV2 config1 = new FacetConfigurationV2();
        config1.setName("facet1");
        config1.setPathInMetadata("facet1");

        FacetConfigurationV2 config2 = new FacetConfigurationV2();
        config2.setName("facet2");
        config2.setPathInMetadata("facet2");

        FacetConfigurationsV2Request request =
                new FacetConfigurationsV2Request(Arrays.asList(config1, config2), "Products");

        assertEquals(2, request.getFacetConfigurations().size());
        assertEquals("Products", request.getSection());
    }

    @Test
    public void testFacetConfigurationsV2RequestWithDefaultSection() {
        FacetConfigurationV2 config = new FacetConfigurationV2();
        config.setName("facet1");
        config.setPathInMetadata("facet1");

        FacetConfigurationsV2Request request =
                new FacetConfigurationsV2Request(Arrays.asList(config));

        assertEquals(1, request.getFacetConfigurations().size());
        assertEquals("Products", request.getSection());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFacetConfigurationsV2RequestWithEmptyList() {
        new FacetConfigurationsV2Request(Arrays.<FacetConfigurationV2>asList(), "Products");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFacetConfigurationsV2RequestWithNullList() {
        new FacetConfigurationsV2Request(null, "Products");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFacetConfigurationsV2RequestWithBlankSection() {
        FacetConfigurationV2 config = new FacetConfigurationV2();
        config.setName("facet1");
        new FacetConfigurationsV2Request(Arrays.asList(config), "   ");
    }

    @Test
    public void testFacetConfigurationsV2GetRequest() {
        FacetConfigurationsV2GetRequest request =
                new FacetConfigurationsV2GetRequest(ConstructorIO.DEFAULT_SECTION);

        assertEquals(ConstructorIO.DEFAULT_SECTION, request.getSection());
    }

    @Test
    public void testFacetConfigurationsV2GetRequestWithDefaultSection() {
        FacetConfigurationsV2GetRequest request = new FacetConfigurationsV2GetRequest();

        assertEquals(ConstructorIO.DEFAULT_SECTION, request.getSection());
    }

    @Test
    public void testFacetConfigurationsV2GetRequestWithPagination() {
        FacetConfigurationsV2GetRequest request = new FacetConfigurationsV2GetRequest();
        request.setPage(2);
        request.setNumResultsPerPage(50);
        request.setOffset(100);

        assertEquals(Integer.valueOf(2), request.getPage());
        assertEquals(Integer.valueOf(50), request.getNumResultsPerPage());
        assertEquals(Integer.valueOf(100), request.getOffset());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFacetConfigurationsV2GetRequestWithNullSectionThrowsException() {
        new FacetConfigurationsV2GetRequest(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFacetConfigurationsV2GetRequestWithBlankSectionThrowsException() {
        new FacetConfigurationsV2GetRequest("   ");
    }
}
