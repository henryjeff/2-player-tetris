package com.jeff.controller;

import com.badlogic.gdx.Input;
import com.jeff.player.Player;

public interface Ability {
	public void use(Input input, Player player, int key);
}
