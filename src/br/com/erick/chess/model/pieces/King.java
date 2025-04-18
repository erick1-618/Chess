package br.com.erick.chess.model.pieces;

import java.util.HashSet;
import java.util.Set;

import br.com.erick.chess.model.Field;
import br.com.erick.chess.model.Field.deslocation;
import br.com.erick.chess.model.Piece;

public class King extends Piece {

	public King(Piece.colors color, Field f) {
		super(color, f);
		f.getBoard().setKingsPositions(f.getCoordinate(), this.getColor());
	}

	public String toString() {
		return this.getColor() == colors.WHITE ? "K" : "k";
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
				if (f.getPiece() == null) {
					set.add(f.getCoordinate());
				} else if (this.getColor() != f.getPiece().getColor()) {
					set.add(f.getCoordinate());
				}
			} catch (ArrayIndexOutOfBoundsException e) {
				continue;
			}
		}
		this.setMovements(set);
		Set<String> rock = getRockMovements();
		set.addAll(rock);
		this.setMovements(set);
	}

	public Set<String> getRockMovements() {
		Set<String> set = new HashSet<>();

		if(!this.isFirstMovement()) return set;
		
		int line0 = this.getColor() == colors.WHITE ? 7 : 0;
		Piece.colors enemy = this.getColor() == colors.WHITE ? colors.BLACK : colors.WHITE;

		boolean leftPiecesCheck;
		try {
			leftPiecesCheck = getBoard().getMatrix()[line0][0].getPiece().isFirstMovement()
					&& getBoard().isFieldEmpty(this.getCurrentField().getCoordinate(deslocation.W))
					&& getBoard().isFieldEmpty(this.getCurrentField().getCoordinate(deslocation.W, 2))
					&& getBoard().isFieldEmpty(this.getCurrentField().getCoordinate(deslocation.W, 3));
		} catch (Exception e) {
			leftPiecesCheck = false;
		}

		boolean rightPiecesCheck;
		try {
			rightPiecesCheck =  getBoard().getMatrix()[line0][7].getPiece().isFirstMovement()
					&& getBoard().isFieldEmpty(this.getCurrentField().getCoordinate(deslocation.E))
					&& getBoard().isFieldEmpty(this.getCurrentField().getCoordinate(deslocation.E, 2));
		} catch (Exception e) {
			rightPiecesCheck = false;
		}

		boolean leftSecure;
		try {
			leftSecure = getBoard().isSecureField(this.getCurrentField().getCoordinate(deslocation.W), enemy)
					&& getBoard().isSecureField(this.getCurrentField().getCoordinate(deslocation.W, 2), enemy);
		} catch (Exception e) {
			leftSecure = false;
		}

		boolean rightSecure;
		try {
			rightSecure = getBoard().isSecureField(this.getCurrentField().getCoordinate(deslocation.E), enemy)
					&& getBoard().isSecureField(this.getCurrentField().getCoordinate(deslocation.E, 2), enemy);
		} catch (Exception e) {
			rightSecure = false;
		}

		if (this.getColor() == colors.WHITE) {
			if (leftPiecesCheck && leftSecure)
				set.add("H3");
			if (rightPiecesCheck && rightSecure)
				set.add("H7");
		} else {
			if (leftPiecesCheck && leftSecure)
				set.add("A3");
			if (rightPiecesCheck && rightSecure)
				set.add("A7");
		}
		return set;
	}

	public Piece move(String coordinate) {
	    // Verifica se o movimento é um roque e executa a lógica do roque apenas se `coordinate` corresponde a uma posição de roque
	    if (isFirstMovement() && getRockMovements().contains(coordinate)) {
	        getBoard().setRockMode(true);
	        switch (coordinate) {
	            case "H3":
	                getBoard().getMatrix()[7][0].getPiece().move("H4");
	                super.move("H3");
	                break;
	            case "H7":
	                getBoard().getMatrix()[7][7].getPiece().move("H6");
	                super.move("H7");
	                break;
	            case "A3":
	                getBoard().getMatrix()[0][0].getPiece().move("A4");
	                super.move("A3");
	                break;
	            case "A7":
	                getBoard().getMatrix()[0][7].getPiece().move("A6");
	                super.move("A7");
	                break;
	        }
	        getBoard().setRockMode(false);
	        return null;
	    } else {
	        // Se não for um movimento de roque, executa o movimento normal
	        return super.move(coordinate);
	    }
	}
	
//	public Piece move(String coordinate) {
//		if(!isFirstMovement()) return super.move(coordinate);
//		getBoard().setRockMode(true);
//		switch (coordinate) {
//		case "H3":
//			getBoard().getMatrix()[7][0].getPiece().move("H4");
//			super.move("H3");
//			break;
//		case "H7":
//			getBoard().getMatrix()[7][7].getPiece().move("H6");
//			super.move("H7");
//			break;
//		case "A3":
//			getBoard().getMatrix()[0][0].getPiece().move("A4");
//			super.move("A3");
//			break;
//		case "A7":
//			getBoard().getMatrix()[0][7].getPiece().move("A6");
//			super.move("A7");
//			break;
//		}
//		getBoard().setRockMode(false);
//		return null;
//	}
}
