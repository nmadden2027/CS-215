
public class Sorting {
    public static int[] InsertSort(int[] arrayToSort) throws Exception {
    	int [] sortedArray = arrayToSort.clone();
        for (int j = 1; j < sortedArray.length; j++) {
            int nextItem = sortedArray[j];
            int i = j - 1;
            while (i >= 0 && sortedArray[i] > nextItem) {
                sortedArray[i + 1] = sortedArray[i];
                i = i - 1;
            }
            sortedArray[i + 1] = nextItem;
        }
        return sortedArray;
    }
    public static int[] HeapSort(int[] arrayToSort) throws Exception {
    	int[] sortedArray = new int[arrayToSort.length];
    	PQMaxHeap sortheap = new PQMaxHeap(arrayToSort);
    	for (int i = 0; i < arrayToSort.length;i++) {
    		sortedArray[i] = sortheap.dequeue();
    	}
		return sortedArray;
    }
}
