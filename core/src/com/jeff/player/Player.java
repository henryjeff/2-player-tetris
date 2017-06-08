package com.jeff.player;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jeff.controller.Ability;
import com.jeff.controller.Action;
import com.jeff.gamescreen.Item;
import com.jeff.gamescreen.Itemqueue;
import com.jeff.gamescreen.Playfield;
import com.jeff.gamescreen.Queuefield;
import com.jeff.gamescreen.Tetromino;
import com.jeff.gamescreen.TileType;

public class Player{
	public HashMap<Integer, Action> controlMap;
	public HashMap<Integer, Ability> abilityMap;
	
	public Tetromino tetromino;
	public Tetromino nextTetromino;
	public TileType tileType;
	public ConcurrentLinkedQueue<Item> items;
	
	public Itemqueue itemqueue;
	public Queuefield queuefield;
	public Playfield playfield;
	
	public int score;
	public int itemTimer;
	public boolean isNormal;
	public boolean isSpeed;
	public boolean isLocked;
	public int spawnOffset;
	public float placeTimer;
	public float placeSpeed;
	public float fallTimer;
	public float fallSpeed;
	public float forceFallSpeed;

	public Player(HashMap<Integer, Action> controlMap, HashMap<Integer, Ability> abilityMap, TileType tileType, int spawnOffset) {
		this.controlMap = controlMap;
		this.abilityMap = abilityMap;
		this.spawnOffset = spawnOffset;
		this.tileType = tileType;
		items = new ConcurrentLinkedQueue<Item>();
		tetromino = null;
		nextTetromino = null;
		playfield = null;
		isLocked = false;
		isSpeed = false;
		placeTimer = 0;
		placeSpeed = 0.5f;
		fallTimer = 0;
		fallSpeed = 0.0f;
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
		for (Entry<Integer, Ability> entry : abilityMap.entrySet()){
			int key = (int) entry.getKey();
			Ability ability = (Ability) entry.getValue();
			ability.use(input, this, key);
		}
		if(queuefield != null){
			queuefield.update(delta);
			if(nextTetromino != null){
				queuefield.changeTetromino(nextTetromino);
			}
		}
		if(itemqueue != null){
			itemqueue.update(delta);
		}
		if(isSpeed == true || isLocked == true){
			itemTimer++;
		}
		if(itemTimer >= 320){
			fallSpeed = playfield.globalFallSpeed;
			forceFallSpeed = 0.02f;
			isNormal = true;
			itemTimer = 0;
			isSpeed = false;
			isLocked = false;
		}
		return 0;
	}
	
	public void draw(SpriteBatch batch) {
		if(queuefield != null){
			queuefield.draw(batch);
		}
		if(itemqueue != null){
			itemqueue.draw(batch);
		}
	}
	
	public void linkQueuefield(Queuefield queuefield){
		this.queuefield = queuefield;
		queuefield.player = this;
	}
	
	public void linkItemQueue(Itemqueue itemqueue){
		this.itemqueue = itemqueue;
		itemqueue.player = this;
	}
	
	public void addPoints(long points){
		score += points;
		queuefield.changeScore(score);
		System.out.println(tileType + ": " + score);
	}
	
	public Tetromino getNextTetromino(){
		return nextTetromino;
	}

	public void addItem(Item item) {
		items.add(item);
	}
	
	public ConcurrentLinkedQueue<Item> getItems(){
		return items;
	}
	
	public void changeFallSpeed(float fallSpeed){
		this.fallSpeed = fallSpeed;
	}

	public Item useItem() {
		Item usedItem = items.poll();
		if(usedItem != null){			
			usedItem.destroy();
		}
		return usedItem;
	}
}
