package com.constructor.client;

import static org.junit.Assert.assertEquals;
import java.util.*;

import org.junit.Test;

public class ConstructorTest {

	/**
	 * The official fake account apiToken is YSOxV00F0Kk2R0KnPQN8
	 * The official fake account acKey is ZqXaOfXuBWD4s3XzCI1q
	 */

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
		assertEquals("ac query should return something", 1, 1);
	}
	
	@Test
	public void addShouldReturnNoParams() throws Exception {
		assertEquals("not implemented yet", 1, 1);
	}
	
	@Test
	public void addShouldReturnParams() throws Exception {
		assertEquals("not implemented yet", 1, 1);
	}
	
	@Test
	public void removeShouldReturnNoParams() throws Exception {
		assertEquals("not implemented yet", 1, 1);
	}
	
	@Test
	public void removeShouldReturnParams() throws Exception {
		assertEquals("not implemented yet", 1, 1);
	}
	
	@Test
	public void modifyShouldReturnNoParams() throws Exception {
		assertEquals("not implemented yet", 1, 1);
	}
	
	@Test
	public void modifyShouldReturnParams() throws Exception {
		assertEquals("not implemented yet", 1, 1);
	}
	
	@Test
	public void conversionShouldReturnNoParams() throws Exception {
		assertEquals("not implemented yet", 1, 1);
	}
	
	@Test
	public void conversionShouldReturnParams() throws Exception {
		assertEquals("not implemented yet", 1, 1);
	}
	
	@Test
	public void searchShouldReturnNoParams() throws Exception {
		assertEquals("not implemented yet", 1, 1);
	}
	
	@Test
	public void searchShouldReturnParams() throws Exception {
		assertEquals("not implemented yet", 1, 1);
	}
	
	@Test
	public void clickThroughShouldReturnNoParams() throws Exception {
		assertEquals("not implemented yet", 1, 1);
	}
	
	@Test
	public void clickThroughShouldReturnParams() throws Exception {
		assertEquals("not implemented yet", 1, 1);
	}

} 
