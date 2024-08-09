package br.com.erick.chess.model;

import java.util.HashSet;
import java.util.Set;

import br.com.erick.chess.application.ChessGame;
import br.com.erick.chess.model.Piece.colors;
import br.com.erick.chess.model.pieces.Bishop;
import br.com.erick.chess.model.pieces.King;
import br.com.erick.chess.model.pieces.Knight;
import br.com.erick.chess.model.pieces.Pawn;
import br.com.erick.chess.model.pieces.Queen;
import br.com.erick.chess.model.pieces.Rook;

public class Board {

	private ChessGame chessGame;
	private Field[][] board = new Field[8][8];
	private String[][] piecesPosition = { { "R", "K", "B", "Q", "KI", "B", "K", "R" },
			{ "P", "P", "P", "P", "P", "P", "P", "P" } };
	private String[] kingsPositions = new String[2];
	private static final String[] letterArray = {"A", "B", "C", "D", "E", "F", "G", "H"};
	private boolean rockMode = false;

	public boolean isRockMode() {
		return rockMode;
	}

	public void setRockMode(boolean rockMode) {
		this.rockMode = rockMode;
	}

	public ChessGame getChessGame() {
		return chessGame;
	}

	public String[] getKingsPositions() {
		return kingsPositions;
	}

	public void setKingsPositions(String kingPos, Piece.colors color) {
		if(color == colors.WHITE) {
			this.kingsPositions[0] = kingPos;
		}else {
			this.kingsPositions[1] = kingPos;			
		}
	}

	public Board(ChessGame cg) {
		this.chessGame = cg;
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				switch(i) {
				case 0: board[i][j] = new Field(this, i, j, piecesPosition[i][j], colors.BLACK); break;
				case 1: board[i][j] = new Field(this, i, j, piecesPosition[i][j], colors.BLACK); break;
				case 6: board[i][j] = new Field(this, i, j, piecesPosition[1][j], colors.WHITE); break;				
				case 7: board[i][j] = new Field(this, i, j, piecesPosition[0][j], colors.WHITE); break;
				default: board[i][j] = new Field(this, i, j);
				}
			}
		}
		recalculeMovements();
	}
	
	public void recalculeMovements() {
		int[][] coord = new int[2][2];	
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				if(board[i][j].getPiece() != null) {
					board[i][j].getPiece().setPossibleMovements();						
				}
			}
		}
	}
	
	public boolean isSecureField(String coord, Piece.colors enemy) {
		Set<String> allMovs = new HashSet<String>();
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				if(board[i][j].getPiece() != null && board[i][j].getPiece().getColor() == enemy)
				allMovs.addAll(board[i][j].getPiece().getMovements());
			}
		}
		return allMovs.contains(coord) ? false: true;
	}
	
	public Set<String> getSnapShot() {
		Set<String> allMovs = new HashSet<String>();
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				if(board[i][j].getPiece() != null)
				allMovs.addAll(board[i][j].getPiece().getMovements());
			}
		}
		return allMovs;
	}
	
	public Set<String> getSnapShot(Piece.colors color) {
		Set<String> allMovs = new HashSet<String>();
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				if(board[i][j].getPiece() != null && board[i][j].getPiece().getColor() == color)
				allMovs.addAll(board[i][j].getPiece().getSecureMovements());
			}
		}
		return allMovs;
	}
	
	public String toString() {
		String str = "";
		str += "	1 2 3 4 5 6 7 8\n\n";
		for(int i = 0; i < 8; i++) {
			str += letterArray[i] + "	";
			for(int j = 0; j < 8; j++) {
				str += this.board[i][j].toString() + " ";
			}
			str += "\n";
		}
		return str;
	}
	
	public colors isCheck() {
		recalculeMovements();
		Set<String> snapShot = getSnapShot();
		if(snapShot.contains(kingsPositions[0]) || snapShot.contains(kingsPositions[1])) {
			return snapShot.contains(kingsPositions[0]) ? colors.WHITE : colors.BLACK;
		}
		return null;
	}
	
	public String promotion() {
		for(int i = 0; i < 8; i++) {
			if(board[0][i].getPiece() instanceof Pawn) return board[0][i].getCoordinate();
			if(board[7][i].getPiece() instanceof Pawn) return board[7][i].getCoordinate();
		}
		return null;
	}
	
	public void applyPromotion(String mov, String piece) {
		int[] coord = Field.uCord(mov);
		Field f = board[coord[0]][coord[1]];
		Piece.colors color = f.getPiece().getColor();
		f.getPiece().setCurrentField(null);
		f.setPiece(null);
		Piece p = null;
		switch(piece) {
		case "Queen" : p = new Queen(color, f); break;
		case "Knight" : p = new Knight(color, f); break;
		case "Bishop" : p = new Bishop(color, f); break;
		case "Rook" : p = new Rook(color, f);
		}
		f.setPiece(p);
		p.setCurrentField(f);
	}
	
	public colors isCheckMate() {
		Piece.colors colorInCheck = isCheck();
		if(colorInCheck == null) return null;
		else {
			Set<String> movements = getSnapShot(colorInCheck);
			if(movements.isEmpty()) return colorInCheck;
			return null;
		}
	}
	
	public boolean isDraw() {
		int pieceCounter = 0;
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				if(board[i][j].getPiece() != null) pieceCounter++;
			}
		}
		if(pieceCounter <= 2) return pieceCounter <= 2;
		Set<String> movs = getSnapShot();
		return movs.isEmpty();
	}
	
	public Field[][] getMatrix() {
		return board;
	}

	public boolean isFieldEmpty(int x, int y) {
		try {
			if (this.board[x][y].getPiece() == null) {
				return true;
			}
			return false;
		} catch (Exception e) {
			return false;		
		}
	}
	
	public boolean isFieldEmpty(String coord) {
		int[] cord = Field.uCord(coord);
		try {
			if (this.board[cord[0]][cord[1]].getPiece() == null) {
				return true;
			}
			return false;
		} catch (Exception e) {
			return false;		
		}
	}
	
	public colors hasPiece(int x, int y) {
		try {
			return this.board[x][y].getPiece().getColor();
		} catch (Exception e) {
			return null;		
		}
	}
}
