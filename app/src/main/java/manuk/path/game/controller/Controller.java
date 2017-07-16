package manuk.path.game.controller;

import android.view.MotionEvent;
import manuk.path.game.util.Measurements;

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
					double x = (event.getX(index) - Measurements.SCREEN_SHIFT_X) / Measurements.SCREEN_WIDTH;
					double y = (event.getY(index) - Measurements.SCREEN_SHIFT_Y) / Measurements.SCREEN_HEIGHT;
					touch[id].setFresh(x, y);
				}
				break;
			case MotionEvent.ACTION_UP:
			case MotionEvent.ACTION_POINTER_UP:
				id = event.getPointerId(event.getActionIndex());
				if (id < 2)
					touch[id].released = true;
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
	
	public Touch getTouch(int touchId, double left, double top, double right, double bottom) {
		if (touchId != -1 && touch[touchId].isDown())
			return touch[touchId];
		
		for (Touch t : touch)
			if (t.isFresh() && t.x > left && t.x < right && t.y > top && t.y < bottom)
				return t;
		
		return null;
	}
	
	public Touch getTouch(int touchId) {
		if (touchId != -1 && touch[touchId].isDown())
			return touch[touchId];
		
		for (Touch t : touch)
			if (t.isFresh())
				return t;
		
		return null;
	}
	
	public static class Touch {
		private static final long DOUBLE_TIME = 500;
		static final int STATE_NONE = 0, STATE_FRESH = 1, STATE_CONSUMED = 2, STATE_FRESH_DOUBLE = 3;
		private static int count = 0;
		private int id;
		public double x, y;
		private int state;
		private boolean released;
		private long time;
		
		private Touch() {
			id = count++;
		}
		
		private void setFresh(double x, double y) {
			this.x = x;
			this.y = y;
			long time = System.currentTimeMillis();
			if (time - this.time < DOUBLE_TIME)
				state = STATE_FRESH_DOUBLE;
			else
				state = STATE_FRESH;
			this.time = time;
		}
		
		private boolean isDown() {
			return state != STATE_NONE;
		}
		
		private boolean isFresh() {
			return state == STATE_FRESH || state == STATE_FRESH_DOUBLE;
		}
		
		public boolean isFreshDouble() {
			return state == STATE_FRESH_DOUBLE;
		}
		
		public int consume() {
			if (released) {
				state = Touch.STATE_NONE;
				released = false;
			} else
				state = Touch.STATE_CONSUMED;
			return id;
		}
	}
}
