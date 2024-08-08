package br.com.erick.chess.model.pieces;

import java.util.HashSet;
import java.util.Set;

import br.com.erick.chess.model.Field;
import br.com.erick.chess.model.Field.deslocation;
import br.com.erick.chess.model.Piece;

public class Pawn extends Piece {
	public Pawn(Piece.colors color, Field f) {
		super(color, f);
	}

	public String toString() {
		return this.getColor() == colors.WHITE ? "P" : "p";
	}

	@Override
	public void setPossibleMovements() {
		Set<String> set = new HashSet<String>();
		String down = this.getCurrentField().getCoordinate(deslocation.S);
		String up = this.getCurrentField().getCoordinate(deslocation.N);
		String dDown = this.getCurrentField().getCoordinate(deslocation.S, 2);
		String dUp = this.getCurrentField().getCoordinate(deslocation.N, 2);
		String kLeftUp = this.getCurrentField().getCoordinate(deslocation.NW);
		String kLeftDown = this.getCurrentField().getCoordinate(deslocation.SW);
		String kRightUp = this.getCurrentField().getCoordinate(deslocation.NE);
		String kRightDown = this.getCurrentField().getCoordinate(deslocation.SE);
		if (this.getColor() == colors.WHITE) {
			if (this.getCurrentField().getBoard().isFieldEmpty(Field.uCord(down)[0], Field.uCord(down)[1])) {
				set.add(down);
				if (this.isFirstMovement()
						&& this.getCurrentField().getBoard().isFieldEmpty(Field.uCord(dDown)[0], Field.uCord(dDown)[1])) {
					set.add(dDown);
				}
			}
			if (this.getCurrentField().getBoard().hasPiece(Field.uCord(kLeftDown)[0], Field.uCord(kLeftDown)[1]) == colors.BLACK) {
				set.add(kLeftDown);
			}
			if (this.getCurrentField().getBoard().hasPiece(Field.uCord(kRightDown)[0],
					Field.uCord(kRightDown)[1]) == colors.BLACK) {
				set.add(kRightDown);
			}
		} else {
			if (this.getCurrentField().getBoard().isFieldEmpty(Field.uCord(up)[0], Field.uCord(up)[1])) {
				set.add(up);
				if (this.isFirstMovement()
						&& this.getCurrentField().getBoard().isFieldEmpty(Field.uCord(dUp)[0], Field.uCord(dUp)[1])) {
					set.add(dUp);
				}
			}
			if (this.getCurrentField().getBoard().hasPiece(Field.uCord(kLeftUp)[0], Field.uCord(kLeftUp)[1]) == colors.WHITE) {
				set.add(kLeftUp);
			}
			if (this.getCurrentField().getBoard().hasPiece(Field.uCord(kRightUp)[0], Field.uCord(kRightUp)[1]) == colors.WHITE) {
				set.add(kRightUp);
			}
		}
		this.setMovements(set);
	}
}
