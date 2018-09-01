package io.constructor.client;

import static org.junit.Assert.assertTrue;

import java.util.*;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;


public class ConstructorIO_RemoveBatch_Test {

    public String getRandString() {
        return UUID.randomUUID().toString().replaceAll("[\\s\\-()]", "");
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

   @Test
    public void removeBatchShouldReturn() throws Exception {
        ConstructorIO constructor = new ConstructorIO("YSOxV00F0Kk2R0KnPQN8", "ZqXaOfXuBWD4s3XzCI1q", true, null);
        ConstructorItem[] items = {
            new ConstructorItem(this.getRandString(), "Search Suggestions"),
            new ConstructorItem(this.getRandString(), "Search Suggestions"),
            new ConstructorItem(this.getRandString(), "Search Suggestions")
        };
        assertTrue("batch removal succeeds", constructor.removeItemBatch(items, "Search Suggestions"));
    }
} 