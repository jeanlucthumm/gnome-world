package jeanluc.gnomeworld;

import java.awt.Point;

public interface Drawable {

	/**
	 * Gets the origin point of the item
	 * 
	 * @return a <code>Point</code> representing the coordinates of item's
	 *         origin point.
	 */
	public Point getOriginPoint();

	/**
	 * Gets the secondary point of the item. Things like lines or circles need
	 * this.
	 * 
	 * @return the <code>Point</code> representing the item's secondary point's
	 *         coordinates
	 */
	public Point getSecondaryPoint();

	/**
	 * Check if there is a secondary point.
	 * 
	 * @return <code>true</code> if there is a secondary point;
	 *         <code>false</code> otherwise.
	 */
	public boolean hasSecondaryPoint();

	/**
	 * Gets the linear distance to another Drawable and returns it as a double.
	 * 
	 * @param other
	 *            the other drawable
	 * @return the distance to the other drawable in double format
	 */
	public double getDistanceTo(Drawable other);
}
