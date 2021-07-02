package info.nemoworks.enkrino.engine.serializer;

import com.google.gson.*;
import info.nemoworks.enkrino.engine.*;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MirrorSerializer implements JsonSerializer<FlowMirror> {
    @Override
    public JsonElement serialize(FlowMirror flowMirror, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject json = new JsonObject();
        List<NodeMirror> nodeMirrorList = new ArrayList<>(flowMirror.nodes());
        List<Edge> edgeList = flowMirror.edges().stream().map(nodeMirrors ->
                new Edge(nodeMirrors.source().getNode().getId(), nodeMirrors.target().getNode().getId())).collect(Collectors.toList());
        json.add("nodeMirrors", new Gson().toJsonTree((nodeMirrorList)));
        json.add("edges", new Gson().toJsonTree(edgeList));
        return json;
    }
}
