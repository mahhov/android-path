package manuk.path.game;

import android.graphics.Canvas;
import android.graphics.Color;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import manuk.path.game.controller.Controller;
import manuk.path.game.util.Frames;

public class Engine implements Runnable {
	static final int MAP_WIDTH = 100, MAP_LENGTH = MAP_WIDTH, MAP_HEIGHT = 3;
	static final int VIEW_RATIO = 1;
	static int VIEW_WIDTH = 30, VIEW_HEIGHT = VIEW_WIDTH * VIEW_RATIO;
	static double BLOCK_WIDTH = 1. / VIEW_WIDTH, BLOCK_HEIGHT = 1. / VIEW_HEIGHT;
	
	private SurfaceHolder surfaceHolder;
	private Frames frames;
	
	private Painter painter;
	private Controller controller;
	private World world;
	
	public Engine() {
		// createWorld();
	}
	
	public void setupSurface(SurfaceView surfaceView, SurfaceHolder surfaceHolder) {
		createWorld();
		
		Canvas canvas = surfaceHolder.lockCanvas();
		int screenWidth = canvas.getWidth();
		int screenHeight = canvas.getHeight();
		surfaceHolder.unlockCanvasAndPost(canvas);
		
		this.surfaceHolder = surfaceHolder;
		
		painter = new Painter(screenWidth, screenWidth * VIEW_RATIO, screenWidth, screenHeight);
		MapPainter.init(painter);
		
		controller = new Controller(screenWidth, screenWidth * VIEW_RATIO, screenWidth, screenHeight, surfaceView.getContext());
		surfaceView.setOnTouchListener(controller);
	}
	
	private void createWorld() {
		world = new World(MAP_WIDTH, MAP_LENGTH, MAP_HEIGHT);
	}
	
	private void update() {
		setScale(controller.scale);
		world.update(controller);
	}
	
	private void setScale(double scale) {
		double temp = 30 * scale;
		VIEW_WIDTH = VIEW_HEIGHT = (int) (temp);
		BLOCK_WIDTH = BLOCK_HEIGHT = 1. / (temp);
	}
	
	private void draw() {
		painter.prep(surfaceHolder);
		world.draw(painter);
		drawFps();
		painter.post();
	}
	
	private void drawFps() {
		double[] xy = painter.convertFromAbsolute(70, 50);
		painter.drawText("fps: " + frames.getFPS(), xy[0], xy[1], Color.GREEN);
	}
	
	private void sleep(long duration) {
		try {
			Thread.sleep(duration);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void run() {
		frames = new Frames();
		while (frames.running) {
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
		frames.running = false;
	}
}

// todo: fix moving to edge of map bug
// todo: move engine map constants to MapPainter
// todo: smooth edge scrolling

// todo: control 2 finger tap
// todo: better random map generation - bug with isolated mini areas
// todo: fix side darwing of character
// todo: lighting and shadow
