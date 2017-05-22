package com.jeff.gamescreen;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.jeff.game.Game;
import com.jeff.statemachine.State;
import com.jeff.statemachine.StateMachine;
import com.jeff.statemachine.StateName;

public class Square {

	private int x, y;
	private TileType type;
	private boolean isEmpty;
	public StateMachine stateMachine;
	
	public Square(int x, int y, TileType type, boolean isEmpty) {
		String idleDir = "";
		boolean soloTexture = false;
		switch (type) {
		case TILE_RED:
			idleDir = "idle/tile_idle_red";
			break;
		case TILE_BLUE:
			idleDir = "idle/tile_idle_blue";
			break;
		case PLAYFIELD_BG:
			idleDir = "textures/tile_playfield_bg.png";
			soloTexture = true;
			break;
		case BG:
			idleDir = "textures/tile_bg.png";
			soloTexture = true;
			break;
		}
		this.type = type;
		this.x = x;
		this.y = y;
		this.setEmpty(isEmpty);
		Animation<TextureRegion> idle;
		if(!soloTexture){
			idle = new Animation<TextureRegion>(0.1f, Game.atlas.findRegions(idleDir), PlayMode.LOOP);
		}else{
			idle = new Animation<TextureRegion>(0.1f, new TextureRegion[] {new TextureRegion(new Texture(idleDir))});
		}
		stateMachine = new StateMachine(new State(StateName.IDLE, idle, true));
		if(type == TileType.TILE_RED){
			Animation<TextureRegion> redCreate = new Animation<TextureRegion>(0.1f, Game.atlas.findRegions("create/red/tile_create_red"), PlayMode.LOOP);
			Animation<TextureRegion> redShine = new Animation<TextureRegion>(0.1f, Game.atlas.findRegions("shine/red/tile_shine_red"), PlayMode.LOOP);
			Animation<TextureRegion> redClear = new Animation<TextureRegion>(0.1f, Game.atlas.findRegions("clear/blue/tile_clear_red"), PlayMode.LOOP);
			stateMachine.addState(new State(StateName.CREATE, redCreate, false));
			stateMachine.addState(new State(StateName.SHINE, redShine, false));
			stateMachine.addState(new State(StateName.CLEAR, redClear, false));
		}else if (type == TileType.TILE_BLUE){
			Animation<TextureRegion> blueCreate = new Animation<TextureRegion>(0.1f, Game.atlas.findRegions("create/blue/tile_create_blue"), PlayMode.LOOP);
			Animation<TextureRegion> blueShine = new Animation<TextureRegion>(0.1f, Game.atlas.findRegions("shine/blue/tile_shine_blue"), PlayMode.LOOP);
			Animation<TextureRegion> blueClear = new Animation<TextureRegion>(0.1f, Game.atlas.findRegions("clear/blue/tile_clear_blue"), PlayMode.LOOP);
			stateMachine.addState(new State(StateName.CREATE, blueCreate, false));
			stateMachine.addState(new State(StateName.SHINE, blueShine, false));
			stateMachine.addState(new State(StateName.CLEAR, blueClear, false));
		}
	}

	public void fall(int fall) {
		this.y += fall;
	}

	public void move(int move) {
		this.x += move;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public TileType getType() {
		return type;
	}

	public void update(float delta){
		stateMachine.update(delta);
	}
	
	public void draw(SpriteBatch batch, Queuefield queuefield) {
		int drawX = (queuefield.getXParallaxOffset() + x * Game.SIZE_CLIP) - (queuefield.getXOffset() * 2);
		int drawY = (queuefield.getYParallaxOffset() + y * Game.SIZE_CLIP) - (queuefield.getYOffset() * 2);
		stateMachine.draw(batch, drawX, drawY, 2);
//		batch.draw(texture, (queuefield.getXParallaxOffset() + x * Game.SIZE_CLIP) - (queuefield.getXOffset() * 2) , (queuefield.getYParallaxOffset() + y * Game.SIZE_CLIP) - (queuefield.getYOffset() * 2), 0, 0, texture.getWidth(), texture.getHeight(), 2, 2, 0, 0, 0, texture.getWidth(), texture.getHeight(),  false, true);
	}

	public void draw(SpriteBatch batch, Playfield playfield) {
		int drawX = (playfield.getXParallaxOffset() + x * Game.SIZE_CLIP) - (playfield.getXOffset() * 2);
		int drawY = (playfield.getYParallaxOffset() + y * Game.SIZE_CLIP) - (playfield.getYOffset() * 2);
		stateMachine.draw(batch, drawX, drawY, 2);
//		batch.draw(texture, (playfield.getXParallaxOffset() + x * Game.SIZE_CLIP) - (playfield.getXOffset() * 2), (playfield.getYParallaxOffset() + y * Game.SIZE_CLIP) - (playfield.getYOffset() * 2), 0, 0, texture.getWidth(), texture.getHeight(), 2, 2, 0, 0, 0, texture.getWidth(), texture.getHeight(),  false, true);
	}
	
	public boolean isEmpty() {
		return isEmpty;
	}

	public void setEmpty(boolean isEmpty) {
		this.isEmpty = isEmpty;
	}
}
