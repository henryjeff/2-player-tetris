package com.jeff.gamescreen;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Square {

	public static final int SIZE_CLIP = 32;

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

	public void draw(SpriteBatch batch, Playfield playfield) {
		int playfieldWidth = playfield.getWidth() * SIZE_CLIP;
		int playfieldHeight = playfield.getHeight() * SIZE_CLIP;
		int xOffset = playfield.getXOffset() - playfieldWidth / 2;
		int yOffset = playfield.getYOffset() - playfieldHeight / 2;
		batch.draw(texture,  xOffset + x * SIZE_CLIP, yOffset + y * SIZE_CLIP, SIZE_CLIP, SIZE_CLIP);
	}

	public boolean isEmpty() {
		return isEmpty;
	}

	public void setEmpty(boolean isEmpty) {
		this.isEmpty = isEmpty;
	}
}
