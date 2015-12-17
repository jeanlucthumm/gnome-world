package jeanluc.gnomeworld;

import java.awt.Point;
import java.util.concurrent.LinkedBlockingQueue;

public class Road extends Thread implements Drawable {

	private static long buildRate = 50; // millis per unit length
	private static LinkedBlockingQueue<Road> all = new LinkedBlockingQueue<>(); // collection of
																// all the roads not doubles
	// all the double roads
	private static LinkedBlockingQueue<Road> doubles = new LinkedBlockingQueue<>();
	

	private Village to;
	private Village from;

	private double length;
	private boolean built;
	private boolean busy;

	public Road(Village from, Village to, double dist) {
		this.to = to;
		this.from = from;
		this.length = dist;
		this.built = false;
		this.busy = false;
		all.add(this);
		this.start();
	}

	public Road(Village from, Village to) {
		this.to = to;
		this.from = from;
		this.length = from.getDistanceTo(to);
		this.built = false;
		this.busy = false;
		all.add(this);
		this.start();
	}

	public static Road create(Village from, Village to) {

		Road r = new Road(from, to); // create new road

		from.getRoadsFrom().add(r); // notify from village
		to.getRoadsTo().add(r); // notify to village
		
		return r;
	}
	
	public static Road create(Village from, Village to, double dist) {
		
		Road r = new Road(from, to, dist); // create new road

		from.getRoadsFrom().add(r); // notify from village
		to.getRoadsTo().add(r); // notify to village
		
		return r;
		
	}
	
	public static LinkedBlockingQueue<Road> getAll() {
		return all;
	}
	
	public static LinkedBlockingQueue<Road> getDoubles() {
		return doubles;
	}

	public Village getTo() {
		return to;
	}

	public Village getFrom() {
		return from;
	}
	

	public void setTo(Village to) {
		this.to = to;
	}

	public void setFrom(Village from) {
		this.from = from;
	}
	
	public void setBuilt(boolean built) {
		this.built = built;
	}

	public double getLength() {
		return length;
	}
	

	public boolean isBuilt() {
		return built;
	}

	public void build() {
		long buildTime = (long) (buildRate * length);
		try {
			Thread.sleep(buildTime);
		} catch (InterruptedException e) {
		}

		built = true;
	}
	
	public void destroy() {
		//TODO
	}

	@Override
	public void run() {
		build();
	}

	@Override
	public Point getOriginPoint() {
		return from.getOriginPoint();
	}

	@Override
	public Point getSecondaryPoint() {
		return to.getOriginPoint();
	}

	@Override
	public boolean hasSecondaryPoint() {
		return true;
	}

	@Override
	public double getDistanceTo(Drawable other) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String toString() {
		return "Road from [" + from.getOriginPoint().x + ", "
				+ from.getOriginPoint().y + "] to [" + to.getOriginPoint().x
				+ ", " + to.getOriginPoint().y + "]";
	}

	public void flip() {
		// notify from village and add as to
		from.getRoadsFrom().remove(this); // remove this from from
		from.getRoadsTo().add(this); // add this to to
		
		// notify to village and add to from
		to.getRoadsTo().remove(this); // remove this from to
		to.getRoadsFrom().add(this); // add this to from
		
		// flip internal pointers
		Village temp = from;
		from = to;
		to = temp;
	}

}
