package jeanluc.gnomeworld;

import java.awt.Component;

public class Worker extends Thread {

	private final Component comp;
	private static final long REFRESH_RATE = 20;

	public Worker(Component comp) {
		this.comp = comp;
	}

	@Override
	public void run() {
		while (true) {
			comp.repaint();
			try {
				Thread.sleep(REFRESH_RATE);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
