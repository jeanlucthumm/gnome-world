package jeanluc.extra;

import java.util.Iterator;

import jeanluc.gnomeworld.Node;

public class LinkedList<T> implements Iterable<T> {

	protected Node<T> front, back;
	private int length;

	public LinkedList() {
		front = null;
		back = null;
	}

	public Node<T> getFront() {
		return front;
	}

	public Node<T> getBack() {
		return back;
	}

	public int getLength() {
		return length;
	}

	public boolean isEmpty() {
		return length == 0;
	}

	public void add(T data) {
		// check if line if empty
		if (isEmpty()) {
			front = new Node<>(data);
			back = front;
		} else {
			back.setNext(new Node<>(data));
			back = back.getNext();
		}
		length++;
	}

	public T remove(T data) {
		// check if line is empty
		if (isEmpty())
			return null;

		// check if it is front
		if (front.getData() == data) {
			if (front.hasNext())
				front = front.getNext();
			else
				front = null;
			length--;
			return data;
		}

		// traverse
		Node<T> itr = front;
		while (itr.hasNext()) {
			// if itr child has data
			if (itr.getNext().getData() == data) {
				// retie
				itr.setNext(itr.getNext().getNext());
				length--;
				return data;
			}

			itr = itr.getNext();
		}

		return null; // could not find node;
	}
	
	

	public Node<T> findNode(T data) {
		// traverse list until found
		Node<T> itr = front;
		while (itr != null) {
			if (itr.getData() == data)
				break;
			itr = itr.getNext();
		}
		
		return itr;
	}

	public void print() {
		Node<T> itr = front;
		while (itr != null) {
			System.out.println(itr);
			itr = itr.getNext();
		}
	}

	@Override
	public Iterator<T> iterator() {
		return new LinkedListIterator<T>(this);
	}

}


class LinkedListIterator<E> implements Iterator<E> {
	
	LinkedList<E> list;
	Node<E> current;
	
	public LinkedListIterator(LinkedList<E> list) {
		this.list = list;
		this.current = list.getFront();
	}

	@Override
	public boolean hasNext() {
		return current != null;
	}

	@Override
	public E next() {
		E temp = current.getData();
		current = current.getNext();
		return temp;
	}
	
}
