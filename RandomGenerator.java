import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class RandomGenerator {
    public static int[] generateFiles(int n) {
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = (int) (Math.random() * 10000);
        }
        return a;
    }

    public static void main(String[] args) throws IOException {
        int[] m = generateFiles(10000);
        File file = new File("src/data");
        FileWriter writer = new FileWriter(file);
        for (int i = 0; i < m.length; ++i) {
            writer.write(m[i] + "\n");
        }
        writer.close();
    }
}
