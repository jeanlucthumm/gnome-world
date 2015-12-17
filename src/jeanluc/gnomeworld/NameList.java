package jeanluc.gnomeworld;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

import jeanluc.extra.DLLNode;


// come with me now

/**
 * Uses linked DLLNode<String>s to create a list of names. Main purpose is to provide
 * unique names until the supply is exhausted.
 * 
 * @author Jean-Luc Thumm, William Bierds
 *
 */
public class NameList {

	private String path; // path of name text file

	private int length; // how many names in the list
	private DLLNode<String> front; // entry point for line
	private DLLNode<String> back; // append point for line

	private Random gen; // random generator

	// CONSTRUCTOR

	/**
	 * Creates an empty list with the potential to be filled with
	 * generateNameList()
	 * 
	 * @param path
	 *            the path of the text file containing the names
	 * @throws IOException
	 *             if text file was not found
	 */
	public NameList(String path) throws IOException {
		this.path = path;
		this.front = null;
		this.gen = new Random();

		generateNameList(path); // fill list
	}

	// GETTERS

	public String getPath() {
		return path;
	}

	public int getLength() {
		return length;
	}

	public DLLNode<String> getFront() {
		return front;
	}

	public DLLNode<String> getBack() {
		return back;
	}

	// LIST FUNCTIONS

	/**
	 * Reads names from file and construct list
	 * 
	 * @param path
	 *            the path of the file to be read
	 * @throws IOException
	 *             if the file is not found
	 */
	public void generateNameList(String path) throws IOException {
		// set up input
		File file = new File(path);

		BufferedReader in = new BufferedReader(new InputStreamReader(
				new FileInputStream(file))); // create reader from file

		// loop through file
		String line, formattedLine;
		while ((line = in.readLine()) != null) { // null = end of file
			formattedLine = line.trim(); // gets rid of whitespace

			if (formattedLine != null)
				this.add(new DLLNode<String>(formattedLine));
		}

		in.close();

		this.path = path; // set new path
	} // end of function

	/**
	 * Adds a DLLNode<String> to the end of the linked list
	 * 
	 * @param n
	 *            the DLLNode<String> to be added
	 */
	public void add(DLLNode<String> n) {
		// if line is empty
		if (isEmpty()) {
			// set the front
			front = n;
			back = n; // front = back if length == 1
			length++; // increase length
			return;
		}

		back.setNext(n); // tie to last DLLNode<String>
		back = n; // shift back
		length++; // increase length
	}

	/**
	 * Gets the name at the specified index
	 * 
	 * @param index
	 * @return the name at the index
	 * @throws IndexOutOfBoundsException
	 *             if the index provided is larger than the current length of
	 *             the list.
	 */
	public String getNameAt(int index) throws IndexOutOfBoundsException {

		// check if index out of bounds
		if (index > length - 1)
			throw new IndexOutOfBoundsException(
					"Name list does not contain that many names");

		DLLNode<String> itr = front; // will traverse list
		int count = 0; // how many DLLNode<String>s traversed

		while (count < index) { // null = end
			itr = itr.getNext(); // traverse
			count++;
		}

		return itr.getData();
	}

	/**
	 * Gets a name at a random index and deletes it from the list to prevent
	 * duplicate names. This limits the amount of Gnomes with names to the
	 * amount of names provided. In this case, that number is 5163 as provided
	 * by formattedNames.txt
	 * 
	 * @return the name at the index
	 * @throws IndexOutOfBoundsException
	 *             if the index provided is larger than the current length of
	 *             the list.
	 */
	public String getUniqueName() throws IndexOutOfBoundsException {

		// get random index in range
		int index = gen.nextInt(length);

		// check if index out of bounds
		if (index > length - 1 || index < 0)
			throw new IndexOutOfBoundsException(
					"Name list does not contain that many names");

		DLLNode<String> temp = null; // DLLNode<String> to be returned;

		DLLNode<String> itr = front; // will traverse list
		int count = 0; // how many DLLNode<String>s traversed

		while (true) { // null = end
			count++;

			if (count == index) { // next DLLNode<String> needs to be returned and deleted

				temp = itr.getNext(); // temp becomes next of current iteration

				itr.setNext(temp.getNext()); // unlink from chain

				if (temp == back)
					itr = back; // reassign back if necessary
				
				length--; // length has decreased

				return temp.getData();
			}
			itr = itr.getNext(); // traverse

		} // end of while

	}

	/**
	 * Traverses the list and prints out the items
	 */
	public void print() {
		// check if line is empty
		if (isEmpty())
			return;

		DLLNode<String> itr = front;

		while (itr != null) {
			System.out.println(itr);
			itr = itr.getNext();
		}
	}

	// CHECKERS

	/**
	 * Check is there are names in the list
	 * 
	 * @return <code>true</code> if there are names; <code>false</code>
	 *         otherwise
	 */
	private boolean isEmpty() {
		return length == 0;
	}

	public static void main(String[] args) throws IOException {
		NameList n = new NameList("formattedNames.txt");
		System.out.println(n.isEmpty());
		n.print();
		System.out.println("=========================");
		System.out.println(n.getUniqueName());
	}
}
