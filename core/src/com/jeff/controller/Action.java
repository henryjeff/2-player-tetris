package com.jeff.controller;

import com.badlogic.gdx.Input;
import com.jeff.gamescreen.Tetromino;
import com.jeff.player.Player;

public interface Action {
	public int update(Player player);

	public void enact(Input input, Tetromino tetromino, int key);
}
