package io.constructor.client;

import static org.junit.Assert.assertEquals;

import java.util.*;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ConstructorItem_New_Test {

    public String getRandString() {
        return UUID.randomUUID().toString().replaceAll("[\\s\\-()]", "");
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void newWithNullItemNameShouldFail() throws Exception {
        thrown.expect(IllegalArgumentException.class);
        new ConstructorItem(null, "Suggested Searches");
    }

    @Test
    public void newWithNullSectionNameShouldFail() throws Exception {
        thrown.expect(IllegalArgumentException.class);
        new ConstructorItem(this.getRandString(), null);
    }

    @Test
    public void newShouldReturnConstructorItem() throws Exception {
        String itemName = this.getRandString();
        String autocompleteSection = "Suggested Searches";
        ConstructorItem item = new ConstructorItem(itemName, autocompleteSection);
        assertEquals(item.getItemName(), itemName);
        assertEquals(item.getAutocompleteSection(), autocompleteSection);
    }

    @Test
    public void newShouldReturnDefaultProperties() throws Exception {
        String itemName = this.getRandString();
        String autocompleteSection = "Suggested Searches";
        ConstructorItem item = new ConstructorItem(itemName, autocompleteSection);
        assertEquals(item.getItemName(), itemName);
        assertEquals(item.getAutocompleteSection(), autocompleteSection);
        assertEquals(item.getSuggestedScore(), 0);
        assertEquals(item.getKeywords().size(), 0);
        assertEquals(item.getUrl(), "");
        assertEquals(item.getImageUrl(), "");
        assertEquals(item.getDescription(), "");
        assertEquals(item.getId(), "");
        assertEquals(item.getFacets().size(), 0);
        assertEquals(item.getFacets().size(), 0);
        assertEquals(item.getGroupIds().size(), 0);
    }

    @Test
    public void settersShouldSet() throws Exception {
        String itemName = this.getRandString();
        String autocompleteSection = "Suggested Searches";
        ConstructorItem item = new ConstructorItem(itemName, autocompleteSection);

        item.setItemName("airline tickets");
        item.setAutocompleteSection("sectional airlines");
        item.setSuggestedScore(100000);
        item.setKeywords(new ArrayList<>(Arrays.asList("London", "Tokyo", "New ")));
        item.setUrl("https://constructor.io/test");
        item.setImageUrl("https://constructor.io/test.png");
        item.setDescription("See the world!");
        item.setId("lucky ticket 7");
        item.setGroupIds(new ArrayList<>(Arrays.asList("Lucky Tickets", "Special Tickets", "Fancy Tickets")));

        assertEquals(item.getItemName(), "airline tickets");
        assertEquals(item.getAutocompleteSection(), "sectional airlines");
        assertEquals(item.getSuggestedScore(), 100000);
        assertEquals(item.getKeywords().size(), 3);
        assertEquals(item.getUrl(), "https://constructor.io/test");
        assertEquals(item.getImageUrl(), "https://constructor.io/test.png");
        assertEquals(item.getDescription(), "See the world!");
        assertEquals(item.getId(), "lucky ticket 7");
        assertEquals(item.getFacets().size(), 0);
        assertEquals(item.getMetadata().size(), 0);
        assertEquals(item.getGroupIds().size(), 3);
    }
} 