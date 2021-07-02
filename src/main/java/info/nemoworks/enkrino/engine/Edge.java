package info.nemoworks.enkrino.engine;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Objects;
import java.util.UUID;

@Data
@AllArgsConstructor
public class Edge{
    @AllArgsConstructor
    @Data
    private class Spec{
        private int token;
        private boolean temporary;
    }

    private String id;
    private String source;
    private String target;
    private Spec spec;

    public Edge(String source, String target) {

        this.id = UUID.randomUUID().toString();
        this.source = source;
        this.target = target;

        this.spec = new Spec(0, false);
    }

    public Edge(String source, String target, boolean temporary, int token) {
        this.id = UUID.randomUUID().toString();
        this.source = source;
        this.target = target;

        this.spec = new Spec(token, temporary);
    }

    @Override
    public boolean equals(Object o){
        String oid = ((Edge) o).getId();
        if(oid.equals(this.id)){
            return true;
        }
        return false;
    }

    @Override
    public int hashCode(){
        return Objects.hash(this.id);
    }

}
