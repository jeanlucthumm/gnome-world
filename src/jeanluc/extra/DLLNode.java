package jeanluc.extra;

import jeanluc.gnomeworld.Node;

public class DLLNode<E> extends Node<E> {

	private DLLNode<E> previous;
	
	public DLLNode(E data, int index) {
		super(data, index);
		this.previous = null;
	}
	
	public DLLNode(E data) {
		super(data);
		this.previous = null;
	}

	public DLLNode() {
		super();
	}

	public DLLNode<E> getPrevious() {
		return previous;
	}
	
	
	@Override
	public DLLNode<E> getNext() {
		return (DLLNode<E>) super.getNext();
	}

	public void setPrevious(DLLNode<E> previous) {
		this.previous = previous;
	}
	
	public boolean hasPrevious() {
		return previous != null;
	}

}
