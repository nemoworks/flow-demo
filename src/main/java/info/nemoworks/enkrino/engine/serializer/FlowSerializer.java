package info.nemoworks.enkrino.engine.serializer;

import com.google.gson.*;
import info.nemoworks.enkrino.engine.Edge;
import info.nemoworks.enkrino.engine.Flow;
import info.nemoworks.enkrino.engine.Node;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Type;
import java.util.*;

@Slf4j
public class FlowSerializer implements JsonSerializer<Flow> {
    @Override
    public JsonElement serialize(Flow flow, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject json = new JsonObject();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        List<Node> nodeList = new ArrayList<>(flow.nodes());
        List<Edge> edgeList = new ArrayList<>(flow.edges());
        json.add("nodes", new Gson().toJsonTree(nodeList));
        json.add("edges", new Gson().toJsonTree(edgeList));
        return json;
    }
}
