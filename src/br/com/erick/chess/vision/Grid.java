package br.com.erick.chess.vision;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import br.com.erick.chess.application.ChessGame;
import br.com.erick.chess.application.ChessGame.actions;
import br.com.erick.chess.model.Board;
import br.com.erick.chess.model.Field;
import br.com.erick.chess.model.Piece;
import br.com.erick.chess.model.Piece.colors;
import br.com.erick.chess.model.pieces.Bishop;
import br.com.erick.chess.model.pieces.King;
import br.com.erick.chess.model.pieces.Knight;
import br.com.erick.chess.model.pieces.Pawn;
import br.com.erick.chess.model.pieces.Queen;
import br.com.erick.chess.model.pieces.Rook;

@SuppressWarnings("serial")
public class Grid extends JPanel implements ButtonListener{
	
	private Board board;
	private Button[][] buttons = new Button[8][8];
	private static final Color brown = new Color(154, 115, 70);
	private static final Color white = Color.WHITE;
	private static final Color greenForWhite = new Color(204, 255, 229);
	private static final Color greenForBrown = new Color(46, 85, 66);
	
	public Grid(Board board){
		super();
		setLayout(new GridLayout(8, 8));
		Button button;
		ImageIcon icon;
		boolean alterner = false;
		this.board = board;
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				button = new Button(board.getMatrix()[i][j], this);
				icon = getImgIcon(i, j);
				button.setIcon(icon);
				if (alterner) {
					button.setBackground(brown);
					button.setColor(brown);
				} else {
					button.setBackground(white);
					button.setColor(white);
				}
				button.addListener(board.getChessGame());
				button.addListener(this);
				buttons[i][j] = button;
				add(button);
				if (j != 7)
					alterner = !alterner;
			}
		}
	}
	
	public void refreshIcons() {
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				buttons[i][j].setIcon(getImgIcon(i, j));
			}
		}
	}
	
	private ImageIcon getImgIcon(int i, int j) {
		Piece piece = this.board.getMatrix()[i][j].getPiece();
		if(piece == null) return null;
		
		String filename = null;
		if(piece.getColor() == colors.WHITE) {
			if(piece instanceof Pawn) {
				filename = "Chess_plt60.png";
			}
			if(piece instanceof Rook) {
				filename = "Chess_rlt60.png";
			}
			if(piece instanceof Knight) {
				filename = "Chess_nlt60.png";
			}
			if(piece instanceof Bishop) {
				filename = "Chess_blt60.png";
			}
			if(piece instanceof King) {
				filename = "Chess_klt60.png";
			}
			if(piece instanceof Queen) {
				filename = "Chess_qlt60.png";
			}
		}else {
			if(piece instanceof Pawn) {
				filename = "Chess_pdt60.png";
			}
			if(piece instanceof Rook) {
				filename = "Chess_rdt60.png";
			}
			if(piece instanceof Knight) {
				filename = "Chess_ndt60.png";
			}
			if(piece instanceof Bishop) {
				filename = "Chess_bdt60.png";
			}
			if(piece instanceof King) {
				filename = "Chess_kdt60.png";
			}
			if(piece instanceof Queen) {
				filename = "Chess_qdt60.png";
			}
		}
		
		if(filename != null) {
            // Carrega a imagem do JAR usando getResource
            return new ImageIcon(getClass().getResource("/br/com/erick/chess/icons/" + filename));
        }
		
		return null;
	}

	public static boolean isGreen(Color color) {
		return color == greenForBrown || color == greenForWhite;
	}

	@Override
	public void performAction(ChessGame.actions a, Field f) {
		if(a == actions.SELECT) {
			ArrayList<String> movs = new ArrayList<String>(f.getPiece().getMovements());
			int[][] coords = new int[movs.size()][2];
			for(int i = 0; i < movs.size(); i++) {
				coords[i] = Field.uCord(movs.get(i));
			}
			for(int i = 0; i < movs.size(); i++) {
				if(buttons[coords[i][0]][coords[i][1]].getColor() == white) {
					buttons[coords[i][0]][coords[i][1]].setBackground(greenForWhite);					
				}else {
					buttons[coords[i][0]][coords[i][1]].setBackground(greenForBrown);					
				}
			}
		}else if(a == actions.MOVE) {
			resetColors();
			refreshIcons();
		}else if(a == actions.DESELECT) {
			resetColors();
		}
	}

	private void resetColors() {
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				buttons[i][j].setBackground(buttons[i][j].getColor());
			}
		}
	}
}
