package io.constructor.client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.*;

import org.apache.commons.lang.ObjectUtils.Null;
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
        assertNotNull(item);
        assertEquals(item.autocompleteSection, autocompleteSection);
        assertEquals(item.itemName, itemName);
    }

} 