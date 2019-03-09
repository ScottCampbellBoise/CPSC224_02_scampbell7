/**
	* Scott Campbell
	* ICA6 - Mouse and Graphics
	* 7 March 2019
*/

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JFrame;
import java.awt.*;

public class DrawGUI extends JFrame{

	private int boxWidth = 100;
	private int boxHeight = 100;

	private int xPos = 100;
	private int yPos = 100;

	public static void main(String[] args)
	{
		new DrawGUI();
	}

	public DrawGUI()
	{
		//Set JFrame Characteristics
		setTitle("Box Mover");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(500,500);
		setResizable(false);

		//Add the Mouse and MouseMotion Listeners
		addMouseListener(new MyMouseListener());
	    addMouseMotionListener(new MyMouseMotionListener());

		setVisible(true);
	}

	public void paint(Graphics g)
	{
		//Paint the rectangle at its updated position
	    super.paint(g);
	    g.setColor(Color.BLACK);
	    g.fillRect(xPos, yPos, boxWidth, boxHeight);
	}

	private class MyMouseListener implements MouseListener {
		public void mousePressed(MouseEvent e)
		{
			//If pressed, set the point to be the now pos coordinate of the rectangle
			xPos = e.getX();
			yPos = e.getY();
		}

		public void mouseClicked(MouseEvent e)
		{
			//If clicked, set the point to be the now pos coordinate of the rectangle
			xPos = e.getX();
			yPos = e.getY();
			repaint();
		}

		public void mouseReleased(MouseEvent e) {}

		public void mouseEntered(MouseEvent e) {}

		public void mouseExited(MouseEvent e) {}
	}

	private class MyMouseMotionListener implements MouseMotionListener
	{
		public void mouseDragged(MouseEvent e)
		{
			//Calculate and update the box's new dimensions
			boxWidth = e.getX() - xPos;
			boxHeight = e.getY() - yPos;
			repaint();
		}

		public void mouseMoved(MouseEvent e) {}
	}
}
