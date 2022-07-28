package io.constructor.client;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import io.constructor.client.models.ItemsResponse;

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
    public void removeItemsShouldReturnTrue() throws Exception {
      ConstructorIO constructor = new ConstructorIO(token, apiKey, true, null);
      ConstructorItem item = Utils.createProductItem();
      ConstructorItem[] items = { item };

      assertTrue("removal succeeds", constructor.removeItems(items, "Products"));
    }

    @Test
    public void getItemsShouldReturnAResponse() throws Exception {
      ConstructorIO constructor = new ConstructorIO(token, apiKey, true, null);
      ItemsRequest request = new ItemsRequest();
      ItemsResponse response = constructor.getItems(request);

      assertTrue("Total count is bigger than 1", response.getTotalCount() > 1);
      assertNotNull("Items exist", response.getItems());
    }

    @Test
    public void getItemsWithAnIdShouldReturnAResponse() throws Exception {
      ConstructorIO constructor = new ConstructorIO(token, apiKey, true, null);
      ItemsRequest request = new ItemsRequest();
      request.setIds(Arrays.asList("10001"));
      ItemsResponse response = constructor.getItems(request);

      assertTrue("Total count is bigger than or equal to 1", response.getTotalCount() >= 1);
      assertNotNull("Items exist", response.getItems());
    }

    @Test
    public void getItemsWithMultipleIdsShouldReturnAResponse() throws Exception {
      ConstructorIO constructor = new ConstructorIO(token, apiKey, true, null);
      ItemsRequest request = new ItemsRequest();
      request.setIds(Arrays.asList("10001", "10002"));
      ItemsResponse response = constructor.getItems(request);

      assertTrue("Total count is bigger than or equal to 2", response.getTotalCount() >= 2);
      assertNotNull("Items exist", response.getItems());
    }
    
    @Test
    public void getItemsAsJsonShouldReturnAResponse() throws Exception {
      ConstructorIO constructor = new ConstructorIO(token, apiKey, true, null);
      ItemsRequest request = new ItemsRequest();
      String response = constructor.getItemsAsJson(request);
      JSONObject jsonObj = new JSONObject(response);
      JSONArray itemsArray = jsonObj.getJSONArray("items");


      assertTrue("Total count is bigger than 1", jsonObj.getInt("total_count") > 1);
      assertNotNull("Items exist", itemsArray);
    }
}
