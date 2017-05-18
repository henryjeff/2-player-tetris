package com.jeff.controller;

import com.badlogic.gdx.Input;
import com.jeff.gamescreen.Playfield;
import com.jeff.gamescreen.Tetromino;

import com.jeff.player.Player;
import com.jeff.systems.RotationSystem;

public class Rotate implements Action {
	RotationSystem rotationSystem;
	Playfield playfield;

	public Rotate(RotationSystem rotationSystem, Playfield playfield) {
		this.rotationSystem = rotationSystem;
		this.playfield = playfield;
	}

	@Override
	public int update(Player player) {
		return 0;
	}

	@Override
	public void enact(Input input, Tetromino tetromino, int key) {
		if(tetromino == null){
			return;
		}
		if (input.isKeyJustPressed(key)) {
			rotationSystem.rotate(Tetromino.TURN_LEFT, tetromino, playfield);
		}
	}
}
