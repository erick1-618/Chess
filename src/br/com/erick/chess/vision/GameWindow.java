package br.com.erick.chess.vision;

import java.awt.Dimension;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import br.com.erick.chess.application.ChessGame;

@SuppressWarnings("serial")
public class GameWindow extends JFrame {
	private ChessGame chessGame;

	public GameWindow(){
		chessGame = new ChessGame(this);
		chessGame.gameStart();
		Grid grid = new Grid(chessGame.getBoard());
		add(grid);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setSize(new Dimension(600, 600));
		setTitle("Chess");
		setVisible(true);
	}
	
	public void restart() {
		this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
		new GameWindow();
	}
	
	public static void main(String[] args){
		new GameWindow();
	}
}
