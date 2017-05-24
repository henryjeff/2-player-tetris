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
		playfield = new Playfield(16, 22, 4.0f);
		
		HashMap<Integer, Action> playerOneControls = new HashMap<Integer, Action>();
		playerOneControls.put(Keys.W, new Rotate(rotationSystem, playfield));
		playerOneControls.put(Keys.A, new Shift(moveSystem, playfield, -1));
		playerOneControls.put(Keys.D, new Shift(moveSystem, playfield, 1));
		playerOneControls.put(Keys.S, new ForceFall(moveSystem, playfield));
		playerOne = new Player(playerOneControls, TileType.TILE_RED, 2);

		HashMap<Integer, Action> playerTwoControls = new HashMap<Integer, Action>();
		playerTwoControls.put(Keys.UP, new Rotate(rotationSystem, playfield));
		playerTwoControls.put(Keys.LEFT, new Shift(moveSystem, playfield, -1));
		playerTwoControls.put(Keys.RIGHT, new Shift(moveSystem, playfield, 1));
		playerTwoControls.put(Keys.DOWN, new ForceFall(moveSystem, playfield));
		playerTwo = new Player(playerTwoControls, TileType.TILE_BLUE, 14);

		playfield.addPlayer(playerOne);
		playfield.addPlayer(playerTwo);
		playerOne.linkQueuefield(new Queuefield(128 + 12, 196, playerOne.tileType, 4.0f));
		playerTwo.linkQueuefield(new Queuefield(1152 - 12,  196, playerTwo.tileType, 4.0f));
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
		return 0;
	}
}
