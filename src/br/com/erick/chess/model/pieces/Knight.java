package br.com.erick.chess.model.pieces;

import java.util.HashSet;
import java.util.Set;

import br.com.erick.chess.model.Field;
import br.com.erick.chess.model.Piece;
import br.com.erick.chess.model.Field.deslocation;

public class Knight extends Piece{
	
	public Knight(Piece.colors color, Field f) {
		super(color, f);
	}

	public String toString() {
		return this.getColor() == colors.WHITE ? "H" : "h";
	}

	@Override
	public void setPossibleMovements() {
		Set<String> set = new HashSet<String>();
		String downLeft = this.getCurrentField().getCoordinate(deslocation.S, 2, deslocation.W, 1);
		String downRight = this.getCurrentField().getCoordinate(deslocation.S, 2, deslocation.E, 1);
		String upLeft = this.getCurrentField().getCoordinate(deslocation.N, 2, deslocation.W, 1);
		String upRight = this.getCurrentField().getCoordinate(deslocation.N, 2, deslocation.E, 1);
		String leftUp = this.getCurrentField().getCoordinate(deslocation.W, 2, deslocation.N, 1);
		String leftDown = this.getCurrentField().getCoordinate(deslocation.W, 2, deslocation.S, 1);
		String rightUp = this.getCurrentField().getCoordinate(deslocation.E, 2, deslocation.N, 1);
		String rightDown = this.getCurrentField().getCoordinate(deslocation.E, 2, deslocation.S, 1);
		String[] array = { downLeft, downRight, upRight, upLeft, leftUp, leftDown, rightUp, rightDown };
		for (int i = 0; i < 8; i++) {
			int[] coord = Field.uCord(array[i]);
			if (coord == null)
				continue;
			Field f;
			try {
				f = this.getCurrentField().getBoard().getMatrix()[coord[0]][coord[1]];
			} catch (ArrayIndexOutOfBoundsException e) {
				continue;
			}
			if(f.getPiece() == null) set.add(Field.pCord(coord[0], coord[1]));
			else if (this.getColor() != f.getPiece().getColor()) {
				set.add(f.getCoordinate());
			}
		}
		this.setMovements(set);
	}
}
