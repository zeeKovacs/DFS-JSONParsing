package codecool;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

public class AppTest {

    static class TreeNode {

        private final int id;

        private List<TreeNode> children;

        public TreeNode(int id, TreeNode... children) {
            this.id = id;
            this.children = Arrays.stream(children).collect(Collectors.toList());
        }

        public int getId() {
            return id;
        }

        public List<TreeNode> getChildren() {
            return children;
        }

        @Override
        public String toString() {
            return "TreeNode{" +
                    "id=" + id +
                    '}';
        }
    }

    TreeNode root;

    @Before
    public void before() {
        // depth 4
        TreeNode n17 = new TreeNode(17);

        // depth 3
        TreeNode n9 = new TreeNode(9);
        TreeNode n10 = new TreeNode(10);
        TreeNode n11 = new TreeNode(11);
        TreeNode n12 = new TreeNode(12);
        TreeNode n13 = new TreeNode(13, n17);
        TreeNode n14 = new TreeNode(14);
        TreeNode n15 = new TreeNode(15);
        TreeNode n16 = new TreeNode(16);

        // depth 2
        TreeNode n4 = new TreeNode(4, n9);
        TreeNode n5 = new TreeNode(5, n10, n11);
        TreeNode n6 = new TreeNode(6, n12, n13, n14);
        TreeNode n7 = new TreeNode(7);
        TreeNode n8 = new TreeNode(8, n15, n16);

        // depth 1
        TreeNode n1 = new TreeNode(1, n4);
        TreeNode n2 = new TreeNode(2, n5, n6);
        TreeNode n3 = new TreeNode(3, n7, n8);

        // depth 0
        root = new TreeNode(0, n1, n2, n3);
    }

    @Test
    public void test1() {
        List<Integer> expected = Arrays.asList(9, 10, 11, 12, 13, 14, 15, 16);
        List<Integer> actual = searchIds(TraversalMode.DEPTH_FIRST, 3, root);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void test2() {
        List<Integer> expected = Arrays.asList(4, 5, 6, 7, 8);
        List<Integer> actual = searchIds(TraversalMode.BREADTH_FIRST, 2, root);
        Assert.assertEquals(expected, actual);
    }

    enum TraversalMode {
        DEPTH_FIRST,
        BREADTH_FIRST
    }

    private List<Integer> searchIds(TraversalMode mode, int depth, TreeNode root) {
        if (mode == TraversalMode.DEPTH_FIRST) {
            List<Integer> result = new ArrayList<>();
            depthFirstSearch(result, depth, 0, root);
            return result;
        }
        return breadthFirstSearch(depth, root);
    }

    private void depthFirstSearch(List<Integer> result, int targetDepth, int currentDepth, TreeNode node) {
        currentDepth++;
        for (TreeNode child : node.getChildren()) {
            if (currentDepth == targetDepth) {
                result.add(child.getId());
            }
            depthFirstSearch(result, targetDepth, currentDepth, child);
        }
    }

    private List<Integer> breadthFirstSearch(int targetDepth, TreeNode node) {
        List<Integer> result = new ArrayList<>();
        Queue<TreeNodeEntry> queue = new LinkedList<>();
        queue.add(new TreeNodeEntry(node, 0));
        while (!queue.isEmpty()) {
            TreeNodeEntry entry = queue.remove();
            if (entry.value == targetDepth) {
                result.add(entry.key.getId());
            }
            for (TreeNode child : entry.key.getChildren()) {
                queue.add(new TreeNodeEntry(child, entry.value + 1));
            }
        }
        return result;
    }

    static class TreeNodeEntry implements Map.Entry<TreeNode, Integer> {

        TreeNode key;
        Integer value;

        public TreeNodeEntry(TreeNode key, Integer value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public TreeNode getKey() {
            return key;
        }

        @Override
        public Integer getValue() {
            return value;
        }

        @Override
        public Integer setValue(Integer value) {
            throw new UnsupportedOperationException();
        }
    }

    //alternative
    /*private List<Integer> breadthFirstSearch(int targetDepth, TreeNode node) {
        Queue queue = new LinkedList();
        List<Integer> target = new ArrayList<>();
        List<TreeNode> nextLevel = new ArrayList<>();
        int depth = 0;

        queue.add(node);

        while(depth <= targetDepth) {
            if (queue.isEmpty()) {
                for (TreeNode n : nextLevel) {
                    queue.add(n);
                }
                depth += 1;
            }
            while (!queue.isEmpty()) {
                TreeNode currentNode = (TreeNode) queue.remove();
                nextLevel.remove(nextLevel.indexOf(currentNode));
                if (depth == targetDepth) {
                    target.add(currentNode.getId());
                }
                for (TreeNode child : currentNode.getChildren()) {
                    nextLevel.add(child);
                }
            }
        }
        return target;
    }*/
}