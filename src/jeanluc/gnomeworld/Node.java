package jeanluc.gnomeworld;

public class Node<E> {
	
	E data;
	Node<E> next;
	Integer index;


	public Node() {
		this.data = null;
		this.next = null;
		this.index = null;
	}
	
	public Node(E data) {
		this.data = data;
		this.next = null;
		this.index = null;
	}
	
	public Node(E data, int index) {
		this.data = data;
		this.next = null;
		this.index = index;
	}

	public Node<E> getNext() {
		return next;
	}

	public E getData() {
		return data;
	}
	
	public int getIndex() {
		return index;
	}

	public void setNext(Node<E> next) {
		this.next = next;
	}

	public void setData(E data) {
		this.data = data;
	}
	
	public void setIndex(int index) {
		this.index = index;
	}

	public boolean hasNext() {
		return next != null;
	}
	
	public boolean hasData() {
		return data != null;
	}
	
	public boolean hasIndex() {
		return index != null;
	}
	

	@Override
	public String toString() {
		return data.toString();
	}
}
