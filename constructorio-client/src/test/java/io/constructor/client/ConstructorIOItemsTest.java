package io.constructor.client;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import io.constructor.client.models.ItemsResponse;
import java.util.ArrayList;
import java.util.Arrays;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.AfterClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ConstructorIOItemsTest {

    private static String token = System.getenv("TEST_API_TOKEN");
    private static String apiKey = System.getenv("TEST_CATALOG_API_KEY");
    private static ArrayList<ConstructorItem> itemsToCleanup = new ArrayList<ConstructorItem>();

    private void addItemsToCleanUpArray(ConstructorItem[] items) {
        for (ConstructorItem constructorItem : items) {
            itemsToCleanup.add(constructorItem);
        }
    }

    @AfterClass
    public static void cleanupItems() throws ConstructorException {
        ConstructorIO constructor = new ConstructorIO(token, apiKey, true, null);

        constructor.deleteItems(
                itemsToCleanup.toArray(new ConstructorItem[itemsToCleanup.size()]), "Products");
    }

    @Rule public ExpectedException thrown = ExpectedException.none();

    @Test
    public void createOrReplaceItemsShouldReturnTrue() throws Exception {
        ConstructorIO constructor = new ConstructorIO(token, apiKey, true, null);
        ConstructorItem[] items = {
            Utils.createProductItem(), Utils.createProductItem(), Utils.createProductItem()
        };

        assertTrue("add or replace succeeds", constructor.createOrReplaceItems(items, "Products"));
        addItemsToCleanUpArray(items);
    }

    @Test
    public void createOrReplaceItemsShouldReturnTrueWithAllParameters() throws Exception {
        ConstructorIO constructor = new ConstructorIO(token, apiKey, true, null);
        ConstructorItem[] items = {
            Utils.createProductItem(), Utils.createProductItem(), Utils.createProductItem()
        };

        assertTrue(
                "add or replace succeeds",
                constructor.createOrReplaceItems(items, "Products", true, "test@constructor.io"));
        addItemsToCleanUpArray(items);
    }

    @Test
    public void updateItemsShouldReturnTrue() throws Exception {
        ConstructorIO constructor = new ConstructorIO(token, apiKey, true, null);
        ConstructorItem[] itemsOld = {Utils.createProductItem()};

        constructor.createOrReplaceItems(itemsOld, "Products");

        Thread.sleep(2000);

        ConstructorItem itemOld = itemsOld[0];
        ConstructorItem itemNew = new ConstructorItem(itemOld.getId());
        itemNew.setUrl(itemOld.getUrl());
        itemNew.setSuggestedScore((float) 1337.00);
        ConstructorItem[] itemsNew = {itemNew};

        assertTrue("update succeeds", constructor.updateItems(itemsNew, "Products"));
        addItemsToCleanUpArray(itemsNew);
    }

    @Test
    public void updateItemsShouldReturnTrueWithAllParameters() throws Exception {
        ConstructorIO constructor = new ConstructorIO(token, apiKey, true, null);
        ConstructorItem[] itemsOld = {Utils.createProductItem()};

        constructor.createOrReplaceItems(itemsOld, "Products");

        Thread.sleep(2000);

        ConstructorItem itemOld = itemsOld[0];
        ConstructorItem itemNew = new ConstructorItem(itemOld.getId());
        itemNew.setUrl(itemOld.getUrl());
        itemNew.setSuggestedScore((float) 1337.00);
        ConstructorItem[] itemsNew = {itemNew};

        assertTrue(
                "update succeeds",
                constructor.updateItems(
                        itemsNew,
                        "Products",
                        true,
                        "test@constructor.io",
                        CatalogRequest.OnMissing.CREATE));
        addItemsToCleanUpArray(itemsNew);
    }

    @Test
    public void deleteItemsShouldReturnTrue() throws Exception {
        ConstructorIO constructor = new ConstructorIO(token, apiKey, true, null);
        ConstructorItem item = Utils.createProductItem();
        ConstructorItem[] items = {item};

        assertTrue("delete succeeds", constructor.deleteItems(items, "Products"));
    }

    @Test
    public void deleteItemsShouldReturnTrueWithAllParameters() throws Exception {
        ConstructorIO constructor = new ConstructorIO(token, apiKey, true, null);
        ConstructorItem item = Utils.createProductItem();
        ConstructorItem[] items = {item};

        assertTrue(
                "delete succeeds",
                constructor.deleteItems(items, "Products", true, "test@constructor.io"));
    }

    @Test
    public void retrieveItemsShouldReturnAResponse() throws Exception {
        ConstructorIO constructor = new ConstructorIO(token, apiKey, true, null);
        ItemsRequest request = new ItemsRequest();
        ItemsResponse response = constructor.retrieveItems(request);

        assertTrue("Total count is bigger than 1", response.getTotalCount() > 1);
        assertNotNull("Items exist", response.getItems());
    }

    @Test
    public void retrieveItemsWithAnIdShouldReturnAResponse() throws Exception {
        ConstructorIO constructor = new ConstructorIO(token, apiKey, true, null);
        ItemsRequest request = new ItemsRequest();
        request.setIds(Arrays.asList("10001"));
        ItemsResponse response = constructor.retrieveItems(request);

        assertTrue("Total count is bigger than or equal to 1", response.getTotalCount() >= 1);
        assertNotNull("Items exist", response.getItems());
    }

    @Test
    public void retrieveItemsWithMultipleIdsShouldReturnAResponse() throws Exception {
        ConstructorIO constructor = new ConstructorIO(token, apiKey, true, null);
        ItemsRequest request = new ItemsRequest();
        request.setIds(Arrays.asList("10001", "10002"));
        ItemsResponse response = constructor.retrieveItems(request);

        assertTrue("Total count is bigger than or equal to 2", response.getTotalCount() >= 2);
        assertNotNull("Items exist", response.getItems());
    }

    @Test
    public void retrieveItemsAsJsonShouldReturnAResponse() throws Exception {
        ConstructorIO constructor = new ConstructorIO(token, apiKey, true, null);
        ItemsRequest request = new ItemsRequest();
        String response = constructor.retrieveItemsAsJson(request);
        JSONObject jsonObj = new JSONObject(response);
        JSONArray itemsArray = jsonObj.getJSONArray("items");

        assertTrue("Total count is bigger than 1", jsonObj.getInt("total_count") > 1);
        assertNotNull("Items exist", itemsArray);
    }

    @Test
    public void retrieveItemsShouldDeserializeItemsCorrectly() throws Exception {
        ConstructorIO constructor = new ConstructorIO(token, apiKey, true, null);
        ItemsRequest request = new ItemsRequest();
        ItemsResponse response = constructor.retrieveItems(request);

        assertTrue("Total count is bigger than 1", response.getTotalCount() > 1);
        assertNotNull("Items exist", response.getItems());

        ConstructorItem item = response.getItems().get(0);

        assertNotNull("Item name deserialized properly", item.getName());
        assertNotNull("Facets deserialized properly", item.getFacets());
        assertNotNull("Metadata deserialized properly", item.getMetadata());
        assertNotNull("URL deserialized properly", item.getUrl());
    }
}
