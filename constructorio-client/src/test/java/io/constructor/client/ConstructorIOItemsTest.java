package io.constructor.client;

import static org.junit.Assert.assertTrue;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

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
}
