import Trees.BST;
import Trees.AVL;
import Nodes.Node;

import java.io.FileWriter;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        int[] sizes = {1000, 2000, 3000, 4000, 5000, 6000, 7000, 8000, 9000, 
                       10000, 11000, 12000, 13000, 14000, 15000, 16000, 17000, 18000, 19000,
                       20000, 21000, 22000, 23000, 24000, 25000, 26000, 27000, 28000, 29000,
                       30000};
        try (FileWriter csvWriter = new FileWriter("execution_times.csv")) {
            csvWriter.append("Size,Tree,Operation,Time\n");

            for (int size : sizes) {
                testTree(new BST<>(), size, "BST", csvWriter);
                testTree(new AVL<>(), size, "AVL", csvWriter);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void testTree(BST<Integer> tree, int size, String treeType, FileWriter csvWriter) throws IOException {
        long startTime, endTime;

        System.out.println("Testing " + treeType + " with size " + size);
        for (int i = 0; i < size; i++) {
            tree.insert(i);
        }

        // Test insert
        startTime = System.nanoTime();
        tree.insert(size + 1);
        endTime = System.nanoTime();
        csvWriter.append(size + "," + treeType + ",insert," + (endTime - startTime) + "\n");

        // Test find
        startTime = System.nanoTime();
        tree.find(size - 1, tree.getRoot());
        endTime = System.nanoTime();
        csvWriter.append(size + "," + treeType + ",find," + (endTime - startTime) + "\n");

        // Test height
        startTime = System.nanoTime();
        tree.height(tree.getRoot());
        endTime = System.nanoTime();
        csvWriter.append(size + "," + treeType + ",height," + (endTime - startTime) + "\n");

        // Test delete
        startTime = System.nanoTime();
        Node<Integer> node = tree.find(size + 1, tree.getRoot());
        if (node != null) {
            tree.delete(node);
        }
        endTime = System.nanoTime();
        csvWriter.append(size + "," + treeType + ",delete," + (endTime - startTime) + "\n");
    }
}