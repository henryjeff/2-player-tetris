package com.jeff.gamescreen;

import java.util.concurrent.ConcurrentLinkedQueue;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.jeff.game.Game;
import com.jeff.player.Player;
import com.jeff.statemachine.StateName;

public class Itemqueue {
	
	public Player player;
	public boolean clearing;
	
	private static final int height = 4;
	private static final int width = 5;
	private Animation<TextureRegion> borderAnimation;
	public Item nextItem;
	private Texture firstSlot;
	private Texture secondSlot;
	private Texture thirdSlot;
	private Texture fourthSlot;
	
	private float parallaxWeight;
	private int xParallaxOffset;
	private int yParallaxOffset;
	private int xOffset;
	private int yOffset;
	private int x;
	private int y;
	
	public Itemqueue(int x, int y, TileType color, float parallaxWeight){
		this.parallaxWeight = parallaxWeight;
		this.x = x;
		this.y = y;
		clearing = false;
		xOffset = 0;
		yOffset = 0;
		xParallaxOffset = 0;
		yParallaxOffset = 0;
		switch (color) {
		case TILE_RED:
			borderAnimation = new Animation<TextureRegion>(0.1f, Game.atlas.findRegions("red/itemqueue_red"),
					PlayMode.LOOP);
			break;
		case TILE_BLUE:
			borderAnimation = new Animation<TextureRegion>(0.1f, Game.atlas.findRegions("blue/itemqueue_blue"),
					PlayMode.LOOP );
			break;
		}
		player = null;
	}
	
	public void update(float delta) {
		int parallaxOffsetX = (int) ((Game.camera.position.x - (Gdx.graphics.getWidth() / 2)) * parallaxWeight);
		int parallaxOffsetY = (int) ((Game.camera.position.y - (Gdx.graphics.getHeight() / 2)) * parallaxWeight);
		xParallaxOffset = x + parallaxOffsetX;
		yParallaxOffset = y + parallaxOffsetY;
		ConcurrentLinkedQueue<Item> tempItems = new ConcurrentLinkedQueue<Item>(player.getItems());
		for(int i = 0; i < 5; i++){
			Item tempItem = tempItems.poll();
			if(tempItem == null){
				switch(i){
					case 0:
						nextItem = null;
						break;
					case 1:
						firstSlot = new Texture(Gdx.files.internal("textures/static_small_item_blank.png"));
						break;
					case 2:
						secondSlot = new Texture(Gdx.files.internal("textures/static_small_item_blank.png"));
						break;
					case 3:
						thirdSlot = new Texture(Gdx.files.internal("textures/static_small_item_blank.png"));
						break;
					case 4:
						fourthSlot = new Texture(Gdx.files.internal("textures/static_small_item_blank.png"));
						break;
				}
			}else{
				String typeString = tempItem.type.toString().toLowerCase();
				switch(i){
					case 0:
						if(nextItem == null){
							nextItem = new Item(-64, -52, tempItem.type, player.playfield);
							nextItem.stateMachine.changeState(nextItem.stateMachine.getState(StateName.CREATE));
						}else{
							nextItem.update(delta);
						}
						break;
					case 1:
						firstSlot = new Texture(Gdx.files.internal("textures/static_small_item_" + typeString + ".png"));
						break;
					case 2:
						secondSlot = new Texture(Gdx.files.internal("textures/static_small_item_" + typeString + ".png"));
						break;
					case 3:
						thirdSlot = new Texture(Gdx.files.internal("textures/static_small_item_" + typeString + ".png"));
						break;
					case 4:
						fourthSlot = new Texture(Gdx.files.internal("textures/static_small_item_" + typeString + ".png"));
						break;
				}
			}
		}
	}

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getXOffset() {
		return xOffset;
	}

	public int getYOffset() {
		return yOffset;
	}

	public int getXParallaxOffset() {
		return xParallaxOffset;
	}

	public int getYParallaxOffset() {
		return yParallaxOffset;
	}

	public float getParallaxWeight() {
		return parallaxWeight;
	}
	
	
	public void draw(SpriteBatch batch) {
		int parallaxOffsetX = (int) ((Game.camera.position.x - (Gdx.graphics.getWidth() / 2)) * parallaxWeight);
		int parallaxOffsetY = (int) ((Game.camera.position.y - (Gdx.graphics.getHeight() / 2)) * parallaxWeight);
		TextureRegion borderRegion = borderAnimation.getKeyFrame(Game.elapsedTime);
		borderRegion.flip(false, true);
		batch.draw(borderRegion, 
				parallaxOffsetX + (x - (borderRegion.getRegionWidth())),
				parallaxOffsetY + (y - (borderRegion.getRegionHeight() / 2)) + 25 - (yOffset * 2), 0, 0,
				borderRegion.getRegionWidth(), borderRegion.getRegionHeight(), 2, 2, 0);
		borderRegion.flip(false, true);
		if(nextItem != null){
			nextItem.draw(batch, this);
		}
		for(int i = 0; i < 4 && !player.getItems().isEmpty(); i++){
			TextureRegion slot = null;
			int textureOffsetX = -4;
			int textureOffsetY = 0;
			switch(i){
				case 0:
					if(firstSlot == null) break;
					slot = new TextureRegion(firstSlot);
					textureOffsetY = 67;
					break;
				case 1:
					if(secondSlot == null) break;
					slot = new TextureRegion(secondSlot);
					textureOffsetY = 91;
					break;
				case 2:
					if(thirdSlot == null) break;
					slot = new TextureRegion(thirdSlot);
					textureOffsetY = 115;
					break;
				case 3:
					if(fourthSlot == null) break;
					textureOffsetY = 139;
					slot = new TextureRegion(fourthSlot);
					break;
				
			}
			if(slot != null){
				slot.flip(false, true);
				batch.draw(slot, 
						parallaxOffsetX + x + textureOffsetX,
						parallaxOffsetY + y + textureOffsetY,
						0, 
						0, 
						slot.getRegionWidth(),
						slot.getRegionHeight(), 
						2, 
						2, 
						0);
			}
		}
	}
}
