package br.com.erick.chess.vision;

import br.com.erick.chess.application.ChessGame;
import br.com.erick.chess.model.Field;

public interface ButtonListener {
	public void performAction(ChessGame.actions a, Field f);
}
