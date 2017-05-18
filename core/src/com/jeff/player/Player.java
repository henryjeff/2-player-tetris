package com.jeff.player;

import java.util.HashMap;
import java.util.Map.Entry;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.jeff.controller.Action;
import com.jeff.gamescreen.Tetromino;
import com.jeff.gamescreen.TileType;

public class Player{
	public HashMap<Integer, Action> controlMap;
	public Tetromino tetromino;
	public Tetromino nextTetromino;
	public TileType tileType;
	
	private long score;
	
	public int spawnOffset;
	public int placeTimer;
	public int placeSpeed;
	public int fallTimer;
	public int fallSpeed;
	private int forceFallSpeed;

	public Player(HashMap<Integer, Action> controlMap, TileType tileType, int spawnOffset) {
		this.controlMap = controlMap;
		this.spawnOffset = spawnOffset;
		this.tileType = tileType;
		tetromino = null;
		nextTetromino = null;
		placeTimer = 0;
		placeSpeed = 60;
		fallTimer = 0;
		fallSpeed = 50;
		forceFallSpeed = 1;
		score = 0;
	}

	public int update() {
		Input input = Gdx.input;
		for (Entry<Integer, Action> entry : controlMap.entrySet()) {
			int key = (int) entry.getKey();
			Action action = (Action) entry.getValue();
			action.enact(input, tetromino, key);
			action.update(this);
		}
		return 0;
	}
	
	public void addPoints(long points){
		score += points;
		System.out.println(tileType + ": " + score);
	}
	
	public Tetromino getNextTetromino(){
		return nextTetromino;
	}
	
	public int getForceFallSpeed(){
		return forceFallSpeed;
	}
}
