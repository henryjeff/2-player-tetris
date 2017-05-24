package com.jeff.gamescreen;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class TextHandler {
	public String text; 
	public TileType color;
	ArrayList<Texture> textures;
	
	public TextHandler(String text, TileType color){
		this.color = color;
		this.text = text;
	}
	
	public void setText(String text){
		this.text = text;
	}
	
	public ArrayList<Texture> getTexture(){
		ArrayList<Texture> tempArray = new ArrayList<Texture>();
		for(char letter : text.toCharArray()){
			Texture texture = new Texture(Gdx.files.internal("numbers/" + color.toString().toLowerCase().substring(5) + "/" + letter + ".png"));
			tempArray.add(texture);
		}
		return tempArray;
	}
}

