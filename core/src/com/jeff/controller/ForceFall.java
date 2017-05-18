package com.jeff.controller;

import com.badlogic.gdx.Input;
import com.jeff.gamescreen.Playfield;
import com.jeff.gamescreen.Tetromino;
import com.jeff.player.Player;
import com.jeff.systems.MoveSystem;

public class ForceFall implements Action {

	MoveSystem moveSystem;
	Playfield playfield;
	int autoFallTimer;
	int fallTimer;
	int fallSpeed;

	public ForceFall(MoveSystem moveSystem, Playfield playfield) {
		this.moveSystem = moveSystem;
		this.playfield = playfield;
		autoFallTimer = 0;
		fallTimer = 0;
	}

	@Override
	public int update(Player player) {
		fallSpeed = player.getForceFallSpeed();
		return 0;
	}

	@Override
	public void enact(Input input, Tetromino tetromino, int key) {
		if(tetromino == null){
			return;
		}
		if(input.isKeyPressed(key)){
			if (autoFallTimer >= 5) {
				if(fallTimer >= fallSpeed){
					fallTimer = 0;
					tetromino.safeFall();	
				}else{
					fallTimer++;
				}
			} else {
				autoFallTimer++;
			}
		} else {
			autoFallTimer = 0;
			fallTimer = 0;
		}
	}
}
