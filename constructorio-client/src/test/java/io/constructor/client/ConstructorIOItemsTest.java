package io.constructor.client;

import static org.junit.Assert.assertTrue;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ConstructorIOItemsTest {
  
    private String token = System.getenv("TEST_API_TOKEN");
    private String apiKey = System.getenv("TEST_API_KEY");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void addOrUpdateItemsShouldReturnTrue() throws Exception {
      ConstructorIO constructor = new ConstructorIO(token, apiKey, true, null);
      ConstructorItem[] items = {
        Utils.createProductItem(),
        Utils.createProductItem(),
        Utils.createProductItem()
      };
      assertTrue("batch upsert succeeds", constructor.addOrUpdateItems(items, "Products"));
    }

    @Test
    public void modifyItemsShouldReturnTrue() throws Exception {
        ConstructorIO constructor = new ConstructorIO(token, apiKey, true, null);
        ConstructorItem[] itemsOld = { Utils.createProductItem() };
        constructor.addOrUpdateItems(itemsOld, "Products");
        Thread.sleep(2000);

        ConstructorItem itemOld = itemsOld[0];
        ConstructorItem itemNew = new ConstructorItem(itemOld.getId());
        itemNew.setUrl(itemOld.getUrl());
        itemNew.setSuggestedScore((float) 1337.00);
        ConstructorItem[] itemsNew = { itemNew };
        assertTrue("modify succeeds", constructor.modifyItems(itemsNew, "Products"));
    }

    @Test
    public void removeBatchShouldReturnTrue() throws Exception {
      ConstructorIO constructor = new ConstructorIO(token, apiKey, true, null);
      ConstructorItem[] items = {
        Utils.createProductItem(),
        Utils.createProductItem(),
        Utils.createProductItem()
      };
      assertTrue("batch removal succeeds", constructor.removeItems(items, "Products"));
    }
}
