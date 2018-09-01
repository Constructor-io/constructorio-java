package io.constructor.client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.*;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;


public class ConstructorIO_Utils_Test {

    public String getRandString() {
        return UUID.randomUUID().toString().replaceAll("[\\s\\-()]", "");
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void serializeParamsShouldEncodeParams() throws Exception {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("foo", "bar");
        params.put("bar", "baz");
        String serializedParams = ConstructorIO.serializeParams(params);
        assertTrue("serializes params correctly", serializedParams.equals("foo=bar&bar=baz") || serializedParams.equals("bar=baz&foo=bar"));
    }

    @Test
    public void makeUrlShouldReturnUrl() throws Exception {
        ConstructorIO constructor = new ConstructorIO("boinka", "doinka", true, null);
        String generatedUrl = constructor.makeUrl("v1/test");
        assertEquals("creates url correctly", generatedUrl, "https://ac.cnstrc.com/v1/test?key=doinka");
    }

    @Test
    public void verifyShouldReturnTrueWithValidKeyTokenPair() throws Exception {
        ConstructorIO constructor = new ConstructorIO("YSOxV00F0Kk2R0KnPQN8", "ZqXaOfXuBWD4s3XzCI1q", false, null);
        assertEquals("verify should return true for testing key/pair", constructor.verify(), true);
    }
} 