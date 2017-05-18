package com.jeff.systems;

import com.jeff.gamescreen.Playfield;
import com.jeff.gamescreen.Tetromino;

public class MoveSystem {

	public MoveSystem() {
		System.out.println("MoveSystem: Initializing");
	}

	public boolean fall(Playfield playfield, Tetromino tetromino) {
		tetromino.fall();
		if (tetromino.isOverlaps(playfield) || tetromino.isColliding(playfield)) {
			tetromino.up();
			return false;
		}
		return true;
	}

	public void shift(Playfield playfield, Tetromino tetromino, int move) {
		tetromino.move(move);
		if (tetromino.isOverlaps(playfield) || tetromino.isColliding(playfield)) {
			tetromino.move(-move);
		}
	}

	public String toString(int move) {
		return (move == 1) ? "RIGHT" : "LEFT";
	}
}
