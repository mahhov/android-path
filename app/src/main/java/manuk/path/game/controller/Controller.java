package manuk.path.game.controller;

import android.content.Context;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import manuk.path.game.util.Math3D;
import manuk.path.game.util.Measurements;

import static manuk.path.game.controller.Controller.Touch.STATE_FRESH;

public class Controller implements View.OnTouchListener {
	static final double SCALE_MIN = .4, SCALE_MAX = 1.8;
	
	public Touch[] touch;
	public double scale;
	//	private ScaleGestureDetector scaleGestureDetector;
	
	public Controller(Context context) {
		scale = 1;
		//		scaleGestureDetector = new ScaleGestureDetector(context, new ScaleListener());
		touch = new Touch[] {new Touch(), new Touch()};
	}
	
	public boolean onTouch(View v, MotionEvent event) {
		//		scaleGestureDetector.onTouchEvent(event);
		
		int id;
		
		switch (event.getActionMasked()) {
			case MotionEvent.ACTION_DOWN:
			case MotionEvent.ACTION_POINTER_DOWN:
				id = event.getPointerId(event.getActionIndex());
				if (id < 2) {
					int index = event.findPointerIndex(id);
					touch[id].x = (event.getX(index) - Measurements.SCREEN_SHIFT_X) / Measurements.SCREEN_WIDTH;
					touch[id].y = (event.getY(index) - Measurements.SCREEN_SHIFT_Y) / Measurements.SCREEN_HEIGHT;
					touch[id].state = STATE_FRESH;
				}
				break;
			case MotionEvent.ACTION_UP:
			case MotionEvent.ACTION_POINTER_UP:
				id = event.getPointerId(event.getActionIndex());
				if (id < 2)
					touch[id].state = Touch.STATE_NONE;
				break;
			case MotionEvent.ACTION_MOVE:
				for (int i = 0; i < event.getPointerCount(); i++) {
					id = event.getPointerId(i);
					if (id < 2) {
						touch[id].x = (event.getX(i) - Measurements.SCREEN_SHIFT_X) / Measurements.SCREEN_WIDTH;
						touch[id].y = (event.getY(i) - Measurements.SCREEN_SHIFT_Y) / Measurements.SCREEN_HEIGHT;
					}
				}
				break;
		}
		return true;
	}
	
	public void refreshTouchStates() {
		for (Touch t : touch)
			if (t.isDown())
				t.state = Touch.STATE_FRESH;
	}
	
	private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
		public boolean onScale(ScaleGestureDetector detector) {
			scale = Math3D.minMax(scale / detector.getScaleFactor(), SCALE_MIN, SCALE_MAX);
			return true;
		}
	}
	
	public static class Touch {
		public static final int STATE_NONE = 0, STATE_FRESH = 1, STATE_CONSUMED = 2;
		public double x, y;
		public int state;
		
		public boolean isDown() {
			return state != STATE_NONE;
		}
		
		public boolean isFresh() {
			return state == STATE_FRESH;
		}
	}
}
