package manuk.snake;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import manuk.snake.snakegame.Controller;
import manuk.snake.snakegame.SnakeGame;

public class MySurface extends SurfaceView implements SurfaceHolder.Callback {
	SnakeGame snakeGame;
	
	public MySurface(Context context, AttributeSet attrs) {
		super(context, attrs);
		getHolder().addCallback(this);
	}
	
	public void surfaceCreated(SurfaceHolder surfaceHolder) {
		Canvas canvas = surfaceHolder.lockCanvas();
		int width = canvas.getWidth();
		int height = canvas.getHeight();
		Controller controller = new Controller(width, height);
		setOnTouchListener(controller);
		snakeGame = new SnakeGame(surfaceHolder, controller, width, height);
		new Thread(snakeGame).start();
		surfaceHolder.unlockCanvasAndPost(canvas);
	}
	
	public void surfaceChanged(SurfaceHolder surfaceHolder, int frmt, int w, int h) {
	}
	
	public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
		if (snakeGame != null)
			snakeGame.stop();
	}
}