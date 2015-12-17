package jeanluc.extra;

public class DoublyLinkedList<T> {

	private int length; // how many items in the list
	private DLLNode<T> front; // first node in list
	private DLLNode<T> back; // last node in list

	public DoublyLinkedList() {
		this.length = 0;
		this.front = null;
		this.back = null;
	}

	public int getLength() {
		return length;
	}

	public DLLNode<T> getFront() {
		return front;
	}

	public DLLNode<T> getBack() {
		return back;
	}

	/**
	 * Adds data to the end of the list
	 * 
	 * @param data
	 *            the data to be added
	 */
	public void add(T data) {
		// if line is empty
		if (isEmpty()) {
			// create new front
			front = new DLLNode<T>(data);
			back = front;
			length++;
		} else {
			// add to end
			DLLNode<T> temp = new DLLNode<>(data);
			back.setNext(temp); // tie nodes
			temp.setPrevious(back);
			back = temp; // update back
			length++; // update length
		}
	}

	/**
	 * Removes data from the list while maintain structure.
	 * 
	 * @param data
	 *            the data to be removed
	 * @throws NotFoundException
	 *             if the data could not be found
	 */
	public DLLNode<T> remove(T data) throws NotFoundException {
		// check if line is empty
		if (isEmpty())
			throw new NotFoundException("list is empty");

		// traverse list
		DLLNode<T> itr = front;

		while (true) {
			// check if end of line
			if (itr == null)
				throw new NotFoundException();

			// check if itr has data
			if (itr.getData() == data) {
				if (itr == front)
					front = itr.getNext(); // update front
				else if (itr == back)
					back = itr.getPrevious(); // update back

				if (itr.hasPrevious())
					itr.getPrevious().setNext(itr.getNext()); // update previous
				if (itr.hasNext())
					itr.getNext().setPrevious(itr.getPrevious()); // update next

				length--; // update length
				return itr;
			}
		} // end of while

	} // end of func

	/**
	 * Removes a node from the list while maintaining structure.
	 * 
	 * @param focusNode
	 *            the node to be deleted
	 * @throws NotFoundException
	 *             if the node could not be found
	 */
	public DLLNode<T> remove(DLLNode<T> focusNode) throws NotFoundException {
		// check if line is empty
		if (isEmpty())
			throw new NotFoundException("list is empty");

		// traverse list
		DLLNode<T> itr = front;

		while (true) {
			// check if end of line
			if (itr == null)
				throw new NotFoundException();

			// check if itr has data
			if (itr == focusNode) {
				if (itr == front)
					front = itr.getNext(); // update front
				else if (itr == back)
					back = itr.getPrevious(); // update back

				if (itr.hasPrevious())
					itr.getPrevious().setNext(itr.getNext()); // update previous
				if (itr.hasNext())
					itr.getNext().setPrevious(itr.getPrevious()); // update next

				length--; // update length
				return itr;
			}
		} // end of while

	} // end of func

	/**
	 * Traverses the list and prints out the data of each node.
	 */
	public void print() {
		DLLNode<T> itr = front;

		while (itr != null) {
			System.out.println(itr);
			itr = itr.getNext();
		}
	}

	/**
	 * @return <code>true</code> if the list is empty; <code>false</code>
	 *         otherwise.
	 */
	public boolean isEmpty() {
		return length == 0;
	}

	/**
	 * @return <code>true</code> if the list is full; <code>false</code>
	 *         otherwise.
	 */
	public boolean isFull() {
		return false;
	}

	public static void main(String[] args) {
		DoublyLinkedList<String> x = new DoublyLinkedList<>();

		x.add("asdf");
		x.add("Billy");
		x.add("jean");

		x.print();

	}
} // end of DoublyLinkedList

/**
 * Thrown when an item is not found for whatever reason.
 * 
 * @author Jean-Luc Thumm
 *
 */
class NotFoundException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3072525280383185931L;

	public NotFoundException() {
		super();
	}

	public NotFoundException(String message) {
		super(message);
	}
}
