package com.jeff.player;

import java.util.HashMap;
import java.util.Map.Entry;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jeff.controller.Action;
import com.jeff.gamescreen.Playfield;
import com.jeff.gamescreen.Queuefield;
import com.jeff.gamescreen.Tetromino;
import com.jeff.gamescreen.TileType;

public class Player{
	public HashMap<Integer, Action> controlMap;
	
	public Tetromino tetromino;
	public Tetromino nextTetromino;
	public TileType tileType;
	
	public Queuefield queuefield;
	public Playfield playfield;
	
	private int score;
	
	public int spawnOffset;
	public float placeTimer;
	public float placeSpeed;
	public float fallTimer;
	public float fallSpeed;
	public float forceFallSpeed;

	public Player(HashMap<Integer, Action> controlMap, TileType tileType, int spawnOffset) {
		this.controlMap = controlMap;
		this.spawnOffset = spawnOffset;
		this.tileType = tileType;
		tetromino = null;
		nextTetromino = null;
		playfield = null;
		placeTimer = 0;
		placeSpeed = 0.5f;
		fallTimer = 0;
		fallSpeed = 1.5f;
		forceFallSpeed = 0.02f;
		score = 0;
	}

	public int update(float delta) {
		Input input = Gdx.input;
		for (Entry<Integer, Action> entry : controlMap.entrySet()) {
			int key = (int) entry.getKey();
			Action action = (Action) entry.getValue();
			action.enact(input, tetromino, key);
			action.update(this);
		}
		if(queuefield != null){
			queuefield.update(delta);
			if(nextTetromino != null){
				queuefield.changeTetromino(nextTetromino);
			}
		}
		return 0;
	}
	
	public void draw(SpriteBatch batch) {
		if(queuefield != null){
			queuefield.draw(batch);
		}
	}
	
	public void linkQueuefield(Queuefield queuefield){
		this.queuefield = queuefield;
		queuefield.player = this;
	}
	
	public void addPoints(long points){
		score += points;
		queuefield.changeScore(score);
		System.out.println(tileType + ": " + score);
	}
	
	public Tetromino getNextTetromino(){
		return nextTetromino;
	}
}
