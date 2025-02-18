public class CL_SList {
	private class LNode
	{
		public int item;
	    public LNode next;
	    public LNode(int newitem)
	    {
	    	item = newitem;
	    	next = null;
	    }
	}
	private LNode ListEnd;
	
	public CL_SList() {
		ListEnd = null;		
	}
	
	
	boolean isFull()  {
		return false;
	}
	
	boolean isEmpty() {
		return ListEnd == null;
	}
	
	public int getLength() {
		if (isEmpty())
			return 0;
		else {
			LNode iterPos = ListEnd.next;
			int length=1;
			while (iterPos != ListEnd) {
				iterPos = iterPos.next;
				length++;
			}
			return length;
		}
	}
	private LNode FindItemNode(int fitem, int[] position) {
		position[0]=0;
		LNode nodeIter=ListEnd;
		   while ((nodeIter != null) && (nodeIter.next !=ListEnd ) && (nodeIter.next.item <fitem)) {
		      nodeIter=nodeIter.next;
		      position[0]++;
		   }
		   if ((nodeIter == null) || (nodeIter.next.item != fitem))
			   position[0] = -1;
		   return nodeIter;
	}

	public int getItem(int gitem) {
		int[] position = {0};
		FindItemNode(gitem,position);
		return position[0];
	}
	
	public void makeEmpty() {
		ListEnd = null; 
	}
	
	public void putItem(int pitem) throws Exception {
		LNode newNode = new LNode(pitem);
		if (ListEnd == null) {
			ListEnd = newNode; 
			ListEnd.next = ListEnd;
		} else if (newNode.item > ListEnd.item) {
			newNode.next = ListEnd.next;
			ListEnd.next = newNode;
			ListEnd = newNode;
		} else {
			LNode nodePred = FindItemNode(pitem, new int[1]);			
			newNode.next = nodePred.next;
			nodePred.next = newNode;
		}
	}
	
	void deleteItem(int ditem) throws Exception { 
		int testarr[] = new int[1];
		LNode predNode = FindItemNode(ditem,testarr);
	    if (testarr[0] == -1)
	        throw new Exception("Item not present in list.");
		if (predNode.next == predNode)
			ListEnd = null;
		else {
			if (predNode.next == ListEnd) 
				ListEnd = ListEnd.next;
			predNode.next = predNode.next.next;
		}
	}	

	void printList() { 
		if (ListEnd == null)
			System.out.println("()");
		else {
			LNode iterPos = ListEnd.next;
			System.out.print("(" + iterPos.item);		
			while (iterPos != ListEnd) {
				iterPos = iterPos.next;
				System.out.print(", " + iterPos.item);			
			}				
			System.out.println(")");
		}
	}
}