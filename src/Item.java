
public class Item implements Comparable<Item> {

    int value;
    int[] weights;
    double valueOverTotalRelativeWeight;

    /*
    Only required because our input format
     */
    int originalIndex;

    Item(int value, int[] weights, double valueOverTotalRelativeWeight,int originalIndex) {

        this.value = value;
        this.weights = weights;
        this.valueOverTotalRelativeWeight = valueOverTotalRelativeWeight;
        this.originalIndex = originalIndex;
    }

    @Override
    public int compareTo(Item item) {
        if (this.valueOverTotalRelativeWeight > item.valueOverTotalRelativeWeight) {
            return -1;
        } else {
            return 1;
        }
    }
}
