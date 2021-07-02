package info.nemoworks.enkrino.engine;

import java.util.Objects;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ToString
@Data
@AllArgsConstructor
public class Node {

    private String id;

    // should include other information
    private String name;

    private int backward;

    public Node(String name){
        this.name = name;
        this.id = UUID.randomUUID().toString();
        this.backward = 2;
    }

    @Override
    public boolean equals(Object o){
        String oid = ((Node) o).getId();
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
