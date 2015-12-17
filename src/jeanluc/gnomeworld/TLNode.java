package jeanluc.gnomeworld;

import jeanluc.extra.DLLNode;

public class TLNode<E> extends DLLNode<E> {

	private TLNode<E> left;
	private TLNode<E> right;
	
	public TLNode() {
		super();
	}
	
	public TLNode(E data) {
		super(data);
		this.left = null;
		this.right = null;
	}

	public TLNode(E data, TLNode<E> left, TLNode<E> right) {
		super(data);
		this.left = null;
		this.right = null;
	}

	public TLNode<E> getLeft() {
		return left;
	}

	public TLNode<E> getRight() {
		return right;
	}
	
	public void setLeft(TLNode<E> left) {
		this.left = left;
	}

	public void setRight(TLNode<E> right) {
		this.right = right;
	}

	public boolean hasLeft() {
		return left != null;
	}

	public boolean hasRight() {
		return right != null;
	}

}
