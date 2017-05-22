package com.jeff.statemachine;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class StateMachine {
	
	public State defaultState;
	public State currentState;
	public ArrayList<State> states;
	public float elapsedTime;
	
	public StateMachine(State defaultState){
		this.defaultState = defaultState;
		elapsedTime = 0.0f;
		states = new ArrayList<State>();
		addState(defaultState);
		currentState = defaultState;
	}
	
	public void addState(State state){
		state.setStateMachineParent(this);
		states.add(state);
	}
	
	public State getState(StateName name){
		for(State state : states){
			if(state.stateName == name){
				return state;
			}
		}
		return null;
	}
	
	public void update(float delta){
		elapsedTime += delta;
		if(currentState.animation.isAnimationFinished(elapsedTime) && !currentState.loop){
			changeState(defaultState);
		}
	}
	
	public void draw(SpriteBatch batch, int x, int y, int scale){
		currentState.draw(batch, x, y, scale);
	}
	
	public void changeState(State newState){
		currentState = newState;
		elapsedTime = 0.0f;
	}
}
