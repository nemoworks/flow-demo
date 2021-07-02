package info.nemoworks.enkrino.model;

import java.util.UUID;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public final class Node {

    @Getter
    @Setter
    private String id;

    // should include other information
    @Getter
    @Setter
    private String name;

    public Node() {
        this.id = UUID.randomUUID().toString();
    }

}