package io.constructor.client;

import static org.junit.Assert.assertTrue;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

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
}
