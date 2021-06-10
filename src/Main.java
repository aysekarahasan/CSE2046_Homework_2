import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static int[] getItemValues(int itemCount,BufferedReader reader) throws Exception{

        int itemValuesCounter = 0;
        int[] itemValues = new int[itemCount];

        while (itemValuesCounter < itemCount - 1) {

            String line = reader.readLine();
            String[] list = line.split(" ");

            for (int i = 0; i < list.length; i++) {
                itemValues[itemValuesCounter] = Integer.parseInt(list[i]);
                itemValuesCounter++;
            }

        }

        return itemValues;

    }

    public static int[] getKnapsackWeights(int itemCount,BufferedReader reader) throws Exception{

        int itemValuesCounter = 0;
        int[] itemValues = new int[itemCount];

        while (itemValuesCounter < itemCount - 1) {

            String line = reader.readLine();
            String[] list = line.split(" ");

            for (int i = 0; i < list.length; i++) {
                itemValues[itemValuesCounter] = Integer.parseInt(list[i]);
                itemValuesCounter++;
            }

        }

        return itemValues;

    }

    public static void main(String[] args) {

        try {

            BufferedReader reader = new BufferedReader(new FileReader("sample1.txt"));


            String firstLine = reader.readLine();
            String[] firstLineArray = firstLine.split(" ");
            int knapsackCount = Integer.parseInt(firstLineArray[0]);
            int itemCount = Integer.parseInt(firstLineArray[1]); // Same for each knapsack


            int[] itemValues = getItemValues(itemCount,reader);


            int[] knapsackCapacities = new int[knapsackCount];
            String line = reader.readLine();
            String[] list = line.split(" ");

            for (int i = 0; i < knapsackCount; i++) {
                knapsackCapacities[i] = Integer.parseInt(list[i]);
            }

            List<int[]> knapsacksWeights = new ArrayList<int[]>();

            for(int i=0;i<knapsackCount;i++){

                knapsacksWeights.add(getKnapsackWeights(itemCount,reader));

            }

            System.out.println(knapsacksWeights);
            System.out.println(knapsackCapacities);




        } catch (Exception e) {
            System.out.println("Exception: " + e);
        }

    }
}
