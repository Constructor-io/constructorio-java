package io.constructor.client;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** Constructor.io Variations Map Object ... uses Gson/Reflection to load data in */
public class VariationsMap {

    public class Value {
        @SerializedName("aggregation")
        public AggregationTypes aggregation;

        @SerializedName("field")
        public String field;

        public Value(AggregationTypes aggregation, String field) {
            this.aggregation = aggregation;
            this.field = field;
        }
    }

    public class Group {
        @SerializedName("name")
        public String name;

        @SerializedName("field")
        public String field;

        public Group(String name, String field) {
            this.name = name;
            this.field = field;
        }
    }

    public enum Dtypes {
        @SerializedName("object")
        object,
        @SerializedName("array")
        array,
    }

    public enum AggregationTypes {
        @SerializedName("first")
        first,
        @SerializedName("min")
        min,
        @SerializedName("max")
        max,
        @SerializedName("all")
        all,
        @SerializedName("count")
        count,
        @SerializedName("field_count")
        field_count,
        @SerializedName("value_count")
        value_count,
    }

    @SerializedName("dtype")
    private Dtypes dType;

    @SerializedName("values")
    private Map<String, Value> values;

    @SerializedName("group_by")
    private List<Group> groupBy;

    @SerializedName("filter_by")
    private JsonObject filterBy;

    /** Creates a variations map */
    public VariationsMap() {
        this.groupBy = new ArrayList<Group>();
        this.values = new HashMap<String, Value>();
        this.dType = Dtypes.array;
    }

    /**
     * Creates and adds a group by rule
     *
     * @param name The name of the group by rule
     * @param field The field to group by
     */
    public void addGroupByRule(String name, String field) {
        if (this.groupBy == null) {
            this.groupBy = new ArrayList<Group>();
        }

        this.groupBy.add(new Group(name, field));
    }

    /**
     * Creates and adds a group by rule
     *
     * @param name The name of the value rule
     * @param aggregation The aggregation type for the rule
     * @param field The field to group by
     */
    public void addValueRule(String name, AggregationTypes aggregation, String field) {
        if (this.values == null) {
            this.values = new HashMap<String, Value>();
        }

        this.values.put(name, new Value(aggregation, field));
    }

    /**
     * @return the dtype
     */
    public Dtypes getdType() {
        return dType;
    }

    /**
     * @param dType the dtype to set
     */
    public void setdType(Dtypes dType) {
        this.dType = dType;
    }

    /**
     * @return the value rules
     */
    public Map<String, Value> getValues() {
        return values;
    }

    /**
     * @param values the value rules to set
     */
    public void setValues(Map<String, Value> values) {
        this.values = values;
    }

    /**
     * @return the group by rules
     */
    public List<Group> getGroupBy() {
        return groupBy;
    }

    /**
     * @param groupBy the group by rules to set
     */
    public void setGroupBy(List<Group> groupBy) {
        this.groupBy = groupBy;
    }

    /**
     * @return JSON encoded filtering expression used to filter variations
     */
    public JsonObject getFilterBy() {
        return filterBy;
    }

    /**
     * @param filterBy JSON encoded filtering expression used to filter variations
     */
    public void setFilterBy(String filterBy) {
        this.filterBy = JsonParser.parseString(filterBy).getAsJsonObject();
    }
}
