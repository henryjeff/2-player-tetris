package com.jeff.views;

import java.util.HashMap;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jeff.controller.Action;
import com.jeff.controller.ForceFall;
import com.jeff.controller.Rotate;
import com.jeff.controller.Shift;
import com.jeff.gamescreen.Playfield;
import com.jeff.gamescreen.TileType;
import com.jeff.player.Player;
import com.jeff.systems.MoveSystem;
import com.jeff.systems.RotationSystem;

public class GameScreen {
	private MoveSystem moveSystem;
	private RotationSystem rotationSystem;
	private Playfield playfield;

	private Player playerOne;
	private Player playerTwo;
	private Player playerThree;
	private Player playerFour;

	public GameScreen(int width, int height) {
		moveSystem = new MoveSystem();
		rotationSystem = new RotationSystem();
		playfield = new Playfield(16, 24);

		HashMap<Integer, Action> playerOneControls = new HashMap<Integer, Action>();
		playerOneControls.put(Keys.UP, new Rotate(rotationSystem, playfield));
		playerOneControls.put(Keys.LEFT, new Shift(moveSystem, playfield, -1));
		playerOneControls.put(Keys.RIGHT, new Shift(moveSystem, playfield, 1));
		playerOneControls.put(Keys.DOWN, new ForceFall(moveSystem, playfield));
		playerOne = new Player(playerOneControls, TileType.TILE_RED, 1);

		HashMap<Integer, Action> playerTwoControls = new HashMap<Integer, Action>();
		playerTwoControls.put(Keys.W, new Rotate(rotationSystem, playfield));
		playerTwoControls.put(Keys.A, new Shift(moveSystem, playfield, -1));
		playerTwoControls.put(Keys.D, new Shift(moveSystem, playfield, 1));
		playerTwoControls.put(Keys.S, new ForceFall(moveSystem, playfield));
		playerTwo = new Player(playerTwoControls, TileType.TILE_BLUE, 5);

//		HashMap<Integer, Action> playerThreeControls = new HashMap<Integer, Action>();
//		playerThreeControls.put(Keys.T, new Rotate(rotationSystem, playfield));
//		playerThreeControls.put(Keys.F, new Shift(moveSystem, playfield, -1));
//		playerThreeControls.put(Keys.H, new Shift(moveSystem, playfield, 1));
//		playerThreeControls.put(Keys.G, new ForceFall(moveSystem, playfield));
//		playerThree = new Player(playerThreeControls, TileType.TILE_GREEN, 10);
//
//		HashMap<Integer, Action> playerFourControls = new HashMap<Integer, Action>();
//		playerFourControls.put(Keys.I, new Rotate(rotationSystem, playfield));
//		playerFourControls.put(Keys.J, new Shift(moveSystem, playfield, -1));
//		playerFourControls.put(Keys.L, new Shift(moveSystem, playfield, 1));
//		playerFourControls.put(Keys.K, new ForceFall(moveSystem, playfield));
//		playerFour = new Player(playerFourControls, TileType.TILE_YELLOW, 15);

		playfield.addPlayer(playerOne);
		playfield.addPlayer(playerTwo);
//		playfield.addPlayer(playerThree);
//		playfield.addPlayer(playerFour);
	}

	public void init() {
		// nothing here
	}

	public void render(SpriteBatch batch) {
		playfield.draw(batch);
	}

	public int update() {
		playfield.update();
		playerOne.update();
		playerTwo.update();
//		playerThree.update();
//		playerFour.update();
		return 0;
	}
}
