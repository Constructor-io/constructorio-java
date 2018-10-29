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
    public void getItemAsJSONShouldReturnJSON() throws Exception {
        ConstructorIO constructor = new ConstructorIO("YSOxV00F0Kk2R0KnPQN8", "ZqXaOfXuBWD4s3XzCI1q", true, null);
        String actualJSON = "{\"description\": \"Keep yourself dry and cozy on rainy days with this stylish rain coat that can complement any outfit.\", \"facets\": {\"Brand\": \"ROTCURTSNOC\", \"Colors\": [\"red\", \"black\", \"purple\"]}, \"id\": \"893092\", \"image_url\": \"https://constructor.io/images/893092.jpg\", \"keywords\": [\"jacket\", \"coat\", \"rain\"], \"metadata\": {\"price\": \"$50.00\"}, \"name\": \"ROTRUCTSNOC Rainy Day Coat\", \"suggested_score\": 52000, \"url\": \"https://constructor.io/pdp/893092\"}";
        String json = constructor.getItemAsJSON("893092", "Products");

        assertEquals("get items as json returns json", actualJSON, json);
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

        assertEquals(item.getName(), "ROTRUCTSNOC Rainy Day Coat");
        assertEquals(item.getId(), "893092");
        assertEquals(item.getDescription(), "Keep yourself dry and cozy on rainy days with this stylish rain coat that can complement any outfit.");
        assertEquals(item.getUrl(), "https://constructor.io/pdp/893092");
        assertEquals(item.getImageUrl(), "https://constructor.io/images/893092.jpg");
        assertEquals(item.getKeywords(), Arrays.asList("jacket", "coat", "rain"));
        assertEquals(item.getFacets(), actualFacets);
        assertEquals(item.getMetadata(), actualMetadata);
    }
}
