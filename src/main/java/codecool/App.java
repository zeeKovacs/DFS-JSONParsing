package codecool;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;


public class App {
    public static void main(String[] args) throws IOException, ParseException, URISyntaxException {

        ClassLoader cl = new App().getClass().getClassLoader();
        String fileName = "vehicles.json";

        File file = new File(cl.getResource(fileName).getFile());

        List<JSONObject> jsonObjects = myParser(file);
        List<TreeNode> nodes = treeNodeGenerator(jsonObjects);

        treeBuilder(nodes);
        for (TreeNode node : nodes) {
            System.out.println(node.getCat() + node.getChildren());
        }
    }

    public static List<JSONObject> myParser(File filename) throws IOException, ParseException {

        JSONParser parser = new JSONParser();
        Object obj = parser.parse(new FileReader(filename));

        List<JSONObject> myObjects = new ArrayList<>();
        JSONArray arr = (JSONArray) obj;

        for (int i = 0; i < arr.size(); i++) {
            myObjects.add((JSONObject) arr.get(i));
        }
        return myObjects;
    }

    public static List<TreeNode> treeNodeGenerator(List<JSONObject> jsonObjects) {
        List<TreeNode> nodes = new ArrayList<>();

        for (JSONObject obj : jsonObjects) {
            nodes.add(new TreeNode((String) obj.get("category"), (String) obj.get("parent_category")));
        }
        return nodes;
    }

    public static void treeBuilder(List<TreeNode> nodes) {
        for (TreeNode node : nodes) {
            outerloop:
            for (TreeNode possibleParent : nodes) {
                if (node.getParent() != null && node.getParent().equals(possibleParent.getCat())) {
                    possibleParent.addChild(node);
                    break outerloop;
                }
            }
        }
    }

}
