package info.nemoworks.enkrino.api;

import java.util.List;

import com.google.gson.Gson;
import info.nemoworks.enkrino.engine.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import info.nemoworks.enkrino.engine.exception.EngineRuntimeException;
import info.nemoworks.enkrino.engine.exception.IllegalFlowException;
import lombok.extern.slf4j.Slf4j;

@CrossOrigin
@RestController
@RequestMapping("api")
@Slf4j
public class EngineController {

    @Autowired
    private Engine engine;
    @Autowired
    private Gson gson;

    @GetMapping(value = "current")
    public Node current() throws EngineRuntimeException {
        Node current = engine.getCurrent();
        if(current!=null){
            return current;
        }else{
            return null;
        }
    }

    @PostMapping(value = "start")
    public Flow start(@RequestBody Flow flow) throws IllegalFlowException {
        engine = new Engine(flow);
        engine.start();
        return engine.getFlow();
    }

    // todo
    @GetMapping(value = "next")
    public List<Node> getNext() {
        return engine.getNext();
    }

    @PostMapping(value = "next")
    public Flow goNext(@RequestBody Node node){
        engine.goNext(node.getId());
        return engine.getFlow();
    }

    @GetMapping(value = "flow", produces = "application/json")
    public Flow getFlow(){
        return engine.getFlow();
    }

    @GetMapping(value = "flow1", produces = "application/json")
    public Flow getFlow1() {
        return FlowRepository.getFlow1();
    }

}
