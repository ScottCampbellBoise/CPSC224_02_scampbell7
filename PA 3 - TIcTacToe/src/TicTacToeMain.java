import javax.swing.JFrame;
import javax.swing.JButton;


public class TicTacToeMain {
	
	public static void main(String[] args)
	{
		new TicTacToeMain();
	}
	
	public TicTacToeMain()
	{
		JFrame masterFrame;
		JButton[][] buttonGrid;
		
		
		masterFrame = new JFrame();
		masterFrame.setTitle("Tic Tac Toe");
		masterFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		masterFrame.setSize(500,500);
		masterFrame.setResizable(false);
		masterFrame.setVisible(true);
		
	}
}
