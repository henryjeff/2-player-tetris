package com.jeff.statemachine;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class State {
	
	public Animation<TextureRegion> animation;
	public StateName stateName;
	
	public State(StateName stateName, Animation<TextureRegion> animation){
		this.stateName = stateName;
		this.animation = animation;
	}
	
	public void draw(SpriteBatch batch, int x, int y, int scale){
		
	}
}
