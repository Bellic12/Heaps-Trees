import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class Main {
    public static void main(String[] args) throws Exception {
        int[] sizes = {1000,10000,100000, 1000000,10000000, 100000000, 200000000,300000000,400000000,500000000};
        int d = 6;

        try (FileWriter csvWriter = new FileWriter("performance_test_results.csv")) {
            csvWriter.append("Size,Heap,Operation,Time (ns)\n");

            for (int size : sizes) {
                testHeap(new BinHeap(), size, "BinHeap", csvWriter);
                testHeap(new DNaryHeap(d), size, "DNaryHeap", csvWriter);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void testHeap(Object heap, int size, String heapType, FileWriter csvWriter) throws IOException {
        long startTime, endTime;
        Random random = new Random();

        System.out.println("Testing " + heapType + " with size " + size);
        for (int i = 0; i < size; i++) {
            if (heap instanceof BinHeap) {
                ((BinHeap) heap).insert(random.nextInt());
            } else if (heap instanceof DNaryHeap) {
                ((DNaryHeap) heap).insert(random.nextInt());
            }
        }

        // Test insert
        startTime = System.nanoTime();
        if (heap instanceof BinHeap) {
            ((BinHeap) heap).insert(random.nextInt());
        } else if (heap instanceof DNaryHeap) {
            ((DNaryHeap) heap).insert(random.nextInt());
        }
        endTime = System.nanoTime();
        csvWriter.append(size + "," + heapType + ",insert," + (endTime - startTime) + "\n");

        // Test delete
        startTime = System.nanoTime();
        if (heap instanceof BinHeap) {
            ((BinHeap) heap).remove(0);
        } else if (heap instanceof DNaryHeap) {
            ((DNaryHeap) heap).remove(0);
        }
        endTime = System.nanoTime();
        csvWriter.append(size + "," + heapType + ",delete," + (endTime - startTime) + "\n");

        // Test changePriority
        startTime = System.nanoTime();
        if (heap instanceof BinHeap) {
            ((BinHeap) heap).changePriority(0, random.nextInt());
        } else if (heap instanceof DNaryHeap) {
            ((DNaryHeap) heap).changePriority(0, random.nextInt());
        }
        endTime = System.nanoTime();
        csvWriter.append(size + "," + heapType + ",changePriority," + (endTime - startTime) + "\n");

        // Test extractMax
        startTime = System.nanoTime();
        if (heap instanceof BinHeap) {
            ((BinHeap) heap).extractMax();
        } else if (heap instanceof DNaryHeap) {
            ((DNaryHeap) heap).extractMax();
        }
        endTime = System.nanoTime();
        csvWriter.append(size + "," + heapType + ",extractMax," + (endTime - startTime) + "\n");
    }
}
