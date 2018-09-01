package io.constructor.client;

import static org.junit.Assert.assertTrue;

import java.util.*;

import org.junit.Test;
import org.junit.BeforeClass;
import org.junit.AfterClass;

public class ConstructorIO_Track_Test {

  public String getRandString() {
    return UUID.randomUUID().toString().replaceAll("[\\s\\-()]", "");
  }

  @BeforeClass
  public static void setup() throws Exception {
    ConstructorIO constructor = new ConstructorIO("YSOxV00F0Kk2R0KnPQN8", "ZqXaOfXuBWD4s3XzCI1q", true, null);
    ConstructorItem item = new ConstructorItem("Stanley_Steamer", "Search Suggestions");
    item.setId("Stanley1");
    constructor.addItem(item);
    Thread.sleep(2000);
  }

  @AfterClass
  public static void teardown() throws Exception {
    ConstructorIO constructor = new ConstructorIO("YSOxV00F0Kk2R0KnPQN8", "ZqXaOfXuBWD4s3XzCI1q", true, null);
    ConstructorItem item = new ConstructorItem("Stanley_Steamer", "Search Suggestions");
    constructor.removeItem(item);
    Thread.sleep(2000);
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
