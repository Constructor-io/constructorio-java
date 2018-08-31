package io.constructor.client;

import static org.junit.Assert.assertTrue;

import java.util.*;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;


public class ConstructorIO_Remove_Test {

    /**
     * The official fake account apiToken is YSOxV00F0Kk2R0KnPQN8
     * The official fake account apiKey is ZqXaOfXuBWD4s3XzCI1q
     */

    public String getRandString() {
        return UUID.randomUUID().toString().replaceAll("[\\s\\-()]", "");
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

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
} 