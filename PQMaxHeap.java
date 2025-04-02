public class PQMaxHeap {
	public static final int MAXSIZE = 1000;	
	private int[] HeapItems;
	private int heapSize;
	
	public PQMaxHeap() {
		heapSize = 0; //a newly constructed list has 0 elements
		HeapItems = new int[MAXSIZE];
	}
	
	public PQMaxHeap(int[] arrayele) {
		heapSize = arrayele.length;
		HeapItems = arrayele.clone();
		for (int ind = heapSize/2; ind>=0; ind--) {
			reheapDown(ind,heapSize-1);
		}
	}
	
	public boolean isFull() {
	    return heapSize == MAXSIZE;
	}

	public boolean isEmpty() {
	    return heapSize == 0;
	}

	public void makeEmpty() {
	    heapSize=0;
	}

	private void reheapDown(int top, int bottom) {		
		int maxChInd = top*2+1;
		if (maxChInd > bottom)
			return;		
		if ((maxChInd + 1 <= bottom) && (HeapItems[maxChInd+1]>HeapItems[maxChInd]))
			maxChInd++;					
		if (HeapItems[maxChInd] > HeapItems[top]) {
		      int tmp = HeapItems[maxChInd];
		      HeapItems[maxChInd] = HeapItems[top];
		      HeapItems[top] = tmp;
		      reheapDown(maxChInd,bottom);
		}
	}
	
	private void reheapUp(int top, int bottom) {
		  if (bottom > top) {
		    int parent = (bottom-1) / 2;    		
		    if (HeapItems[parent] < HeapItems[bottom]) {
		      int tmp = HeapItems[parent];
		      HeapItems[parent] = HeapItems[bottom];
		      HeapItems[bottom] = tmp;
		      reheapUp(top, parent);
		    }
		  }
	}

	public int dequeue() throws Exception {
		if (heapSize == 0)
			throw new Exception("Heap Underflow");
		int retval = HeapItems[0];
		HeapItems[0] = HeapItems[heapSize-1];
		heapSize--;
		reheapDown(0,heapSize-1);
		return retval;
	}
	
	public void enqueue(int newitem) throws Exception {
		if (heapSize == MAXSIZE)
			throw new Exception("Heap Overflow");
		HeapItems[heapSize] = newitem;
		heapSize++;
		reheapUp(0,heapSize-1);
	}
	
	
	
}
