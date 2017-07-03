package manuk.path;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;
import manuk.path.game.controller.Controller;
import manuk.path.game.render.MyRenderer;

public class MySurface extends GLSurfaceView {
	Controller controller;
	
	public MySurface(Context context, MyRenderer myRenderer) {
		super(context);
		try {
			setEGLContextClientVersion(3);
		} catch (Exception e) {
			System.out.println("could not found opengl 3");
			setEGLContextClientVersion(2);
		}
		setRenderer(myRenderer);
		setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
	}
	
	public void setController(Controller controller) {
		this.controller = controller;
	}
	
	public boolean onTouchEvent(MotionEvent e) {
		if (controller != null)
			controller.onTouch(e);
		return true;
	}
}