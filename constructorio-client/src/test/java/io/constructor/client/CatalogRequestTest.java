package io.constructor.client;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;
import java.io.File;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class CatalogRequestTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void newWithNullFilesShouldFail() throws Exception {
      thrown.expect(IllegalArgumentException.class);
      new CatalogRequest(null);
    }

    @Test
    public void newShouldReturnCatalogRequest() throws Exception {
      Map<String, File> files = new HashMap<String, File>();
      files.put("items", new File("src/test/resources/csv/items.csv"));
      CatalogRequest request = new CatalogRequest(files);
      assertEquals(request.getFiles(), files);
    }

    @Test
    public void newShouldReturnDefaultProperties() throws Exception {
      Map<String, File> files = new HashMap<String, File>();
      files.put("items", new File("src/test/resources/csv/items.csv"));
      CatalogRequest request = new CatalogRequest(files);
      assertEquals(request.getFiles(), files);
      assertEquals(request.getSection(), "Products");
    }

    @Test
    public void settersShouldSet() throws Exception {
        Map<String, File> files = new HashMap<String, File>();
        files.put("items", new File("src/test/resources/csv/items.csv"));
        CatalogRequest request = new CatalogRequest(files);

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
}
