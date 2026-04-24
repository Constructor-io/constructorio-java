package io.constructor.client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class CatalogRequestTest {

    @Rule public ExpectedException thrown = ExpectedException.none();

    @Test
    public void newWithNullFilesShouldFail() throws Exception {
        thrown.expect(IllegalArgumentException.class);
        new CatalogRequest(null, "Products");
    }

    @Test
    public void newWithNullSectionShouldFail() throws Exception {
        Map<String, File> files = new HashMap<String, File>();
        files.put("items", new File("src/test/resources/csv/items.csv"));

        thrown.expect(IllegalArgumentException.class);
        new CatalogRequest(null, null);
    }

    @Test
    public void newShouldReturnCatalogRequest() throws Exception {
        Map<String, File> files = new HashMap<String, File>();
        files.put("items", new File("src/test/resources/csv/items.csv"));
        CatalogRequest request = new CatalogRequest(files, "Products");
        assertEquals(request.getFiles(), files);
    }

    @Test
    public void newShouldReturnDefaultProperties() throws Exception {
        Map<String, File> files = new HashMap<String, File>();
        files.put("items", new File("src/test/resources/csv/items.csv"));
        CatalogRequest request = new CatalogRequest(files, "Products");
        assertEquals(request.getFiles(), files);
        assertEquals(request.getSection(), "Products");
    }

    @Test
    public void getNotificationEmailShouldReturnNullByDefault() throws Exception {
        Map<String, File> files = new HashMap<String, File>();
        files.put("items", new File("src/test/resources/csv/items.csv"));
        CatalogRequest request = new CatalogRequest(files, "Products");

        assertNull(request.getNotificationEmail());
        assertNull(request.getNotificationEmails());
    }

    @Test
    public void settersShouldSet() throws Exception {
        Map<String, File> files = new HashMap<String, File>();
        files.put("items", new File("src/test/resources/csv/items.csv"));
        CatalogRequest request = new CatalogRequest(files, "Products");

        Map<String, File> newFiles = new HashMap<String, File>();
        newFiles.put("variations", new File("src/test/resources/csv/variations.csv"));
        newFiles.put("item_groups", new File("src/test/resources/csv/item_groups.csv"));

        request.setFiles(newFiles);
        request.setSection("Content");
        request.setNotificationEmail("test@constructor.io");
        request.setForce(true);

        assertEquals(request.getFiles(), newFiles);
        assertEquals(request.getSection(), "Content");
        assertEquals(request.getNotificationEmail(), "test@constructor.io");
        assertEquals(request.getForce(), true);
    }

    @Test
    public void setNotificationEmailShouldSet() throws Exception {
        Map<String, File> files = new HashMap<String, File>();
        files.put("items", new File("src/test/resources/csv/items.csv"));
        CatalogRequest request = new CatalogRequest(files, "Products");

        List<String> emails = Arrays.asList("a@constructor.io", "b@constructor.io");
        request.setNotificationEmails(emails);

        assertEquals(request.getNotificationEmails(), emails);
        assertEquals(request.getNotificationEmail(), "a@constructor.io");
    }

    @Test
    public void setNotificationEmailShouldSetListWithOneElement() throws Exception {
        Map<String, File> files = new HashMap<String, File>();
        files.put("items", new File("src/test/resources/csv/items.csv"));
        CatalogRequest request = new CatalogRequest(files, "Products");

        request.setNotificationEmail("test@constructor.io");

        assertEquals(request.getNotificationEmails(), Arrays.asList("test@constructor.io"));
        assertEquals(request.getNotificationEmail(), "test@constructor.io");
    }

    @Test
    public void setNotificationEmailNullShouldClearList() throws Exception {
        Map<String, File> files = new HashMap<String, File>();
        files.put("items", new File("src/test/resources/csv/items.csv"));
        CatalogRequest request = new CatalogRequest(files, "Products");

        request.setNotificationEmail("test@constructor.io");
        request.setNotificationEmail((String) null);

        assertNull(request.getNotificationEmails());
        assertNull(request.getNotificationEmail());
    }
}
