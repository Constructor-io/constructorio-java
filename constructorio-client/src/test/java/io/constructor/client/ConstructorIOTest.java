package io.constructor.client;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import org.junit.BeforeClass;
import org.junit.AfterClass;

import java.util.*;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ConstructorIOTest {

    public String getRandString() {
        return UUID.randomUUID().toString().replaceAll("[\\s\\-()]", "");
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @BeforeClass
    public static void setup() throws Exception {
      ConstructorIO constructor = new ConstructorIO("YSOxV00F0Kk2R0KnPQN8", "ZqXaOfXuBWD4s3XzCI1q", true, null);
      ConstructorItem item = new ConstructorItem("Stanley_Steamer", "Search Suggestions");
      item.setId("Stanley1");
      constructor.removeItem(item);
      Thread.sleep(2000);
    }

    @Test
    public void newShouldSetApiToken() throws Exception {
        ConstructorIO constructor = new ConstructorIO("boinkaToken", "doinkaKey", true, null);
        assertEquals("api token should be set", constructor.apiToken, "boinkaToken");
    }

    @Test
    public void newShouldSetApiKey() throws Exception {
        ConstructorIO constructor = new ConstructorIO("boinkaToken", "doinkaKey", true, null);
        assertEquals("api key should be set", constructor.apiKey, "doinkaKey");
    }

    @Test
    public void newShouldSetProtocol() throws Exception {
        ConstructorIO constructor = new ConstructorIO("boinkaToken", "doinkaKey", false, null);
        assertEquals("protocol should be set", constructor.protocol, "http");
    }

    @Test
    public void newShouldSetHostname() throws Exception {
        ConstructorIO constructor = new ConstructorIO("boinkaToken", "doinkaKey", false, "com.cnstrc.ac");
        assertEquals("host should be set", constructor.host, "com.cnstrc.ac");
    }

    @Test
    public void newShouldSetHostnameDefault() throws Exception {
        ConstructorIO constructor = new ConstructorIO("boinkaToken", "doinkaKey", false, null);
        assertEquals("host should be set to default", constructor.host, "ac.cnstrc.com");
    }

    @Test
    public void serializeParamsShouldEncodeParams() throws Exception {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("foo", "bar");
        params.put("bar", "baz");
        String serializedParams = ConstructorIO.serializeParams(params);
        assertTrue("serializes params should serialize params", serializedParams.equals("foo=bar&bar=baz") || serializedParams.equals("bar=baz&foo=bar"));
    }

    @Test
    public void makeUrlShouldReturnUrl() throws Exception {
        ConstructorIO constructor = new ConstructorIO("boinka", "doinka", true, null);
        String generatedUrl = constructor.makeUrl("v1/test");
        assertEquals("make url should make urls", generatedUrl, "https://ac.cnstrc.com/v1/test?key=doinka");
    }

    @Test
    public void verifyShouldReturnTrueWithValidKeyTokenPair() throws Exception {
        ConstructorIO constructor = new ConstructorIO("YSOxV00F0Kk2R0KnPQN8", "ZqXaOfXuBWD4s3XzCI1q", false, null);
        assertEquals("verify should return true for testing key/pair", constructor.verify(), true);
    }

    @Test
    public void addItemShouldReturnTrue() throws Exception {
      ConstructorIO constructor = new ConstructorIO("YSOxV00F0Kk2R0KnPQN8", "ZqXaOfXuBWD4s3XzCI1q", true, null);
      ConstructorItem item = new ConstructorItem(this.getRandString(), "Search Suggestions");
      assertTrue("addition succeeds", constructor.addItem(item));
    }

    @Test
    public void addOrUpdateItemShouldReturnTrue() throws Exception {
        ConstructorIO constructor = new ConstructorIO("YSOxV00F0Kk2R0KnPQN8", "ZqXaOfXuBWD4s3XzCI1q", true, null);
        ConstructorItem item = new ConstructorItem(this.getRandString(), "Search Suggestions");
        assertTrue("upsert succeeds", constructor.addOrUpdateItem(item));
    }

    @Test
    public void addBatchShouldReturnTrue() throws Exception {
        ConstructorIO constructor = new ConstructorIO("YSOxV00F0Kk2R0KnPQN8", "ZqXaOfXuBWD4s3XzCI1q", true, null);
        ConstructorItem[] items = {
            new ConstructorItem(this.getRandString(), "Search Suggestions"),
            new ConstructorItem(this.getRandString(), "Search Suggestions"),
            new ConstructorItem(this.getRandString(), "Search Suggestions")
        };
        assertTrue("batch addition succeeds", constructor.addItemBatch(items, "Search Suggestions"));
    }
  
    @Test
    public void addOrUpdateBatchShouldReturnTrue() throws Exception {
      ConstructorIO constructor = new ConstructorIO("YSOxV00F0Kk2R0KnPQN8", "ZqXaOfXuBWD4s3XzCI1q", true, null);
      ConstructorItem[] items = {
          new ConstructorItem(this.getRandString(), "Search Suggestions"),
          new ConstructorItem(this.getRandString(), "Search Suggestions"),
          new ConstructorItem(this.getRandString(), "Search Suggestions")
      };
      assertTrue("batch upsert succeeds", constructor.addOrUpdateItemBatch(items, "Search Suggestions"));
    }

    @Test
    public void modifyShouldReturnTrue() throws Exception {
        ConstructorIO constructor = new ConstructorIO("YSOxV00F0Kk2R0KnPQN8", "ZqXaOfXuBWD4s3XzCI1q", true, null);
        String itemName = this.getRandString();
        ConstructorItem itemOld = new ConstructorItem(itemName, "Search Suggestions");
        ConstructorItem itemNew = new ConstructorItem(itemName, "Search Suggestions");
        itemNew.setSuggestedScore(1337);
        constructor.addItem(itemOld);
        Thread.sleep(2000);
        assertTrue("modify succeeds", constructor.modifyItem(itemOld, itemNew));
    }

    @Test
    public void removeShouldReturnTrue() throws Exception {
        ConstructorIO constructor = new ConstructorIO("YSOxV00F0Kk2R0KnPQN8", "ZqXaOfXuBWD4s3XzCI1q", true, null);
        ConstructorItem item = new ConstructorItem(this.getRandString(), "Search Suggestions");
        constructor.addItem(item);
        Thread.sleep(2000);
        assertTrue("remove succeeds", constructor.removeItem(item));
    }

    public void removeBatchShouldReturnTrue() throws Exception {
      ConstructorIO constructor = new ConstructorIO("YSOxV00F0Kk2R0KnPQN8", "ZqXaOfXuBWD4s3XzCI1q", true, null);
      ConstructorItem[] items = {
          new ConstructorItem(this.getRandString(), "Search Suggestions"),
          new ConstructorItem(this.getRandString(), "Search Suggestions"),
          new ConstructorItem(this.getRandString(), "Search Suggestions")
      };
      assertTrue("batch removal succeeds", constructor.removeItemBatch(items, "Search Suggestions"));
    }
  
    @Test
    public void trackConversionNoParamsShouldReturn() throws Exception {
        ConstructorIO constructor = new ConstructorIO("YSOxV00F0Kk2R0KnPQN8", "ZqXaOfXuBWD4s3XzCI1q", true, null);
        assertTrue("conversion without params succeeds", constructor.trackConversion("Stanley_Steamer", "Search Suggestions", "Stanley1", "$1.99"));
    }
  
    @Test
    public void trackSearchNoParamsShouldReturn() throws Exception {
        ConstructorIO constructor = new ConstructorIO("YSOxV00F0Kk2R0KnPQN8", "ZqXaOfXuBWD4s3XzCI1q", true, null);
        assertTrue("search without params succeeds", constructor.trackSearch("Stanley_Steamer", 22));
    }
  
    @Test
    public void trackClickThroughNoParamsShouldReturn() throws Exception {
        ConstructorIO constructor = new ConstructorIO("YSOxV00F0Kk2R0KnPQN8", "ZqXaOfXuBWD4s3XzCI1q", true, null);
        assertTrue("search without params succeeds", constructor.trackClickThrough("Stanley_Steamer", "Search Suggestions", "Stanley1"));
    }
} 