package manuk.path.game;

import android.view.SurfaceHolder;
import android.view.View;

public class Engine implements Runnable {
	static final int MAP_WIDTH = 30, MAP_LENGTH = 30, MAP_HEIGHT = 3;
	static final int VIEW_RATIO = 1;
	static final int VIEW_WIDTH = 20, VIEW_HEIGHT = VIEW_WIDTH * VIEW_RATIO;
	static final double BLOCK_WIDTH = 1. / VIEW_WIDTH, BLOCK_HEIGHT = 1. / VIEW_HEIGHT;
	private boolean running = true;
	private SurfaceHolder surfaceHolder;
	private Painter painter;
	private Controller controller;
	private World world;
	
	public Engine(SurfaceHolder surfaceHolder, int screenWidth, int screenHeight) {
		this.surfaceHolder = surfaceHolder;
		painter = new Painter(screenWidth, screenWidth * VIEW_RATIO, screenWidth, screenHeight);
		MapPainter.init(painter);
		controller = new Controller(screenWidth, screenWidth * VIEW_RATIO, screenWidth, screenHeight);
		createWorld();
	}
	
	private void createWorld() {
		world = new World(MAP_WIDTH, MAP_LENGTH, MAP_HEIGHT);
	}
	
	public View.OnTouchListener getTouchListener() {
		return controller;
	}
	
	private void update() {
		world.update(controller);
	}
	
	private void draw() {
		painter.prep(surfaceHolder);
		world.draw(painter);
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
			if (!world.gameOver)
				update();
			else if (controller.isPress())
				createWorld();
			controller.ageTouch();
			draw();
			sleep(5);
		}
	}
	
	public void stop() {
		running = false;
	}
}

// todo: character movement collision slide
// todo: move engine map constants to MapPainter
// todo: control
// todo: random map generation
// todo: fix side darwing of character
// todo: shades to map drawing