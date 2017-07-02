package manuk.path.game;

import android.graphics.Canvas;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import manuk.path.game.controller.Controller;
import manuk.path.game.painter.MapPainter;
import manuk.path.game.painter.Painter;
import manuk.path.game.util.Frames;
import manuk.path.game.util.Measurements;

import static manuk.path.game.util.Measurements.*;

public class Engine implements Runnable {
	private SurfaceHolder surfaceHolder;
	private Frames frames;
	
	private Painter painter;
	private Controller controller;
	private World world;
	private UserInterface userInterface;
	
	public Engine() {
		world = new World(MAP_WIDTH, MAP_LENGTH, MAP_HEIGHT);
		userInterface = new UserInterface();
	}
	
	public void setupSurface(SurfaceView surfaceView, SurfaceHolder surfaceHolder) {
		Canvas canvas = surfaceHolder.lockCanvas();
		int screenWidth = canvas.getWidth();
		int screenHeight = canvas.getHeight();
		surfaceHolder.unlockCanvasAndPost(canvas);
		
		Measurements.init(screenWidth, screenHeight);
		this.surfaceHolder = surfaceHolder;
		
		painter = new Painter();
		MapPainter.init(painter);
		
		controller = new Controller(surfaceView.getContext());
		surfaceView.setOnTouchListener(controller);
	}
	
	private void update() {
		Measurements.setScale(controller.scale);
		userInterface.handleInput(controller);
		world.update(controller);
	}
	
	private void draw() {
		painter.prep(surfaceHolder);
		world.draw(painter);
		userInterface.draw(painter);
		painter.post();
		frames.draw(painter);
		painter.end();
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
				System.out.println("restart logic?"); // createWorld();
			controller.ageTouch();
			draw();
			sleep(5);
		}
	}
	
	public void stop() {
		frames.running = false;
	}
}

// todo: control 2 finger tap
// todo: fix side darwing of character
// todo: lighting and shadow
