package sList;

public class Ar_SList {
		public static final int MAXSIZE = 10;	
		private int[] ListItems;
		private int length;
		private int currentPos = 0;
		
		public Ar_SList() {
			length = 0; 
			ListItems = new int[MAXSIZE];
		}
		boolean isFull()  {
			return (length == MAXSIZE); 	
		}
		
		int getLength() {
		  return length;
		}

		int getItem(int item) throws Exception {
			if (currentPos == length) 
				throw new Exception ("End of list reached");
			int midpoint = 0, first = 0, last = length-1;
			boolean moreToSearch = first <= last;
			while(moreToSearch) {
				midpoint = (first + last)/2;
				if(item == ListItems[midpoint]) {
					return midpoint;
				}
				else if(item < ListItems[midpoint]) {
					last = midpoint - 1;
				}
				else {
					first = midpoint+1;
				}
				moreToSearch = first <= last;
			}
			return -1;
		}
		
		
		void makeEmpty() {
			length = 0; 
		}
		
		void putItem(int item) throws Exception{
			if (length == MAXSIZE) 
				throw new Exception("List is full");
		    int loc = 0;
		    boolean moreToSearch = loc < length;
		    while (moreToSearch) {
		        if (item < ListItems[loc]) {
		            moreToSearch = false;  
		        } else {
		            loc++;
		            moreToSearch = loc < length;  
		        }
		    }

		    for (int i = length; i > loc; i--) {
		        ListItems[i] = ListItems[i - 1];
		    }
		    ListItems[loc] = item;
		    length++;  
		}

		
		void deleteItemShift(int item) { 	
			boolean indexfound=false;
			for (int loc=0; loc<length; loc++) {
				if (ListItems[loc]==item) 
					indexfound=true;
				if (indexfound && loc < MAXSIZE - 1)
					ListItems[loc]=ListItems[loc+1];	
			}
			length--;
		}

		
		void deleteItem(int item) throws Exception{
			if (getItem(item) == -1) 
				throw new Exception ("Not in List");
			deleteItemShift(item);
		}
		
		void resetList() {
			currentPos = 0;
		}

		
		int getNextItem() {
			currentPos++;
			return ListItems[currentPos-1];   
		}

		void printList() { 
			System.out.print("(");
			for (int loc=0; loc<length; loc++) {
			  	System.out.print(ListItems[loc]);
				if (loc<length-1)
			  	  System.out.print(", ");
			}
			System.out.println(")");
		}
}
