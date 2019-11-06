package codecool;

import java.util.ArrayList;
import java.util.List;

public class TreeNode {

    private List<TreeNode> children;
    private String parent;
    private String cat;

    public String getParent() {
        return parent;
    }

    public String getCat() {
        return cat;
    }

    public TreeNode(String cat, String parent) {
        this.cat = cat;
        this.parent = parent;
        this.children = new ArrayList<>();
    }

    public void addChild(TreeNode child) {
        this.children.add(child);
    }

    public List<TreeNode> getChildren() {
        return this.children;
    }

    @Override
    public String toString() {
        return cat;
    }
}
