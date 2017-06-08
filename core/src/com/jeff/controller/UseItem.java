package com.jeff.controller;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Input;
import com.jeff.gamescreen.Item;
import com.jeff.gamescreen.Playfield;
import com.jeff.player.Player;

public class UseItem implements Ability {
	private Playfield playfield;

	public UseItem(Playfield playfield) {
		this.playfield = playfield;
	}

	@Override
	public void use(Input input, Player player, int key) {
		if (input.isKeyJustPressed(key)) {
			Player otherPlayer = playfield.getOtherPlayer(player);
			player.itemqueue.nextItem = null;
			Item item = player.useItem();
			if (item != null) {
				switch (item.type) {
					case SKIP:
						player.tetromino = null;
						System.out.println("used skip on " + otherPlayer.tileType);
						break;
					case LOCK:
						System.out.println("used lock");
						otherPlayer.isLocked = true;
						otherPlayer.fallSpeed = 1000.0f;
						otherPlayer.forceFallSpeed = 1000.0f;
						break;
					case SPEED:
						
						System.out.println("used speed");
						otherPlayer.isSpeed = true;
						otherPlayer.fallSpeed = 0.1f;
						break;
					case DESTROY:
						otherPlayer.tetromino = null;
						System.out.println("used destory");
						break;
				}
			}

		}
	}

}
