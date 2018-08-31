package io.constructor.client;

import static org.junit.Assert.assertTrue;

import java.util.*;

import org.junit.Test;

public class ConstructorIO_Add_Test {

  public String getRandString() {
    return UUID.randomUUID().toString().replaceAll("[\\s\\-()]", "");
  }

  @Test
  public void addNoParamsShouldReturn() throws Exception {
      ConstructorIO constructor = new ConstructorIO("YSOxV00F0Kk2R0KnPQN8", "ZqXaOfXuBWD4s3XzCI1q", true, null);
      String randStr = this.getRandString();
      assertTrue("addition without params succeeds", constructor.add(randStr, "Search Suggestions"));
  }

  @Test
  public void addWithParamsShouldReturn() throws Exception {
      ConstructorIO constructor = new ConstructorIO("YSOxV00F0Kk2R0KnPQN8", "ZqXaOfXuBWD4s3XzCI1q", true, null);
      String randStr = this.getRandString();
      HashMap<String, Object> params = new HashMap<String, Object>();
      params.put("suggested_score", 1337);
      assertTrue("addition with params succeeds", constructor.add(randStr, "Search Suggestions", params));
  }

  @Test
  public void addConstructorItemShouldReturn() throws Exception {
      ConstructorIO constructor = new ConstructorIO("YSOxV00F0Kk2R0KnPQN8", "ZqXaOfXuBWD4s3XzCI1q", true, null);
      String randStr = this.getRandString();
      assertTrue("addition of a constructor item succeeds", constructor.add(new ConstructorItem(randStr), "Search Suggestions"));
  }

  @Test
  public void addConstructorItemWithHashMapParamsShouldReturn() throws Exception {
      ConstructorIO constructor = new ConstructorIO("YSOxV00F0Kk2R0KnPQN8", "ZqXaOfXuBWD4s3XzCI1q", true, null);
      String randStr = this.getRandString();
      HashMap<String, Object> params = new HashMap<String, Object>();
      params.put("suggested_score", 1337);
      assertTrue("addition of a constructor item with params as a HashMap succeeds", constructor.add(new ConstructorItem(randStr, params), "Search Suggestions"));
  }

  @Test
  public void addConstructorItemWithFunctionParamsShouldReturn() throws Exception {
      ConstructorIO constructor = new ConstructorIO("YSOxV00F0Kk2R0KnPQN8", "ZqXaOfXuBWD4s3XzCI1q", true, null);
      String randStr = this.getRandString();
      assertTrue("addition of a constructor item with params set using the functions provided succeeds", constructor.add(new ConstructorItem(randStr).setSuggestedScore(100), "Search Suggestions"));
  }

  @Test
  public void addConstructorItemWithEdgeCasesShouldReturn() throws Exception {
      ConstructorIO constructor = new ConstructorIO("YSOxV00F0Kk2R0KnPQN8", "ZqXaOfXuBWD4s3XzCI1q", true, null);
      String randStr = this.getRandString();
      HashMap<String, Object> params = new HashMap<String, Object>();
      params.put("id", "test");
      assertTrue("addition of a constructor item with some edge cases succeeds", constructor.add(new ConstructorItem("")
                      .setSuggestedScore(1009)
                      .setSuggestedScore(0)
                      .setSuggestedScore(100)
                      .setKeywords("hello", "world")
                      .put("keywords", null)
                      .put("item_name", randStr)
              , "Search Suggestions"));
  }

  @Test
  public void addOrUpdateShouldReturn() throws Exception {
      ConstructorIO constructor = new ConstructorIO("YSOxV00F0Kk2R0KnPQN8", "ZqXaOfXuBWD4s3XzCI1q", true, null);
      String randStr = this.getRandString();
      assertTrue("upsert succeeds", constructor.addOrUpdate(randStr, "Search Suggestions"));
  }
} 
