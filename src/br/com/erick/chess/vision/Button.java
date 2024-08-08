package br.com.erick.chess.vision;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;

import br.com.erick.chess.application.ChessGame.actions;
import br.com.erick.chess.model.Field;

@SuppressWarnings("serial")
public class Button extends JButton implements MouseListener{
	private Field field;
	private Grid grid;
	private Color defaultColor;
	private List<ButtonListener> listeners = new ArrayList<ButtonListener>();
	
	public Color getColor() {
		return defaultColor;
	}

	public void setColor(Color color) {
		this.defaultColor = color;
	}

	public Button(Field field, Grid grid) {
		super();
		this.field = field;
		this.grid = grid;
		addMouseListener(this);
	}
	
	public void addListener(ButtonListener l) {
		listeners.add(l);
	}
	
	public void notifyListeners(actions a) {
		listeners.forEach(l -> l.performAction(a, field));
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON1) {
			if(field.getBoard().getChessGame().getCurrentAction() == actions.SELECT 
					&& this.field.getPiece() != null 
					&& this.field.getPiece().getColor() == this.field.getBoard().getChessGame().getTurn()
					&& !this.field.getPiece().getSecureMovements().isEmpty()) {
				notifyListeners(actions.SELECT);
			}else if(field.getBoard().getChessGame().getCurrentAction() == actions.MOVE && field.getBoard().getChessGame().getSelectedPiece().equals(field.getPiece())) {
				notifyListeners(actions.DESELECT);
			}else if(field.getBoard().getChessGame().getCurrentAction() == actions.MOVE && Grid.isGreen(this.getBackground())) {
				notifyListeners(actions.MOVE);
			}
		}
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
	}
	@Override
	public void mouseReleased(MouseEvent e) {
	}
	@Override
	public void mouseEntered(MouseEvent e) {
	}
	@Override
	public void mouseExited(MouseEvent e) {
	}
}
