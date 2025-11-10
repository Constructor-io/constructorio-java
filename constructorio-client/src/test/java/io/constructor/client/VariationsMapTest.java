package io.constructor.client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.junit.Test;

public class VariationsMapTest {

    @Test
    public void newShouldReturnVariationsMap() throws Exception {
        VariationsMap variationsMap = new VariationsMap();
        assertNotNull(variationsMap);
        assertEquals(variationsMap.getdType(), VariationsMap.Dtypes.array);
    }

    @Test
    public void addGroupByRuleShouldAddRule() throws Exception {
        VariationsMap variationsMap = new VariationsMap();
        variationsMap.addGroupByRule("variation", "data.variation_id");
        assertEquals(variationsMap.getGroupBy().size(), 1);
        assertEquals(variationsMap.getGroupBy().get(0).name, "variation");
        assertEquals(variationsMap.getGroupBy().get(0).field, "data.variation_id");
    }

    @Test
    public void addValueRuleShouldAddRule() throws Exception {
        VariationsMap variationsMap = new VariationsMap();
        variationsMap.addValueRule(
                "size", VariationsMap.AggregationTypes.first, "data.facets.size");
        assertEquals(variationsMap.getValues().size(), 1);
        assertEquals(
                variationsMap.getValues().get("size").aggregation,
                VariationsMap.AggregationTypes.first);
        assertEquals(variationsMap.getValues().get("size").field, "data.facets.size");
    }

    @Test
    public void allAggregationTypesShouldSerializeCorrectly() throws Exception {
        VariationsMap variationsMap = new VariationsMap();
        variationsMap.addValueRule(
                "size", VariationsMap.AggregationTypes.first, "data.facets.size");
        variationsMap.addValueRule(
                "min_price", VariationsMap.AggregationTypes.min, "data.facets.price");
        variationsMap.addValueRule(
                "max_price", VariationsMap.AggregationTypes.max, "data.facets.price");
        variationsMap.addValueRule("tags", VariationsMap.AggregationTypes.all, "data.facets.tags");
        variationsMap.addValueRule(
                "variation_count", VariationsMap.AggregationTypes.count, "data.variation_id");
        variationsMap.addValueRule(
                "color_count", VariationsMap.AggregationTypes.field_count, "data.facets.color");
        variationsMap.addValueRule(
                "blue_count", VariationsMap.AggregationTypes.value_count, "data.facets.color");

        Gson gson = new Gson();
        String json = gson.toJson(variationsMap);
        JsonObject jsonObj = gson.fromJson(json, JsonObject.class);

        // Verify all aggregation types are serialized correctly in JSON string
        assertTrue(json.contains("\"aggregation\":\"first\""));
        assertTrue(json.contains("\"aggregation\":\"min\""));
        assertTrue(json.contains("\"aggregation\":\"max\""));
        assertTrue(json.contains("\"aggregation\":\"all\""));
        assertTrue(json.contains("\"aggregation\":\"count\""));
        assertTrue(json.contains("\"aggregation\":\"field_count\""));
        assertTrue(json.contains("\"aggregation\":\"value_count\""));

        // Verify each aggregation type is correctly deserialized
        assertEquals(
                jsonObj.getAsJsonObject("values")
                        .getAsJsonObject("size")
                        .get("aggregation")
                        .getAsString(),
                "first");
        assertEquals(
                jsonObj.getAsJsonObject("values")
                        .getAsJsonObject("min_price")
                        .get("aggregation")
                        .getAsString(),
                "min");
        assertEquals(
                jsonObj.getAsJsonObject("values")
                        .getAsJsonObject("max_price")
                        .get("aggregation")
                        .getAsString(),
                "max");
        assertEquals(
                jsonObj.getAsJsonObject("values")
                        .getAsJsonObject("tags")
                        .get("aggregation")
                        .getAsString(),
                "all");
        assertEquals(
                jsonObj.getAsJsonObject("values")
                        .getAsJsonObject("variation_count")
                        .get("aggregation")
                        .getAsString(),
                "count");
        assertEquals(
                jsonObj.getAsJsonObject("values")
                        .getAsJsonObject("color_count")
                        .get("aggregation")
                        .getAsString(),
                "field_count");
        assertEquals(
                jsonObj.getAsJsonObject("values")
                        .getAsJsonObject("blue_count")
                        .get("aggregation")
                        .getAsString(),
                "value_count");

        assertEquals(variationsMap.getValues().size(), 7);
    }
}
