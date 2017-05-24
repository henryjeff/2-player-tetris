package com.jeff.gamescreen;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.jeff.game.Game;
import com.jeff.player.Player;
import com.jeff.statemachine.StateName;

public class Queuefield {

	public Square[][] squares;
	public Player player;
	public boolean clearing;
	
	private static final int[] bobY = new int[] { 0, 5, 7, 6, 5, -2 };
	private static final int height = 4;
	private static final int width = 5;
	private Tetromino tetromino;
	private Animation<TextureRegion> borderAnimation;
	private TextHandler scoreText;
	
	private float parallaxWeight;
	private int xParallaxOffset;
	private int yParallaxOffset;
	private int xOffset;
	private int yOffset;
	private int x;
	private int y;

	
	public Queuefield(int x, int y, TileType color, float parallaxWeight) {
		this.squares = new Square[width][height];
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
			borderAnimation = new Animation<TextureRegion>(0.1f, Game.atlas.findRegions("red/queuefield_red"),
					PlayMode.LOOP);
			scoreText = new TextHandler("0", TileType.TILE_RED);
			break;
		case TILE_BLUE:
			borderAnimation = new Animation<TextureRegion>(0.1f, Game.atlas.findRegions("blue/queuefield_blue"),
					PlayMode.LOOP );
			scoreText = new TextHandler("0", TileType.TILE_BLUE);
			break;
		}
		player = null;
		for (int y1 = 0; y1 < height; y1++) {
			for (int x1 = 0; x1 < width; x1++) {
				squares[x1][y1] = new Square(x1, y1, TileType.PLAYFIELD_BG, true);
			}
		}
	}

	public void update(float delta) {
		int queuefieldWidth = width * Game.SIZE_CLIP;
		int queuefieldHeight = height * Game.SIZE_CLIP;
		int parallaxOffsetX = (int) ((Game.camera.position.x - (Gdx.graphics.getWidth() / 2)) * parallaxWeight);
		int parallaxOffsetY = (int) ((Game.camera.position.y - (Gdx.graphics.getHeight() / 2)) * parallaxWeight);
		xParallaxOffset = x - queuefieldWidth / 2 + parallaxOffsetX;
		yParallaxOffset = y - queuefieldHeight / 2 + parallaxOffsetY;
		yOffset = bobY[(int) ((Game.elapsedTime / 0.1f) % 6)];
		if(tetromino != null){
			for(Square square : tetromino.squares){
				square.update(delta);
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

	public void changeScore(int score){
		scoreText.setText(Integer.toString(score));
	}
	
	public void changeTetromino(Tetromino tetromino) {
		if (this.tetromino != tetromino) {
			this.tetromino = tetromino;
			setTetromino(tetromino);
		}
	}

	public void setTetromino(Tetromino tetromino) {
		for (Square square : tetromino.squares) {
			square.stateMachine.changeState(square.stateMachine.getState(StateName.CREATE));
		}
	}

	public void draw(SpriteBatch batch) {
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				squares[x][y].draw(batch, this);
			}
		}
		tetromino.draw(batch, this);
		int parallaxOffsetX = (int) ((Game.camera.position.x - (Gdx.graphics.getWidth() / 2)) * parallaxWeight);
		int parallaxOffsetY = (int) ((Game.camera.position.y - (Gdx.graphics.getHeight() / 2)) * parallaxWeight);
		TextureRegion borderRegion = borderAnimation.getKeyFrame(Game.elapsedTime);
		borderRegion.flip(false, true);
		batch.draw(borderRegion, 
				parallaxOffsetX + (x - (borderRegion.getRegionWidth())), 
				parallaxOffsetY + (y - (borderRegion.getRegionHeight() / 2)) + 25 - (yOffset * 2), 
				0, 
				0, 
				borderRegion.getRegionWidth(),
				borderRegion.getRegionHeight(), 
				2, 
				2, 
				0);
		borderRegion.flip(false, true);
		int numOfTextures = scoreText.getTexture().size();
		ArrayList<Texture> scoreTextures = scoreText.getTexture();
		for(int i = 0; i < numOfTextures; i++){
			TextureRegion scoreRegion = new TextureRegion(scoreTextures.get(i));
			int x1 = parallaxOffsetX + (x - (scoreRegion.getRegionWidth())) + (i * 12 + (i * 2));
			scoreRegion.flip(false, true);
			batch.draw(scoreRegion, 
					x1 - 28, 
					parallaxOffsetY + (y - (scoreRegion.getRegionHeight() / 2)) + 118 - (yOffset * 2), 
					0, 
					0, 
					scoreRegion.getRegionWidth(),
					scoreRegion.getRegionHeight(), 
					2, 
					2, 
					0);
			scoreRegion.flip(false, true);
		}
	}
}