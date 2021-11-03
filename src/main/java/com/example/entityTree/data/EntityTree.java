package com.example.entityTree.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Objects;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Streams;
import org.apache.commons.lang3.tuple.Pair;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

public class EntityTree {

    @JsonProperty("tree")
    private Entity root = null;

    @JsonIgnore
    private HashMap<Integer, Entity> allNodes = new HashMap<>();

    public Optional<Entity> getById(int nodeId)
    {
        return Optional.ofNullable(allNodes.get(nodeId));
    }

    public Entity getRoot()
    {
        return root;
    }

    public Entity addChild(Pair<Integer, Entity> childPair) throws NoSuchElementException
    {
        // root case
        if(null == root && allNodes.size() == 0) {
            allNodes.put(childPair.getRight().getId(), childPair.getRight());
            root = childPair.getRight();
            return null;
        }
        Optional<Entity> parent = getById(childPair.getLeft());
        if (parent.isPresent()) {
            childPair.getRight().setParent(parent.get());
            parent.get().addToChildList(childPair.getRight());
            allNodes.put(childPair.getRight().getId(), childPair.getRight());
        }
        else
        {
            throw new NoSuchElementException(String.format("No parent element with id: {} found", childPair.getLeft()));
        }
        return parent.get();
    }

    public List<Integer> getChildIds(int parentId) throws NoSuchElementException {
        if(!allNodes.containsKey(parentId))
            throw new NoSuchElementException(String.format("Element with id: {} not found"));
        else
            return allNodes.get(parentId).getChildren().stream().map(Entity::getId).collect(Collectors.toList());
    }

    @JsonIgnore
    public ImmutableList<Entity> getAll()
    {
        return ImmutableList.copyOf(allNodes.values());
    }

    public EntityTree addAll(EntityTree otherTree)
    {
        List<Pair<Integer, Entity>> mergedList = Streams.concat(getAll().stream(), otherTree.getAll().stream())
                .map(entity -> {
                    int parentId = entity.getParentId();
                    entity.setParent(null);
                    return Pair.of(parentId, entity);
                })
                .sorted()
                .collect(Collectors.toList());
        EntityTree newTree = new EntityTree();
        mergedList.forEach(newTree::addChild);

        return newTree;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EntityTree that = (EntityTree) o;
        return Objects.equal(root, that.root) && Objects.equal(allNodes, that.allNodes);
    }

    @Override
    public String toString() {
        return root.toString();
    }
}
