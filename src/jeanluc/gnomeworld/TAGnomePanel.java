package jeanluc.gnomeworld;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Ellipse2D;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class TAGnomePanel extends JPanel implements MouseListener {

	private int x, y;
	private Gnome data;

	public TAGnomePanel(int x, int y, Gnome check) {
		setLocation(x, y);
		data = check;
		this.setBounds(this.x, this.y, this.x + 40, this.y + 40);
	}

	public TAGnomePanel(Point originPoint, Gnome i) {
		this(originPoint.x, originPoint.y, i);
	}

	public void setLocation(int x, int y) {
		this.x = x;
		this.y = y;
		this.setBounds(x, y, this.x + 40, this.x + 40);
	}

	public void setLocation(Point p) {
		setLocation(p.x, p.y);
		this.setBounds(x, y, this.x + 30, this.x + 30);
	}

	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		Ellipse2D.Double circle = new Ellipse2D.Double(this.x, this.y, 10, 10);
		g2.setColor(Color.RED);
		g2.fill(circle);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	public static void main(String[] args) {
		JFrame f = new JFrame("TEST");
		JPanel outerPanel = new JPanel();
		outerPanel.setLayout(null);

		f.setPreferredSize(new Dimension(400, 400));
		outerPanel.setPreferredSize(new Dimension(400, 400));

		TAGnomePanel g = new TAGnomePanel(20, 20, new Gnome(new Village(20, 20)));
		g.setVisible(true);
		outerPanel.add(g);

		outerPanel.setVisible(true);
		f.add(outerPanel);
		f.pack();
		f.setVisible(true);

		while (true) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			g.setLocation(g.x + 12, g.y + 12);
		}

	}
}
