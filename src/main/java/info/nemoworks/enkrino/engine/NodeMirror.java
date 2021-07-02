package info.nemoworks.enkrino.engine;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import java.time.Instant;
import java.util.Stack;

@AllArgsConstructor
public class NodeMirror{
    @Data
    @AllArgsConstructor
    private class Context{
        Instant timestamp;
        String message;
    }
    @Getter@Setter
    private Node node;
    // currently omitting execution context
    @Getter
    private Stack<Context> log;

    public NodeMirror(Node node) {
        this.node = node;
        this.log = new Stack<>();
    }

    public void log(String message) { log.push(new Context(Instant.now(), message)); }
}
