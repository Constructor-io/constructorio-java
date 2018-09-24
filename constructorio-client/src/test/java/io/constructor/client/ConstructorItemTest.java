package io.constructor.client;

import static org.junit.Assert.assertEquals;

import java.util.UUID;
import java.util.Arrays;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ConstructorItemTest {

    public String getRandomProductName() {
        return "Product" + UUID.randomUUID().toString().replaceAll("[\\s\\-()]", "");
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void newWithNullItemNameShouldFail() throws Exception {
        thrown.expect(IllegalArgumentException.class);
        new ConstructorItem(null);
    }

    @Test
    public void newShouldReturnConstructorItem() throws Exception {
        String itemName = this.getRandomProductName();
        ConstructorItem item = new ConstructorItem(itemName);
        assertEquals(item.getItemName(), itemName);
    }

    @Test
    public void newShouldReturnDefaultProperties() throws Exception {
        String itemName = this.getRandomProductName();
        ConstructorItem item = new ConstructorItem(itemName);
        assertEquals(item.getItemName(), itemName);
        assertEquals(item.getSuggestedScore(), null);
        assertEquals(item.getKeywords(), null);
        assertEquals(item.getUrl(), null);
        assertEquals(item.getImageUrl(), null);
        assertEquals(item.getDescription(), null);
        assertEquals(item.getId(), null);
        assertEquals(item.getFacets(), null);
        assertEquals(item.getFacets(), null);
        assertEquals(item.getGroupIds(), null);
    }

    @Test
    public void settersShouldSet() throws Exception {
        String itemName = this.getRandomProductName();
        ConstructorItem item = new ConstructorItem(itemName);

        item.setItemName("airline tickets");
        item.setSuggestedScore(100000);
        item.setKeywords(Arrays.asList("London", "Tokyo", "New "));
        item.setUrl("https://constructor.io/test");
        item.setImageUrl("https://constructor.io/test.png");
        item.setDescription("See the world!");
        item.setId("TICK-007");
        item.setGroupIds(Arrays.asList("Lucky Tickets", "Special Tickets", "Fancy Tickets"));

        assertEquals(item.getItemName(), "airline tickets");
        assertEquals(item.getSuggestedScore(), Integer.valueOf(100000));
        assertEquals(item.getKeywords().size(), 3);
        assertEquals(item.getUrl(), "https://constructor.io/test");
        assertEquals(item.getImageUrl(), "https://constructor.io/test.png");
        assertEquals(item.getDescription(), "See the world!");
        assertEquals(item.getId(), "TICK-007");
        assertEquals(item.getFacets(), null);
        assertEquals(item.getMetadata(), null);
        assertEquals(item.getGroupIds().size(), 3);
    }
}
