package com.example.entityTree;

import com.example.entityTree.data.Entity;
import com.example.entityTree.data.EntityTree;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class EntityTreeTest {

    @Test
    public void canAddRoot()
    {
        EntityTree tree = new EntityTree();
        tree.addChild(Pair.of(null, Entity.builder().id(0).name("root").build()));

        Entity expected = Entity.builder().id(0).name("root").build();
        List<Entity> actualEntityList = tree.getAll();

        assertThat(actualEntityList).isEqualTo(Collections.singletonList(expected));
    }
}
