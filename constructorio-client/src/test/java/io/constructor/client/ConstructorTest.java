package io.constructor.client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.*;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;


public class ConstructorTest {

    public String getRandString() {
        return UUID.randomUUID().toString().replaceAll("[\\s\\-()]", "");
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void encodesParamsShouldEncodeParams() throws Exception {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("foo", "bar");
        params.put("bar", "baz");
        String serializedParams = ConstructorIO.serializeParams(params);
        assertTrue("serializes params correctly", serializedParams.equals("foo=bar&bar=baz") || serializedParams.equals("bar=baz&foo=bar"));
    }

    @Test
    public void urlsShouldBeCreated() throws Exception {
        ConstructorIO constructor = new ConstructorIO("boinka", "doinka", true, null);
        String generatedUrl = constructor.makeUrl("v1/test");
        assertEquals("creates url correctly", generatedUrl, "https://ac.cnstrc.com/v1/test?autocomplete_key=doinka");
    }

    @Test
    public void queryShouldReturn() throws Exception {
        ConstructorIO constructor = new ConstructorIO("YSOxV00F0Kk2R0KnPQN8", "ZqXaOfXuBWD4s3XzCI1q", true, null);
        ArrayList<String> res = constructor.query("S");
        assertEquals("autocomplete query should return something", "[]", res.toString());
    }

   @Test
    public void removeBatchShouldReturn() throws Exception {
        ConstructorIO constructor = new ConstructorIO("YSOxV00F0Kk2R0KnPQN8", "ZqXaOfXuBWD4s3XzCI1q", true, null);
        assertTrue("batch removal succeeds", constructor.removeBatch(new String[]{this.getRandString(), this.getRandString(), this.getRandString()}, "Search Suggestions"));
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
} 