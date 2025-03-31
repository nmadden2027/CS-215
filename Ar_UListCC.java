
public class Ar_UListCC {
	
	public static final int MAXSIZE = 1000000;	
	private CCFraudRecord[] ListItems;
	private int length;
	private int currentPos = 0;
	
	public Ar_UListCC() {
		length = 0; 
		ListItems = new CCFraudRecord[MAXSIZE];
	}
	boolean isFull()  {
		return (length == MAXSIZE); 
	}
	
	int getLength() {
	  return length;
	}

	CCFraudRecord getItem(int gitem) {
		int searchiter;
		for (searchiter = length-1; searchiter>=0; searchiter--) { 
			if (ListItems[searchiter].time == gitem) 
				return ListItems[searchiter];  
		}
		return null;
	}
	
	void makeEmpty() {
		length = 0; 
	}
	
	void putItem(CCFraudRecord pitem) throws Exception {
		if (this.isFull()) {
			throw new Exception("List is full -- unable to add new item.");
		}
		 ListItems[length] = pitem; 
		 length++;
	}
		
	void deleteItem(int ditem) throws Exception { 
		  boolean indexfound=false;
		  for (int loc=0; loc<length; loc++) {
			  if (ListItems[loc].time==ditem) { 
				  indexfound=true;
				  ListItems[loc] = ListItems[length-1];
			  }
		  }
		  if (!indexfound)
			  throw new Exception("Value not present in list -- unable to delete.");
		  length--;
	}
	
	void resetList() {
		currentPos = 0;
	}

	
	CCFraudRecord getNextItem() throws Exception {
		if (currentPos == length)
			throw new Exception("End of list has been reached.");
		
		currentPos++;
		return ListItems[currentPos-1];   
	}

	void printList() { 
		for (int loc=0; loc<length; loc++) {
		  	System.out.println(ListItems[loc].time + ", " + ListItems[loc].amount + " ," + ListItems[loc].fraudclass + "\n");
		}
	}
}
