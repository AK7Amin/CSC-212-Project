
public class ArrayStack<T> {
	private int maxsize;
	private int top;
	private T[] nodes;
	
	public ArrayStack(int n) {
		maxsize = n ;
		top = -1 ;
		nodes = (T[]) new  Object[n]; 
	}
	
	public boolean empty() {
		return top == -1;
	}
	public boolean full() {
		return top == maxsize - 1 ; 
	}
	
	public void push (T e) {
		nodes[++top] = e;
	}
	
	public T pop() {
		if(!empty()) {
			return nodes[top--];
		}
		else return null;
	}

	@Override
	public String toString() {
		String output = "";
		ArrayStack tempArrayStack = new ArrayStack(maxsize);
		T tempNode;
		for(int i = 0; i <= top; i++){
			output += nodes[i];
		}
		return output;
	}
}
