package com.jeff.views;

import java.util.ArrayList;
import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jeff.controller.Action;
import com.jeff.controller.ForceFall;
import com.jeff.controller.Rotate;
import com.jeff.controller.Shift;
import com.jeff.gamescreen.Background;
import com.jeff.gamescreen.BackgroundLayer;
import com.jeff.gamescreen.Playfield;
import com.jeff.gamescreen.Queuefield;
import com.jeff.gamescreen.TileType;
import com.jeff.player.Player;
import com.jeff.systems.MoveSystem;
import com.jeff.systems.RotationSystem;

public class GameScreen {
	private MoveSystem moveSystem;
	private RotationSystem rotationSystem;
	private Playfield playfield;
	private Background background;
	
	private Player playerOne;
	private Player playerTwo;
	private Player playerThree;
	private Player playerFour;

	public GameScreen(int width, int height) {
		BackgroundLayer sky = new BackgroundLayer(new Texture(Gdx.files.internal("backgrounds/sky.png")), 0.25f);
		BackgroundLayer bg = new BackgroundLayer(new Texture(Gdx.files.internal("backgrounds/background.png")), 0.5f);
		BackgroundLayer mg = new BackgroundLayer(new Texture(Gdx.files.internal("backgrounds/midground.png")), 1.3f);
		BackgroundLayer fg = new BackgroundLayer(new Texture(Gdx.files.internal("backgrounds/foreground.png")), 2.0f);
		ArrayList<BackgroundLayer> bgLayers = new ArrayList<BackgroundLayer>();
		bgLayers.add(sky);
		bgLayers.add(bg);
		bgLayers.add(mg);
		bgLayers.add(fg);
		background = new Background(bgLayers);
		
		moveSystem = new MoveSystem();
		rotationSystem = new RotationSystem();	
		playfield = new Playfield(16, 24, 4.0f);
		
		HashMap<Integer, Action> playerOneControls = new HashMap<Integer, Action>();
		playerOneControls.put(Keys.UP, new Rotate(rotationSystem, playfield));
		playerOneControls.put(Keys.LEFT, new Shift(moveSystem, playfield, -1));
		playerOneControls.put(Keys.RIGHT, new Shift(moveSystem, playfield, 1));
		playerOneControls.put(Keys.DOWN, new ForceFall(moveSystem, playfield));
		playerOne = new Player(playerOneControls, TileType.TILE_RED, 2);

		HashMap<Integer, Action> playerTwoControls = new HashMap<Integer, Action>();
		playerTwoControls.put(Keys.W, new Rotate(rotationSystem, playfield));
		playerTwoControls.put(Keys.A, new Shift(moveSystem, playfield, -1));
		playerTwoControls.put(Keys.D, new Shift(moveSystem, playfield, 1));
		playerTwoControls.put(Keys.S, new ForceFall(moveSystem, playfield));
		playerTwo = new Player(playerTwoControls, TileType.TILE_BLUE, 17);

		HashMap<Integer, Action> playerThreeControls = new HashMap<Integer, Action>();
		playerThreeControls.put(Keys.T, new Rotate(rotationSystem, playfield));
		playerThreeControls.put(Keys.F, new Shift(moveSystem, playfield, -1));
		playerThreeControls.put(Keys.H, new Shift(moveSystem, playfield, 1));
		playerThreeControls.put(Keys.G, new ForceFall(moveSystem, playfield));
		playerThree = new Player(playerThreeControls, TileType.TILE_GREEN, 0);

		HashMap<Integer, Action> playerFourControls = new HashMap<Integer, Action>();
		playerFourControls.put(Keys.I, new Rotate(rotationSystem, playfield));
		playerFourControls.put(Keys.J, new Shift(moveSystem, playfield, -1));
		playerFourControls.put(Keys.L, new Shift(moveSystem, playfield, 1));
		playerFourControls.put(Keys.K, new ForceFall(moveSystem, playfield));
		playerFour = new Player(playerFourControls, TileType.TILE_YELLOW, 0);

		playfield.addPlayer(playerOne);
		playfield.addPlayer(playerTwo);
//		playfield.addPlayer(playerThree);
//		playfield.addPlayer(playerFour);
		playerOne.linkQueuefield(new Queuefield(128, 128 + 32 + 8, playerOne.tileType, 4.0f));
		playerTwo.linkQueuefield(new Queuefield(1152,  128 + 32 + 8, playerTwo.tileType, 4.0f));
//		playerThree.linkQueuefield(new Queuefield(1920, 1080, playerThree.tileType));
//		playerFour.linkQueuefield(new Queuefield(0, 1080, playerFour.tileType));
	}

	public void init() {
		// nothing here
	}

	public void render(SpriteBatch batch) {
		background.draw(batch);
		playfield.draw(batch);
	}

	public int update(float delta) {
		background.update(delta);
		playfield.update(delta);
		playerOne.update(delta);
		playerTwo.update(delta);
//		playerThree.update(delta);
//		playerFour.update(delta);
		return 0;
	}
}
