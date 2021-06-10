public class Utils {


    public static boolean hasMoreSpace(int[] list) {

        /*
        If every element of list is zero there is no more space.
        So stop the algorithm.
         */

        for (int i : list) {
            if (i > 0) {
                return true;
            }
        }
        return false;

    }

    public static boolean canItemFit(Item item, int[] capacities) {

        int[] weights = item.weights;

        for (int i = 0; i < capacities.length; i++) {

            if (weights[i] > capacities[i]) {
                return false;
            }

        }
        return true;
    }

}
