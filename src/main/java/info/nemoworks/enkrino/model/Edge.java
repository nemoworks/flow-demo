package info.nemoworks.enkrino.model;

import java.util.UUID;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public final class Edge {

    @Getter
    @Setter
    private String id;

    // should change this to a map for edge labels, to include token and other
    // information
    @Getter
    @Setter
    private Boolean temporary = false;

    public Edge() {
        this.id = UUID.randomUUID().toString();
    }

}
