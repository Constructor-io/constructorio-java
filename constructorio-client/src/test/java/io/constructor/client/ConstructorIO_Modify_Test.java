package io.constructor.client;

import static org.junit.Assert.assertTrue;

import java.util.*;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;


public class ConstructorIO_Modify_Test {

    public String getRandString() {
        return UUID.randomUUID().toString().replaceAll("[\\s\\-()]", "");
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

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