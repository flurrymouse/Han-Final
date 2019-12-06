import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class MovingObject extends StationaryObject {

	private int vx;
	private int vy;
	private Boolean bFlag;

	public MovingObject(int posx, int posy, BufferedImage bi, int imageW,
			int imageH, int vx, int vy, Boolean bFlag) {

		super(posx, posy, bi, imageW, imageH);
		this.vx = vx;
		this.vy = vy;
		this.bFlag = bFlag;
	}

	public void drawImage(Graphics g) {
		g.drawImage(getBi(), getPosx() + vx, getPosy() + vy, getImageW(),
				getImageH(), null);
		if (bFlag) { // Enemy
			vx -= 100;
			vy += 100;
		} else { // You
			vx += 250;
			vy -= 250;
		}

	}

	public int getVx() {
		return vx;
	}

	public void setVx(int vx) {
		this.vx = vx;
	}

	public int getVy() {
		return vy;
	}

	public void setVy(int vy) {
		this.vy = vy;
	}

}