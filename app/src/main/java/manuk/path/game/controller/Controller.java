package manuk.path.game.controller;

import android.view.MotionEvent;
import manuk.path.game.util.Measurements;

import static manuk.path.game.controller.Controller.Touch.STATE_FRESH;

public class Controller {
	public Touch[] touch;
	
	public Controller() {
		touch = new Touch[] {new Touch(), new Touch()};
	}
	
	public void onTouch(MotionEvent event) {
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
	}
	
	public void refreshTouchStates() {
		for (Touch t : touch)
			if (t.isDown())
				t.state = Touch.STATE_FRESH;
	}
	
	public static class Touch {
		static final int STATE_NONE = 0, STATE_FRESH = 1, STATE_CONSUMED = 2;
		public double x, y;
		private int state;
		
		public boolean isDown() {
			return state != STATE_NONE;
		}
		
		public boolean isFresh() {
			return state == STATE_FRESH;
		}
		
		public void consume() {
			state = Touch.STATE_CONSUMED;
		}
	}
}
