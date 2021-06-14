import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Main {

    public static int[] readMultilineInput(int itemCount, BufferedReader reader) throws Exception {

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

            BufferedReader reader = new BufferedReader(new FileReader("test4.txt"));


            String firstLine = reader.readLine();
            String[] firstLineArray = firstLine.split(" ");
            int knapsackCount = Integer.parseInt(firstLineArray[0]);
            int itemCount = Integer.parseInt(firstLineArray[1]); // Same for each knapsack


            int[] itemValues = readMultilineInput(itemCount, reader);
            int[] knapsackCapacities = readMultilineInput(knapsackCount,reader );

            List<int[]> knapsacksWeights = new ArrayList<int[]>();

            for (int i = 0; i < knapsackCount; i++) {

                knapsacksWeights.add(readMultilineInput(itemCount, reader));

            }



            greedyHeursitic(knapsacksWeights, itemValues, knapsackCapacities);


        } catch (Exception e) {
            System.out.println("Exception: " + e);
        }

    }

    public static List<Item> generateItemsList(List<int[]> knapsackWeights,int[] itemValues, int[] knapsackCapacities){

        List<Item> items = new ArrayList<>();

        for (int i = 0; i < itemValues.length; i++) { // For each item.

            // 1. Calculate 'Total relative weight'

            int value = itemValues[i];

            int[] weights = getWeightsForGivenItem(knapsackWeights, i);

            double sumOfRelativeWeights = getSumOfRelativeWeights(weights, knapsackCapacities);

            double valueOverPercentageWeight = value / sumOfRelativeWeights; // This is the value we will sort respect to.

            Item item = new Item(value, weights, valueOverPercentageWeight, i);
            items.add(item);
        }

        Collections.sort(items); // Sorts items from highest valueOverTotalRelativeWeight to lowest.
        return items;

    }

    public static List<Item> updateItemList(List<Item> items,List<int[]> knapsackWeights,int[] remainingKnapsackCapacities){

        List<Item> newItems = new ArrayList<>();

        for (int i = 0; i < items.size(); i++) { // For each item.

            // 1. Calculate 'Total relative weight'

            int value = items.get(i).value;

            int[] weights = items.get(i).weights;

            double sumOfRelativeWeights = getSumOfRelativeWeights(weights, remainingKnapsackCapacities);

            double valueOverPercentageWeight = value / sumOfRelativeWeights; // This is the value we will sort respect to.

            int index = items.get(i).originalIndex;

            Item item = new Item(value, weights, valueOverPercentageWeight, index);
            newItems.add(item);
        }

        Collections.sort(newItems); // Sorts items from highest valueOverTotalRelativeWeight to lowest.
        return newItems;

    }

    public static void greedyHeursitic(List<int[]> knapsackWeights, int[] itemValues, int[] knapsackCapacities) {


        // 1. Calculate sum of value/weight for each item.

        List<Item> items = generateItemsList(knapsackWeights,itemValues,knapsackCapacities);


        int[] remainingKnapsackCapacities = knapsackCapacities.clone();
        int[] selectedElements = new int[items.size()];
        int totalValue = 0;

        // Now we have the items start the actual process.
        while (items.size() > 0 && Utils.hasMoreSpace(remainingKnapsackCapacities)) {

            // Selected the item with the maximum valueOverTotalRelativeWeight

            Item currentItem = items.remove(0);

            if (!Utils.canItemFit(currentItem, remainingKnapsackCapacities)) {
                continue;
            }
            selectedElements[currentItem.originalIndex] = 1;
            totalValue += currentItem.value;

            updateRemainingCapacities(currentItem.weights, remainingKnapsackCapacities);
            items = updateItemList(items,knapsackWeights,remainingKnapsackCapacities);


        }
        System.out.println("Total Value: " + totalValue);
        System.out.println(Arrays.toString(selectedElements));
        writeToOutputFile(totalValue,selectedElements);


    }

    public static void writeToOutputFile(int totalValue,int[] selectedElements ) {

        try {
            FileWriter file = new FileWriter("output.txt");
            BufferedWriter writer = new BufferedWriter(file);
            writer.write(totalValue+"");
            writer.newLine();

            for(int i : selectedElements){

                writer.write(i+"");
                writer.newLine();
            }

            writer.close();


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static int[] updateRemainingCapacities(int[] itemWeights, int[] remainingKnapsackCapacities) {

        for (int i = 0; i < itemWeights.length; i++) {

            remainingKnapsackCapacities[i] = remainingKnapsackCapacities[i] - itemWeights[i];

        }
        return remainingKnapsackCapacities;

    }

    private static double getSumOfRelativeWeights(int[] weights, int[] knapsackCapacities) {

        /*
        We found the weight of an item in each knapsack (weights array)
        now we divide the weight to the capacity to find the relative weight percentage.
        And add all of them together
        //todo instead of adding them think about averaging or something like that ?
         */

        double sum = 0;

        for (int i = 0; i < weights.length; i++) {

            double weight = weights[i];
            double capacity = knapsackCapacities[i];
            double relativeWeight = weight * 100 / capacity;
            sum += relativeWeight;

        }
        return sum;
    }

    public static int[] getWeightsForGivenItem(List<int[]> knapsackWeights, int index) {

        /*
        Index represents the item so for instance if index: 0 we want to get the weight of first item
        each in knapsack and return them.
         */


        int[] result = new int[knapsackWeights.size()];
        int resultCounter = 0;

        for (int[] arr : knapsackWeights) {

            result[resultCounter] = arr[index];
            resultCounter++;
        }
        return result;

    }
}
