package jeanluc.gnomeworld;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Ellipse2D;

import javax.swing.JPanel;

public class VillagePanel extends JPanel implements MouseListener{
	
	private int x, y;
	Village village;
	
	public VillagePanel(Village village) {
		this.x = village.getOriginPoint().x;
		this.y = village.getOriginPoint().y;
		this.village = village;
		
		setBounds(this.x, this.y, this.x + 10, this.y + 10);
		setBackground(Color.BLACK);
		setOpaque(true);
		
		addMouseListener(this);
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D graphics = (Graphics2D) g;
		Ellipse2D.Double circle = new Ellipse2D.Double(this.x, this.y, 10, 10);
		graphics.setColor(Color.RED);
		graphics.fill(circle);
	}
	
	public void setLocation(int x, int y) {
		this.x = x;
		this.y = y;
		this.setBounds(x, y, this.x + 10, this.y + 10);
	}
	
	public void setLocation(Point p) {
		setLocation(p.x, p.y);
		setBounds(x, y, this.x + 10, this.x + 10);
	}
	
	

	@Override
	public void mouseClicked(MouseEvent e) {
		System.out.println("Cicked: " + village);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	
}
