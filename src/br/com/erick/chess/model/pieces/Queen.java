package br.com.erick.chess.model.pieces;

import java.util.HashSet;
import java.util.Set;

import br.com.erick.chess.model.Field;
import br.com.erick.chess.model.Piece;
import br.com.erick.chess.model.Field.deslocation;

public class Queen extends Piece {

	public Queen(Piece.colors color, Field f) {
		super(color, f);
	}

	public String toString() {
		return this.getColor() == colors.WHITE ? "Q" : "q";
	}

	@Override
	public void setPossibleMovements() {
		Set<String> set = new HashSet<String>();
		String left = this.getCurrentField().getCoordinate(deslocation.W);
		String down = this.getCurrentField().getCoordinate(deslocation.S);
		String right = this.getCurrentField().getCoordinate(deslocation.E);
		String up = this.getCurrentField().getCoordinate(deslocation.N);
		String leftUp = this.getCurrentField().getCoordinate(deslocation.NW);
		String leftDown = this.getCurrentField().getCoordinate(deslocation.SW);
		String rightUp = this.getCurrentField().getCoordinate(deslocation.NE);
		String rightDown = this.getCurrentField().getCoordinate(deslocation.SE);
		String[] array = { left, down, right, up, leftUp, leftDown, rightUp, rightDown };
		for (int i = 0; i < 8; i++) {
			int[] coord = Field.uCord(array[i]);
			if (coord == null)
				continue;
			Field f;
			try {
				f = this.getCurrentField().getBoard().getMatrix()[coord[0]][coord[1]];
			} catch (ArrayIndexOutOfBoundsException e) {
				f = null;
				continue;
			}
			while (f.getPiece() == null && f != null) {
				set.add(Field.pCord(coord[0], coord[1]));
				switch (i) {
				case 0:
					coord[1]--;
					break;
				case 1:
					coord[0]++;
					break;
				case 2:
					coord[1]++;
					break;
				case 3:
					coord[0]--;
					break;
				case 4:
					coord[0]--;
					coord[1]--;
					break;
				case 5:
					coord[0]++;
					coord[1]--;
					break;
				case 6:
					coord[0]--;
					coord[1]++;
					break;
				case 7:
					coord[0]++;
					coord[1]++;
					break;
				}
				try {
					f = this.getCurrentField().getBoard().getMatrix()[coord[0]][coord[1]];
				} catch (ArrayIndexOutOfBoundsException e) {
					f = null;
					break;
				}
			}
			if (f != null && this.getColor() != f.getPiece().getColor()) {
				set.add(f.getCoordinate());
			}
		}
		this.setMovements(set);
	}
}
