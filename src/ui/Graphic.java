package ui;
import java.awt.*;

/** This class runs for animation of congratulations for the winner
 * 
 * @author Yuan Tang & Yusheng Wang
 */

public class Graphic extends Frame{
	
	double x1 = 0;
	double y1 = 0;
	double t1 = 0;
	
	public void update(Graphics g) {
		if(offScreenImage == null)
			offScreenImage = this.createImage(900, 900);
		
		Graphics gOff = offScreenImage.getGraphics();
		Color oldColor = gOff.getColor();
		gOff.setColor(Color.BLACK);
		gOff.fillRect(0, 0, 900, 600);
		gOff.setColor(oldColor);
		paint(gOff);
		g.drawImage(offScreenImage, 0, 0, null);
	}
	
	Image piece = Toolkit.getDefaultToolkit().getImage("images/piece.jpg");
	Image bg = Toolkit.getDefaultToolkit().getImage("images/bg.jpg");
	Image congrats = Toolkit.getDefaultToolkit().getImage("images/congrats.jpg");
	
	public void paint(Graphics g){
		g.setColor(Color.blue);
		g.drawImage(bg , 0 , 0 , null);
		g.drawImage(piece , 300 ,280 , null);
		
		t1 = t1 + Math.PI / 68.7;
	
		g.drawImage(congrats , (int)x1 ,(int)y1, null);
		x1 = 300+ 300 * Math.cos(t1);
		y1 = 250+ 180 * Math.sin(t1);
	
	}

	public static void runGraphics(String[] args){
		Graphic ball = new Graphic();
		ball.loadFrame();
	}
	
	private Image offScreenImage = null;
	void loadFrame(){
		this.setSize(900,600);
		this.setLocation(0,0);
		this.setVisible(true);
		new PaintThread().start();
	}
	
	private class PaintThread extends Thread{
		public void run(){
			while(true){
				repaint();
				try {
					Thread.sleep(40);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	private static final long serialVersionUID = 1L;

}