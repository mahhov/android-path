package manuk.path;

import android.content.Context;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import manuk.path.game.Engine;

public class MySurface extends SurfaceView implements SurfaceHolder.Callback {
	Engine engine;
	
	public MySurface(Context context, AttributeSet attrs) {
		super(context, attrs);
		getHolder().addCallback(this);
		engine = new Engine();
	}
	
	public void surfaceCreated(SurfaceHolder surfaceHolder) {
		engine.setupSurface(this, surfaceHolder);
		new Thread(engine).start();
	}
	
	public void surfaceChanged(SurfaceHolder surfaceHolder, int frmt, int w, int h) {
	}
	
	public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
		if (engine != null)
			engine.stop();
	}
}