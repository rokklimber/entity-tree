package com.example.entityTree;

import com.example.entityTree.data.EntityTree;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Profile;

@Profile("standalone")
@SpringBootApplication
@Log
public class EntityTreeCommandLineApplication implements CommandLineRunner {

    EntityTreeController controller = new EntityTreeController();

    public static void main (String ... args) {
        SpringApplication.run(EntityTreeCommandLineApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        String inputData =
                "null,0,grandpa|0,1,son|0,2,daugther|1,3,grandkid|1,4,grandkid|2,5,grandkid|5,6,greatgrandkid";
        log.severe("Converting default input to tree form: " + inputData);
        EntityTree tree = controller.getEntityTree(inputData);
        System.out.println(tree);
        System.exit(0);
    }
}
