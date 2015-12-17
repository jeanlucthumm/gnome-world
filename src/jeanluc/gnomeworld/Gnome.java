package jeanluc.gnomeworld;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

public class Gnome extends Thread implements Drawable {

	private static double speed = 60; // how fast Gnome travels on roads
	private static long restingTime = 1000; // staying time in villages in milis
	private static LinkedList<Gnome> all = new LinkedList<>();
	private static ArrayList<String> names;
	private static final String PATH = "formattedNames.txt";

	static {
		names = new ArrayList<>();
		try {
			generateNameArray(PATH);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private Village current;
	private String name;

	private boolean onRoute; // true if on a road to another village
	private Road route; // route currently being taken. If in village, this is
						// null
	private double percentage; // completion

	public Gnome(Village start) {
		this.current = start;
		this.name = getRandomName();

		this.current.add(this); // notify village of arrival
		this.onRoute = false;
		this.route = null;
		all.add(this);

		this.start();
	}

	public static LinkedList<Gnome> getAll() {
		return all;
	}

	public static ArrayList<String> getNames() {
		return names;
	}

	public Village getCurrent() {
		return current;
	}

	public String getGnomeName() {
		return name;
	}

	public boolean isOnRoute() {
		return onRoute;
	}

	public Road getRoute() {
		return route;
	}

	public double getPercentage() {
		return percentage;
	}

	public void setGnomeName(String name) {
		this.name = name;
	}

	private static void generateNameArray(String path) throws IOException {
		// set up input
		File file = new File(path);

		BufferedReader in = new BufferedReader(new InputStreamReader(
				new FileInputStream(file)));

		// loop through the file
		String line, formattedLine;
		while ((line = in.readLine()) != null) { // null = end of file
			formattedLine = line.trim();

			if (formattedLine != null)
				names.add(formattedLine);
		}

		in.close();
	}

	private String getRandomName() {
		// name list init happens at compile time
		// => there will always be available names

		return names.get(new Random().nextInt(names.size()));
	}

	public void randomTravel() {

		route = getRandomRoad();

		// check if there were no roads
		if (route == null)
			return;

		onRoute = true; // set flag

		// update current village
		current.leave(this);
		current = route.getTo();
		current.add(this);
		/*
		new Thread(new Runnable() {

			@Override
			public void run() {
				while (updatePercentage(route.getLength()))
					;
			}

		}).start();
		*/
		
		while (updatePercentage(route.getLength())) {};

	}

	private boolean updatePercentage(double distance) {

		// get current progress
		double progress = percentage * distance;

		// add progress
		progress += speed / 100;

		// simulate travel
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
		}

		// update percentage
		percentage = progress / distance;

		// check if reached destination
		if (percentage < 1)
			return true;
		else {
			onRoute = false;
			percentage = 0;
			return false;
		}

	}

	private Road getRandomRoad() {

		// check if there are no roads
		if (current.getRoadsFrom().isEmpty())
			return null;
		LinkedList<Road> built = new LinkedList<>();

		// get all the built roads
		for (Road r : current.getRoadsFrom())
			if (r.isBuilt())
				built.add(r);

		// check if there are built roads
		if (built.isEmpty())
			return null;

		// get random index
		int index = (int) (Math.random() * built.size());

		for (Road r : built) {
			if (--index < 0)
				return r;
		}
		throw new AssertionError("Something went wrong getting a random road");
	}

	@Override
	public void run() {

		while (true) {
			
			// if traveling, no need to call randomTravel()
			if (!onRoute)
				randomTravel();
			
			try {
				Thread.sleep(restingTime);
			} catch (InterruptedException e) {
			}
		}

	}

	@Override
	public Point getOriginPoint() {

		double currentPercentage = percentage; // capture for thread safety;

		if (onRoute) {

			// get the slope of the line
			double deltaX = route.getSecondaryPoint().getX()
					- route.getOriginPoint().getX();
			double deltaY = route.getSecondaryPoint().getY()
					- route.getOriginPoint().getY();

			// calculate where on the line the gnome is
			double currX = route.getOriginPoint().getX()
					+ (deltaX * currentPercentage);
			double currY = route.getOriginPoint().getY()
					+ (deltaY * currentPercentage);

			// create new point
			Point temp = new Point();
			
			temp.setLocation(currX, currY);
			return temp;

		} else {

			// In a village
			return current.getOriginPoint();
		}

	}

	@Override
	public Point getSecondaryPoint() {
		return null;
	}

	@Override
	public boolean hasSecondaryPoint() {
		return false;
	}

	@Override
	public double getDistanceTo(Drawable other) {
		return getOriginPoint().distance(other.getOriginPoint());
	}
}
