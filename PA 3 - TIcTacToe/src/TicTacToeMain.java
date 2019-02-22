import javax.swing.JFrame;

public class TicTacToeMain extends JFrame
{
	GamePanel gamePanel;
	PlayerPanel player1;
	PlayerPanel player2;
	MenuPanel menu;
	BoardPanel board;

	public static void main(String[] args) { new TicTacToeMain(); }

	public TicTacToeMain()
	{
		player1 = new PlayerPanel("Player 1 (X):");
		player2 = new PlayerPanel("Player 2 (O):");
		menu = new MenuPanel();
		board = new BoardPanel();
		gamePanel = new GamePanel(player1, player2, menu, board);
		menu.setMasterPanel(gamePanel);
		board.setMasterPanel(gamePanel);
		
		setTitle("Tic Tac Toe");
		setSize(500,500);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		add(gamePanel);
		
		revalidate();
		repaint();
		setVisible(true);
	}
}
