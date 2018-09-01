package io.constructor.client;

import static org.junit.Assert.assertTrue;

import java.util.*;

import org.junit.Test;

public class ConstructorIO_Add_Test {

  public String getRandString() {
    return UUID.randomUUID().toString().replaceAll("[\\s\\-()]", "");
  }


  @Test
  public void addItemShouldReturn() throws Exception {
    ConstructorIO constructor = new ConstructorIO("YSOxV00F0Kk2R0KnPQN8", "ZqXaOfXuBWD4s3XzCI1q", true, null);
    ConstructorItem item = new ConstructorItem(this.getRandString(), "Search Suggestions");
    assertTrue("addition succeeds", constructor.addItem(item));
  }

  @Test
  public void addOrUpdateItemShouldReturn() throws Exception {
      ConstructorIO constructor = new ConstructorIO("YSOxV00F0Kk2R0KnPQN8", "ZqXaOfXuBWD4s3XzCI1q", true, null);
      ConstructorItem item = new ConstructorItem(this.getRandString(), "Search Suggestions");
      assertTrue("upsert succeeds", constructor.addOrUpdateItem(item));
  }
} 
