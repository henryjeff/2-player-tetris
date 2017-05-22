package com.jeff.gamescreen;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Background {
	public ArrayList<BackgroundLayer> backgroundLayers;
	
	public Background(ArrayList<BackgroundLayer> backgroundLayers){
		this.backgroundLayers = backgroundLayers;
	}
	public void draw(SpriteBatch batch){
		for(BackgroundLayer bgLayer : backgroundLayers){
			bgLayer.draw(batch);
		}
	}
	
	public void update(float delta){
		for(BackgroundLayer bgLayer : backgroundLayers){
			bgLayer.update(delta);
		}
	}
	
}
