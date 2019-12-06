import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class StationaryObject {
	private int posx;
	private int posy;
	private BufferedImage bi;
	private int imageW;
	private int imageH;

	StationaryObject(int posx, int posy, BufferedImage bi, int imageW,
			int imageH) {
		this.posx = posx;
		this.posy = posy;
		this.bi = bi;
		this.imageW = imageW;
		this.imageH = imageH;
	}

	public void drawImage(Graphics g) {
		g.drawImage(bi, posx, posy, imageW, imageH, null);
	}

	public int getPosx() {
		return posx;
	}

	public int getPosy() {
		return posy;
	}

	public int getImageW() {
		return imageW;
	}

	public int getImageH() {
		return imageH;
	}

	public void setPosx() {
		this.posx = posx;
	}

	public void setPosy() {
		this.posy = posy;
	}

	public void setImageW() {
		this.imageW = imageW;
	}

	public void setImageH() {
		this.imageH = imageH;
	}

	public BufferedImage getBi() {
		return bi;
	}

	public void setBi(BufferedImage bi) {
		this.bi = bi;
	}
}