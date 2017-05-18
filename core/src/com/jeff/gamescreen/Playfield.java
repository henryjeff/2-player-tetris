package com.jeff.gamescreen;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jeff.player.Player;

public class Playfield {
	private int height, width;
	private Square[][] squares;

	private ConcurrentHashMap<Player, Tetromino> tetrominoMap;
	private List<Player> players;
	private RandomGenerator randomGenerator;

	private int xOffset;
	private int yOffset;

	private int offsetTimer;
	private int delta;

	public Playfield(int height, int width) {
		this.height = height;
		this.width = width;
		this.squares = new Square[width][height];
		randomGenerator = new RandomGenerator();
		offsetTimer = 0;
		delta = 0;
		xOffset = Gdx.graphics.getWidth() / 2;
		yOffset = Gdx.graphics.getHeight() / 2;
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

	public int getXOffset() {
		return xOffset;
	}

	public int getYOffset() {
		return yOffset;
	}

	public void setXOffset(int xOffset) {
		this.xOffset = xOffset;
	}

	public void setYOffset(int yOffset) {
		this.yOffset = yOffset;
	}

	public void addPlayer(Player player) {
		players.add(player);
	}

	public void update() {
		for (Player player : players) {
			if (player.nextTetromino == null) {
				player.nextTetromino = randomGenerator.nextPiece(this, player);
			}
			if (player.tetromino == null) {
				player.tetromino = player.nextTetromino;
				player.nextTetromino = randomGenerator.nextPiece(this, player);
				removeTetromino(player);
				addTetromino(player, player.tetromino);
			}
		}
		for (Entry<Player, Tetromino> entry : tetrominoMap.entrySet()) {
			Player player = entry.getKey();
			Tetromino tetromino = entry.getValue();
			if (tetromino.safeFall()) {
				if (player.fallTimer >= player.fallSpeed) {
					player.fallTimer = 0;
				} else {
					tetromino.up();
					player.fallTimer++;
				}
				player.placeTimer = 0;
			} else {
				if (!tetromino.isColliding(this)) {
					player.placeTimer++;
				}
			}
			if (player.placeTimer >= player.placeSpeed) {
				setTetromino(player.tetromino);
				long clearedLines = checkLines(player.tetromino);
				if(clearedLines >= 1){
					player.addPoints(clearedLines);
				}
				removeTetromino(player);
				player.tetromino = null;
				player.placeTimer = 0;
			}
		}
		// if(offsetTimer > 4){
		// int offset = (int) (Math.sin(delta) * 5);
		// this.setYOffset(yOffset += offset);
		// delta++;
		// offsetTimer = 0;
		// }
		// offsetTimer++;
	}

	public void draw(SpriteBatch batch) {
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				squares[x][y].draw(batch, this);
			}
		}
		for (Tetromino tetromino : tetrominoMap.values()) {
			tetromino.draw(batch, this);
		}
	}

	public void setTetromino(Tetromino tetromino) {
		for (Square square : tetromino.squares) {
			setSquare(square);
		}
	}

	public long checkLines(Tetromino tetromino) {
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
			ArrayList<Square> line = getLines(lines.get(y) + yyy);
			for (Square square : line) {
				if (square.isEmpty()) {
					isComplete = false;
				}
			}
			if (isComplete) {
				lineClear++;
				removeLine(line);
				fallLine(lines.get(y) + yyy);
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
	}

	public ConcurrentHashMap<Player, Tetromino> getTetrominoMap() {
		return tetrominoMap;
	}
}
