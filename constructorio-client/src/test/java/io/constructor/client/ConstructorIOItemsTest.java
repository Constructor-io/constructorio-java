package io.constructor.client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import io.constructor.client.models.Item;
import io.constructor.client.models.ItemsResponse;

public class ConstructorIOItemsTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void addItemShouldReturnTrue() throws Exception {
      ConstructorIO constructor = new ConstructorIO("YSOxV00F0Kk2R0KnPQN8", "ZqXaOfXuBWD4s3XzCI1q", true, null);
      ConstructorItem item = Utils.createProductItem();
      assertTrue("addition succeeds", constructor.addItem(item, "Products"));
    }

    @Test
    public void addOrUpdateItemShouldReturnTrue() throws Exception {
        ConstructorIO constructor = new ConstructorIO("YSOxV00F0Kk2R0KnPQN8", "ZqXaOfXuBWD4s3XzCI1q", true, null);
        ConstructorItem item = Utils.createProductItem();
        assertTrue("upsert succeeds", constructor.addOrUpdateItem(item, "Products"));
    }

    @Test
    public void addBatchShouldReturnTrue() throws Exception {
        ConstructorIO constructor = new ConstructorIO("YSOxV00F0Kk2R0KnPQN8", "ZqXaOfXuBWD4s3XzCI1q", true, null);
        ConstructorItem[] items = {
            Utils.createProductItem(),
            Utils.createProductItem(),
            Utils.createProductItem()
        };
        assertTrue("batch addition succeeds", constructor.addItemBatch(items, "Products"));
    }

    @Test
    public void addOrUpdateBatchShouldReturnTrue() throws Exception {
      ConstructorIO constructor = new ConstructorIO("YSOxV00F0Kk2R0KnPQN8", "ZqXaOfXuBWD4s3XzCI1q", true, null);
      ConstructorItem[] items = {
        Utils.createProductItem(),
        Utils.createProductItem(),
        Utils.createProductItem()
      };
      assertTrue("batch upsert succeeds", constructor.addOrUpdateItemBatch(items, "Products"));
    }

    @Test
    public void modifyShouldReturnTrue() throws Exception {
        ConstructorIO constructor = new ConstructorIO("YSOxV00F0Kk2R0KnPQN8", "ZqXaOfXuBWD4s3XzCI1q", true, null);
        ConstructorItem itemOld = Utils.createProductItem();
        constructor.addItem(itemOld, "Products");
        Thread.sleep(2000);

        ConstructorItem itemNew = new ConstructorItem(itemOld.getItemName());
        itemNew.setUrl(itemOld.getUrl());
        itemNew.setSuggestedScore(1337);
        assertTrue("modify succeeds", constructor.modifyItem(itemOld, "Products", itemOld.getItemName()));
    }

    @Test
    public void removeShouldReturnTrue() throws Exception {
        ConstructorIO constructor = new ConstructorIO("YSOxV00F0Kk2R0KnPQN8", "ZqXaOfXuBWD4s3XzCI1q", true, null);
        ConstructorItem item = Utils.createProductItem();
        constructor.addItem(item, "Products");
        Thread.sleep(2000);

        assertTrue("remove succeeds", constructor.removeItem(item, "Products"));
    }

    @Test
    public void removeBatchShouldReturnTrue() throws Exception {
      ConstructorIO constructor = new ConstructorIO("YSOxV00F0Kk2R0KnPQN8", "ZqXaOfXuBWD4s3XzCI1q", true, null);
      ConstructorItem[] items = {
        Utils.createProductItem(),
        Utils.createProductItem(),
        Utils.createProductItem()
      };
      assertTrue("batch removal succeeds", constructor.removeItemBatch(items, "Products"));
    }

    @Test
    public void getItemShouldReturnItem() throws Exception {
        ConstructorIO constructor = new ConstructorIO("YSOxV00F0Kk2R0KnPQN8", "ZqXaOfXuBWD4s3XzCI1q", true, null);
        Item item = constructor.getItem("893092", "Products");
        Map<String, Object> actualFacets = new HashMap<String, Object>();
        actualFacets.put("Brand", "ROTCURTSNOC");
        actualFacets.put("Colors", Arrays.asList("red", "black", "purple"));
        Map<String, String> actualMetadata = new HashMap<String, String>();
        actualMetadata.put("price", "$50.00");

        assertEquals("ROTRUCTSNOC Rainy Day Coat", item.getName());
        assertEquals("893092", item.getId());
        assertEquals("Keep yourself dry and cozy on rainy days with this stylish rain coat that can complement any outfit.", item.getDescription());
        assertEquals("https://constructor.io/pdp/893092", item.getUrl());
        assertEquals("https://constructor.io/images/893092.jpg", item.getImageUrl());
        assertEquals(Arrays.asList("jacket", "coat", "rain"), item.getKeywords());
        assertEquals(actualFacets, item.getFacets());
        assertEquals(actualMetadata, item.getMetadata());
    }

    @Test
    public void getItemsShouldReturnItems() throws Exception {
        ConstructorIO constructor = new ConstructorIO("YSOxV00F0Kk2R0KnPQN8", "ZqXaOfXuBWD4s3XzCI1q", true, null);
        ItemsResponse items = constructor.getItems("Products", 2, 1);
        Item item1 = items.getItems().get(0);
        Item item2 = items.getItems().get(1);

        assertEquals(items.getItems().size(), 2);
        assertEquals("power_drill 1", item1.getName());
        assertEquals("/condition/1", item1.getUrl());
        assertEquals("value1", item1.getMetadata().get("key1"));
        assertEquals("value2", item1.getMetadata().get("key2"));
        assertEquals(Integer.valueOf(-1), item1.getSuggestedScore());
        
        assertEquals("power_drill 2", item2.getName());
        assertEquals("/condition/2", item2.getUrl());
        assertEquals("value3", item2.getMetadata().get("key3"));
        assertEquals("value4", item2.getMetadata().get("key4"));
        assertEquals(Integer.valueOf(-1), item2.getSuggestedScore());
    }
}