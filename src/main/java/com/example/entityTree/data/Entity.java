package com.example.entityTree.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.google.common.base.Objects;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonPropertyOrder({"node_id", "node_name", "child_nodes"})
public class Entity implements Comparable<Entity> {
    @JsonIgnore
    Entity parent;

    @JsonIgnore
    int getParentId() {
        return (null == parent ? -1 : parent.getId());
    }

    @JsonProperty("node_id")
    int id;
    @JsonProperty("node_name")
    String name;

    @JsonProperty("child_nodes")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private final List<Entity> children = new ArrayList<Entity>();

    @JsonIgnore
    public int addToChildList(Entity child) {
        children.add(child);
        return children.size();
    }

    @Override
    public int compareTo(Entity o) {
        return id-o.getId();
    }

    @Override
    public String toString() {
        return "Entity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", children=" + children +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Entity entity = (Entity) o;
        return id == entity.id && Objects.equal(name, entity.name) && Objects.equal(children, entity.children);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, name, children);
    }
}