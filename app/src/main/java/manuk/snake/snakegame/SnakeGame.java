package manuk.snake.snakegame;

import android.view.SurfaceHolder;

public class SnakeGame implements Runnable {
	private boolean running = true;
	private SurfaceHolder surfaceHolder;
	
	static final int WIDTH = 20, HEIGHT = 20;
	static final double BLOCK_WIDTH = 1. / WIDTH, BLOCK_HEIGHT = 1. / HEIGHT;
	private Painter painter;
	private Controller controller;
	private Snake snake;
	
	public SnakeGame(SurfaceHolder surfaceHolder, Controller controller, int width, int height) {
		this.surfaceHolder = surfaceHolder;
		painter = new Painter(width, width, width, height);
		this.controller = controller;
		snake = new Snake();
	}
	
	private void update() {
		if (controller.getTouch()) {
			double threshold = .4;
			double deltaX = controller.touchX > .5 ? 1 - controller.touchX : controller.touchX;
			double deltaY = controller.touchY > .5 ? 1 - controller.touchY : controller.touchY;
			if (deltaX < deltaY) {
				if (controller.touchX < threshold)
					snake.setDirection(Snake.LEFT);
				else if (controller.touchX > 1 - threshold)
					snake.setDirection(Snake.RIGHT);
			} else {
				if (controller.touchY < threshold)
					snake.setDirection(Snake.UP);
				else if (controller.touchY > 1 - threshold)
					snake.setDirection(Snake.DOWN);
			}
		}
		snake.update();
	}
	
	private void draw() {
		painter.prep(surfaceHolder);
		snake.draw(painter);
		painter.post();
	}
	
	private void sleep(long duration) {
		try {
			Thread.sleep(duration);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void run() {
		running = true;
		while (running) {
			if (!snake.gameOver)
				update();
			else if (controller.getTouch())
				snake = new Snake();
			draw();
			sleep(100);
		}
	}
	
	public void stop() {
		running = false;
	}
}
