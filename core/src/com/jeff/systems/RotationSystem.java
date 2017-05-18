package com.jeff.systems;

import com.jeff.gamescreen.Playfield;
import com.jeff.gamescreen.Tetromino;

public class RotationSystem {

	public RotationSystem() {
		System.out.println("RotationSystem: Initializing");
	}

	public void rotate(int rotation, Tetromino tetromino, Playfield playfield) {
		tetromino.rotate(rotation);
		if (tetromino.isOverlaps(playfield) || tetromino.isColliding(playfield)) {
			tetromino.rotate(-rotation);
		}
	}

	public String toString(int rotation) {

		return (rotation == 1) ? "RIGHT" : "LEFT";
	}
}
