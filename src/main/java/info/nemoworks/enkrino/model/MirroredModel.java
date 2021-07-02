package info.nemoworks.enkrino.model;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

import com.google.common.graph.Graph;
import com.google.common.graph.GraphBuilder;
import com.google.common.graph.MutableGraph;
import com.google.common.graph.MutableNetwork;
import com.google.common.graph.NetworkBuilder;

import org.javatuples.Pair;

import info.nemoworks.enkrino.engine.exception.IllegalNetworkException;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class MirroredModel {

    private class NodeMirror {
        private Node node;
        // currently omitting execution context
        private Stack<Pair<Instant, Node>> log;

        public NodeMirror(Node node) {
            this.node = node;
            this.log = new Stack<>();
        }

        public void log(Node logger) {
            log.push(new Pair<>(Instant.now(), logger));
        }

        public Node getNode() {
            return node;
        }

    }

    // edges are typed as boolean to denote if the edge is temporary
    private MutableNetwork<Node, Edge> model;

    private MutableGraph<NodeMirror> mirror;

    // No parallelism support yet.
    @Getter
    @Setter
    private Node current;

    public MirroredModel() {
        this.model = NetworkBuilder.directed().build();
        this.mirror = GraphBuilder.directed().build();
    }

    // todo
    public static MirroredModel fromGraph(Graph<Node> g) throws IllegalNetworkException {

        MirroredModel mirroredNetwork = new MirroredModel();

        g.nodes().forEach(n -> mirroredNetwork.addNode(n));
        g.edges().forEach(e -> mirroredNetwork.addEdge(e.source(), e.target()));

        // a node with a zero indegree is considered the initial node
        for (Node node : mirroredNetwork.getNodes()) {

            // multiple initial node is not considered yet
            if (mirroredNetwork.inDegree(node) == 0) {
                mirroredNetwork.setCurrent(node);
                return mirroredNetwork;
            }
        }

        throw new IllegalNetworkException("no initail node");

    }

    public boolean addNode(Node node) {
        return (model.addNode(node) && mirror.addNode(new NodeMirror(node)));
    }

    public boolean addEdge(Node from, Node to) {
        return model.addEdge(from, to, new Edge());
    }

    public boolean addTemporaryEdge(Node from, Node to) {
        Edge edge = new Edge();
        edge.setTemporary(true);
        return model.addEdge(from, to, edge);
    }

    public Set<Node> getNodes() {
        return model.nodes();
    }

    public Set<Edge> getEdges() { return model.edges(); }

    public int inDegree(Node node) {
        return model.inDegree(node);
    }

    public Set<Node> getSuccssors(Node node) {
        return model.successors(node);
    }

    public Set<Node> getMirrorSuccessors(Node node) throws IllegalNetworkException {

        // recursively or non-recursively, depends on policy

        Set<Node> successors = new HashSet<>();
        mirror.successors(getMirror(node)).forEach(e -> successors.add(e.getNode()));
        return successors;
    }

    private NodeMirror getMirror(Node node) throws IllegalNetworkException {
        for (NodeMirror m : mirror.nodes()) {
            if (m.getNode().equals(node)) {
                return m;
            }
        }
        throw new IllegalNetworkException("No mirror node for " + node);

    }

    // a next node will be in the successors of the original network, which is call
    // a forward
    // or be in the successors of the mirror network, which is call a backtrace
    public boolean goNext(Node next) throws IllegalNetworkException {

        // forward in the original network
        if (getSuccssors(current).contains(next)) {

            // connecting edge in mirror
            if (mirror.putEdge(getMirror(next), getMirror(current))) {
                getMirror(next).log(current);
            }
            current = next;

            return true;

        } else if (getMirrorSuccessors(current).contains(next)) { // forward in mirror

            if (mirror.hasEdgeConnecting(getMirror(current), getMirror(next))) {
                getMirror(next).log(current);
                // actually we don't need to remove the edge in mirror, do we?
            }

            if (!model.hasEdgeConnecting(current, next)) {
                // todo: should labelling this edge to be temp
                addTemporaryEdge(current, next);
            }

            current = next;

            return true;

        }

        // this cannot happen
        return false;
    }

}
