package io.constructor.client;

import static org.junit.Assert.assertEquals;

import java.util.UUID;
import java.util.Arrays;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ConstructorVariationTest {

    public String getRandomProductName() {
        return "Product" + UUID.randomUUID().toString().replaceAll("[\\s\\-()]", "");
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void newWithNullvariationNameShouldFail() throws Exception {
        thrown.expect(IllegalArgumentException.class);
        new ConstructorVariation(null, null, null);
    }

    @Test
    public void newShouldReturnConstructorVariation() throws Exception {
        String variationName = this.getRandomProductName();
        String variationId = variationName;
        String itemId = this.getRandomProductName();
        ConstructorVariation variation = new ConstructorVariation(variationId, itemId, variationName);
        assertEquals(variation.getName(), variationName);
    }

    @Test
    public void newShouldReturnDefaultProperties() throws Exception {
        String variationName = this.getRandomProductName();
        String variationId = variationName;
        String itemId = this.getRandomProductName();
        ConstructorVariation variation = new ConstructorVariation(variationId, itemId, variationName);
        assertEquals(variation.getName(), variationName);
        assertEquals(variation.getSuggestedScore(), null);
        assertEquals(variation.getKeywords(), null);
        assertEquals(variation.getUrl(), null);
        assertEquals(variation.getImageUrl(), null);
        assertEquals(variation.getDescription(), null);
        assertEquals(variation.getId(), variationName);
        assertEquals(variation.getItemId(), itemId);
        assertEquals(variation.getFacets(), null);
        assertEquals(variation.getFacets(), null);
        assertEquals(variation.getGroupIds(), null);
    }

    @Test
    public void settersShouldSet() throws Exception {
        String variationName = this.getRandomProductName();
        String itemId = this.getRandomProductName();
        ConstructorVariation variation = new ConstructorVariation(variationName, variationName, itemId);
        Float suggestedScore = (float) 100000.00;
        variation.setName("airline tickets");
        variation.setSuggestedScore(suggestedScore);
        variation.setKeywords(Arrays.asList("London", "Tokyo", "New "));
        variation.setUrl("https://constructor.io/test");
        variation.setImageUrl("https://constructor.io/test.png");
        variation.setDescription("See the world!");
        variation.setId("TICK-007");
        variation.setItemId("TICK");
        variation.setGroupIds(Arrays.asList("Lucky Tickets", "Special Tickets", "Fancy Tickets"));

        assertEquals(variation.getName(), "airline tickets");
        assertEquals(variation.getSuggestedScore(), suggestedScore);
        assertEquals(variation.getKeywords().size(), 3);
        assertEquals(variation.getUrl(), "https://constructor.io/test");
        assertEquals(variation.getImageUrl(), "https://constructor.io/test.png");
        assertEquals(variation.getDescription(), "See the world!");
        assertEquals(variation.getId(), "TICK-007");
        assertEquals(variation.getItemId(), "TICK");
        assertEquals(variation.getFacets(), null);
        assertEquals(variation.getMetadata(), null);
        assertEquals(variation.getGroupIds().size(), 3);
    }
}
