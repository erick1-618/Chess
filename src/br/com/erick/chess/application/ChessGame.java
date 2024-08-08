package br.com.erick.chess.application;

import javax.swing.JOptionPane;

import br.com.erick.chess.model.Board;
import br.com.erick.chess.model.Field;
import br.com.erick.chess.model.Piece;
import br.com.erick.chess.model.Piece.colors;
import br.com.erick.chess.vision.ButtonListener;
import br.com.erick.chess.vision.GameWindow;

public class ChessGame implements ButtonListener{
	
	private Board board;
	private Piece.colors turn;
	private Piece.colors winner;
	private GameWindow gameWindow;
	private Piece selectedPiece;
	private actions currentAction;
	
	public Piece getSelectedPiece() {
		return selectedPiece;
	}

	public enum actions {
		SELECT, MOVE, DESELECT
	}
	
	public Piece.colors getTurn() {
		return turn;
	}

	public void setCurrentAction(actions currentAction) {
		this.currentAction = currentAction;
	}

	public actions getCurrentAction() {
		return currentAction;
	}

	public ChessGame(GameWindow gw) {
		this.board = new Board(this);
		this.gameWindow = gw;
	}
	
	public Board getBoard() {
		return board;
	}

	public void gameStart() {
		this.turn = colors.WHITE;
		currentAction = actions.SELECT;
	}

	@Override
	public void performAction(actions a, Field f) {
		if(a == actions.SELECT) {
			selectedPiece = f.getPiece();
			currentAction = actions.MOVE;
		}else if(a == actions.MOVE) {
			selectedPiece.move(f.getCoordinate());
			selectedPiece = null;
			turn = turn == colors.BLACK ? colors.WHITE : colors.BLACK;
			currentAction = actions.SELECT;
			winner = board.isCheckMate();
			if(winner != null) {
				JOptionPane.showMessageDialog(gameWindow, "Check Mate!\n" + winner + " team wins!");
				gameWindow.restart();
			}
			if(board.isDraw()) {
				JOptionPane.showMessageDialog(gameWindow, "Tie!");				
				gameWindow.restart();
			}
		}else {
			selectedPiece = null;
			currentAction = actions.SELECT;
		}
	}
}
