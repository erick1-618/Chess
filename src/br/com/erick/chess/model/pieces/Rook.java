package br.com.erick.chess.model.pieces;

import java.util.HashSet;
import java.util.Set;

import br.com.erick.chess.model.Field;
import br.com.erick.chess.model.Piece;
import br.com.erick.chess.model.Field.deslocation;

public class Rook extends Piece{
	public Rook(Piece.colors color, Field f) {
		super(color, f);
	}
	
	public String toString() {
		return this.getColor() == colors.WHITE ? "R" : "r";
	}

	@Override
	public void setPossibleMovements() {
		Set<String> set = new HashSet<String>();
		String left = this.getCurrentField().getCoordinate(deslocation.W);
		String down = this.getCurrentField().getCoordinate(deslocation.S);
		String right = this.getCurrentField().getCoordinate(deslocation.E);
		String up = this.getCurrentField().getCoordinate(deslocation.N);
		String[] array = { left, down, right, up };
		for (int i = 0; i < 4; i++) {
			int[] coord = Field.uCord(array[i]);
			if(coord == null) continue;
			Field f;
			try {
				f = this.getBoard().getMatrix()[coord[0]][coord[1]];
			} catch (ArrayIndexOutOfBoundsException e) {
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
