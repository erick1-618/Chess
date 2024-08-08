package br.com.erick.chess.model;

import java.util.Arrays;
import java.util.List;

import br.com.erick.chess.model.pieces.Bishop;
import br.com.erick.chess.model.pieces.King;
import br.com.erick.chess.model.pieces.Knight;
import br.com.erick.chess.model.pieces.Pawn;
import br.com.erick.chess.model.pieces.Queen;
import br.com.erick.chess.model.pieces.Rook;

public class Field {
	
	private Board board;
	private Piece piece;
	private String coordinate;
	private static final String[] letterArray = {"A", "B", "C", "D", "E", "F", "G", "H"};
	public static enum deslocation {N, W, S, E, NW, SW, NE, SE};
	
	public Field(Board b ,int x, int y, String piece, Piece.colors color) {
		this.board = b;
		setCoordinate(x, y);
		switch(piece) {
		case "P": this.piece = new Pawn(color, this); break;
		case "K": this.piece = new Knight(color, this); break;
		case "R": this.piece = new Rook(color, this); break;
		case "B": this.piece = new Bishop(color, this); break;
		case "KI": this.piece = new King(color, this); break;
		case "Q": this.piece = new Queen(color, this); break;
		}
	}
	
	public Board getBoard() {
		return board;
	}

	public Field(Board b, int x, int y) {
		this.board = b;
		setCoordinate(x, y);
		this.piece = null;
	}
	
	public void setPiece(Piece piece) {
		this.piece = piece;
	}

	public Piece getPiece() {
		if(this.piece == null) {
			return null;
		}
		return piece;
	}

	private void setCoordinate(int x, int y) {
		this.coordinate = letterArray[x] + (y + 1);
	}

	public String getCoordinate() {
		return coordinate;
	}
	
	public String getCoordinate(deslocation deslocation) {
		String cord = this.getCoordinate();
		try {
			switch (deslocation) {
			case N:
				return pCord(uCord(cord)[0] - 1, uCord(cord)[1]);
			case W:
				return pCord(uCord(cord)[0], uCord(cord)[1] - 1);
			case E:
				return pCord(uCord(cord)[0], uCord(cord)[1] + 1);
			case S:
				return pCord(uCord(cord)[0] + 1, uCord(cord)[1]);
			case NW:
				return pCord(uCord(cord)[0] - 1, uCord(cord)[1] - 1);
			case SW:
				return pCord(uCord(cord)[0] + 1, uCord(cord)[1] - 1);
			case NE:
				return pCord(uCord(cord)[0] - 1, uCord(cord)[1] + 1);
			case SE:
				return pCord(uCord(cord)[0] + 1, uCord(cord)[1] + 1);
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			return null;
		}
		return null;
	}
	
	public String getCoordinate(deslocation deslocation, int qt) {
		String cord = this.getCoordinate();
		try {
			switch (deslocation) {
			case N:
				return pCord(uCord(cord)[0] - qt, uCord(cord)[1]);
			case W:
				return pCord(uCord(cord)[0], uCord(cord)[1] - qt);
			case E:
				return pCord(uCord(cord)[0], uCord(cord)[1] + qt);
			case S:
				return pCord(uCord(cord)[0] + qt, uCord(cord)[1]);
			case NW:
				return pCord(uCord(cord)[0] - qt, uCord(cord)[1] - qt);
			case SW:
				return pCord(uCord(cord)[0] + qt, uCord(cord)[1] - qt);
			case NE:
				return pCord(uCord(cord)[0] - qt, uCord(cord)[1] + qt);
			case SE:
				return pCord(uCord(cord)[0] + qt, uCord(cord)[1] + qt);
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			return null;
		}
		return null;
	}
	
	public String getCoordinate(deslocation deslocation, int qt, deslocation secondDeslocation, int qt2) {
		int[] cord = Field.uCord(this.getCoordinate());
		try {
			switch (deslocation) {
			case N:
				cord[0] -= qt;
				break;
			case W:
				cord[1] -= qt;
				break;
			case E:
				cord[1] += qt;
				break;
			case S:
				cord[0] += qt;
				break;
			case NW:
				cord[0] -= qt;
				cord[1] -= qt;
				break;
			case SW:
				cord[0] += qt;
				cord[1] -= qt;
				break;
			case NE:
				cord[0] -= qt;
				cord[1] += qt;
				break;
			case SE:
				cord[0] += qt;
				cord[1] += qt;
				break;
			}
			switch (secondDeslocation) {
			case N:
				cord[0] -= qt2;
				break;
			case W:
				cord[1] -= qt2;
				break;
			case E:
				cord[1] += qt2;
				break;
			case S:
				cord[0] += qt2;
				break;
			case NW:
				cord[0] -= qt2;
				cord[1] -= qt2;
				break;
			case SW:
				cord[0] += qt2;
				cord[1] -= qt2;
				break;
			case NE:
				cord[0] -= qt2;
				cord[1] += qt2;
				break;
			case SE:
				cord[0] += qt2;
				cord[1] += qt2;
				break;
			}
			return Field.pCord(cord[0], cord[1]);
		} catch (ArrayIndexOutOfBoundsException e) {
			return null;
		}
	}
	
	@Override
	public String toString() {
		if(this.piece == null) {
			return ".";
		}else {
			return this.piece.toString();
		}
	}
	
	public static String pCord(int x, int y) {
		return letterArray[x] + (y + 1);
	}
	
	public static int[] uCord(String cord) {
		if (cord != null) {
			List<String> list = Arrays.asList(letterArray);
			int[] coordenate = new int[2];
			coordenate[0] = list.indexOf(cord.substring(0, 1));
			coordenate[1] = Integer.parseInt(cord.substring(1)) - 1;
			return coordenate;
		}
		return null;
	}
}
