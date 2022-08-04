package io.constructor.client;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.AfterClass;

import io.constructor.client.models.VariationsResponse;

public class ConstructorIOVariationsTest {
  
    private static String token = System.getenv("TEST_API_TOKEN");
    private static String apiKey = System.getenv("TEST_API_KEY");
    private static ArrayList<ConstructorVariation> variationsToCleanup = new ArrayList<ConstructorVariation>();

    private void addVariationsToCleanUpArray(ConstructorVariation[] variations) {
      for (ConstructorVariation constructorVariation : variations) {
        variationsToCleanup.add(constructorVariation);
      }
    }

    @AfterClass
    public static void cleanupItems() throws ConstructorException {
      ConstructorIO constructor = new ConstructorIO(token, apiKey, true, null);
      
      constructor.deleteVariations(variationsToCleanup.toArray(new ConstructorVariation[variationsToCleanup.size()]), "Products");
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void createOrReplaceVariationsShouldReturnTrue() throws Exception {
      ConstructorIO constructor = new ConstructorIO(token, apiKey, true, null);
      ConstructorVariation[] variations = {
        Utils.createProductVariation("random-id"),
        Utils.createProductVariation("random-id"),
        Utils.createProductVariation("random-id"),
      };

      assertTrue("create or replace succeeds", constructor.createOrReplaceVariations(variations, "Products"));
      addVariationsToCleanUpArray(variations);
    }

    @Test
    public void createOrReplaceVariationsShouldReturnTrueWithAllParameters() throws Exception {
      ConstructorIO constructor = new ConstructorIO(token, apiKey, true, null);
      ConstructorVariation[] variations = {
        Utils.createProductVariation("random-id"),
        Utils.createProductVariation("random-id"),
        Utils.createProductVariation("random-id"),
      };

      assertTrue("create or replace succeeds", constructor.createOrReplaceVariations(variations, "Products", true, "test@constructor.io"));
      addVariationsToCleanUpArray(variations);
    }

    @Test
    public void updateVariationsShouldReturnTrue() throws Exception {
        ConstructorIO constructor = new ConstructorIO(token, apiKey, true, null);
        ConstructorVariation[] variationsOld = { Utils.createProductVariation("random-id") };

        constructor.createOrReplaceVariations(variationsOld, "Products");

        Thread.sleep(2000);

        ConstructorVariation variationOld = variationsOld[0];
        ConstructorVariation variationNew = new ConstructorVariation(variationOld.getId(), variationOld.getItemId());
        variationNew.setUrl(variationOld.getUrl());
        variationNew.setSuggestedScore((float) 1337.00);
        ConstructorVariation[] variationsNew = { variationNew };

        assertTrue("update succeeds", constructor.updateVariations(variationsNew, "Products"));
        addVariationsToCleanUpArray(variationsNew);
    }

    @Test
    public void updateVariationsShouldReturnTrueWithAllParameters() throws Exception {
        ConstructorIO constructor = new ConstructorIO(token, apiKey, true, null);
        ConstructorVariation[] variationsOld = { Utils.createProductVariation("random-id") };

        constructor.createOrReplaceVariations(variationsOld, "Products");

        Thread.sleep(2000);

        ConstructorVariation variationOld = variationsOld[0];
        ConstructorVariation variationNew = new ConstructorVariation(variationOld.getId(), variationOld.getItemId());
        variationNew.setUrl(variationOld.getUrl());
        variationNew.setSuggestedScore((float) 1337.00);
        ConstructorVariation[] variationsNew = { variationNew };

        assertTrue("update succeeds", constructor.updateVariations(variationsNew, "Products", true, "test@constructor.io"));
        addVariationsToCleanUpArray(variationsNew);
    }

    @Test
    public void updateVariationsWithoutAnItemIdShouldReturnTrue() throws Exception {
        ConstructorIO constructor = new ConstructorIO(token, apiKey, true, null);
        ConstructorVariation[] variationsOld = { Utils.createProductVariation("random-id") };

        constructor.createOrReplaceVariations(variationsOld, "Products");

        Thread.sleep(2000);

        ConstructorVariation variationOld = variationsOld[0];
        ConstructorVariation variationNew = new ConstructorVariation(variationOld.getId());
        variationNew.setUrl(variationOld.getUrl());
        variationNew.setSuggestedScore((float) 1337.00);
        ConstructorVariation[] variationsNew = { variationNew };

        assertTrue("update succeeds", constructor.updateVariations(variationsNew, "Products"));
        addVariationsToCleanUpArray(variationsNew);
    }

    @Test
    public void removeVariationsShouldReturnTrue() throws Exception {
      ConstructorIO constructor = new ConstructorIO(token, apiKey, true, null);
      ConstructorVariation[] variations = {
        Utils.createProductVariation("Random-ID"),
      };

      assertTrue("delete succeeds", constructor.deleteVariations(variations, "Products"));
    }

    @Test
    public void removeVariationsShouldReturnTrueWithAllParameters() throws Exception {
      ConstructorIO constructor = new ConstructorIO(token, apiKey, true, null);
      ConstructorVariation[] variations = {
        Utils.createProductVariation("Random-ID"),
      };

      assertTrue("delete succeeds", constructor.deleteVariations(variations, "Products", true, "test@constructor.io"));
    }

    @Test
    public void retrieveVariationsShouldReturnAResponse() throws Exception {
      ConstructorIO constructor = new ConstructorIO(token, apiKey, true, null);
      VariationsRequest request = new VariationsRequest();
      VariationsResponse response = constructor.retrieveVariations(request);

      assertTrue("Total count is bigger than 1", response.getTotalCount() > 1);
      assertNotNull("Variations exist", response.getVariations());
    }

    @Test
    public void retrieveVariationsWithAnIdShouldReturnAResponse() throws Exception {
      ConstructorIO constructor = new ConstructorIO(token, apiKey, true, null);
      VariationsRequest request = new VariationsRequest();
      request.setIds(Arrays.asList("20001"));
      VariationsResponse response = constructor.retrieveVariations(request);

      assertTrue("Total count is bigger than or equal to 1", response.getTotalCount() >= 1);
      assertNotNull("Variations exist", response.getVariations());
    }

    @Test
    public void retrieveVariationsWithAnItemIdShouldReturnAResponse() throws Exception {
      ConstructorIO constructor = new ConstructorIO(token, apiKey, true, null);
      VariationsRequest request = new VariationsRequest();
      request.setItemId("10001");
      VariationsResponse response = constructor.retrieveVariations(request);

      assertTrue("Total count is bigger than or equal to 1", response.getTotalCount() >= 1);
      assertNotNull("Variations exist", response.getVariations());
    }

    @Test
    public void retrieveVariationsWithMultipleIdsShouldReturnAResponse() throws Exception {
      ConstructorIO constructor = new ConstructorIO(token, apiKey, true, null);
      VariationsRequest request = new VariationsRequest();
      request.setIds(Arrays.asList("20001", "M0E20000000E2ZK"));
      VariationsResponse response = constructor.retrieveVariations(request);

      assertTrue("Total count is bigger than or equal to 2", response.getTotalCount() >= 2);
      assertNotNull("Variations exist", response.getVariations());
    }
    
    @Test
    public void retrieveVariationsAsJsonShouldReturnAResponse() throws Exception {
      ConstructorIO constructor = new ConstructorIO(token, apiKey, true, null);
      VariationsRequest request = new VariationsRequest();
      String response = constructor.retrieveVariationsAsJson(request);
      JSONObject jsonObj = new JSONObject(response);
      JSONArray variationsArray = jsonObj.getJSONArray("variations");


      assertTrue("Total count is bigger than 1", jsonObj.getInt("total_count") > 1);
      assertNotNull("Variations exist", variationsArray);
    }
}
