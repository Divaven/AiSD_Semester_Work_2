import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        SplayTree splayTree = new SplayTree();

        String path = "src/data";
        List<Integer> numbersList = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] numbers = line.trim().split("\\s+");
                for (String number : numbers) {
                    if (!number.isEmpty()) {
                        numbersList.add(Integer.parseInt(number));
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        int[] array = new int[numbersList.size()];
        for (int i = 0; i < numbersList.size(); i++) {
            array[i] = numbersList.get(i);
        }

        long total_Insert_Cnt = 0;
        long total_Search_Cnt = 0;
        long total_Delete_Cnt = 0;
        long total_Insert_Time = 0;
        long total_Search_Time = 0;
        long total_Delete_Time = 0;

        System.out.println("\n  Поэлементное добавление чисел в структуру:");
        for (int i = 0; i < array.length; i++) {
            long start_time = System.nanoTime();
            splayTree.insert(array[i]);
            long end_time = System.nanoTime();
            long ans_time = end_time - start_time;
            total_Insert_Time += ans_time;
            total_Insert_Cnt += splayTree.getInsertCnt();
            //System.out.println(ans_time);
            System.out.printf("Добавление элемента %d: Время: %d нс, Операции: %d%n", array[i], ans_time, splayTree.getInsertCnt());
        }

        System.out.println("\n\n   Поиск 100 случайных элементов в структуре:");
        Random random = new Random();
        for (int i = 0; i < 100; i++) {
            long k = array[random.nextInt(array.length)];
            long start_time = System.nanoTime();
            splayTree.find(k);
            long end_time = System.nanoTime();
            long ans_time = end_time - start_time;
            total_Search_Time += ans_time;
            total_Search_Cnt += splayTree.getSearchCnt();
            //System.out.println(ans_time);
            System.out.printf("Поиск элемента %d: Время: %d нс, Операции: %d%n", k, ans_time, splayTree.getSearchCnt());
        }

        System.out.println("\n\n    Удаление 1000 случайных элементов из структуры:");
        for (int i = 0; i < 1000; i++) {
            long k = array[random.nextInt(array.length)];
            long start_time = System.nanoTime();
            splayTree.delete(k);
            long end_time = System.nanoTime();
            long ans_time = end_time - start_time;
            total_Delete_Time += ans_time;
            total_Delete_Cnt += splayTree.getDeleteCnt();
            //System.out.println(ans_time);
            System.out.printf("Удаление элемента %d: Время: %d нс, Операции: %d%n", k, ans_time, splayTree.getDeleteCnt());
        }

        long average_Insert_Cnt = total_Insert_Cnt / array.length;
        long average_Search_Cnt = total_Search_Cnt / 100;
        long average_Delete_Cnt = total_Delete_Cnt / 1000;

        long average_Insert_Time = total_Insert_Time / array.length;
        long average_Search_Time = total_Search_Time / 100;
        long average_Delete_Time = total_Delete_Time / 1000;


        System.out.println();
        System.out.println("Среднее количество операций вставки: " + average_Insert_Cnt);
        System.out.println("Среднее количество операций поиска: " + average_Search_Cnt);
        System.out.println("Среднее количество операций удаления: " + average_Delete_Cnt);
        System.out.println("Среднее время вставки: " + average_Insert_Time + " нс");
        System.out.println("Среднее время поиска: " + average_Search_Time + " нс");
        System.out.println("Среднее время удаления: " + average_Delete_Time + " нс");
    }

}
