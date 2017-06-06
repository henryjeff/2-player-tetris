package com.jeff.gamescreen;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.graphics.OrthographicCamera;

public class CameraShake {
	public float intensity;
	public float interval;
	public float length;

	private CameraAnimation cameraAnimation;
	private Random randomX;
	private Random randomY;
	
	public CameraShake(float intensity, float interval, float length){
		this.intensity = intensity;
		this.interval = interval;
		this.length = length;
		cameraAnimation = null;
		randomX = new Random();
		randomY = new Random();
	}
	
	public ArrayList<Point> generateShake(){
		ArrayList<Point> keyframes = new ArrayList<Point>();
		int tempIntensity = (int) intensity;
		for(int i = 0; i < length * interval; i++){
			int x = randomX.nextInt(tempIntensity);
			int y = randomY.nextInt(tempIntensity);
			keyframes.add(new Point(x, y));
			tempIntensity = getIntensity(i + 1);
		}
		return keyframes;
	}
 	
	private int getIntensity(int i){
		int keyframeLength = (int) (interval * length);
		float intensityInterval = this.intensity / keyframeLength;
		return (int) (this.intensity - (intensityInterval * i) + 1);
	}
	
	public void playShake(float elapsedTime){
		cameraAnimation = new CameraAnimation(generateShake(), length / interval);
		cameraAnimation.startAnimation(elapsedTime);
	}
	
	public void update(float elapsedTime, OrthographicCamera camera){
		if(cameraAnimation != null){
			if(cameraAnimation.getKeyframe(elapsedTime) < cameraAnimation.getKeyframes().size()){
				System.out.println("FRAME: " + cameraAnimation.getKeyframe(elapsedTime) + "\nTIME: " + elapsedTime + "\n");
				Point tempPoint = cameraAnimation.getKeyframes().get(cameraAnimation.getKeyframe(elapsedTime));
				camera.position.x += tempPoint.x;
				camera.position.y += tempPoint.y;
//				System.out.println("Key:\nX: " + tempPoint.getX() + "\nY: " + tempPoint.getY() + "\nTime: " + elapsedTime);
			}else{
				System.out.println("FUUUCK");
				cameraAnimation = null;
			}
		}
	}
	
	
}
