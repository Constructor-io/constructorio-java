package com.constructor.client;

import static org.junit.Assert.assertEquals;
import java.util.*;

import org.junit.Test;

public class ConstructorTest {

	@Test
	public void encodesParamsShouldEncodeParams() {
		ConstructorIO constructor = new ConstructorIO("boinka", "doinka", true, null);
		HashMap<String, Object> innerParams = new HashMap<String, Object>();
		ArrayList<String> innerArray = new ArrayList<String>();
		innerArray.add("a");
		innerArray.add("b");
		innerParams.put("baz", innerArray);
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("foo", arr);
		params.put("bar", innerParams);
		String serializedParams = constructor.serializeParams(params);
		assertEquals("serializes params correctly", serializedParams, "foo%5B0%5D=1&foo%5B1%5D=2&bar%5Bbaz%5D%5B0%5D=a&bar%5Bbaz%5D%5B1%5D=b");
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
