package com.example.entityTree;

import com.example.entityTree.data.Entity;
import com.example.entityTree.data.EntityTree;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class EntityTreeControllerTest {

    private EntityTreeController entityTreeController = new EntityTreeController();

    @Test
    public void testGetEntityTree()
    {
        String input = "null,0,grandpa|0,1,son|0,2,daugther|1,3,grandkid|1,4,grandkid|2,5,grandkid|5,6,greatgrandkid";
        EntityTree actual = entityTreeController.getEntityTree(input);
        List<String> nodeList = Arrays.asList(input.split("\\|"));
        List<Pair<Integer, Entity>> entityList = nodeList.stream().map(text -> {
            String nodeValues[] = text.split(",");
            return Pair.of(nodeValues[0].equals("null") ? null : Integer.parseInt(nodeValues[0]),
                    Entity.builder().id(Integer.parseInt(nodeValues[1])).name(nodeValues[2]).build());
        }).collect(Collectors.toList());
        EntityTree expected = new EntityTree();
        entityList.forEach(expected::addChild);
        assertThat(actual).isEqualTo(expected).usingRecursiveComparison().ignoringFields("hashCode");
    }
}
