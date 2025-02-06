package stackqueue;

public class Ar_StackInt {
	public static final int MAXSIZE = 10;
	private int top;
	private int StackItems[];
	public Ar_StackInt() {
		top = -1;
		StackItems = new int[MAXSIZE];
	}

	public boolean isEmpty() {
		return top == -1;
	}

	public boolean isFull() {
		return top == MAXSIZE -1;
	}

	
	void push(int item) { 
		  top++;
		  StackItems[top] = item;
	}
	
	int pop() { 
		  int tmpitem=StackItems[top];
		  top--;
		  return tmpitem;

	}
	
	int peek() { 
		  return StackItems[top]; 
	}

	

}
