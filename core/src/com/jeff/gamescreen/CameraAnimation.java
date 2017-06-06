package com.jeff.gamescreen;

import java.awt.Point;
import java.util.ArrayList;

public class CameraAnimation {

	private ArrayList<Point> keyframes;
	private float frameTime;
	private float marker;

	public CameraAnimation(ArrayList<Point> keyframes, float frameTime) {
		this.keyframes = keyframes;
		this.frameTime = frameTime;
		this.marker = -1.0f;
	}

	public void startAnimation(float elapsedTime) {
		marker = elapsedTime;
	}

	public int getKeyframe(float elapsedTime) {
		if (marker != -1.0f) {
			elapsedTime -= marker;
			System.out.println("Elapsedtime " + elapsedTime + "\nframetime : " + frameTime);
			return (int) (elapsedTime / frameTime);
		} else {
			return -1;
		}
	}
	
	public ArrayList<Point> getKeyframes(){
		return keyframes;
	}
}
