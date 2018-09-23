package io.constructor.client;

import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ConstructorIOTrackingTest {

  @Rule
  public ExpectedException thrown = ExpectedException.none();


  @BeforeClass
  public static void setupItemToTrack() throws Exception {
      ConstructorIO constructor = new ConstructorIO("YSOxV00F0Kk2R0KnPQN8", "ZqXaOfXuBWD4s3XzCI1q", true, null);
      ConstructorItem item = new ConstructorItem("Stanley_Steamer");
      item.setId("Stanley1");
      item.setUrl("https://constructor.io/products/Stanley1");
      constructor.addOrUpdateItem(item, "Products");
      Thread.sleep(2000);
  }

  @Test
  public void trackConversionShouldReturnTrue() throws Exception {
      ConstructorIO constructor = new ConstructorIO("YSOxV00F0Kk2R0KnPQN8", "ZqXaOfXuBWD4s3XzCI1q", true, null);
      UserInfo userInfo = new UserInfo(3, "c62a-2a09-faie");
      assertTrue("conversion without params succeeds", constructor.trackConversion("Stanley_Steamer", "Products", "Stanley1", "$1.99", userInfo));
  }

  @Test
  public void trackConversionShouldReturnTrueWithNullUserInfo() throws Exception {
      ConstructorIO constructor = new ConstructorIO("YSOxV00F0Kk2R0KnPQN8", "ZqXaOfXuBWD4s3XzCI1q", true, null);
      assertTrue("conversion without params succeeds", constructor.trackConversion("Stanley_Steamer", "Products", "Stanley1", "$1.99", null));
  }

  @Test
  public void trackSearchShouldReturnTrue() throws Exception {
      ConstructorIO constructor = new ConstructorIO("YSOxV00F0Kk2R0KnPQN8", "ZqXaOfXuBWD4s3XzCI1q", true, null);
      UserInfo userInfo = new UserInfo(3, "c62a-2a09-faie");
      assertTrue("search without params succeeds", constructor.trackSearch("Stanley_Steamer", 22, userInfo));
  }

  @Test
  public void trackSearchShouldReturnTrueWithNullUserInfo() throws Exception {
      ConstructorIO constructor = new ConstructorIO("YSOxV00F0Kk2R0KnPQN8", "ZqXaOfXuBWD4s3XzCI1q", true, null);
      assertTrue("search without params succeeds", constructor.trackSearch("Stanley_Steamer", 22, null));
  }

  @Test
  public void trackClickThroughShouldReturnTrue() throws Exception {
      ConstructorIO constructor = new ConstructorIO("YSOxV00F0Kk2R0KnPQN8", "ZqXaOfXuBWD4s3XzCI1q", true, null);
      UserInfo userInfo = new UserInfo(3, "c62a-2a09-faie");
      assertTrue("search without params succeeds", constructor.trackClickThrough("Stanley_Steamer", "Products", "Stanley1", userInfo));
  }

  @Test
  public void trackClickThroughShouldReturnTrueWithNullUserInfo() throws Exception {
      ConstructorIO constructor = new ConstructorIO("YSOxV00F0Kk2R0KnPQN8", "ZqXaOfXuBWD4s3XzCI1q", true, null);
      assertTrue("search without params succeeds", constructor.trackClickThrough("Stanley_Steamer", "Products", "Stanley1", null));
  }

}