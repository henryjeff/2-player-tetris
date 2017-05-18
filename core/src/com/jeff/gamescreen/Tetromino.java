package com.jeff.gamescreen;

import java.util.Random;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import collision.BoundingSquare;

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
	public char type;
	public boolean isPowered;
	public int position;

	public BoundingSquare boundingSquare;

	public Playfield playfield;

	public Tetromino(Playfield playfield, TileType tileType, int xSpawnOffset, char type) {
		Random r = new Random();
		this.playfield = playfield;
		if (r.nextInt(50) > 45) {
			this.isPowered = true;
		} else {
			this.isPowered = false;
		}
		this.type = type;

		squares = new Square[4];
		switch (this.type) {
		case 'O':
			// O
			squares[0] = new Square(5 + xSpawnOffset, 0, tileType, false);
			squares[1] = new Square(5 + xSpawnOffset, 1, tileType, false);
			squares[2] = new Square(6 + xSpawnOffset, 0, tileType, false);
			squares[3] = new Square(6 + xSpawnOffset, 1, tileType, false);
			break;
		case 'T':
			// T
			squares[0] = new Square(4 + xSpawnOffset, 1, tileType, false);
			squares[1] = new Square(5 + xSpawnOffset, 1, tileType, false);
			squares[2] = new Square(6 + xSpawnOffset, 1, tileType, false);
			squares[3] = new Square(5 + xSpawnOffset, 0, tileType, false);
			break;
		case 'I':
			// I
			squares[0] = new Square(4 + xSpawnOffset, 0, tileType, false);
			squares[1] = new Square(5 + xSpawnOffset, 0, tileType, false);
			squares[2] = new Square(6 + xSpawnOffset, 0, tileType, false);
			squares[3] = new Square(7 + xSpawnOffset, 0, tileType, false);
			break;
		case 'L':
			// L
			squares[0] = new Square(4 + xSpawnOffset, 1, tileType, false);
			squares[1] = new Square(5 + xSpawnOffset, 1, tileType, false);
			squares[2] = new Square(6 + xSpawnOffset, 1, tileType, false);
			squares[3] = new Square(6 + xSpawnOffset, 0, tileType, false);
			break;
		case 'J':
			// J
			squares[0] = new Square(4 + xSpawnOffset, 1, tileType, false);
			squares[1] = new Square(5 + xSpawnOffset, 1, tileType, false);
			squares[2] = new Square(6 + xSpawnOffset, 1, tileType, false);
			squares[3] = new Square(4 + xSpawnOffset, 0, tileType, false);
			break;
		case 'Z':
			// Z
			squares[0] = new Square(4 + xSpawnOffset, 0, tileType, false);
			squares[1] = new Square(5 + xSpawnOffset, 0, tileType, false);
			squares[2] = new Square(5 + xSpawnOffset, 1, tileType, false);
			squares[3] = new Square(6 + xSpawnOffset, 1, tileType, false);
			break;
		case 'S':
			// S
			squares[0] = new Square(4 + xSpawnOffset, 1, tileType, false);
			squares[1] = new Square(5 + xSpawnOffset, 1, tileType, false);
			squares[2] = new Square(5 + xSpawnOffset, 0, tileType, false);
			squares[3] = new Square(6 + xSpawnOffset, 0, tileType, false);
			break;
		}

		boundingSquare = new BoundingSquare(this);
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
