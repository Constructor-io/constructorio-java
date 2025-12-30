package io.constructor.client;

import static org.junit.Assert.*;

import com.google.gson.Gson;
import io.constructor.client.models.SearchabilityV2;
import java.util.Arrays;
import org.junit.Test;

public class SearchabilityV2Test {

    @Test
    public void testDeserializeSearchabilityV2FromJson() throws Exception {
        String string = Utils.getTestResource("searchability.v2.json");
        SearchabilityV2 searchability = new Gson().fromJson(string, SearchabilityV2.class);

        assertEquals("title", searchability.getName());
        assertEquals(true, searchability.getFuzzySearchable());
        assertEquals(false, searchability.getExactSearchable());
        assertEquals(true, searchability.getDisplayable());
        assertEquals(false, searchability.getHidden());
    }

    @Test
    public void testSerializeSearchabilityV2ToJson() {
        SearchabilityV2 searchability = new SearchabilityV2();
        searchability.setName("description");
        searchability.setFuzzySearchable(false);
        searchability.setExactSearchable(true);
        searchability.setDisplayable(true);
        searchability.setHidden(false);

        String json = new Gson().toJson(searchability);

        assertTrue(json.contains("\"name\":\"description\""));
        assertTrue(json.contains("\"fuzzy_searchable\":false"));
        assertTrue(json.contains("\"exact_searchable\":true"));
        assertTrue(json.contains("\"displayable\":true"));
        assertTrue(json.contains("\"hidden\":false"));
    }

    @Test
    public void testSearchabilityV2Timestamps() {
        SearchabilityV2 searchability = new SearchabilityV2();
        searchability.setCreatedAt("2024-01-15T10:30:00Z");
        searchability.setUpdatedAt("2024-01-16T14:45:00Z");

        assertEquals("2024-01-15T10:30:00Z", searchability.getCreatedAt());
        assertEquals("2024-01-16T14:45:00Z", searchability.getUpdatedAt());
    }

    @Test
    public void testSearchabilityV2Request() {
        SearchabilityV2 config = new SearchabilityV2();
        config.setName("title");
        config.setFuzzySearchable(true);

        SearchabilityV2Request request =
                new SearchabilityV2Request(config, "title", ConstructorIO.DEFAULT_SECTION);

        assertEquals(config, request.getSearchability());
        assertEquals("title", request.getName());
        assertEquals(ConstructorIO.DEFAULT_SECTION, request.getSection());
    }

    @Test
    public void testSearchabilityV2RequestWithDefaultSection() {
        SearchabilityV2 config = new SearchabilityV2();
        config.setName("title");

        SearchabilityV2Request request = new SearchabilityV2Request(config, "title");

        assertEquals(config, request.getSearchability());
        assertEquals("title", request.getName());
        assertEquals(ConstructorIO.DEFAULT_SECTION, request.getSection());
    }

    @Test
    public void testSearchabilityV2RequestForReadOperations() {
        SearchabilityV2Request request =
                new SearchabilityV2Request("title", ConstructorIO.DEFAULT_SECTION);

        assertNull(request.getSearchability());
        assertEquals("title", request.getName());
        assertEquals(ConstructorIO.DEFAULT_SECTION, request.getSection());
    }

    @Test
    public void testSearchabilityV2RequestForReadWithDefaultSection() {
        SearchabilityV2Request request = new SearchabilityV2Request("title");

        assertNull(request.getSearchability());
        assertEquals("title", request.getName());
        assertEquals(ConstructorIO.DEFAULT_SECTION, request.getSection());
    }

    @Test
    public void testSearchabilityV2RequestWithSkipRebuild() {
        SearchabilityV2Request request = new SearchabilityV2Request("title");
        request.setSkipRebuild(true);

        assertEquals(true, request.getSkipRebuild());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSearchabilityV2RequestWithNullNameThrowsException() {
        new SearchabilityV2Request((String) null, ConstructorIO.DEFAULT_SECTION);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSearchabilityV2RequestWithEmptyNameThrowsException() {
        new SearchabilityV2Request("   ", ConstructorIO.DEFAULT_SECTION);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSearchabilityV2RequestWithNullSectionThrowsException() {
        SearchabilityV2 config = new SearchabilityV2();
        config.setName("title");
        new SearchabilityV2Request(config, "title", null);
    }

    @Test
    public void testSearchabilitiesV2Request() {
        SearchabilityV2 config1 = new SearchabilityV2();
        config1.setName("title");
        config1.setFuzzySearchable(true);

        SearchabilityV2 config2 = new SearchabilityV2();
        config2.setName("description");
        config2.setExactSearchable(true);

        SearchabilitiesV2Request request =
                new SearchabilitiesV2Request(
                        Arrays.asList(config1, config2), ConstructorIO.DEFAULT_SECTION);

        assertEquals(2, request.getSearchabilities().size());
        assertEquals(ConstructorIO.DEFAULT_SECTION, request.getSection());
    }

    @Test
    public void testSearchabilitiesV2RequestWithDefaultSection() {
        SearchabilityV2 config = new SearchabilityV2();
        config.setName("title");

        SearchabilitiesV2Request request = new SearchabilitiesV2Request(Arrays.asList(config));

        assertEquals(1, request.getSearchabilities().size());
        assertEquals(ConstructorIO.DEFAULT_SECTION, request.getSection());
    }

    @Test
    public void testSearchabilitiesV2RequestWithSkipRebuild() {
        SearchabilityV2 config = new SearchabilityV2();
        config.setName("title");

        SearchabilitiesV2Request request = new SearchabilitiesV2Request(Arrays.asList(config));
        request.setSkipRebuild(true);

        assertEquals(true, request.getSkipRebuild());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSearchabilitiesV2RequestWithNullListThrowsException() {
        new SearchabilitiesV2Request(null, ConstructorIO.DEFAULT_SECTION);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSearchabilitiesV2RequestWithEmptyListThrowsException() {
        new SearchabilitiesV2Request(
                Arrays.<SearchabilityV2>asList(), ConstructorIO.DEFAULT_SECTION);
    }

    @Test
    public void testSearchabilitiesV2GetRequest() {
        SearchabilitiesV2GetRequest request =
                new SearchabilitiesV2GetRequest(ConstructorIO.DEFAULT_SECTION);

        assertEquals(ConstructorIO.DEFAULT_SECTION, request.getSection());
    }

    @Test
    public void testSearchabilitiesV2GetRequestWithDefaultSection() {
        SearchabilitiesV2GetRequest request = new SearchabilitiesV2GetRequest();

        assertEquals(ConstructorIO.DEFAULT_SECTION, request.getSection());
    }

    @Test
    public void testSearchabilitiesV2GetRequestWithPagination() {
        SearchabilitiesV2GetRequest request = new SearchabilitiesV2GetRequest();
        request.setPage(2);
        request.setNumResultsPerPage(50);
        request.setOffset(100);

        assertEquals(Integer.valueOf(2), request.getPage());
        assertEquals(Integer.valueOf(50), request.getNumResultsPerPage());
        assertEquals(Integer.valueOf(100), request.getOffset());
    }

    @Test
    public void testSearchabilitiesV2GetRequestWithFilters() {
        SearchabilitiesV2GetRequest request = new SearchabilitiesV2GetRequest();
        request.setName("title*");
        request.setFuzzySearchable(true);
        request.setExactSearchable(false);
        request.setDisplayable(true);
        request.setMatchType("and");
        request.setSortBy("name");
        request.setSortOrder("ascending");

        assertEquals("title*", request.getName());
        assertEquals(true, request.getFuzzySearchable());
        assertEquals(false, request.getExactSearchable());
        assertEquals(true, request.getDisplayable());
        assertEquals("and", request.getMatchType());
        assertEquals("name", request.getSortBy());
        assertEquals("ascending", request.getSortOrder());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSearchabilitiesV2GetRequestWithNullSectionThrowsException() {
        new SearchabilitiesV2GetRequest(null);
    }

    @Test
    public void testSearchabilitiesV2DeleteRequest() {
        SearchabilitiesV2DeleteRequest request =
                new SearchabilitiesV2DeleteRequest(
                        Arrays.asList("title", "description"), ConstructorIO.DEFAULT_SECTION);

        assertEquals(2, request.getSearchabilityNames().size());
        assertTrue(request.getSearchabilityNames().contains("title"));
        assertTrue(request.getSearchabilityNames().contains("description"));
        assertEquals(ConstructorIO.DEFAULT_SECTION, request.getSection());
    }

    @Test
    public void testSearchabilitiesV2DeleteRequestWithDefaultSection() {
        SearchabilitiesV2DeleteRequest request =
                new SearchabilitiesV2DeleteRequest(Arrays.asList("title"));

        assertEquals(1, request.getSearchabilityNames().size());
        assertEquals(ConstructorIO.DEFAULT_SECTION, request.getSection());
    }

    @Test
    public void testSearchabilitiesV2DeleteRequestWithSkipRebuild() {
        SearchabilitiesV2DeleteRequest request =
                new SearchabilitiesV2DeleteRequest(Arrays.asList("title"));
        request.setSkipRebuild(true);

        assertEquals(true, request.getSkipRebuild());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSearchabilitiesV2DeleteRequestWithNullNamesThrowsException() {
        new SearchabilitiesV2DeleteRequest(null, ConstructorIO.DEFAULT_SECTION);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSearchabilitiesV2DeleteRequestWithEmptyNamesThrowsException() {
        new SearchabilitiesV2DeleteRequest(Arrays.<String>asList(), ConstructorIO.DEFAULT_SECTION);
    }
}
