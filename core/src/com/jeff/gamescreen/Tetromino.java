package com.jeff.gamescreen;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Tetromino {

	public static final int TURN_RIGHT = 1;
	public static final int TURN_LEFT = -1;
	public static final int MOVE_RIGHT = 1;
	public static final int MOVE_LEFT = -1;

	public static final int POSITION_TOP = 0;
	public static final int POSITION_RIGHT = 1;
	public static final int POSITION_BOTTOM = 2;
	public static final int POSITION_LEFT = 3;
	
	public Square[] squares;
	public TileType tileType;
	public char type;
	public int position;
	public int xSpawnOffset;
	public int ySpawnOffset;
	
	public BoundingSquare boundingSquare;

	public Playfield playfield;

	public Tetromino(Playfield playfield, TileType tileType, int xSpawnOffset, int ySpawnOffset, char type) {
		this.playfield = playfield;
		this.type = type;
		this.tileType = tileType;
		this.xSpawnOffset = xSpawnOffset;
		this.ySpawnOffset = ySpawnOffset;
		squares = new Square[4];
		switch (this.type) {
		case 'O':
			squares[0] = new Square(2 + xSpawnOffset, 0 + ySpawnOffset, tileType, false);
			squares[1] = new Square(2 + xSpawnOffset, 1 + ySpawnOffset, tileType, false);
			squares[2] = new Square(3 + xSpawnOffset, 0 + ySpawnOffset, tileType, false);
			squares[3] = new Square(3 + xSpawnOffset, 1 + ySpawnOffset, tileType, false);
			break;
		case 'T':
			squares[0] = new Square(1 + xSpawnOffset, 1 + ySpawnOffset, tileType, false);
			squares[1] = new Square(2 + xSpawnOffset, 1 + ySpawnOffset, tileType, false);
			squares[2] = new Square(3 + xSpawnOffset, 1 + ySpawnOffset, tileType, false);
			squares[3] = new Square(2 + xSpawnOffset, 0 + ySpawnOffset, tileType, false);
			break;
		case 'I':
			squares[0] = new Square(1 + xSpawnOffset, 0 + ySpawnOffset, tileType, false);
			squares[1] = new Square(2 + xSpawnOffset, 0 + ySpawnOffset, tileType, false);
			squares[2] = new Square(3 + xSpawnOffset, 0 + ySpawnOffset, tileType, false);
			squares[3] = new Square(4 + xSpawnOffset, 0 + ySpawnOffset, tileType, false);
			break;
		case 'L':
			squares[0] = new Square(1 + xSpawnOffset, 1 + ySpawnOffset, tileType, false);
			squares[1] = new Square(2 + xSpawnOffset, 1 + ySpawnOffset, tileType, false);
			squares[2] = new Square(3 + xSpawnOffset, 1 + ySpawnOffset, tileType, false);
			squares[3] = new Square(3 + xSpawnOffset, 0 + ySpawnOffset, tileType, false);
			break;
		case 'J':
			squares[0] = new Square(1 + xSpawnOffset, 1 + ySpawnOffset, tileType, false);
			squares[1] = new Square(2 + xSpawnOffset, 1 + ySpawnOffset, tileType, false);
			squares[2] = new Square(3 + xSpawnOffset, 1 + ySpawnOffset, tileType, false);
			squares[3] = new Square(1 + xSpawnOffset, 0 + ySpawnOffset, tileType, false);
			break;
		case 'Z':
			squares[0] = new Square(1 + xSpawnOffset, 0 + ySpawnOffset, tileType, false);
			squares[1] = new Square(2 + xSpawnOffset, 0 + ySpawnOffset, tileType, false);
			squares[2] = new Square(2 + xSpawnOffset, 1 + ySpawnOffset, tileType, false);
			squares[3] = new Square(3 + xSpawnOffset, 1 + ySpawnOffset, tileType, false);
			break;
		case 'S':
			squares[0] = new Square(1 + xSpawnOffset, 1 + ySpawnOffset, tileType, false);
			squares[1] = new Square(2 + xSpawnOffset, 1 + ySpawnOffset, tileType, false);
			squares[2] = new Square(2 + xSpawnOffset, 0 + ySpawnOffset, tileType, false);
			squares[3] = new Square(3 + xSpawnOffset, 0 + ySpawnOffset, tileType, false);
			break;
		}

		boundingSquare = new BoundingSquare(this);
	}
	
	public Tetromino(Tetromino tetromino){
		this(tetromino.playfield, tetromino.tileType, 0, 1, tetromino.type);
	}
	
	public void setXSpawnOffset(int xSpawnOffset){
		this.xSpawnOffset = xSpawnOffset;
	}
	
	public void fall() {
		for (int i = 0; i < squares.length; i++) {
			squares[i].fall(1);
		}
	}
	
	public boolean safeFall(){
		fall();
		if(this.isColliding(playfield) || this.isOverlaps(playfield)){
			up();
			return false;
		}
		return true;
	}
	
	public void move(int move) {
		for (int i = 0; i < squares.length; i++) {
			squares[i].move(move);
		}
	}

	public void draw(SpriteBatch batch, Queuefield queuefield) {
		for (Square square : squares) {
			square.draw(batch, queuefield);
		}
	}
	
	public void draw(SpriteBatch batch, Playfield playfield) {
		for (Square square : squares) {
			square.draw(batch, playfield);
		}
	}

	public void rotate(int rotation) {
		switch (type) {
		case 'O':
			// O
			break;
		case 'T':
			// T
			if (position == 0) {

				if (rotation == TURN_RIGHT) {

					squares[0].move(1);
					squares[0].fall(-1);
					squares[2].move(-1);
					squares[2].fall(1);
					squares[3].move(1);
					squares[3].fall(1);

					position = 1;
				} else if (rotation == TURN_LEFT) {

					squares[0].move(1);
					squares[0].fall(1);
					squares[2].move(-1);
					squares[2].fall(-1);
					squares[3].move(-1);
					squares[3].fall(1);

					position = 3;
				}
			} else if (position == 1) {

				if (rotation == TURN_RIGHT) {

					squares[0].move(1);
					squares[0].fall(1);
					squares[2].move(-1);
					squares[2].fall(-1);
					squares[3].move(-1);
					squares[3].fall(1);

					position = 2;
				} else if (rotation == TURN_LEFT) {

					squares[0].move(-1);
					squares[0].fall(1);
					squares[2].move(1);
					squares[2].fall(-1);
					squares[3].move(-1);
					squares[3].fall(-1);

					position = 0;
				}
			} else if (position == 2) {

				if (rotation == TURN_RIGHT) {

					squares[0].move(-1);
					squares[0].fall(1);
					squares[2].move(1);
					squares[2].fall(-1);
					squares[3].move(-1);
					squares[3].fall(-1);

					position = 3;
				} else if (rotation == TURN_LEFT) {

					squares[0].move(-1);
					squares[0].fall(-1);
					squares[2].move(1);
					squares[2].fall(1);
					squares[3].move(1);
					squares[3].fall(-1);

					position = 1;
				}
			} else if (position == 3) {

				if (rotation == TURN_RIGHT) {

					squares[0].move(-1);
					squares[0].fall(-1);
					squares[2].move(1);
					squares[2].fall(1);
					squares[3].move(1);
					squares[3].fall(-1);

					position = 0;
				} else if (rotation == TURN_LEFT) {

					squares[0].move(1);
					squares[0].fall(-1);
					squares[2].move(-1);
					squares[2].fall(1);
					squares[3].move(1);
					squares[3].fall(1);

					position = 2;
				}
			}

			break;

		case 'I':

			// I
			if (position == 0) {

				if (rotation == TURN_RIGHT) {

					squares[0].move(2);
					squares[0].fall(-1);
					squares[1].move(1);
					squares[1].fall(0);
					squares[2].move(0);
					squares[2].fall(1);
					squares[3].move(-1);
					squares[3].fall(2);

					position = 1;
				} else if (rotation == TURN_LEFT) {

					squares[0].move(1);
					squares[0].fall(2);
					squares[1].move(0);
					squares[1].fall(1);
					squares[2].move(-1);
					squares[2].fall(0);
					squares[3].move(-2);
					squares[3].fall(-1);

					position = 3;
				}
			} else if (position == 1) {

				if (rotation == TURN_RIGHT) {

					squares[0].move(1);
					squares[0].fall(2);
					squares[1].move(0);
					squares[1].fall(1);
					squares[2].move(-1);
					squares[2].fall(0);
					squares[3].move(-2);
					squares[3].fall(-1);

					position = 2;
				} else if (rotation == TURN_LEFT) {

					squares[0].move(-2);
					squares[0].fall(1);
					squares[1].move(-1);
					squares[1].fall(0);
					squares[2].move(0);
					squares[2].fall(-1);
					squares[3].move(1);
					squares[3].fall(-2);

					position = 0;
				}
			} else if (position == 2) {

				if (rotation == TURN_RIGHT) {

					squares[0].move(-2);
					squares[0].fall(1);
					squares[1].move(-1);
					squares[1].fall(0);
					squares[2].move(0);
					squares[2].fall(-1);
					squares[3].move(1);
					squares[3].fall(-2);

					position = 3;
				} else if (rotation == TURN_LEFT) {

					squares[0].move(-1);
					squares[0].fall(-2);
					squares[1].move(0);
					squares[1].fall(-1);
					squares[2].move(1);
					squares[2].fall(0);
					squares[3].move(2);
					squares[3].fall(1);

					position = 1;
				}
			} else if (position == 3) {

				if (rotation == TURN_RIGHT) {

					squares[0].move(-1);
					squares[0].fall(-2);
					squares[1].move(0);
					squares[1].fall(-1);
					squares[2].move(1);
					squares[2].fall(0);
					squares[3].move(2);
					squares[3].fall(1);

					position = 0;
				} else if (rotation == TURN_LEFT) {

					squares[0].move(2);
					squares[0].fall(-1);
					squares[1].move(1);
					squares[1].fall(0);
					squares[2].move(0);
					squares[2].fall(1);
					squares[3].move(-1);
					squares[3].fall(2);

					position = 2;
				}
			}

			break;

		case 'L':

			// L
			if (position == 0) {

				if (rotation == TURN_RIGHT) {

					squares[0].move(1);
					squares[0].fall(-1);
					squares[2].move(-1);
					squares[2].fall(1);
					squares[3].move(0);
					squares[3].fall(2);

					position = 1;
				} else if (rotation == TURN_LEFT) {

					squares[0].move(1);
					squares[0].fall(1);
					squares[2].move(-1);
					squares[2].fall(-1);
					squares[3].move(-2);
					squares[3].fall(0);

					position = 3;
				}
			} else if (position == 1) {

				if (rotation == TURN_RIGHT) {

					squares[0].move(1);
					squares[0].fall(1);
					squares[2].move(-1);
					squares[2].fall(-1);
					squares[3].move(-2);
					squares[3].fall(0);

					position = 2;
				} else if (rotation == TURN_LEFT) {

					squares[0].move(-1);
					squares[0].fall(1);
					squares[2].move(1);
					squares[2].fall(-1);
					squares[3].move(0);
					squares[3].fall(-2);

					position = 0;
				}
			} else if (position == 2) {

				if (rotation == TURN_RIGHT) {

					squares[0].move(-1);
					squares[0].fall(1);
					squares[2].move(1);
					squares[2].fall(-1);
					squares[3].move(0);
					squares[3].fall(-2);

					position = 3;
				} else if (rotation == TURN_LEFT) {

					squares[0].move(-1);
					squares[0].fall(-1);
					squares[2].move(1);
					squares[2].fall(1);
					squares[3].move(2);
					squares[3].fall(0);

					position = 1;
				}
			} else if (position == 3) {

				if (rotation == TURN_RIGHT) {

					squares[0].move(-1);
					squares[0].fall(-1);
					squares[2].move(1);
					squares[2].fall(1);
					squares[3].move(2);
					squares[3].fall(0);

					position = 0;
				} else if (rotation == TURN_LEFT) {

					squares[0].move(1);
					squares[0].fall(-1);
					squares[2].move(-1);
					squares[2].fall(1);
					squares[3].move(0);
					squares[3].fall(2);

					position = 2;
				}
			}

			break;

		case 'J':

			// J
			if (position == 0) {

				if (rotation == TURN_RIGHT) {

					squares[0].move(1);
					squares[0].fall(-1);
					squares[2].move(-1);
					squares[2].fall(1);
					squares[3].move(2);
					squares[3].fall(0);

					position = 1;
				} else if (rotation == TURN_LEFT) {

					squares[0].move(1);
					squares[0].fall(1);
					squares[2].move(-1);
					squares[2].fall(-1);
					squares[3].move(0);
					squares[3].fall(2);

					position = 3;
				}
			} else if (position == 1) {

				if (rotation == TURN_RIGHT) {

					squares[0].move(1);
					squares[0].fall(1);
					squares[2].move(-1);
					squares[2].fall(-1);
					squares[3].move(0);
					squares[3].fall(2);

					position = 2;
				} else if (rotation == TURN_LEFT) {

					squares[0].move(-1);
					squares[0].fall(1);
					squares[2].move(1);
					squares[2].fall(-1);
					squares[3].move(-2);
					squares[3].fall(0);

					position = 0;
				}
			} else if (position == 2) {

				if (rotation == TURN_RIGHT) {

					squares[0].move(-1);
					squares[0].fall(1);
					squares[2].move(1);
					squares[2].fall(-1);
					squares[3].move(-2);
					squares[3].fall(0);

					position = 3;
				} else if (rotation == TURN_LEFT) {

					squares[0].move(-1);
					squares[0].fall(-1);
					squares[2].move(1);
					squares[2].fall(1);
					squares[3].move(0);
					squares[3].fall(-2);

					position = 1;
				}
			} else if (position == 3) {

				if (rotation == TURN_RIGHT) {

					squares[0].move(-1);
					squares[0].fall(-1);
					squares[2].move(1);
					squares[2].fall(1);
					squares[3].move(0);
					squares[3].fall(-2);

					position = 0;
				} else if (rotation == TURN_LEFT) {

					squares[0].move(1);
					squares[0].fall(-1);
					squares[2].move(-1);
					squares[2].fall(1);
					squares[3].move(2);
					squares[3].fall(0);

					position = 2;
				}
			}

			break;

		case 'Z':

			// Z
			if (position == 0) {

				if (rotation == TURN_RIGHT) {

					squares[0].move(2);
					squares[0].fall(0);
					squares[1].move(1);
					squares[1].fall(1);
					squares[3].move(-1);
					squares[3].fall(1);

					position = 1;
				} else if (rotation == TURN_LEFT) {

					squares[0].move(0);
					squares[0].fall(2);
					squares[1].move(-1);
					squares[1].fall(1);
					squares[3].move(-1);
					squares[3].fall(-1);

					position = 3;
				}
			} else if (position == 1) {

				if (rotation == TURN_RIGHT) {

					squares[0].move(0);
					squares[0].fall(2);
					squares[1].move(-1);
					squares[1].fall(1);
					squares[3].move(-1);
					squares[3].fall(-1);

					position = 2;
				} else if (rotation == TURN_LEFT) {

					squares[0].move(-2);
					squares[0].fall(0);
					squares[1].move(-1);
					squares[1].fall(-1);
					squares[3].move(1);
					squares[3].fall(-1);

					position = 0;
				}
			} else if (position == 2) {

				if (rotation == TURN_RIGHT) {

					squares[0].move(-2);
					squares[0].fall(0);
					squares[1].move(-1);
					squares[1].fall(-1);
					squares[3].move(1);
					squares[3].fall(-1);

					position = 3;
				} else if (rotation == TURN_LEFT) {

					squares[0].move(0);
					squares[0].fall(-2);
					squares[1].move(1);
					squares[1].fall(-1);
					squares[3].move(1);
					squares[3].fall(1);

					position = 1;
				}
			} else if (position == 3) {

				if (rotation == TURN_RIGHT) {

					squares[0].move(0);
					squares[0].fall(-2);
					squares[1].move(1);
					squares[1].fall(-1);
					squares[3].move(1);
					squares[3].fall(1);

					position = 0;
				} else if (rotation == TURN_LEFT) {

					squares[0].move(2);
					squares[0].fall(0);
					squares[1].move(1);
					squares[1].fall(1);
					squares[3].move(-1);
					squares[3].fall(1);

					position = 2;
				}
			}

			break;

		case 'S':

			// S
			if (position == 0) {

				if (rotation == TURN_RIGHT) {

					squares[0].move(1);
					squares[0].fall(-1);
					squares[2].move(1);
					squares[2].fall(1);
					squares[3].move(0);
					squares[3].fall(2);

					position = 1;
				} else if (rotation == TURN_LEFT) {

					squares[0].move(1);
					squares[0].fall(1);
					squares[2].move(-1);
					squares[2].fall(1);
					squares[3].move(-2);
					squares[3].fall(0);

					position = 3;
				}
			} else if (position == 1) {

				if (rotation == TURN_RIGHT) {

					squares[0].move(1);
					squares[0].fall(1);
					squares[2].move(-1);
					squares[2].fall(1);
					squares[3].move(-2);
					squares[3].fall(0);

					position = 2;
				} else if (rotation == TURN_LEFT) {

					squares[0].move(-1);
					squares[0].fall(1);
					squares[2].move(-1);
					squares[2].fall(-1);
					squares[3].move(0);
					squares[3].fall(-2);

					position = 0;
				}
			} else if (position == 2) {

				if (rotation == TURN_RIGHT) {

					squares[0].move(-1);
					squares[0].fall(1);
					squares[2].move(-1);
					squares[2].fall(-1);
					squares[3].move(0);
					squares[3].fall(-2);

					position = 3;
				} else if (rotation == TURN_LEFT) {

					squares[0].move(-1);
					squares[0].fall(-1);
					squares[2].move(1);
					squares[2].fall(-1);
					squares[3].move(2);
					squares[3].fall(0);

					position = 1;
				}
			} else if (position == 3) {

				if (rotation == TURN_RIGHT) {

					squares[0].move(-1);
					squares[0].fall(-1);
					squares[2].move(1);
					squares[2].fall(-1);
					squares[3].move(2);
					squares[3].fall(0);

					position = 0;
				} else if (rotation == TURN_LEFT) {

					squares[0].move(1);
					squares[0].fall(-1);
					squares[2].move(1);
					squares[2].fall(1);
					squares[3].move(0);
					squares[3].fall(2);

					position = 2;
				}
			}

			break;
		}
	}

	public boolean isOverlaps(Playfield playfield) {
		for (Square square : squares) {
			if (!playfield.getSquare(square.getX(), square.getY()).isEmpty()) {
				return true;
			}
		}
		return false;
	}

	public boolean isColliding(Playfield playfield) {
		for (Square square : squares) {
			for (Tetromino tetromino : playfield.getTetrominoMap().values()) {
				if (tetromino != this) {
					for (Square compareSquare : tetromino.squares) {
						if (square.getX() == compareSquare.getX() && square.getY() == compareSquare.getY()) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}

	public int getMinY() {
		int min = Integer.MAX_VALUE;
		for (Square square : squares) {
			if (square.getY() < min) {
				min = square.getY();
			}
		}
		return min;
	}

	public void up() {
		for (int i = 0; i < squares.length; i++) {
			squares[i].fall(-1);
		}
	}

	@Override
	public String toString() {
		return type + "";
	}
}
