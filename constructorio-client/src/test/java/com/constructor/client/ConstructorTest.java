package com.constructor.client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import java.util.*;

import org.junit.Test;
import com.mashape.unirest.http.*;
import com.mashape.unirest.http.exceptions.UnirestException;

public class ConstructorTest {

	/**
	 * The official fake account apiToken is YSOxV00F0Kk2R0KnPQN8
	 * The official fake account acKey is ZqXaOfXuBWD4s3XzCI1q
	 */

	public String getRandString() {
		return UUID.randomUUID().toString().replaceAll("[\\s\\-()]", "");
	}

	@Test
	public void encodesParamsShouldEncodeParams() throws Exception {
		ConstructorIO constructor = new ConstructorIO("boinka", "doinka", true, null);
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("foo", "bar");
		params.put("bar", "baz");
		String serializedParams = constructor.serializeParams(params);
		assertEquals("serializes params correctly", serializedParams, "foo=bar&bar=baz");
	}
	
	@Test
	public void urlsShouldBeCreated() throws Exception{
		ConstructorIO constructor = new ConstructorIO("boinka", "doinka", true, null);
		String generatedUrl = constructor.makeUrl("v1/test");
		assertEquals("creates url correctly", generatedUrl, "https://ac.cnstrc.com/v1/test?autocomplete_key=doinka");
	}
	
	@Test
	public void apiTokenShouldBeSet() throws Exception {
		ConstructorIO constructor = new ConstructorIO("boinka", "doinka", true, null);
		assertEquals("api token should be set properly", constructor.apiToken, "boinka");
	}
	
	@Test
	public void acKeyShouldBeSet() throws Exception {
		ConstructorIO constructor = new ConstructorIO("boinka", "doinka", true, null);
		assertEquals("autocomplete key should be set properly", constructor.autocompleteKey, "doinka");
	}
	
	@Test
	public void acQueryShouldReturn() throws Exception {
		ConstructorIO constructor = new ConstructorIO("YSOxV00F0Kk2R0KnPQN8", "ZqXaOfXuBWD4s3XzCI1q", true, null);
		ArrayList<String> res = constructor.query("S");
		assertEquals("autocomplete query should return something", "[]", res.toString());
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
	public void addOrUpdateShouldReturn() throws Exception {
		ConstructorIO constructor = new ConstructorIO("YSOxV00F0Kk2R0KnPQN8", "ZqXaOfXuBWD4s3XzCI1q", true, null);
		String randStr = this.getRandString();
		assertTrue("upsert succeeds", constructor.addOrUpdate(randStr, "Search Suggestions"));
	}
	
	@Test
	public void addConstructorItemShouldReturn() throws Exception {
		ConstructorIO constructor = new ConstructorIO("YSOxV00F0Kk2R0KnPQN8", "ZqXaOfXuBWD4s3XzCI1q", true, null);
		String randStr = this.getRandString();
		assertTrue("addition of a constructor item succeeds", constructor.add(new ConstructorItem(randStr), "Search Suggestions"));
	}
	
	@Test
	public void addBatchShouldReturn() throws Exception {
		ConstructorIO constructor = new ConstructorIO("YSOxV00F0Kk2R0KnPQN8", "ZqXaOfXuBWD4s3XzCI1q", true, null);
		ConstructorItem[] items = {new ConstructorItem(this.getRandString()), new ConstructorItem(this.getRandString()), new ConstructorItem(this.getRandString())};
		assertTrue("batch addition succeeds", constructor.addBatch(items, "Search Suggestions"));
	}
	
	@Test
	public void addOrUpdateBatchShouldReturn() throws Exception {
		ConstructorIO constructor = new ConstructorIO("YSOxV00F0Kk2R0KnPQN8", "ZqXaOfXuBWD4s3XzCI1q", true, null);
		ConstructorItem[] items = {new ConstructorItem(this.getRandString()), new ConstructorItem(this.getRandString()), new ConstructorItem(this.getRandString())};
		assertTrue("batch upsert succeeds", constructor.addOrUpdateBatch(items, "Search Suggestions"));
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
	public void constructorItem() throws Exception {
		String randStr = this.getRandString();
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("id", "test");
		assertEquals("ConstructorItem handles a missing suggested_score correctly", new ConstructorItem(randStr, params)
			.setSuggestedScore(1009)
			.setSuggestedScore(0)
			.get("suggested_score"), null);
		assertEquals("ConstructorItem handles constructor params correctly", new ConstructorItem(randStr, params)
			.get("id"), "test");
		assertEquals("ConstructorItem handles keywords correctly", new ConstructorItem(randStr, params)
			.setKeywords("hello", "world")
			.getKeywords()[1], "world");
		assertEquals("ConstructorItem handles item removal by setting the value to null correctly", new ConstructorItem(randStr, params)
			.setKeywords(null)
			.getKeywords(), null);
	}
	
	@Test
	public void removeShouldReturn() throws Exception {
		ConstructorIO constructor = new ConstructorIO("YSOxV00F0Kk2R0KnPQN8", "ZqXaOfXuBWD4s3XzCI1q", true, null);
		String randStr = this.getRandString();
		constructor.add(randStr, "Search Suggestions");
		Thread.sleep(2000);
		assertTrue("remove succeeds", constructor.remove(randStr, "Search Suggestions"));
	}
	
	@Test
	public void removeConstructorItemShouldReturn() throws Exception {
		ConstructorIO constructor = new ConstructorIO("YSOxV00F0Kk2R0KnPQN8", "ZqXaOfXuBWD4s3XzCI1q", true, null);
		String randStr = this.getRandString();
		constructor.add(new ConstructorItem(randStr), "Search Suggestions");
		Thread.sleep(2000);
		assertTrue("remove w/ ConstructorItem succeeds", constructor.remove(new ConstructorItem(randStr), "Search Suggestions"));
	}
	
	@Test
	public void modifyShouldReturn() throws Exception {
		ConstructorIO constructor = new ConstructorIO("YSOxV00F0Kk2R0KnPQN8", "ZqXaOfXuBWD4s3XzCI1q", true, null);
		String randStr = this.getRandString();
		constructor.add(randStr, "Search Suggestions");
		Thread.sleep(2000);
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("suggested_score", 1337);
		assertTrue("modify succeeds", constructor.modify(randStr, randStr, "Search Suggestions", params));
	}
	
	@Test
	public void modifyWithNullNameShouldReturn() throws Exception {
		ConstructorIO constructor = new ConstructorIO("YSOxV00F0Kk2R0KnPQN8", "ZqXaOfXuBWD4s3XzCI1q", true, null);
		String randStr = this.getRandString();
		ConstructorItem item = new ConstructorItem(randStr);
		item.setId(randStr).setUrl("https://google.org/");
		constructor.add(item, "Products");
		Thread.sleep(2000);
		assertTrue("modify w/o item_name succeeds", constructor.modify(((ConstructorItem) item.clone()).setItemName(null), "Products", ((ConstructorItem) item.clone()).setUrl("https://google.com/") ));
	}
	
	@Test
	public void conversionNoParamsShouldReturn() throws Exception {
		ConstructorIO constructor = new ConstructorIO("YSOxV00F0Kk2R0KnPQN8", "ZqXaOfXuBWD4s3XzCI1q", true, null);
		assertTrue("conversion without params succeeds", constructor.trackConversion("Stanley_Steamer", "Search Suggestions"));
	}

	@Test
	public void conversionWithParamsShouldReturn() throws Exception {
		ConstructorIO constructor = new ConstructorIO("YSOxV00F0Kk2R0KnPQN8", "ZqXaOfXuBWD4s3XzCI1q", true, null);
		String randStr = this.getRandString();
		constructor.add(randStr, "Search Suggestions");
		Thread.sleep(2000);
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("item", randStr);
		assertTrue("conversion with params succeeds", constructor.trackConversion("Stanley_Steamer", "Search Suggestions", params));
	}
	
	@Test
	public void searchNoParamsShouldReturn() throws Exception {
		ConstructorIO constructor = new ConstructorIO("YSOxV00F0Kk2R0KnPQN8", "ZqXaOfXuBWD4s3XzCI1q", true, null);
		assertTrue("search without params succeeds", constructor.trackSearch("Stanley_Steamer"));
	}
	
	@Test
	public void searchWithParamsShouldReturn() throws Exception {
		ConstructorIO constructor = new ConstructorIO("YSOxV00F0Kk2R0KnPQN8", "ZqXaOfXuBWD4s3XzCI1q", true, null);
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("num_results", 1337);
		assertTrue("search with params succeeds", constructor.trackSearch("Stanley_Steamer", params));
	}
	
	@Test
	public void clickThroughNoParamsShouldReturn() throws Exception {
		ConstructorIO constructor = new ConstructorIO("YSOxV00F0Kk2R0KnPQN8", "ZqXaOfXuBWD4s3XzCI1q", true, null);
		assertTrue("search without params succeeds", constructor.trackClickThrough("Stanley_Steamer", "Search Suggestions"));
	}
	
	@Test
	public void clickThroughWithParamsShouldReturn() throws Exception {
		ConstructorIO constructor = new ConstructorIO("YSOxV00F0Kk2R0KnPQN8", "ZqXaOfXuBWD4s3XzCI1q", true, null);
		String randStr = this.getRandString();
		constructor.add(randStr, "Search Suggestions");
		Thread.sleep(2000);
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("item", randStr);
		assertTrue("click-through with params succeeds", constructor.trackClickThrough("Stanley_Steamer", "Search Suggestions", params));
	}

} 
