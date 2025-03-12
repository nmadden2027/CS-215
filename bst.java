public class bst {
	private class TNode {
		public int item;
		public TNode left;
		public TNode right;
		public TNode(int newitem) {
			item = newitem;
			left = null;
			right = null;
		}
	}
	private TNode root;
	public bst() {
		root = null;
	}
	
	private TNode insert(TNode curnode, int newitem) {
		if (curnode == null) {
			return new TNode(newitem);
		}
		else if (newitem<curnode.item)
			curnode.left = insert(curnode.left, newitem);
		else
			curnode.right = insert(curnode.right, newitem);
		return curnode;
	}
	
	public void putItem(int newitem) {
	    root = insert(root,newitem);
	} 
	
	private int findItem(TNode curnode, int gitem) {
		if(curnode == null)
				return Integer.MAX_VALUE;
		else if (curnode.item == gitem)
			return curnode.item;
		else if (gitem <curnode.item)
			return findItem(curnode.left, gitem);
		else
			return findItem(curnode.right, gitem);
	}
	
	public int getItem(int gitem) {
		return findItem(root, gitem);
	}
	
	public boolean isEmpty() {
		return root == null;
	}
	
	private void printNodes(TNode curnode) {
		if (curnode != null) 
			printNodes(curnode.left);
			System.out.print(curnode.item + ", ");
			printNodes(curnode.right);
	}
		
	public void printTree() {
		if (isEmpty())
			System.out.println("Empty Tree");
		else
			printNodes(root);
			
	}
	
	
}
