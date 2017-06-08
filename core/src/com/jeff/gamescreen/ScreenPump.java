package com.jeff.gamescreen;

import com.badlogic.gdx.graphics.OrthographicCamera;

public class ScreenPump {
	private float amount;
	private float duration;
	private boolean isPumping;
	private int currentFrame;
	
	public ScreenPump(float amount, float decayTime){
		this.amount = amount;
		this.duration = decayTime;
	}
	
	public void Pump(){
		currentFrame = 0;
		isPumping = true;
	}
	
	public void update(float delta, OrthographicCamera camera){
		if(isPumping){
			if(currentFrame >= duration){
				isPumping = false;
				camera.zoom = 1.0f;
				return;
			}
			float zoomAmount =  amount * ((duration - currentFrame) / duration) ;
			camera.zoom = 1.0f + zoomAmount;
			currentFrame++;
		}
	}
	
	public float ease(float t, float b, float c, float d) {
		return c*t/d + b;
	};
	
}
