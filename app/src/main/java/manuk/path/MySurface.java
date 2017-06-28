package manuk.path;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import manuk.path.game.Engine;

public class MySurface extends SurfaceView implements SurfaceHolder.Callback {
	Engine engine;
	
	public MySurface(Context context, AttributeSet attrs) {
		super(context, attrs);
		getHolder().addCallback(this);
	}
	
	public void surfaceCreated(SurfaceHolder surfaceHolder) {
		Canvas canvas = surfaceHolder.lockCanvas();
		engine = new Engine(surfaceHolder, canvas.getWidth(), canvas.getHeight());
		surfaceHolder.unlockCanvasAndPost(canvas);
		setOnTouchListener(engine.getTouchListener());
		new Thread(engine).start();
	}
	
	public void surfaceChanged(SurfaceHolder surfaceHolder, int frmt, int w, int h) {
	}
	
	public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
		if (engine != null)
			engine.stop();
	}
}