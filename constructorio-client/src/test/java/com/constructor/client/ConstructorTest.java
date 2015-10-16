package com.constructor.client;

import static org.junit.Assert.assertEquals;
import java.util.*;

import org.junit.Test;

public class ConstructorTest {

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
	public void encodesComplexParamsShouldEncodeParams() throws Exception {
		ConstructorIO constructor = new ConstructorIO("boinka", "doinka", true, null);
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("foo", "bar");
		params.put("bar", "baz");
		String serializedParams = constructor.serializeParams(params);
		assertEquals("serializes params correctly", serializedParams, "foo=bar&bar=baz");
	}
	
	@Test
	public void urlsShouldBeCreated() {
		assertEquals("not implemented yet", 1, 1);
	}
	
	@Test
	public void apiTokenShouldBeSet() {
		assertEquals("not implemented yet", 1, 1);
	}
	
	@Test
	public void acKeyShouldBeSet() {
		assertEquals("not implemented yet", 1, 1);
	}
	
	@Test
	public void acQueryShouldReturn() {
		assertEquals("not implemented yet", 1, 1);
	}
	
	@Test
	public void addShouldReturnNoParams() {
		assertEquals("not implemented yet", 1, 1);
	}
	
	@Test
	public void addShouldReturnParams() {
		assertEquals("not implemented yet", 1, 1);
	}
	
	@Test
	public void removeShouldReturnNoParams() {
		assertEquals("not implemented yet", 1, 1);
	}
	
	@Test
	public void removeShouldReturnParams() {
		assertEquals("not implemented yet", 1, 1);
	}
	
	@Test
	public void modifyShouldReturnNoParams() {
		assertEquals("not implemented yet", 1, 1);
	}
	
	@Test
	public void modifyShouldReturnParams() {
		assertEquals("not implemented yet", 1, 1);
	}
	
	@Test
	public void conversionShouldReturnNoParams() {
		assertEquals("not implemented yet", 1, 1);
	}
	
	@Test
	public void conversionShouldReturnParams() {
		assertEquals("not implemented yet", 1, 1);
	}
	
	@Test
	public void searchShouldReturnNoParams() {
		assertEquals("not implemented yet", 1, 1);
	}
	
	@Test
	public void searchShouldReturnParams() {
		assertEquals("not implemented yet", 1, 1);
	}
	
	@Test
	public void clickThroughShouldReturnNoParams() {
		assertEquals("not implemented yet", 1, 1);
	}
	
	@Test
	public void clickThroughShouldReturnParams() {
		assertEquals("not implemented yet", 1, 1);
	}

} 
