package com.jeff.gamescreen;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.jeff.game.Game;
import com.jeff.player.Player;
import com.jeff.statemachine.StateName;

public class Playfield {
	private int height;
	private int width;
	private Square[][] squares;
	private static final List<ItemType> itemTypes = Arrays.asList(ItemType.SPEED, ItemType.LOCK, ItemType.DESTROY, ItemType.SKIP);
	private static final HashMap<Integer, Float> levelData = new HashMap<Integer, Float>();
	private Random random;
	private ConcurrentHashMap<Player, Tetromino> tetrominoMap;
	private List<Player> players;
	private List<Item> items;
	private RandomGenerator randomGenerator;
	private Animation<TextureRegion> borderTexture;
	private float parallaxWeight;
	public float globalFallSpeed;
	private int xParallaxOffset;
	private int yParallaxOffset;
	private int xOffset;
	private int yOffset;
	private int x;
	private int y;
	private int scoreThreshold;
	
	public Playfield(int height, int width, float parallaxWeight) {
		this.borderTexture = new Animation<TextureRegion>(0.1f, Game.atlas.findRegions("playfield"),
				PlayMode.LOOP);
		this.height = height;
		this.width = width;
		this.parallaxWeight = parallaxWeight;
		this.squares = new Square[width][height];
		globalFallSpeed = 1.5f;
		scoreThreshold = 100;
		randomGenerator = new RandomGenerator();
		random = new Random();
		x = Gdx.graphics.getWidth() / 2;
		y = Gdx.graphics.getHeight() / 2;
		xOffset = 0;
		yOffset = 0;
		items = Collections.synchronizedList(new ArrayList<Item>());
		players = new ArrayList<Player>();
		tetrominoMap = new ConcurrentHashMap<Player, Tetromino>();
		for (int y = 0; y < this.height; y++) {
			for (int x = 0; x < this.width; x++) {
				if (y == this.height - 1 || x == 0 || x == this.width - 1) {
					squares[x][y] = new Square(x, y, TileType.BG, false);
				} else {
					squares[x][y] = new Square(x, y, TileType.PLAYFIELD_BG, true);
				}
			}
		}
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getXOffset() {
		return xOffset;
	}

	public int getYOffset() {
		return yOffset;
	}

	public int getXParallaxOffset() {
		return xParallaxOffset;
	}

	public int getYParallaxOffset() {
		return yParallaxOffset;
	}

	public float getParallaxWeight() {
		return parallaxWeight;
	}

	public void addPlayer(Player player) {
		players.add(player);
		player.playfield = this;
		player.fallSpeed = globalFallSpeed;
	}

	public void update(float delta) {
		int chance = 750;
		int seed = random.nextInt(chance) + 1;
		if (seed == chance) {
			int itemX = random.nextInt(width - 2) + 1;
			int itemY = random.nextInt(3);
			for (Item item : items) {
				if (item.x == itemX && item.y == itemY) {
					itemX = random.nextInt(width - 2) + 1;
					itemY = random.nextInt(3);
				}
			}
			itemY = 15 - itemY;
			ItemType type = itemTypes.get(random.nextInt(itemTypes.size()));
			Item item = new Item(itemX, itemY, type, this);
			item.stateMachine.changeState(item.stateMachine.getState(StateName.CREATE));
			addItem(item);
		}
		for (Item item : items) {
				item.update(delta);
		}
		for (Iterator<Item> iter = items.iterator(); iter.hasNext();) {
			Item next = iter.next();
			if (next.shouldBeDestroyed()) {
				iter.remove();
			}
		}
		int queuefieldWidth = width * Game.SIZE_CLIP;
		int queuefieldHeight = height * Game.SIZE_CLIP;
		int parallaxOffsetX = (int) ((Game.camera.position.x - (Gdx.graphics.getWidth() / 2)) * parallaxWeight);
		int parallaxOffsetY = (int) ((Game.camera.position.y - (Gdx.graphics.getHeight() / 2)) * parallaxWeight);
		xParallaxOffset = x - queuefieldWidth / 2 + parallaxOffsetX;
		yParallaxOffset = y - queuefieldHeight / 2 + parallaxOffsetY;
		for (Player player : players) {
			if (player.nextTetromino == null) {
				player.nextTetromino = new Tetromino(randomGenerator.nextPiece(this, player));
			}
			if (player.tetromino == null) {
				player.nextTetromino.setXSpawnOffset(player.spawnOffset);
				player.tetromino = new Tetromino(player.nextTetromino.playfield, player.nextTetromino.tileType,
						player.spawnOffset, 0, player.nextTetromino.type);
				player.nextTetromino = new Tetromino(randomGenerator.nextPiece(this, player));
				removeTetromino(player);
				addTetromino(player, player.tetromino);
			}
		}
		for (Entry<Player, Tetromino> entry : tetrominoMap.entrySet()) {
			Player player = entry.getKey();
			Tetromino tetromino = entry.getValue();
			if(player.score > scoreThreshold){
				
			}
			for (Square square : tetromino.squares) {
				square.update(delta);
			}
			if (tetromino.safeFall()) {
				if (player.fallTimer >= player.fallSpeed) {
					player.fallTimer = 0;
				} else {
					tetromino.up();
					player.fallTimer += delta;
				}
				player.placeTimer = 0;
			} else {
				if (!tetromino.isColliding(this)) {		
					for(Player player2 : players){
						if(tetromino.isOverlapTetromino(player2.tetromino) && player2 != player){
							player.placeTimer = 0;
						}
					}
					player.placeTimer += delta;
				}
				
			}
			if (player.placeTimer >= player.placeSpeed) {
				int minY = 16;
				for(Square square : player.tetromino.squares){
					if(square.getY() < minY) minY = square.getY();
				}
				if(minY - 1 <= 0){
					System.out.println("ya lost");
				}else{
					setTetromino(player.tetromino);
					for(Item item : items){
						player.addItem(item);
						item.destroy();
					}
					long clearedLines = checkLines(player, player.tetromino);
					if (clearedLines >= 1) {
						int pnts = 0;
						switch((int)clearedLines){
						case 1: pnts = 125;
						break;
						case 2: pnts = 300;
						break;
						case 3: pnts = 555;
						break;
						case 4: pnts = 1000;
						break;
						}
						player.addPoints(pnts);
					}
				}
				removeTetromino(player);
				player.tetromino = null;
				player.placeTimer = 0;
			}
		}
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				squares[x][y].update(delta);
			}
		}
	}

	public void draw(SpriteBatch batch) {
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				squares[x][y].draw(batch, this);
			}
		}
		for (Item item : items) {
			item.draw(batch, this);
		}
		for (Tetromino tetromino : tetrominoMap.values()) {
			tetromino.draw(batch, this);
		}
		int parallaxOffsetX = (int) ((Game.camera.position.x - (Gdx.graphics.getWidth() / 2)) * parallaxWeight);
		int parallaxOffsetY = (int) ((Game.camera.position.y - (Gdx.graphics.getHeight() / 2)) * parallaxWeight);
		
		TextureRegion borderRegion = borderTexture.getKeyFrame(Game.elapsedTime);
		borderRegion.flip(false, true);
		batch.draw(borderRegion, parallaxOffsetX + (x - (borderRegion.getRegionWidth())),
				parallaxOffsetY + y / 2 - 134 - (yOffset * 2), 0, 0, borderRegion.getRegionWidth(),
				borderRegion.getRegionHeight(), 2, 2, 0);
		borderRegion.flip(false, true);
		for (Player player : players) {
			player.draw(batch);
		}
	}

	public void setTetromino(Tetromino tetromino) {
		for (Square square : tetromino.squares) {
			setSquare(square);
		}
	}

	public long checkLines(Player player, Tetromino tetromino) {
		long lineClear = 0;
		ArrayList<Integer> lines = new ArrayList<Integer>();
		for (Square square : tetromino.squares) {
			if (!lines.contains(square.getY())) {
				lines.add(square.getY());
			}
		}
		for (int y = 0; y < lines.size(); y++) {
			int yyy = 0;
			for (int yy = 0; yy < y; yy++) {
				if (lines.get(y) < lines.get(yy)) {
					yyy++;
				}
			}
			boolean isComplete = true;
			List<Item> lineItems = new ArrayList<Item>();
			ArrayList<Square> line = getLines(lines.get(y) + yyy);
			for (Square square : line) {
				if (square.isEmpty()) {
					isComplete = false;
					break;
				}
				for (Item item : items) {
					if (item.y == square.getY()) {
						lineItems.add(item);
					}
				}
			}
			if (isComplete) {
				lineClear++;
				removeLine(line);
				fallLine(lines.get(y) + yyy);
				for (Item item : lineItems) {
					player.addItem(item);
					item.destroy();
				}
			}
		}
		return lineClear;
	}

	public void fallLine(int start) {
		for (int y = start; y > 2; y--) {
			for (int x = 1; x < width - 1; x++) {
				if (y - 1 == 2) {
					squares[x][y] = new Square(x, y, TileType.PLAYFIELD_BG, true);
				} else {
					Square cube = squares[x][y - 1];
					cube.fall(1);
					squares[x][y] = cube;
					squares[x][y - 1] = new Square(x, y - 1, TileType.PLAYFIELD_BG, true);
				}
			}
		}
	}

	public Square getSquare(int x, int y) {
		try {
			return squares[x][y];
		} catch (ArrayIndexOutOfBoundsException e) {
			return squares[0][0];
		}
	}

	public void setSquare(Square square) {
		square.stateMachine.changeState(square.stateMachine.getState(StateName.SHINE));
		squares[square.getX()][square.getY()] = square;
	}

	public int getHeight() {

		return height;
	}

	public int getWidth() {

		return width;
	}

	public ArrayList<Square> getLines(int y) {
		ArrayList<Square> line = new ArrayList<Square>();
		for (int x = 1; x < width - 1; x++) {

			line.add(squares[x][y]);
		}
		return line;
	}

	public void removeLine(ArrayList<Square> line) {
		for (Square square : line) {
			setSquare(new Square(square.getX(), square.getY(), TileType.PLAYFIELD_BG, true));
		}
	}

	public void removeTetromino(Player player) {
		Iterator<ConcurrentHashMap.Entry<Player, Tetromino>> it;
		it = tetrominoMap.entrySet().iterator();
		while (it.hasNext()) {
			Entry<Player, Tetromino> entry = it.next();
			if (entry.getKey().equals(player)) {
				it.remove();
			}
		}
	}

	public void addTetromino(Player player, Tetromino tetromino) {
		tetrominoMap.put(player, tetromino);
		for (Square square : tetrominoMap.get(player).squares) {
			square.stateMachine.changeState(square.stateMachine.getState(StateName.CREATE));
		}
	}

	public void addItem(Item item) {
		items.add(item);
	}

	public void removeItem(Item item) {
		items.remove(item);
	}

	public ConcurrentHashMap<Player, Tetromino> getTetrominoMap() {
		return tetrominoMap;
	}

	public Player getOtherPlayer(Player player) {
		for(Player tempPlayer: players){
			if(player != tempPlayer){
				return tempPlayer;
			}
		}
		return null;
	}
}
