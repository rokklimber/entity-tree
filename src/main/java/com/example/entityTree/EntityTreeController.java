package com.example.entityTree;

import com.example.entityTree.data.Entity;
import com.example.entityTree.data.EntityTree;
import lombok.extern.java.Log;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.StringTokenizer;

@RestController
@RequestMapping(value = "/entityTree")
@Log
public class EntityTreeController {

    @PostMapping(value = "/get", consumes = MediaType.TEXT_PLAIN_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    EntityTree getEntityTree(@RequestBody String treeDataText) {
        return Collections.list(new StringTokenizer(treeDataText, "|")).stream()
                .map(entityText -> {
                    String entityValues[] = ((String)entityText).split(",");
                    boolean parentIsNull = entityValues[0].equals("null");
                    return Pair.of(parentIsNull ? -1 : Integer.parseInt(entityValues[0]), Entity.builder()
                            .id(Integer.parseInt(entityValues[1]))
                            .name(entityValues[2])
                            .build());
                })
                .sorted()
                .collect(EntityTree::new, EntityTree::addChild, EntityTree::addAll);
    }
}
