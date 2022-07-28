package io.constructor.client;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import io.constructor.client.models.VariationsResponse;

public class ConstructorIOVariationsTest {
  
    private String token = System.getenv("TEST_API_TOKEN");
    private String apiKey = System.getenv("TEST_API_KEY");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void addOrUpdateVariationsShouldReturnTrue() throws Exception {
      ConstructorIO constructor = new ConstructorIO(token, apiKey, true, null);
      ConstructorItem item = Utils.createProductItem();
      ConstructorItem[] items = { item };
      constructor.addOrUpdateItems(items);

      Thread.sleep(2000);

      ConstructorVariation[] variations = {
        Utils.createProductVariation(item.getId()),
        Utils.createProductVariation(item.getId()),
        Utils.createProductVariation(item.getId())
      };
      assertTrue("batch upsert succeeds", constructor.addOrUpdateVariations(variations, "Products"));
    }

    @Test
    public void modifyVariationsShouldReturnTrue() throws Exception {
        ConstructorIO constructor = new ConstructorIO(token, apiKey, true, null);
        ConstructorItem item = Utils.createProductItem();
        ConstructorItem[] items = { item };
        constructor.addOrUpdateItems(items);
        Thread.sleep(2000);

        ConstructorVariation[] variationsOld = { Utils.createProductVariation(item.getId()) };
        constructor.addOrUpdateVariations(variationsOld, "Products");
        Thread.sleep(2000);

        ConstructorVariation variationOld = variationsOld[0];
        ConstructorVariation variationNew = new ConstructorVariation(variationOld.getId(), variationOld.getItemId());
        variationNew.setUrl(variationOld.getUrl());
        variationNew.setSuggestedScore((float) 1337.00);
        ConstructorVariation[] variationsNew = { variationNew };
        assertTrue("modify succeeds", constructor.modifyVariations(variationsNew, "Products"));
    }

    @Test
    public void removeVariationsShouldReturnTrue() throws Exception {
      ConstructorIO constructor = new ConstructorIO(token, apiKey, true, null);
      ConstructorVariation[] variations = {
        Utils.createProductVariation("Random-ID"),
      };

      assertTrue("removal succeeds", constructor.removeVariations(variations, "Products"));
    }

    @Test
    public void getVariationsShouldReturnAResponse() throws Exception {
      ConstructorIO constructor = new ConstructorIO(token, apiKey, true, null);
      VariationsRequest request = new VariationsRequest();
      VariationsResponse response = constructor.getVariations(request);

      assertTrue("Total count is bigger than 1", response.getTotalCount() > 1);
      assertNotNull("Variations exist", response.getVariations());
    }

    @Test
    public void getVariationsWithAnIdShouldReturnAResponse() throws Exception {
      ConstructorIO constructor = new ConstructorIO(token, apiKey, true, null);
      VariationsRequest request = new VariationsRequest();
      request.setIds(Arrays.asList("20001"));
      VariationsResponse response = constructor.getVariations(request);

      assertTrue("Total count is bigger than or equal to 1", response.getTotalCount() >= 1);
      assertNotNull("Variations exist", response.getVariations());
    }

    @Test
    public void getVariationsWithAnItemIdShouldReturnAResponse() throws Exception {
      ConstructorIO constructor = new ConstructorIO(token, apiKey, true, null);
      VariationsRequest request = new VariationsRequest();
      request.setItemId("10001");
      VariationsResponse response = constructor.getVariations(request);

      assertTrue("Total count is bigger than or equal to 1", response.getTotalCount() >= 1);
      assertNotNull("Variations exist", response.getVariations());
    }

    @Test
    public void getVariationsWithMultipleIdsShouldReturnAResponse() throws Exception {
      ConstructorIO constructor = new ConstructorIO(token, apiKey, true, null);
      VariationsRequest request = new VariationsRequest();
      request.setIds(Arrays.asList("20001", "M0E20000000E2ZK"));
      VariationsResponse response = constructor.getVariations(request);

      assertTrue("Total count is bigger than or equal to 2", response.getTotalCount() >= 2);
      assertNotNull("Variations exist", response.getVariations());
    }
    
    @Test
    public void getVariationsAsJsonShouldReturnAResponse() throws Exception {
      ConstructorIO constructor = new ConstructorIO(token, apiKey, true, null);
      VariationsRequest request = new VariationsRequest();
      String response = constructor.getVariationsAsJson(request);
      JSONObject jsonObj = new JSONObject(response);
      JSONArray variationsArray = jsonObj.getJSONArray("variations");


      assertTrue("Total count is bigger than 1", jsonObj.getInt("total_count") > 1);
      assertNotNull("Variations exist", variationsArray);
    }
}
