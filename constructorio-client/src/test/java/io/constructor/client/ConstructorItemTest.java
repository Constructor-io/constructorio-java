package io.constructor.client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.*;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ConstructorItemTest {

    public String getRandString() {
        return UUID.randomUUID().toString().replaceAll("[\\s\\-()]", "");
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void creatingConstructorItemWithNullNameShouldFail() throws Exception {
        thrown.expect(IllegalArgumentException.class);
        HashMap<String, Object> params = new HashMap<String, Object>();
        new ConstructorItem(null, params);
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
        assertNull("ConstructorItem handles item removal by setting the value to null correctly", new ConstructorItem(randStr, params)
                .setKeywords(null)
                .getKeywords());
    }

} 