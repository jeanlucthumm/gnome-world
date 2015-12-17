package jeanluc.gnomeworld;

import java.util.Collection;
import java.util.Iterator;
import java.util.Random;

public class LinkedCollection<E> implements Collection<E> {

	private Node<E> front, back;
	private int size;

	public E getFront() {
		return front.getData();
	}

	public E getBack() {
		return back.getData();
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	@Override
	public boolean contains(Object o) {

		if (o == null)
			return false;

		boolean found = false;

		// traverse list
		for (E data : this) {
			if (data.equals(o))
				found = true;
		}

		return found;
	}

	@Override
	public Iterator<E> iterator() {

		return new Iterator<E>() {

			Node<E> current = front;

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
		};
	}

	@Override
	public Object[] toArray() {

		// create large enough array
		Object[] array = new Object[size];

		// traverse list and add items
		int index = 0;

		for (E data : this) {
			array[index] = data;
			index++;
		}

		return array;
	}

	@Override
	public <T> T[] toArray(T[] a) {

		return null;
	}

	@Override
	public boolean add(E e) {

		// check if input is null
		if (e == null)
			return false;

		E data = e;

		// check if line is empty
		if (isEmpty()) {
			front = new Node<>(data);
			back = front;
		} else {
			back.setNext(new Node<>(data));
			back = back.getNext();
		}

		size++; // collection has grown
		return true;
	}

	@Override
	public boolean remove(Object o) {

		// check if line is empty
		if (isEmpty())
			return false;

		// check if parameter is null
		if (o == null)
			return false;

		// check if it is front
		if (front.getData() == o) {

			if (front.hasNext())
				front = front.getNext();
			else
				front = null;

			size--;
			return true;
		}

		// traverse collection
		Node<E> itr = front;
		while (itr.hasNext()) {

			// check if itr child has data
			if (itr.getNext().getData() == o) {

				// retie
				itr.setNext(itr.getNext().getNext());

				size--;
				return true;
			}

			itr = itr.getNext();
		}

		return false; // could not find node
	}

	@Override
	public boolean containsAll(Collection<?> c) {

		// collection cannot contain other collection greater than itself
		if (c.size() > this.size)
			return false;

		for (Object o : c) {
			if (!contains(o))
				return false; // found object not contained in collection
		}
		return true;
	}

	@Override
	public boolean addAll(Collection<? extends E> c) {

		boolean success = true;

		// traverse collection and add each
		for (E data : c)
			if (!add(data))
				success = false; // failed to add

		return success;
	}

	@Override
	public boolean removeAll(Collection<?> c) {

		boolean success = true;

		// traverse collection and remove each
		for (Object data : c)
			if (!remove(data))
				success = false; // failed to remove

		return success;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean retainAll(Collection<?> c) {

		clear();
		try {
			return addAll((Collection<? extends E>) c);
		} catch (ClassCastException e) {
			return false;
		}
	}

	@Override
	public void clear() {
		front = null;
		back = null;
		size = 0;
	}

	public void print() {
		// traverse and print
		for (E data : this) {
			System.out.println(data);
		}
	}

	public static void test() {

		System.out.println("Creating LinkedCollection. Data Type: Integer");

		LinkedCollection<Integer> c = new LinkedCollection<>();

		String line = "=========================";

		System.out.println(line + "\nBEGIN INTEGER SIM\n" + line);

		Random gen = new Random();

		for (int i = 0; i < 15; i++) {
			c.add(gen.nextInt(100));
		}

		System.out.println("Size of collection: " + c.size());

		System.out.println("PRINT");
		c.print();

		System.out.println("Look up 1400: " + c.contains(1400));

		System.out.println("Look up \"Help\": " + c.contains("Help"));

		int del = 5;

		System.out.print("DELETING MEMBER " + del);
		System.out.println(" => Deleted? : " + c.remove(del));
		System.out.println("Size of collection: " + c.size());

		System.out.println("PRINT");
		c.print();

		del = 12;

		System.out.print("DELETING MEMBER " + del);
		System.out.println(" => Deleted? : " + c.remove(del));
		System.out.println("Size of collection: " + c.size());

		System.out.println("PRINT");
		c.print();

		System.out.println(line + "\nEND INTEGER SIM\n" + line);
	}

	public static void main(String[] args) {
		test();
	}

}
