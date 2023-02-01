
public class LinkedList<T>{
	private Node<T> head;
	private Node<T> current;
	
	public LinkedList () {
		head = current = null;
	}
	
	public boolean empty () {
		return head == null;
	}
	
	public boolean last () {
		return current.next == null;
	}

	public boolean full () { 
		return false;
	}
	public void findfirst () {
	current = head;
	}
	public void findnext () {
		current = current.next;
	}
	public T retrieve () {
		if(current!=null) {
			return current.data;
		}
		else return null;
	} 
	public void update (T val) {
		current.data = val;
	}
	
	public void insert (T val) {
		Node<T> tmp; 
		if (empty()) {
			current = head = new Node<T> (val);
		} 
		else {
				tmp = current.next; 
				current.next = new Node<T> (val); 
				current = current.next; 
				current.next = tmp;
			}
	}

	public void remove () {
			if (current == head) { 
					head = head.next;
			} 
			else {
					Node<T> tmp = head; 
					while (tmp.next != current)
							tmp = tmp.next;
					tmp.next = current.next;
			} 
			if (current.next == null) 
				current = head; 
			else
				current = current.next;
	}

	public void printVisitors(){
		findfirst();
		visitorInfo temp = (visitorInfo) retrieve();
		while(temp != null){
			System.out.println("--------------------");
			temp.printVisitorInfo();
			findnext();
			temp = (visitorInfo) retrieve();
		}
	}


}
