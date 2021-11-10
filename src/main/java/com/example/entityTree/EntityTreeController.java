package com.example.entityTree;

import com.example.entityTree.data.Entity;
import com.example.entityTree.data.EntityTree;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.java.Log;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/entityTree")
@Log
public class EntityTreeController {

    @PostMapping(value = "/get", consumes = MediaType.TEXT_PLAIN_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    EntityTree getEntityTree(@RequestBody String treeDataText) {
        return Collections.list(new StringTokenizer(treeDataText, "|")).stream()
                .map(entityText -> {
                    String[] entityValues = ((String)entityText).split(",");
                    boolean parentIsNull = entityValues[0].equals("null");
                    return Pair.of(parentIsNull ? -1 : Integer.parseInt(entityValues[0]), Entity.builder()
                            .id(Integer.parseInt(entityValues[1]))
                            .name(entityValues[2])
                            .build());
                })
                .sorted()
                .collect(EntityTree::new, EntityTree::addChild, EntityTree::addAll);
    }


    @PostMapping(value = "/getSubtree/{nodeId}", consumes = MediaType.TEXT_PLAIN_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    Entity getEntitySubTree(@RequestBody String treeDataText, @PathVariable int nodeId) {
        EntityTree root = getEntityTree(treeDataText);
        Optional<Entity> subTree = root.getSubTree(nodeId);
        if(subTree.isPresent())  return subTree.get();
        else
        {
            throw new NoSuchElementException("Element with id: " + nodeId + " no found");
        }
    }

    @PostMapping(value = "/getParents/{nodeId}", consumes = MediaType.TEXT_PLAIN_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    List<NodeInfo> getEntityPath(@RequestBody String treeDataText, @PathVariable int nodeId) {
        EntityTree root = getEntityTree(treeDataText);
        Optional<Entity> childNode = root.getById(nodeId);
        List<Entity> response = new ArrayList<>();
        if (childNode.isPresent()) {
            Entity childEntity = childNode.get();
            Entity now = childEntity.getParent();
            while (now != null) {
                response.add(now);
                now = now.getParent();
            }
        } else throw new NoSuchElementException("Element with id: " + nodeId + " no found");

        Collections.reverse(response);
        response.forEach(node -> node.setParent(null));

        List<NodeInfo> infoResponse = new ArrayList<>();
        return response.stream().sorted().map(node -> {
            NodeInfo element = new NodeInfo();
            element.setName(node.getName());
            element.setNodeId(node.getId());

            return element;
        }).collect(Collectors.toList());
    }

    @Data
    @NoArgsConstructor
    private static class NodeInfo {
        private int nodeId;
        private String name;
    }
}
