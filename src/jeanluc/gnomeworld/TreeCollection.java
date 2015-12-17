package jeanluc.gnomeworld;

import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Random;

import jeanluc.extra.DLLNode;

/**
 * TreeCollection implements the typ
 * 
 * @author Jean-Luc Thumm
 *
 * @param <E>
 *            The type of Objects to be stored within the collection. One should
 *            be able to write a <code>Comparator</code> class for the Object
 *            type, or else instantiation will be impossible.
 */
public class TreeCollection<E> implements Collection<E> {

	private TLNode<E> root; // first node of the tree
	private TLNode<E> front; // last node of the list, back
	private TLNode<E> back; // front of the list
	private Comparator<E> comparator; // method for ordering the tree
	private int size; // how many items in the list

	/**
	 * Construct a new TreeCollection with the given comparator.
	 * <p>
	 * It is suggested that the constructor should be given an anonymous class
	 * to work with.
	 * </p>
	 * 
	 * @param comparator
	 *            the comparator object which defines how the objects will be
	 *            sorted within the trees
	 */
	public TreeCollection(Comparator<E> comparator) {
		this.root = null;
		this.front = null;
		this.back = null;
		this.comparator = comparator;
		this.size = 0;
	}

	public TLNode<E> getRoot() {
		return root;
	}

	public TLNode<E> getFront() {
		return front;
	}

	public TLNode<E> getBack() {
		return back;
	}

	public Comparator<E> getComparator() {
		return comparator;
	}

	public void setRoot(TLNode<E> root) {
		this.root = root;
	}

	public void setFront(TLNode<E> front) {
		this.front = front;
	}

	public void setBack(TLNode<E> back) {
		this.back = back;
	}

	public void setComparator(Comparator<E> comparator) {
		this.comparator = comparator;
	}

	public int size() {
		return size;
	}

	public boolean isEmpty() {
		return size == 0;
	}

	@SuppressWarnings("unchecked")
	public boolean contains(Object o) {

		// check if data == null
		if (o == null)
			throw new NullPointerException("null data type not allowed");

		// call to recursive function
		return contains(root, (E) o);
	}

	private boolean contains(TLNode<E> focusNode, E data) {

		// check if tree is empty / no tree exists
		if (focusNode == null)
			return false;

		// check down which child we must travel
		if (comparator.compare(data, focusNode.getData()) < 0) {
			// data is smaller => travel left

			return contains(focusNode.getLeft(), data);
		}

		else if (comparator.compare(data, focusNode.getData()) > 0) {
			// data is bigger => travel right

			return contains(focusNode.getRight(), data);
		}

		else
			// data neither bigger or smaller => found node
			return true;
	}

	public Iterator<E> iterator() {

		return new Iterator<E>() {

			Node<E> current = front;

			@Override
			public boolean hasNext() {
				return current != null;
			}

			@Override
			public E next() {
				E tempData = current.getData();
				current = current.getNext();
				return tempData;
			}

		};

	}

	public Object[] toArray() {

		// Create an array large enough to hold all items
		Object[] array = new Object[size];

		// Traverse list and add items
		int index = 0;
		for (E data : this) {
			array[index] = data;
			index++; // next index
		}

		return array;
	}

	public <T> T[] toArray(T[] a) {

		return null;
	}

	public boolean add(E data) {

		// check if data is null
		if (data == null)
			throw new NullPointerException("cannot add null");

		// check if tree is empty
		if (isEmpty()) {

			// make new root
			root = new TLNode<E>(data);

			// list maintenance
			front = root;
			back = root;
			size++;

			return true;
		}

		return add(root, data);
	}

	private boolean add(TLNode<E> focusNode, E data) {

		// check if left is empty
		if (focusNode.getLeft() == null
				&& comparator.compare(data, focusNode.getData()) < 0) {

			// add to tree
			focusNode.setLeft(new TLNode<E>(data));

			// list maintenance
			addToList(focusNode.getLeft());
			size++;

			return true;
		}
		// check if right is empty
		else if (focusNode.getRight() == null
				&& comparator.compare(data, focusNode.getData()) > 0) {

			// add to tree
			focusNode.setRight(new TLNode<E>(data));

			// list maintenance
			addToList(focusNode.getRight());
			size++;

			return true;
		}
		// check if should go left
		else if (comparator.compare(data, focusNode.getData()) < 0) {
			return add(focusNode.getLeft(), data);
		}
		// check if we should go right
		else if (comparator.compare(data, focusNode.getData()) > 0) {
			return add(focusNode.getRight(), data);
		}
		// check if duplicate
		else
			return false;

	}

	private void addToList(TLNode<E> node) {
		back.setNext(node);
		node.setPrevious(back);
		back = (TLNode<E>) back.getNext();
	}

	public boolean remove(Object o) {

		// check for null parameter
		if (o == null)
			throw new NullPointerException("null data unsuported");

		// try type casting
		@SuppressWarnings("unchecked")
		E data = (E) o;

		// check if tree exists
		if (root == null)
			return false;

		// check if root is the one
		if (comparator.compare(root.getData(), data) == 0) {

			// create dummy parent for root
			TLNode<E> dummy = new TLNode<E>();
			dummy.setLeft(root);

			// if root is front => fix front since root will be deleted
			if (root == front)
				front = (TLNode<E>) root.getNext();

			// delete the root and reassign the root pointer to the shifted node
			boolean succesful = remove(root, data, dummy);
			root = dummy.getLeft();

			dummy.setLeft(null); // isolate dummy node

			if (succesful)
				size--; // size decreased only if remove successful
			return succesful;

		} else {
			boolean succesful = remove(root, data, null);

			if (succesful)
				size--; // size decreased only if remove successful

			return succesful;
		}
	}

	private boolean remove(TLNode<E> focusNode, E data, TLNode<E> parent) {

		// find the right node
		if (comparator.compare(data, focusNode.getData()) < 0) {
			// data < focusNode => go left

			// check if reached end. If not => recur
			return (focusNode.hasLeft()) ? remove(focusNode.getLeft(), data,
					focusNode) : false;

		} else if (comparator.compare(data, focusNode.getData()) > 0) {
			// data > focusNode => go down right child

			// check if reached end. If not => recur
			return (focusNode.hasRight()) ? remove(focusNode.getRight(), data,
					focusNode) : false;
		}

		// actual deleting
		else {
			// focusNode == data

			// both children exist
			if (focusNode.hasLeft() && focusNode.hasRight()) {
				// 1. Swap node with minimum on the right subtree
				// 2. Call remove() on that node

				TLNode<E> min = minValue(focusNode.getRight());

				// swap
				cloneNode(min, focusNode);

				return remove(focusNode.getRight(), min.getData(), focusNode);
			}

			// one child is null / both children are null
			else if (parent.getLeft() == focusNode) {

				// right can exist or be null, does not matter
				if (focusNode.hasLeft())
					parent.setLeft(focusNode.getLeft());
				else
					parent.setLeft(focusNode.getRight());

				// delete from list
				removeFromList(focusNode);

				return true;
			} else if (parent.getRight() == focusNode) {

				// right can exist or be null, does not matter
				if (focusNode.hasLeft())
					parent.setLeft(focusNode.getLeft());
				else
					parent.setRight(focusNode.getRight());

				// delete from list
				removeFromList(focusNode);

				return true;
			}

			return false;
		} // end of big else

	} // end of function

	private boolean removeFromList(DLLNode<E> focusNode) {

		// check for special condition after invoking cloneNode()
		if (focusNode.hasPrevious()
				&& focusNode.getPrevious().getNext() == focusNode) {
			focusNode.getPrevious().setNext(focusNode.getNext());
		}

		if (focusNode.hasNext()
				&& focusNode.getNext().getPrevious() == focusNode) {
			focusNode.getNext().setPrevious(focusNode.getPrevious());
		}

		// isolate removed node
		focusNode.setPrevious(null);
		focusNode.setNext(null);

		return true;
	}

	private void cloneNode(TLNode<E> from, TLNode<E> to) {

		to.setData(from.getData()); // copy data

		to.setPrevious(from.getPrevious()); // copy previous and fix list
		if (from.hasPrevious())
			from.getPrevious().setNext(to);

		to.setNext(from.getNext()); // copy next and fix list
		if (from.hasNext())
			from.getNext().setPrevious(to);
	}

	private TLNode<E> minValue(TLNode<E> focusNode) {

		// check if focusNode is null
		if (focusNode == null)
			return focusNode;

		// travel down left branch until reached end
		if (focusNode.hasLeft())
			return minValue(focusNode.getLeft());
		else
			return focusNode;
	}

	public boolean containsAll(Collection<?> c) {
		
		if (c.size() > this.size)
			return false;

		Iterator<?> itr = c.iterator();

		while (itr.hasNext()) {

			// call contains() on each item
			try {
				if (!contains(itr.next()))
					return false;
			} catch (ClassCastException e) {
				// list does not contain objects of that class
				return false;
			}
		}

		return true;
	}

	public boolean addAll(Collection<? extends E> c) {

		Iterator<? extends E> itr = c.iterator();

		while (itr.hasNext()) {

			// add each item
			if (!add(itr.next()))
				return false; // adding failed
		}

		return true;
	}

	public boolean addAll(int index, Collection<? extends E> c) {

		throw new UnsupportedOperationException();
	}

	public boolean removeAll(Collection<?> c) {

		Iterator<?> itr = c.iterator();

		while (itr.hasNext()) {

			// remove each item
			if (!remove(itr.next()))
				return false; // removing failed
		}

		return true;
	}

	public boolean retainAll(Collection<?> c) {

		Iterator<?> itr = c.iterator();

		while (itr.hasNext()) {
			Object current = itr.next();

			// check if item is already in list
			if (!contains(current))
				if (!remove(current))
					return false; // remove failed
		}

		return true;
	}

	public void clear() {
		root = null;
		front = null;
		back = front;
		size = 0;
	}

	/**
	 * Sorts the collection based on the criteria defined in the
	 * <code>Comparator</code> class.
	 * <p>
	 * Internally, sort() simply traverses the tree recursively and in order,
	 * rearranging the next and previous pointers of each node as it moves
	 * along.
	 * </p>
	 */
	public void sort() {

		// check if tree exists
		if (root == null)
			return;

		// get min and set to front
		front = minValue(root);
		back = front;

		sort(root);

		// clear the end of the list
		back.setNext(null);
	}

	private void sort(TLNode<E> focusNode) {

		// go left until hit minimum value
		if (focusNode.hasLeft())
			sort(focusNode.getLeft());

		// special cases if focusNode is front (minimum value, already in list)
		if (focusNode == front && focusNode.hasRight()) {
			// front has right children => add them

			sort(focusNode.getRight());
			return;
		} else if (focusNode == front) {
			// front has no children
			return;
		}

		// focusNode is not front => add to list
		addToList(focusNode);

		// go through each right subtree
		if (focusNode.hasRight())
			sort(focusNode.getRight());
	}
	
	public E getMin() {
		return minValue(root).getData();
	}

	public void printTree() {
		// check if tree exists
		if (root == null)
			return;

		printTree(root);
	}

	private void printTree(TLNode<E> node) {
		if (node.getLeft() != null) {
			printTree(node.getLeft());
		}

		System.out.println(node);

		if (node.getRight() != null) {
			printTree(node.getRight());
		}

	}

	public void printList() {
		for (E data : this) {
			System.out.println(data);
		}
	}

	public static void test() {

		System.out
				.println("Creating TreeCollection. Method of comparison: Integer compareTo()");

		TreeCollection<Integer> c = new TreeCollection<>(
				new Comparator<Integer>() {

					@Override
					public int compare(Integer o1, Integer o2) {
						return o1.compareTo(o2);
					}

				});

		String line = "=========================";

		System.out.println(line + "\nBEGIN INTEGER SIM\n" + line);

		Random gen = new Random();

		for (int i = 0; i < 15; i++) {
			try {
				c.add(gen.nextInt(100));
			} catch (IllegalArgumentException e) {
				System.out.println("Found duplicate, skipping.");
			}
		}

		System.out.println("TREE PRINT");
		c.printTree();

		System.out.println("LIST PRINT");
		c.printList();

		System.out.println("Look up 1400: " + c.contains(1400));
		
		try {
			System.out.println("Look up \"Help\": " + c.contains("Help"));
		} catch (Exception e) {
			e.printStackTrace();
		}

		int del = 5;

		System.out.print("DELETING MEMBER " + del);
		System.out.println(" => Deleted? : " + c.remove(del));

		System.out.println("TREE PRINT");
		c.printTree();

		System.out.println("LIST PRINT");
		c.printList();

		System.out.println("SORTING LIST");
		c.sort();

		System.out.println("LIST PRINT");
		c.printList();

		del = 12;

		System.out.print("DELETING MEMBER " + del);
		System.out.println(" => Deleted? : " + c.remove(del));

		System.out.println("TREE PRINT");
		c.printTree();

		System.out.println("LIST PRINT");
		c.printList();

		System.out.println(line + "\nEND INTEGER SIM\n" + line);
	}
	
	public static void main(String[] args) {
		test();
	}

}
