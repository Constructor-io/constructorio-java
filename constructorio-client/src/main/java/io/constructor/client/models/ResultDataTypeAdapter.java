package io.constructor.client.models;
 
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import com.google.gson.TypeAdapter;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import org.apache.commons.lang3.NotImplementedException;
 
/**
 * Data adapter for the ResultData class
 */
public class ResultDataTypeAdapter extends TypeAdapter<ResultData> {

  @Override
    public ResultData read(JsonReader reader) throws IOException {
      // the first token is the start object
      JsonToken token = reader.peek();
      ResultData data = new ResultData();
      if (token.equals(JsonToken.BEGIN_OBJECT)) {
          reader.beginObject();
          while (!reader.peek().equals(JsonToken.END_OBJECT)) {
              if (reader.peek().equals(JsonToken.NAME)) {
                String name = reader.nextName();
                switch (name) {
                  
                  case "description":
                    data.description = reader.nextString();
                    break;
                  
                  case "id":
                    data.id = reader.nextString();
                    break;

                  case "url":
                    data.url = reader.nextString();
                    break;

                  case "image_url":
                    data.imageUrl = reader.nextString();
                    break;

                  case "groups":
                    Type groupType = new TypeToken<List<ResultGroup>>() {}.getType();
                    data.groups = new JsonAdapter().fromJson(reader);

                    // data.groups = new ArrayList<ResultGroup>();
                    // reader.beginArray();
                    // while (reader.hasNext())  {
                    //   data.groups.add(new Gson().fromJson(reader.nextString(), ResultGroup.class));
                    // }
                    // reader.endArray();
                    reader.skipValue();
                    break;

                  case "facets":
                    // data.facets = new ArrayList<ResultFacet>();
                    // reader.beginArray();
                    // while (reader.hasNext())  {
                    //   data.facets.add(new Gson().fromJson(reader.nextString(), ResultFacet.class));
                    // }
                    // reader.endArray();
                    reader.skipValue();
                    break;
                  
                  default:
                    data.metadata.put(name, reader.nextString());
                    break;

                }
              }
          }
          reader.endObject();
      }
      return data;
    }

  @Override
  public void write(JsonWriter out, ResultData value) throws IOException {
    throw new NotImplementedException("nerp");
  }

}