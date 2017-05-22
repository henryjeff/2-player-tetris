package com.jeff.statemachine;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.jeff.game.Game;

public class State {

	public Animation<TextureRegion> animation;
	public StateMachine stateMachineParent;
	public StateName stateName;
	public boolean loop;
	
	public State(StateName stateName, Animation<TextureRegion> animation, boolean loop) {
		stateMachineParent = null;
		this.stateName = stateName;
		this.animation = animation;
		this.loop = loop;
	}

	public void draw(SpriteBatch batch, int x, int y, int scale) {
		TextureRegion textureRegion = animation.getKeyFrame(stateMachineParent.elapsedTime);
		textureRegion.flip(false, true);
		batch.draw(textureRegion, x, y, 0, 0, textureRegion.getRegionWidth(), textureRegion.getRegionHeight(), scale, scale, 0);
		textureRegion.flip(false, true);
	}

	public void setStateMachineParent(StateMachine stateMachine){
		this.stateMachineParent = stateMachine;
	}

}
