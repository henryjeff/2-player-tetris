package com.jeff.gamescreen;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.jeff.game.Game;
import com.jeff.statemachine.State;
import com.jeff.statemachine.StateMachine;
import com.jeff.statemachine.StateName;

public class Item {
	public int x, y;
	public ItemType type;
	public Playfield playfield;
	public StateMachine stateMachine;
	public boolean destory;
	public int timer;
	
	private boolean shouldBeDestroyed;
	
	public Item(int x, int y, ItemType type, Playfield playfield){
		this.x = x;
		this.y = y;
		this.type = type;
		this.playfield = playfield;
		timer = 0;
		shouldBeDestroyed = false;
		destory = false;
		String typeString = type.toString().toLowerCase();
		Animation<TextureRegion> idle = new Animation<TextureRegion>(0.1f, Game.atlas.findRegions(
				typeString + "/idle/items_" + typeString + "_idle"), PlayMode.LOOP);
		Animation<TextureRegion> create = new Animation<TextureRegion>(0.1f, Game.atlas.findRegions(
				typeString + "/create/items_" + typeString + "_create"), PlayMode.LOOP);
		Animation<TextureRegion> clear = new Animation<TextureRegion>(0.1f, Game.atlas.findRegions(
				typeString + "/clear/items_" + typeString + "_clear"), PlayMode.LOOP);
		stateMachine = new StateMachine(new State(StateName.IDLE, idle, true));
		stateMachine.addState(new State(StateName.CREATE, create, false));
		stateMachine.addState(new State(StateName.CLEAR, clear, false));
	}
	
	public Item(Item tempItem) {
		this.x = tempItem.x;
		this.y = tempItem.y;
		this.type = tempItem.type;
		this.playfield = tempItem.playfield;
		timer = 0;
		shouldBeDestroyed = false;
		destory = false;
		String typeString = type.toString().toLowerCase();
		Animation<TextureRegion> idle = new Animation<TextureRegion>(0.1f, Game.atlas.findRegions(
				typeString + "/idle/items_" + typeString + "_idle"), PlayMode.LOOP);
		Animation<TextureRegion> create = new Animation<TextureRegion>(0.1f, Game.atlas.findRegions(
				typeString + "/create/items_" + typeString + "_create"), PlayMode.LOOP);
		Animation<TextureRegion> clear = new Animation<TextureRegion>(0.1f, Game.atlas.findRegions(
				typeString + "/clear/items_" + typeString + "_clear"), PlayMode.LOOP);
		stateMachine = new StateMachine(new State(StateName.IDLE, idle, true));
		stateMachine.addState(new State(StateName.CREATE, create, false));
		stateMachine.addState(new State(StateName.CLEAR, clear, false));
	}

	
	public void update(float delta){
		stateMachine.update(delta);
		if(destory && stateMachine.currentState == stateMachine.getState(StateName.IDLE)){
			shouldBeDestroyed = true;
		}
	}
	
	public void draw(SpriteBatch batch, Playfield playfield){
		int drawX = (playfield.getXParallaxOffset() + x * Game.SIZE_CLIP) - (48);
		int drawY = (playfield.getYParallaxOffset() + y * Game.SIZE_CLIP) - (48 + 32);
		stateMachine.draw(batch, drawX, drawY, 2);
	}
	
	public void draw(SpriteBatch batch, Itemqueue itemqueue){
		int drawX = (itemqueue.getXParallaxOffset() + x);
		int drawY = (itemqueue.getYParallaxOffset() + y);
		stateMachine.draw(batch, drawX, drawY, 2);
	}
	
	public boolean shouldBeDestroyed(){
		return shouldBeDestroyed;
	}
	
	public void destroy(){
		destory = true;
		stateMachine.changeState(stateMachine.getState(StateName.CLEAR));
	}
}
