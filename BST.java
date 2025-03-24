
public class BST {

	private class TNode
	{
		public int item;
	    public TNode left;
	    public TNode right;

	    public TNode(int newitem)
	    {
	    	item = newitem;
	    	left = null;
	    	right = null;
	    }
	}
	private TNode root;
	CL_Queue travQueue;	
	
	public BST() {
		root = null;	
		travQueue=new CL_Queue();
	}
	
	public BST(TNode cpNode) {
		root = cpNode;	
		travQueue=new CL_Queue();
	}
	
	private int countNodes(TNode curnode) { //Return total number of nodes in (sub-)tree denoted by curnode
		  if (curnode == null)
		    return 0;
		  else 
		    return countNodes(curnode.left) + countNodes(curnode.right) + 1;
		}

	public int getLength()
	{
		return countNodes(root);
	}

	public boolean isEmpty()
	// Returns true if the tree is empty; false otherwise.
	{
		return root == null;
	}
		  
	private TNode insert (TNode curnode, int newitem) { //Helper function to insert an item into (sub-)tree denoted by curnode
	  if (curnode==null) {
	    curnode = new TNode(newitem);
	    return curnode;
	  } else if (newitem<curnode.item)
	    curnode.left = insert(curnode.left,newitem);
	  else
	    curnode.right = insert(curnode.right,newitem);
	  return curnode;
	}
	
	public void putItem(int newitem) {
		root = insert(root,newitem);
	}  
	
		  
	private int findItem(TNode curnode, int gitem) { //Helper function to locate an item in (sub-)tree denoted by curnode
	  if (curnode == null)
	    return -1;
	  else if (gitem == curnode.item)
	    return curnode.item;
	  else if (gitem < curnode.item)
	    return findItem(curnode.left,gitem);
	  else
	    return findItem(curnode.right,gitem);
	}

	public int getItem(int gitem) {
	  return findItem(root, gitem);
	}
	
	private TNode deleteNode(TNode delnode, int ditem) { 
		  
		  if (delnode.left == null) { 
		    return delnode.right;
		  } else if (delnode.right == null) { //Check to see if other easy case applies (right child is NULL, but left is not)
		    return delnode.left;
		  } else { //Difficult case -- we need to replace item with that of the logical predecessor
		    TNode predecessor = delnode.left;
		    while (predecessor.right != null) {	
		      predecessor = predecessor.right;
		    }
		    int replaceitem = predecessor.item;
		    delnode.item = replaceitem;
		    delnode.left = delete(delnode.left, replaceitem);
		    return delnode;
		  }     
		  
	}



	private TNode delete(TNode curnode, int ditem) { //Helper function to delete an item in (sub-)tree denoted by curnode
	  if (ditem < curnode.item)
	    curnode.left = delete(curnode.left, ditem);   // Look in left subtree.
	  else if (ditem > curnode.item)
	    curnode.right = delete(curnode.right, ditem);  // Look in right subtree.
	  else
	    curnode = deleteNode(curnode, ditem);           // Node found; call DeleteNode.
	  return curnode;
	}   


	public void deleteItem(int ditem) {
	  delete(root,ditem);
	}

	public void printNodes(TNode curnode) { // Prints items in (sub-)tree in sorted order
	  if (curnode != null) {
	    printNodes(curnode.left);   // Print left subtree.
	    System.out.print(curnode.item + ", ");
	    printNodes(curnode.right);   // Print right subtree.
	  }
	}

	public void printTree() {// Calls recursive function PrintNodes to print items in the tree.
	  if (isEmpty())
	    System.out.println("(Empty Tree)");
	  else {
	    printNodes(root);
	    System.out.println("\b\b   ");
	  }
	}

	private TNode copyNodes(TNode origNode) {	  
	  if (origNode == null)
	    return null;
	  else {
	    TNode copyNode = new TNode(origNode.item);
	    copyNode.left = copyNodes(origNode.left);
	    copyNode.right = copyNodes(origNode.right);
	    return copyNode; 
	  }
	}

	public BST copy() { //Constructor that calls recursive function CopyNodes to copy original tree into root		
		TNode newroot = copyNodes(root);
		BST cpBSTE = new BST(newroot);
		return cpBSTE;
	}
		
	public boolean travEmpty() {
		return travQueue.isEmpty();	
	}

	private void preNodes(TNode curNode) {    
	  if (curNode != null) {
	    travQueue.enqueue(curNode.item);		    
		preNodes(curNode.left);    
	    preNodes(curNode.right);
	  }
	}

	private void inNodes(TNode curNode) {    
	  if (curNode != null) {
		inNodes(curNode.left);
		travQueue.enqueue(curNode.item);		    
	    inNodes(curNode.right);
	  }
	}
	
	private void postNodes(TNode curNode) {    
	  if (curNode != null) {
		postNodes(curNode.left);
	    postNodes(curNode.right);
		travQueue.enqueue(curNode.item);		    
	  }
	}
	
	public int getNextItem() throws Exception {
		if (travQueue.isEmpty())
		  return Integer.MIN_VALUE;
		else
		  return travQueue.dequeue();
	}

	public void resetTree(String order) {
	  travQueue.makeEmpty();  
	  switch (order) {
	    case "PRE" : preNodes(root);
	    	break;
	    case "IN" : inNodes(root);
    		break;
	    case "POST" : postNodes(root);
    		break;
      }
	  
	}
	
	public void splitBalance() throws Exception{
		resetTree("IN");
		int treeLength = getLength();
		int[] myElements = new int [getLength()];
		int len = 0;
		while(!travEmpty()) {
			myElements[len] = travQueue.dequeue();
			len++;
		}
		//System.out.println(myElements);
		root=null;
		SBHelper(myElements,0,treeLength-1);
	}

	private void SBHelper(int[] myElements, int lindex, int rindex) {
		if (rindex - lindex >= 0) {
		int midpoint = (lindex + rindex) /2;
		putItem(myElements[midpoint]);
		SBHelper(myElements,lindex,midpoint-1);
		SBHelper(myElements,midpoint+1,rindex);
		}
	}
		
}

	