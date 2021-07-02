package info.nemoworks.enkrino.model;

import com.google.common.graph.Graph;
import com.google.common.graph.GraphBuilder;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import info.nemoworks.enkrino.engine.exception.IllegalNetworkException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
class MirroredModelTest {

    private Graph<Node> graph;

    @BeforeEach
    public void setup() {

        Node node1 = new Node();
        node1.setName("1");
        Node node2 = new Node();
        node2.setName("2");
        Node node3 = new Node();
        node3.setName("3");
        Node node4 = new Node();
        node4.setName("4");

        graph = GraphBuilder.directed().<Node>immutable().putEdge(node1, node2).putEdge(node1, node3)
                .putEdge(node2, node4).putEdge(node3, node4).build();

    }

    @Test
    void testModelInitialization() throws IllegalNetworkException {

        MirroredModel flow = MirroredModel.fromGraph(graph);
        log.debug(flow.getEdges().toString());

        log.debug(flow.getCurrent().toString());

        log.debug(flow.getSuccssors(flow.getCurrent()).toString());
        log.debug(flow.getMirrorSuccessors(flow.getCurrent()).toString());

        flow.goNext(flow.getSuccssors(flow.getCurrent()).iterator().next());

        log.debug(flow.getCurrent().toString());

        log.debug(flow.getSuccssors(flow.getCurrent()).toString());
        log.debug(flow.getMirrorSuccessors(flow.getCurrent()).toString());

        flow.goNext(flow.getSuccssors(flow.getCurrent()).iterator().next());

        log.debug(flow.getCurrent().toString());

        log.debug(flow.getSuccssors(flow.getCurrent()).toString());
        log.debug(flow.getMirrorSuccessors(flow.getCurrent()).toString());

    }

}
