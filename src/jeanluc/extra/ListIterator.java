package jeanluc.extra;

import java.util.Iterator;
import java.util.List;

import jeanluc.gnomeworld.Node;

public class ListIterator<E> implements Iterator<E> {
	
	LinkedList<E> list;
	Node<E> current;
	
	@SuppressWarnings("unchecked")
	public ListIterator(List<E> list) {
		this.list = (LinkedList<E>) list;
		this.current = ((LinkedList<E>) list).getFront();
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
