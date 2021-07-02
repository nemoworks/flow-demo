package info.nemoworks.enkrino.engine;

import com.google.common.graph.*;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
public class FlowMirror{
    @Getter
    private MutableGraph<NodeMirror> mirror;

    // initiator
    public FlowMirror(Flow flow){
        this.mirror = GraphBuilder.directed().build();
        MutableNetwork<Node,Edge> net = flow.getFlow();
        net.nodes().forEach(node -> this.mirror.addNode(new NodeMirror(node)));
    }

    // encapsulation
    public Set<NodeMirror> nodes(){return this.mirror.nodes();}

    public Set<EndpointPair<NodeMirror>> edges(){return this.mirror.edges();}

    public Set<Node> successors(Node node){
        return this.mirror.successors(this.findMirror(node)).stream().
                map(nodeMirror -> nodeMirror.getNode()).collect(Collectors.toSet());
    }

    public Set<Node> reachableNodes(Node node){
        return Graphs.
            reachableNodes(this.mirror, this.findMirror(node)).stream().
            map(nodeMirror -> nodeMirror.getNode()).collect(Collectors.toSet());
    }

    public void addEdge(Node source, Node target){
        this.mirror.putEdge(this.findMirror(source), this.findMirror(target));
    }

    public NodeMirror findMirror(Node node) {
        for(NodeMirror nodeMirror:this.mirror.nodes()){
            if(nodeMirror.getNode().equals(node)){
                return nodeMirror;
            }
        }
        return null;
    }
}
