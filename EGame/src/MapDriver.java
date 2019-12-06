import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class MapDriver extends JPanel {

	private MovingObject M[] = new MovingObject[100];
	private BufferedImage fireball;

	public MapDriver() {

	}

	//if bFlag is true it means NPC
	public void fire(int n, String fileName, Boolean bFlag) {
		try {
			fireball = ImageIO.read(new File(fileName));
			MovingObject m = null;
			if (bFlag) { // Enemy
				m = new MovingObject(550, 0, fireball, 250, 250, -10, 10,
						bFlag);
			} else { // You
				m = new MovingObject(0, 760, fireball, 100, 100, 10, -10,
						bFlag);
			}
			M[n] = m;

		} catch (IOException e) {
			System.out.println("Unable to read in image file");
		}

	}

	public void paint(Graphics g) {

		super.paint(g);
		for (MovingObject m : M) {
			if (m != null) {
				if ((m.getVx() + m.getPosx()) < getWidth()
						&& (m.getVy() + m.getPosx()) < getHeight())
					m.drawImage(g);
			}
		}
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		repaint();
	}
	
}
