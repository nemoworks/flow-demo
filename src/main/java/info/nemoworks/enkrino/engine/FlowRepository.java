package info.nemoworks.enkrino.engine;

import com.google.common.graph.GraphBuilder;
import com.google.common.graph.MutableGraph;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FlowRepository {
    private FlowRepository() {
        super();
    }

    public static Flow getFlow1() {
        MutableGraph<Node> flow = GraphBuilder.directed().build();
        Node step1 = new Node("Step1");
        Node step2 = new Node ("Step2");
        Node step3 = new Node ("Step3");
        Node step4 = new Node ("Step4");
        Node step5 = new Node ("Step5");

        flow.addNode(step1);
        flow.addNode(step2);
        flow.addNode(step3);
        flow.addNode(step4);
        flow.addNode(step5);

        flow.putEdge(step1, step2);
        flow.putEdge(step2, step4);
        flow.putEdge(step1, step3);
        flow.putEdge(step3, step4);
        flow.putEdge(step4, step5);

        return new Flow(flow);
    }
}
