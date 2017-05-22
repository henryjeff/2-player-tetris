package com.jeff.gamescreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jeff.game.Game;

public class Square {

	private int x, y;
	private TileType type;
	private boolean isEmpty;
	private Texture texture;

	public Square(int x, int y, TileType type, boolean isEmpty) {
		String textureDir = "";
		switch (type) {
		case TILE_RED:
			textureDir = "./textures/tile_red.png";
			break;
		case TILE_BLUE:
			textureDir = "./textures/tile_blue.png";
			break;
		case TILE_GREEN:
			textureDir = "./textures/tile_green.png";
			break;
		case TILE_YELLOW:
			textureDir = "./textures/tile_yellow.png";
			break;
		case PLAYFIELD_BG:
			textureDir = "./textures/tile_playfield_bg.png";
			break;
		case BG:
			textureDir = "./textures/tile_bg.png";
			break;
		}
		texture = new Texture(textureDir);
		texture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
		this.type = type;
		this.x = x;
		this.y = y;
		this.setEmpty(isEmpty);
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

	public void draw(SpriteBatch batch, Queuefield queuefield) {
		batch.draw(texture, (queuefield.getXParallaxOffset() + x * Game.SIZE_CLIP) - (queuefield.getXOffset() * 2) , (queuefield.getYParallaxOffset() + y * Game.SIZE_CLIP) - (queuefield.getYOffset() * 2), 0, 0, texture.getWidth(), texture.getHeight(), 2, 2, 0, 0, 0, texture.getWidth(), texture.getHeight(),  false, true);
	}

	public void draw(SpriteBatch batch, Playfield playfield) {
		batch.draw(texture, (playfield.getXParallaxOffset() + x * Game.SIZE_CLIP) - (playfield.getXOffset() * 2), (playfield.getYParallaxOffset() + y * Game.SIZE_CLIP) - (playfield.getYOffset() * 2), 0, 0, texture.getWidth(), texture.getHeight(), 2, 2, 0, 0, 0, texture.getWidth(), texture.getHeight(),  false, true);
	}
	
	public boolean isEmpty() {
		return isEmpty;
	}

	public void setEmpty(boolean isEmpty) {
		this.isEmpty = isEmpty;
	}
}
