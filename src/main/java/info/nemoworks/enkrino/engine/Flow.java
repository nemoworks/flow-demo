package info.nemoworks.enkrino.engine;

import com.google.common.graph.MutableGraph;
import com.google.common.graph.MutableNetwork;
import com.google.common.graph.NetworkBuilder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Set;

@Slf4j
public class Flow {
    @Getter
    private MutableNetwork<Node, Edge> flow;

    // initiator
    public Flow(List<Node> nodes, List<Edge> edges){
        this.flow = NetworkBuilder.directed().build();
        nodes.forEach(node -> this.flow.addNode(node));
        edges.forEach(edge -> this.flow.addEdge(findNode(edge.getSource()), findNode(edge.getTarget()), edge));
    }

    public Flow(MutableGraph<Node> flowgraph){
        this.flow = NetworkBuilder.directed().build();
        flowgraph.nodes().forEach(node -> this.flow.addNode(node));
        flowgraph.edges().forEach(edge -> this.flow.addEdge(edge.source(), edge.target(), new Edge(edge.source().getId(), edge.target().getId())));
    }

    // encapsulation
    public Set<Node> nodes(){return this.flow.nodes();}

    public Set<Edge> edges(){return this.flow.edges();}

    public int inDegree(Node node){return this.flow.inDegree(node);}

    public int outDegree(Node node){return this.flow.outDegree(node);}

    public void addEdge(Node source, Node target){
        if(this.flow.hasEdgeConnecting(source, target)){
            return;
        }
        this.flow.addEdge(source, target, new Edge(source.getId(), target.getId(), true, 0));
    }

    public Set<Node> successors(Node node){return flow.successors(node);}

    public Node findNode(String id){

        for(Node node:flow.nodes()){
            if(id.equals(node.getId())){
//                log.info(node.getId()+"<->"+id+"true");
                return node;
            }
//            log.info(node.getId()+"<->"+id+"false");
        }
        return null;
    }

}
