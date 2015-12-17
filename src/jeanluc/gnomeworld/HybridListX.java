package jeanluc.gnomeworld;

import jeanluc.extra.DoublyLinkedList;

public class HybridListX<T extends Comparable<T>> {

	private TLNode<T> root; // root of the tree
	private DoublyLinkedList<T> list; // the list the tree interfaces with
	
	public HybridListX() {
		root = null;
		list = null;
	}

	/**
	 * @return the root
	 */
	public TLNode<T> getRoot() {
		return root;
	}

	/**
	 * @return the list
	 */
	public DoublyLinkedList<T> getList() {
		return list;
	}
	
	/**
	 * @return the amount of items currently in the list
	 */
	public int getLength() {
		return list.getLength();
	}

	/**
	 * @param root the root to set
	 */
	public void setRoot(TLNode<T> root) {
		this.root = root;
	}

	/**
	 * @param list the list to set
	 */
	public void setList(DoublyLinkedList<T> list) {
		this.list = list;
	}
	
	public boolean isFull() {
		return false;
	}
	
	public boolean isEmpty() {
		return list.getLength() == 0;
	}
	
	/**
	 * Looks for a certain value in the Binary Search Tree. Uses a private
	 * helper method.
	 * 
	 * @param data
	 *            the data value to look for
	 * @return <code>true</code> if the value was found; <code>false</code> if
	 *         the value was not found.
	 */
	public boolean lookup(T data) {
		return lookup(root, data);
	}

	/**
	 * Helper method that uses recursion to look for a value.
	 * 
	 * @param n
	 *            the current node
	 * @param data
	 *            the data to look for
	 * @return <code>true</code> if the value was found; <code>false</code> if
	 *         the value was not found.
	 */
	private boolean lookup(TLNode<T> node, T data) {
		// check if reached end / no tree exists
		if (node == null)
			return false;

		// check down which child we must travel
		if (data.compareTo(node.getData()) < 0) {
			// data is smaller => travel left
			boolean temp = lookup(node.getLeft(), data);
			return temp;
		}

		else if (data.compareTo(node.getData()) > 0) {
			// data is bigger => travel right
			boolean temp = lookup(node.getRight(), data);
			return temp;
		}

		else
			// data neither bigger or smaller => found node
			return true;
		
		
	}
	
		
}
