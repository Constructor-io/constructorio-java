package com.constructor.client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
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
		assertEquals("ac key should be set properly", constructor.autocompleteKey, "doinka");
	}
	
	@Test
	public void acQueryShouldReturn() throws Exception {
		ConstructorIO constructor = new ConstructorIO("YSOxV00F0Kk2R0KnPQN8", "ZqXaOfXuBWD4s3XzCI1q", true, null);
		JsonNode res = constructor.query("S");
		assertEquals("ac query should return something", res.toString(), "{\"suggestions\":[]}");
	}
	
	@Test
	public void addNoParamsShouldReturn() throws Exception {
		ConstructorIO constructor = new ConstructorIO("YSOxV00F0Kk2R0KnPQN8", "ZqXaOfXuBWD4s3XzCI1q", true, null);
		String randStr = this.getRandString();
		assertTrue("addition without params returns alright", constructor.add(randStr, "Search Suggestions"));
	}
	
	@Test
	public void addWithParamsShouldReturn() throws Exception {
		ConstructorIO constructor = new ConstructorIO("YSOxV00F0Kk2R0KnPQN8", "ZqXaOfXuBWD4s3XzCI1q", true, null);
		assertEquals("not implemented yet", 1, 0);
	}
	
	@Test
	public void removeNoParamsShouldReturn() throws Exception {
		ConstructorIO constructor = new ConstructorIO("YSOxV00F0Kk2R0KnPQN8", "ZqXaOfXuBWD4s3XzCI1q", true, null);
		assertEquals("not implemented yet", 1, 0);
	}
	
	@Test
	public void removeWithParamsShouldReturn() throws Exception {
		ConstructorIO constructor = new ConstructorIO("YSOxV00F0Kk2R0KnPQN8", "ZqXaOfXuBWD4s3XzCI1q", true, null);
		assertEquals("not implemented yet", 1, 0);
	}
	
	@Test
	public void modifyNoParamsShouldReturn() throws Exception {
		ConstructorIO constructor = new ConstructorIO("YSOxV00F0Kk2R0KnPQN8", "ZqXaOfXuBWD4s3XzCI1q", true, null);
		assertEquals("not implemented yet", 1, 0);
	}
	
	@Test
	public void modifyWithParamsShouldReturn() throws Exception {
		ConstructorIO constructor = new ConstructorIO("YSOxV00F0Kk2R0KnPQN8", "ZqXaOfXuBWD4s3XzCI1q", true, null);
		assertEquals("not implemented yet", 1, 0);
	}
	
	@Test
	public void conversionNoParamsShouldReturn() throws Exception {
		ConstructorIO constructor = new ConstructorIO("YSOxV00F0Kk2R0KnPQN8", "ZqXaOfXuBWD4s3XzCI1q", true, null);
		assertEquals("not implemented yet", 1, 0);
	}
	
	@Test
	public void conversionWithParamsShouldReturn() throws Exception {
		ConstructorIO constructor = new ConstructorIO("YSOxV00F0Kk2R0KnPQN8", "ZqXaOfXuBWD4s3XzCI1q", true, null);
		assertEquals("not implemented yet", 1, 0);
	}
	
	@Test
	public void searchNoParamsShouldReturn() throws Exception {
		ConstructorIO constructor = new ConstructorIO("YSOxV00F0Kk2R0KnPQN8", "ZqXaOfXuBWD4s3XzCI1q", true, null);
		assertEquals("not implemented yet", 1, 0);
	}
	
	@Test
	public void searchWithParamsShouldReturn() throws Exception {
		ConstructorIO constructor = new ConstructorIO("YSOxV00F0Kk2R0KnPQN8", "ZqXaOfXuBWD4s3XzCI1q", true, null);
		assertEquals("not implemented yet", 1, 0);
	}
	
	@Test
	public void clickThroughNoParamsShouldReturn() throws Exception {
		ConstructorIO constructor = new ConstructorIO("YSOxV00F0Kk2R0KnPQN8", "ZqXaOfXuBWD4s3XzCI1q", true, null);
		assertEquals("not implemented yet", 1, 0);
	}
	
	@Test
	public void clickThroughWithParamsShouldReturn() throws Exception {
		ConstructorIO constructor = new ConstructorIO("YSOxV00F0Kk2R0KnPQN8", "ZqXaOfXuBWD4s3XzCI1q", true, null);
		assertEquals("not implemented yet", 1, 0);
	}

} 
