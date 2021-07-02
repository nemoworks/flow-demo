package info.nemoworks.enkrino.engine;


import com.google.common.graph.MutableGraph;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class Engine {
    @Getter
    private Node current;
    @Getter
    private Flow flow;

    private FlowMirror mirror;

    public Engine(MutableGraph<Node> graph){
        this.flow = new Flow(graph);
        this.mirror = new FlowMirror(this.flow);
        this.current = null;
    }

    public Engine(Flow flow){
        this.flow = flow;
        this.mirror = new FlowMirror(flow);
        this.current = null;
    }

    public void start(){
        for(Node node:this.flow.nodes()){
            if(this.flow.inDegree(node)==0){
                this.current = node;
                break;
            }
        }
        // log
        this.mirror.findMirror(this.current).log("started");
    }

    public List<Node> getNext() {
        List<Node> next = new ArrayList<>(this.flow.successors(this.current));
        List<Node> backwards;
        switch (this.current.getBackward()){
            case 0: backwards = null;break;
            case 1: backwards = new ArrayList<>(this.mirror.successors(this.current));break;
            default: backwards = new ArrayList<>(this.mirror.reachableNodes(this.current));
        }
        if(backwards!=null){
            backwards.remove(this.current);
            next.addAll(backwards);
        }
        return next;
    }

    public void goNext(String id) {
        Node node = this.flow.findNode(id);
        // log current
        this.mirror.findMirror(this.current).log("executed");
        // judge forward or backward
        if(this.flow.successors(this.current).contains(node)) {
            // connect
            this.mirror.addEdge(node, this.current);
            // log
            this.mirror.findMirror(node).log("forwarded from " +this.current.getName());
            // forward
            this.current = node;
        }else{
            this.flow.addEdge(node, this.current);
            // log
            this.mirror.findMirror(node).log("backwarded from "+this.current.getName());
            // backward
            this.current = node;
        }
    }
}
