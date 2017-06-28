package manuk.snake.snakegame;

import android.graphics.Color;

import static manuk.snake.snakegame.SnakeGame.BLOCK_HEIGHT;
import static manuk.snake.snakegame.SnakeGame.BLOCK_WIDTH;

class Snake {
	static final int LEFT = 0, RIGHT = 1, UP = 2, DOWN = 3;
	static final int[] OPPOSITE = new int[] {RIGHT, LEFT, DOWN, UP};
	boolean gameOver;
	private int direction;
	private int x, y;
	private LList<Pos> body;
	private int size;
	private int foodX, foodY;
	private boolean[][] collision;
	
	Snake() {
		direction = RIGHT;
		size = 10;
		body = new LList<>();
		collision = new boolean[SnakeGame.WIDTH][SnakeGame.HEIGHT];
		for (x = 0; x < size; x++) {
			collision[x][y] = true;
			body.add(new Pos(x, y));
		}
		x--;
		generateFood();
	}
	
	void setDirection(int direction) {
		if (direction != OPPOSITE[this.direction])
			this.direction = direction;
	}
	
	void update() {
		switch (direction) {
			case LEFT:
				if (--x < 0)
					x = SnakeGame.WIDTH - 1;
				break;
			case RIGHT:
				if (++x == SnakeGame.WIDTH)
					x = 0;
				break;
			case UP:
				if (--y < 0)
					y = SnakeGame.HEIGHT - 1;
				break;
			case DOWN:
				if (++y == SnakeGame.HEIGHT)
					y = 0;
				break;
		}
		if (x == foodX && y == foodY) {
			size++;
			generateFood();
		} else {
			Pos tailXY = body.removeTail();
			collision[tailXY.x][tailXY.y] = false;
		}
		if (collision[x][y])
			gameOver = true;
		else {
			collision[x][y] = true;
			body.add(new Pos(x, y));
		}
	}
	
	private void generateFood() {
		do {
			foodX = (int) (Math.random() * SnakeGame.WIDTH);
			foodY = (int) (Math.random() * SnakeGame.HEIGHT);
		} while (collision[foodX][foodY]);
	}
	
	void draw(Painter painter) {
		for (Pos p : body)
			painter.drawRect(p.x * BLOCK_WIDTH, p.y * BLOCK_HEIGHT, BLOCK_WIDTH, BLOCK_HEIGHT, Color.WHITE);
		painter.drawRect(foodX * BLOCK_WIDTH, foodY * BLOCK_HEIGHT, BLOCK_WIDTH, BLOCK_HEIGHT, Color.GREEN);
		if (gameOver) {
			painter.drawText("GAME OVER :(", .5, .5, Color.GREEN);
			painter.drawText("Size: " + size, .5, .6, Color.GREEN);
			painter.drawText("Tap to Restart", .5, .7, Color.GREEN);
		}
	}
	
	private class Pos {
		private int x, y;
		
		private Pos(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}
}