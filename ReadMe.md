# Entity Tree Application
## Standalone code
* code can be found in com.example.entityTree package
* See example commandline application for simple code
runner

#### Example Code Usage
      String inputData =
                "null,0,grandpa|0,1,son|0,2,daugther|1,3,grandkid|1,4,grandkid|2,5,grandkid|5,6,greatgrandkid";
      log.severe("Converting default input to tree form: " + inputData);
      EntityTree tree = controller.getEntityTree(inputData);
      System.out.println(tree);
## Running the REST application
* from gradle
  
        ./gradlew bootRun
* from the command line

        java -jar entityTree-<VERSION>.jar
### Calling the REST api
    curl --location --request POST 'http://localhost:8080/entityTree/get' \
    --header 'Content-Type: text/plain' \
    --data-raw 'null,0,grandpa|0,1,son|0,2,daugther|1,3,grandkid|1,4,grandkid|2,5,grandkid|5,6,greatgrandkid'
### Example JSON response
```
{
    "tree": {
        "node_id": 0,
        "node_name": "grandpa",
        "child_nodes": [
            {
                "node_id": 1,
                "node_name": "son",
                "child_nodes": [
                    {
                        "node_id": 3,
                        "node_name": "grandkid"
                    },
                    {
                        "node_id": 4,
                        "node_name": "grandkid"
                    }
                ]
            },
            {
                "node_id": 2,
                "node_name": "daugther",
                "child_nodes": [
                    {
                        "node_id": 5,
                        "node_name": "grandkid",
                        "child_nodes": [
                            {
                                "node_id": 6,
                                "node_name": "greatgrandkid"
                            }
                        ]
                    }
                ]
            }
        ]
    }
}
```