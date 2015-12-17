package jeanluc.gnomeworld;

import jeanluc.extra.LinkedList;

public class Test {

	public static void main(String[] args) throws InterruptedException {
		villageTest();
	}

	@SuppressWarnings("unused")
	public static void villageTest() throws InterruptedException {

		System.out.println("Creating village one at 25, 25.");
		Village one = new Village(25, 25);

		System.out.println("Creating village two at 50, 50.");
		Village two = new Village(50, 50);
		
		System.out.println("Creating village three at 100, 90.");
		Village three = new Village(100, 90);
		
		System.out.println("Creating village four at 300, 230.");
		Village four = new Village(300, 230);

		System.out.println("Putting gnome in village one.");
		Gnome a = new Gnome(one);
		
		System.out.println("Querying gnome location");
		Thread.sleep(100);
		while (true) {
			System.out.println(a.getOriginPoint() + " | " + a.isOnRoute());
			Thread.sleep(250);
		}
	}

	public static void roadTest() {
		Road road = new Road(new Village(2, 3), new Village(25, 25));
		long current, after;
		while ((current = System.nanoTime()) != 0 && !road.isBuilt()) {
			System.out.print("Road status: under construction ");
			after = System.nanoTime() - current;
			System.out.println("| checked in " + after + ".");
		}
		System.out.println("==============\nRoad status: built");
	}

	public static void iteratorTest() {
		LinkedList<Integer> list = new LinkedList<>();

		for (int i = 0; i < 20; i++)
			list.add(i);

		for (int i : list) {
			System.out.println(i);
		}
	}
}
