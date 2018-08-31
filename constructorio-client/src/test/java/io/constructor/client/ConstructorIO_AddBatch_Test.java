package io.constructor.client;

import static org.junit.Assert.assertTrue;

import java.util.*;

import org.junit.Test;

public class ConstructorIO_AddBatch_Test {

  public String getRandString() {
    return UUID.randomUUID().toString().replaceAll("[\\s\\-()]", "");
  }

  @Test
  public void addBatchShouldReturn() throws Exception {
      ConstructorIO constructor = new ConstructorIO("YSOxV00F0Kk2R0KnPQN8", "ZqXaOfXuBWD4s3XzCI1q", true, null);
      ConstructorItem[] items = {new ConstructorItem(this.getRandString()), new ConstructorItem(this.getRandString()), new ConstructorItem(this.getRandString())};
      assertTrue("batch addition succeeds", constructor.addBatch(items, "Search Suggestions"));
  }

  @Test
  public void addBatchStringItemsShouldReturn() throws Exception {
      ConstructorIO constructor = new ConstructorIO("YSOxV00F0Kk2R0KnPQN8", "ZqXaOfXuBWD4s3XzCI1q", true, null);
      String[] items = {this.getRandString(), this.getRandString(), this.getRandString()};
      assertTrue("batch addition succeeds", constructor.addBatch(items, "Search Suggestions"));
  }

  @Test
  public void addOrUpdateBatchShouldReturn() throws Exception {
      ConstructorIO constructor = new ConstructorIO("YSOxV00F0Kk2R0KnPQN8", "ZqXaOfXuBWD4s3XzCI1q", true, null);
      ConstructorItem[] items = {new ConstructorItem(this.getRandString()), new ConstructorItem(this.getRandString()), new ConstructorItem(this.getRandString())};
      assertTrue("batch upsert succeeds", constructor.addOrUpdateBatch(items, "Search Suggestions"));
  }
}