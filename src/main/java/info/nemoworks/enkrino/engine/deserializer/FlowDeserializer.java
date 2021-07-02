package info.nemoworks.enkrino.engine.deserializer;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import info.nemoworks.enkrino.engine.Edge;
import info.nemoworks.enkrino.engine.Flow;
import info.nemoworks.enkrino.engine.Node;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class FlowDeserializer implements JsonDeserializer<Flow>{
    @Override
    public Flow deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject json = jsonElement.getAsJsonObject();
        Gson gson = new Gson();
        Type nodeListType = new TypeToken<ArrayList<Node>>(){}.getType();
        Type edgeListType = new TypeToken<ArrayList<Edge>>(){}.getType();
        List<Node> nodeList = gson.fromJson(json.get("nodes").toString(), nodeListType);
        List<Edge> edgeList = gson.fromJson(json.get("edges").toString(), edgeListType);
        return new Flow(nodeList, edgeList);
    }
}
