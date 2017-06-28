package manuk.snake.snakegame;

import android.view.MotionEvent;
import android.view.View;

public class Controller implements View.OnTouchListener {
	private int width, height;
	private boolean touch;
	double touchX, touchY;
	
	public Controller(int width, int height) {
		this.width = width;
		this.height = height;
	}
	
	boolean getTouch() {
		boolean r = touch;
		touch = false;
		return r;
	}
	
	public boolean onTouch(View v, MotionEvent event) {
		touchX = event.getX() / width;
		touchY = event.getY() / height;
		switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
			case MotionEvent.ACTION_MOVE:
				touch = true;
				break;
		}
		return true;
	}
}
