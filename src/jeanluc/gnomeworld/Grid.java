package jeanluc.gnomeworld;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * JPanel that houses the main world
 * 
 * @author Jean-Luc Thumm
 *
 */
public class Grid extends JPanel implements MouseListener {

	private CreationState creationState = CreationState.None;

	public Grid(Dimension preferredSize) {
		setLayout(null);
		setPreferredSize(preferredSize);
		setBackground(Color.WHITE);
		setOpaque(true);
		
		addMouseListener(this);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
		System.out.println("Mouse Cliked"); //FIXME

		switch (creationState) {
		
		case None:
			return;
			
		case Village:
			
			// create new JPanel
			Village v = new Village(e.getPoint());
			VillagePanel villagePanel = new VillagePanel(v);
			villagePanel.setVisible(true);
			add(villagePanel);
			
			System.out.println(v); // FIXME
			
			break;
			
		default:
			throw new AssertionError();

		}
		
		repaint();
	}
	
	public CreationState getCreationState() {
		return creationState;
	}
	
	public void setCreationState(CreationState cs) {
		creationState = cs;
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
	
	public static void createAndShowGUI() {
		JFrame frame = new JFrame("Test");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Grid grid = new Grid(new Dimension(500, 500));
		grid.setVisible(true);
		grid.setCreationState(CreationState.Village);
		
		frame.add(grid);
		
		frame.pack();
		frame.setLocationRelativeTo(null);
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

}
