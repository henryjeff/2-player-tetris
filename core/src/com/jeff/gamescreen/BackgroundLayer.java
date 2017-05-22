package com.jeff.gamescreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jeff.game.Game;

public class BackgroundLayer {
	private float parallaxWeight;
	private int drawX;
	private int drawY;
	private Texture texture;
	
	public BackgroundLayer(Texture texture, float parallaxWeight){
		this.texture = texture;
		this.parallaxWeight = parallaxWeight;
	}
	
	public void draw(SpriteBatch batch){
		batch.draw(texture, drawX - Gdx.graphics.getWidth(), drawY - Gdx.graphics.getHeight(), 0, 0, texture.getWidth(), texture.getHeight(), 2, 2, 0, 0, 0, texture.getWidth(), texture.getHeight(),  false, true);
	}
	
	public void update(float delta){
		drawX = (int) ((Game.camera.position.x - (Gdx.graphics.getWidth() / 2)) * parallaxWeight);
		drawY = (int) ((Game.camera.position.y - (Gdx.graphics.getHeight() / 2)) * parallaxWeight);
	}
}
