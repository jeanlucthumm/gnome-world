package jeanluc.gnomeworld;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Drawer extends JPanel implements MouseListener {

	private Worker worker;

	public Drawer() {
		setPreferredSize(new Dimension(500, 500));
		setBackground(Color.WHITE);
		setOpaque(true);

		addMouseListener(this);

		worker = new Worker(this);
		worker.start();
	}

	public void paintComponent(Graphics g) {

		super.paintComponent(g);

		Graphics2D graphics = (Graphics2D) g;
		graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		// paint single roads
		for (Road r : Road.getAll()) {

			if (r.isBuilt())
				graphics.setColor(Color.BLACK);
			else
				graphics.setColor(Color.RED);

			graphics.drawLine(r.getOriginPoint().x, r.getOriginPoint().y,
					r.getSecondaryPoint().x, r.getSecondaryPoint().y);

		}

		// paint double roads
		for (Road doubleR : Road.getDoubles()) {

			if (doubleR.isBuilt())
				graphics.setColor(Color.BLUE);
			else
				graphics.setColor(Color.RED);

			graphics.drawLine(doubleR.getOriginPoint().x,
					doubleR.getOriginPoint().y, doubleR.getSecondaryPoint().x,
					doubleR.getSecondaryPoint().y);
		}

		// paint villages
		for (Village v : Village.getAll()) {

			graphics.setColor(Color.BLACK);

			graphics.fillRoundRect(v.getOriginPoint().x - 5,
					v.getOriginPoint().y - 5, 10, 10, 10, 10);
		}

		// paint gnomes
		for (Gnome gnome : Gnome.getAll()) {
			graphics.setColor(Color.GREEN);
			Point p = gnome.getOriginPoint();

			graphics.fillRoundRect(p.x - 3, p.y - 3, 6, 6, 6, 6);
		}

	}

	public Village getClicked(Point p) {

		// check if there are no villages
		if (Village.getAll().isEmpty())
			return null;

		// Get min village
		Village min = Village.getAll().peek();
		double minDistance = min.getOriginPoint().distance(p);
		double currentDistance;

		for (Village v : Village.getAll()) {

			if ((currentDistance = v.getOriginPoint().distance(p)) < minDistance) {
				min = v;
				minDistance = currentDistance;
			}

		}

		if (minDistance <= 10)
			return min;
		else
			return null;
	}

	public static void createAndShowGUI() {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Drawer drawer = new Drawer();
		frame.add(drawer);

		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setFocusable(true);
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				createAndShowGUI();
			}

		});
	}

	@Override
	public void mouseClicked(MouseEvent e) {

	}

	@Override
	public void mousePressed(MouseEvent e) {
		switch (e.getButton()) {
		case 1:
			System.out.println("Registered Left Click"); // FIXME
			Village v = new Village(e.getPoint());
			v.add(new Gnome(v)); // FIXME

			System.out.println(v + " : created");
			break;

		case 2:
			System.out.println("Registered Middle Click.");
			Village clicked = getClicked(e.getPoint());

			if (clicked == null)
				return;

			clicked.add(new Gnome(clicked));

			System.out.println(clicked + " : added Gnome");
			break;
		case 3:
			System.out.println("Registered Right Click");
			Village gone = getClicked(e.getPoint());

			if (gone == null)
				return;

			boolean success = Village.delete(gone);

			if (success)
				System.out.println(gone + " : deleted");
			else
				System.out.println(gone
						+ " : still under construction...cannot delete");
			break;
		default:
			throw new AssertionError();
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}
}
