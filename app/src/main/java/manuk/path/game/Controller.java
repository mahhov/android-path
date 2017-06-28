package manuk.path.game;

import android.view.MotionEvent;
import android.view.View;

class Controller implements View.OnTouchListener {
	static final int UP = 0, DOWN = 1, PRESS = 2, RELEASE = 3;
	double touchX, touchY;
	private int width, height, shiftX, shiftY;
	private int touch;
	
	Controller(int width, int height, int fullWidth, int fullHeight) {
		this.width = width;
		this.height = height;
		shiftX = (fullWidth - width) / 2;
		shiftY = (fullHeight - height) / 2;
	}
	
	void ageTouch() {
		if (touch == PRESS)
			touch = DOWN;
		else if (touch == RELEASE)
			touch = UP;
	}
	
	boolean isDown() {
		return touch == DOWN || touch == PRESS;
	}
	
	boolean isPress() {
		return touch == PRESS;
	}
	
	public boolean onTouch(View v, MotionEvent event) {
		touchX = (event.getX() - shiftX) / width;
		touchY = (event.getY() - shiftY) / height;
		switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				touch = PRESS;
				break;
			case MotionEvent.ACTION_UP:
				touch = RELEASE;
		}
		return true;
	}
}
