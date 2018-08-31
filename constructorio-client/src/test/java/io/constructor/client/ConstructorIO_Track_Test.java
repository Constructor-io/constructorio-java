package io.constructor.client;

import static org.junit.Assert.assertTrue;

import java.util.*;

import org.junit.Test;

public class ConstructorIO_Track_Test {

  public String getRandString() {
    return UUID.randomUUID().toString().replaceAll("[\\s\\-()]", "");
  }

  @Test
  public void trackConversionNoParamsShouldReturn() throws Exception {
      ConstructorIO constructor = new ConstructorIO("YSOxV00F0Kk2R0KnPQN8", "ZqXaOfXuBWD4s3XzCI1q", true, null);
      assertTrue("conversion without params succeeds", constructor.trackConversion("Stanley_Steamer", "Search Suggestions"));
  }

  @Test
  public void trackConversionWithParamsShouldReturn() throws Exception {
      ConstructorIO constructor = new ConstructorIO("YSOxV00F0Kk2R0KnPQN8", "ZqXaOfXuBWD4s3XzCI1q", true, null);
      String randStr = this.getRandString();
      constructor.add(randStr, "Search Suggestions");
      Thread.sleep(2000);
      HashMap<String, Object> params = new HashMap<String, Object>();
      params.put("item", randStr);
      assertTrue("conversion with params succeeds", constructor.trackConversion("Stanley_Steamer", "Search Suggestions", params));
  }

  @Test
  public void trackSearchNoParamsShouldReturn() throws Exception {
      ConstructorIO constructor = new ConstructorIO("YSOxV00F0Kk2R0KnPQN8", "ZqXaOfXuBWD4s3XzCI1q", true, null);
      assertTrue("search without params succeeds", constructor.trackSearch("Stanley_Steamer"));
  }

  @Test
  public void trackSearchWithParamsShouldReturn() throws Exception {
      ConstructorIO constructor = new ConstructorIO("YSOxV00F0Kk2R0KnPQN8", "ZqXaOfXuBWD4s3XzCI1q", true, null);
      HashMap<String, Object> params = new HashMap<String, Object>();
      params.put("num_results", 1337);
      assertTrue("search with params succeeds", constructor.trackSearch("Stanley_Steamer", params));
  }

  @Test
  public void trackClickThroughNoParamsShouldReturn() throws Exception {
      ConstructorIO constructor = new ConstructorIO("YSOxV00F0Kk2R0KnPQN8", "ZqXaOfXuBWD4s3XzCI1q", true, null);
      assertTrue("search without params succeeds", constructor.trackClickThrough("Stanley_Steamer", "Search Suggestions"));
  }

  @Test
  public void trackClickThroughWithParamsShouldReturn() throws Exception {
      ConstructorIO constructor = new ConstructorIO("YSOxV00F0Kk2R0KnPQN8", "ZqXaOfXuBWD4s3XzCI1q", true, null);
      String randStr = this.getRandString();
      constructor.add(randStr, "Search Suggestions");
      Thread.sleep(2000);
      HashMap<String, Object> params = new HashMap<String, Object>();
      params.put("item", randStr);
      assertTrue("click-through with params succeeds", constructor.trackClickThrough("Stanley_Steamer", "Search Suggestions", params));
  }
} 
