package com.jeff.controller;

import com.badlogic.gdx.Input;
import com.jeff.gamescreen.Playfield;
import com.jeff.gamescreen.Tetromino;

import com.jeff.player.Player;
import com.jeff.systems.MoveSystem;

public class Shift implements Action {

	MoveSystem moveSystem;
	Playfield playfield;
	int autoShiftTimer;
	int shiftTimer;
	int shiftAmount;
	boolean enacting;

	public Shift(MoveSystem moveSystem, Playfield playfield, int shiftAmount) {
		this.moveSystem = moveSystem;
		this.playfield = playfield;
		autoShiftTimer = 0;
		shiftTimer = 0;
		this.shiftAmount = shiftAmount;
	
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
		if(input.isKeyJustPressed(key)){
			moveSystem.shift(playfield, tetromino, shiftAmount);
		}
		if(input.isKeyPressed(key)){
			if (autoShiftTimer >= 5) {
				if(shiftTimer >= 2){
					shiftTimer = 0;
					moveSystem.shift(playfield, tetromino, shiftAmount);				
				}else{
					shiftTimer++;
				}
			} else {
				autoShiftTimer++;
			}
		} else {
			autoShiftTimer = 0;
			shiftTimer = 0;
		}
	}
}
