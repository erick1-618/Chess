package br.com.erick.chess.model;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import br.com.erick.chess.model.Field.deslocation;
import br.com.erick.chess.model.pieces.King;

public abstract class Piece {
	public static enum colors {WHITE, BLACK};
	private Piece.colors color;
	private Field currentField;
	private Set<String> movements = new HashSet<String>();
	private boolean adminMode = false;
	private boolean isFirstMovement = true;
	private static String[] possiblesRockMoves = {"H2", "H7", "A2", "A7"}; 
	private List<String> rock = Arrays.asList(possiblesRockMoves);
	
	public boolean isFirstMovement() {
		return isFirstMovement;
	}

	public void setFirstMovement(boolean isFirstMovement) {
		this.isFirstMovement = isFirstMovement;
	}

	public boolean isAdminMode() {
		return adminMode;
	}

	public void setAdminMode(boolean adminMode) {
		this.adminMode = adminMode;
	}

	public void setCurrentField(Field currentField) {
		this.currentField = currentField;
	}

	public Piece.colors getColor() {
		return color;
	}
	
	public Board getBoard() {
		return this.getCurrentField().getBoard();
	}

	public Piece(Piece.colors color, Field f) {
		this.color = color;
		this.currentField = f;
	}
	
	public Field getCurrentField() {
		return currentField;
	}

	
	public Piece move(String coordinate) {
			if(this.getMovements().contains(coordinate) || adminMode || getBoard().isRockMode()) {
				if(!adminMode && isFirstMovement) isFirstMovement = false;
				int[] coord = Field.uCord(coordinate);
				Field field = getBoard().getMatrix()[coord[0]][coord[1]];
				Piece piece = field.getPiece();
				if(piece != null) {
					piece.setCurrentField(null);
				}
				this.getCurrentField().setPiece(null);
				field.setPiece(this);
				this.setCurrentField(field);
				if(this instanceof King) {
					getBoard().setKingsPositions(coordinate, color);
				}
				this.getBoard().recalculeMovements();
				return piece;
			}else {
				throw new RuntimeException("Invalid move");
			}
	}
	
	public void setMovements(Set<String> movements) {
		this.movements = movements;
	}

	public abstract void setPossibleMovements();

	public Set<String> getMovements() {
		return movements;
	}
	
	public Set<String> getSecureMovements(){
		exposuresTheKing();
		return this.movements;
	}
	
	private boolean containsAKing() {
		String left = this.getCurrentField().getCoordinate(deslocation.W);
		String down = this.getCurrentField().getCoordinate(deslocation.S);
		String right = this.getCurrentField().getCoordinate(deslocation.E);
		String up = this.getCurrentField().getCoordinate(deslocation.N);
		String leftUp = this.getCurrentField().getCoordinate(deslocation.NW);
		String leftDown = this.getCurrentField().getCoordinate(deslocation.SW);
		String rightUp = this.getCurrentField().getCoordinate(deslocation.NE);
		String rightDown = this.getCurrentField().getCoordinate(deslocation.SE);
		String[] array = { left, down, right, up, leftUp, leftDown, rightUp, rightDown };
		List<String> movs = Arrays.asList(array);
		return movs.stream().anyMatch(m -> {
			int[] coord;
			Piece p;
			try {
				coord = Field.uCord(m);
				p = currentField.getBoard().getMatrix()[coord[0]][coord[1]].getPiece();
			} catch (Exception e) {
				return false;
			}
			return p instanceof King;
		});
	}
	
	public void exposuresTheKing() {
		String initialPoint = this.getCurrentField().getCoordinate();
		Set<String> secureMovements = new HashSet<>(this.movements);
		for(String m : this.movements) {
			if(this instanceof King && isFirstMovement && rock.contains(m)) {
				if(getBoard().isCheck() == this.color) secureMovements.remove(m);
				continue;					
			}
			Field f = getBoard().getMatrix()[Field.uCord(m)[0]][Field.uCord(m)[1]];
			adminMode = true;
			Piece enemy = this.move(m);
	        Piece.colors isCheck = getBoard().isCheck();
	        if (isCheck == this.color) {
	        	secureMovements.remove(m);
	        }
	        if(containsAKing() && this instanceof King) secureMovements.remove(m);
	        this.move(initialPoint);
	        adminMode = false;
	        if(enemy != null) {
	        	f.setPiece(enemy);
	        	enemy.setCurrentField(f);
	        }
		}
		this.movements = secureMovements;
	}
}
