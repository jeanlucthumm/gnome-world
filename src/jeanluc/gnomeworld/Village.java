package jeanluc.gnomeworld;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.concurrent.LinkedBlockingQueue;

public class Village implements Drawable {

	// contains every village created
	private static LinkedBlockingQueue<Village> all = new LinkedBlockingQueue<>();
	private Point origin; // location of village in 2D Cartesian space
	private int population; // how many gnomes currently in the village
	private static int count = 0;

	private LinkedBlockingQueue<Road> roadsFrom; // raods from this vlillage
	private LinkedBlockingQueue<Road> roadsTo; // roads to this village
	private LinkedBlockingQueue<Gnome> gnomes; // current gnomes in village

	private int name; // name of the village

	private static double range = 50; // the range from the min distance where
										// villages will be connected to current
										// village

	/**
	 * Constructs a village at the specified point
	 * 
	 * @param origin the location of the new village
	 */
	public Village(Point origin) {
		this.origin = origin;
		this.population = 0;

		this.roadsFrom = new LinkedBlockingQueue<>();
		this.roadsTo = new LinkedBlockingQueue<>();
		this.gnomes = new LinkedBlockingQueue<>();

		this.name = count;
		count++;

		all.add(this);
		this.connectToGrid();
	}

	/**
	 * Constructs a village at the specified coordinates
	 * 
	 * @param x x coordinate of new village
	 * @param y y coordinate of new village
	 */
	public Village(int x, int y) {
		this(new Point(x, y));
	}

	/**
	 * This method deletes the 
	 * 
	 * @param v
	 * @return
	 */
	public static boolean delete(Village v) {

		// close off roadsJ
		for (Road closed : v.roadsTo) {

			// cannot delete village while under construction
			if (!closed.isBuilt())
				return false;
			else
				closed.setBuilt(false);
		}
		
		// capture gnome population for monitoring
		ArrayList<Gnome> currentGnomes = new ArrayList<>(v.population);
		for (Gnome g : v.gnomes) {
			currentGnomes.add(g);
		}

		// wait until population is 0;
		
		/*//FIXME
		while (v.population != 0) {
			System.out.println("Stuck in 0 pop loop"); //FIXME
		}
		*/
		
		/*//FIXME
		// check if all gnomes reached destination
		boolean reached = false;
		while(!reached) {
			reached = true;
			for (Gnome g : currentGnomes) {
				if (g.isOnRoute())
					reached = false;
			}
		}
		*/
		
		// delete all roads from
		for (Road r : v.roadsFrom) {
			r.getTo().roadsTo.remove(r); // notify village
			Road.getAll().remove(r); // update master lsit
			Road.getDoubles().remove(r);
		}
		for (Road r : v.roadsTo) {
			r.getFrom().roadsFrom.remove(r);
			Road.getAll().remove(r);
			Road.getDoubles().remove(r);
		}
		
		// remove village
		Village.all.remove(v);
		
		return true;

	}

	public int getName() {
		return name;
	}

	public void setName(int name) {
		this.name = name;
	}

	public LinkedBlockingQueue<Road> getRoadsFrom() {
		return roadsFrom;
	}

	public LinkedBlockingQueue<Road> getRoadsTo() {
		return roadsTo;
	}

	public static LinkedBlockingQueue<Village> getAll() {
		return all;
	}

	public int getPopulation() {
		return population;
	}

	public LinkedBlockingQueue<Gnome> getGnomes() {
		return gnomes;
	}
	

	public void connectToGrid() {

		// check if there are villages other than this
		if (all.size() <= 1)
			return;

		Hashtable<Village, Double> distances = new Hashtable<>(all.size());

		// get all the distances
		for (Village village : all) {
			distances.put(village, village.getDistanceTo(this));
		}

		// find the minimum
		Village min = all.peek();
		double minDistance = distances.get(min);
		double currentDistance;

		for (Village village : all) {

			if (village == this)
				continue;

			currentDistance = distances.get(village);
			if (currentDistance < minDistance) {
				min = village;
				minDistance = currentDistance;
			}
		}

		// add all within range
		double ceil = minDistance + range;

		for (Village village : all) {

			// skip itself
			if (village == this)
				continue;

			currentDistance = distances.get(village);
			if (currentDistance <= ceil) {
				/*
				 * Road r = new Road(village, this, currentDistance); // create
				 * a // new road // to the // village
				 * village.getRoadsFrom().add(r); this.getRoadsTo().add(r);
				 */

				Road.create(village, this, currentDistance);
			}
		}

		// flip one road to from

		// check if theres one toRoad
		if (this.getRoadsTo().size() == 1) {

			// add original to double road list
			Road original = getRoadsTo().peek();
			Road.getAll().remove(original); // remove from all
			Road.getDoubles().add(original); // add to doubles

			// add a double road from this to road pointing to this
			Road.create(this, original.getFrom());

		} else if (!this.getRoadsTo().isEmpty()) {

			// get random index
			int index = (int) (Math.random() * getRoadsTo().size());

			// flip random road
			Road flipped = null;
			for (Road r : getRoadsTo()) {
				if (--index < 0) {
					r.flip();
					flipped = r;
					break;
				}
			}
			
			if (getRoadsTo().size() > 3)  {
				
				index = (int) (Math.random() * getRoadsTo().size());
				
				for (Road r : getRoadsTo()) {
					if (--index < 0 && r != flipped) {
						r.flip();
						flipped = r;
						break;
					}
				}
				
			}
		}

	}

	public void add(Gnome g) {
		gnomes.add(g); // add to current population
		population++;
	}

	public void leave(Gnome g) {
		gnomes.remove(g); // remove from current population
		population--;
	}

	@Override
	public Point getOriginPoint() {
		return origin;
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
		/*
		 * double deltaX = other.getOriginPoint().getX() - origin.getX(); double
		 * deltaY = other.getOriginPoint().getY() - origin.getY();
		 * 
		 * double distance = Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY,
		 * 2)); return distance;
		 */

		return getOriginPoint().distance(other.getOriginPoint());
	}

	@Override
	public String toString() {
		return "Village at: [" + origin.x + ", " + origin.y + "]";
	}
}
