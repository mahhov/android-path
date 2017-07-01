package manuk.path.game.controller;

import android.content.Context;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

public class Controller implements View.OnTouchListener {
	static final int UP = 0, DOWN = 1, PRESS = 2, RELEASE = 3;
	public double touchX, touchY;
	private int width, height, shiftX, shiftY;
	private int touch;
	private ScaleGestureDetector scaleGestureDetector;
	
	public Controller(int width, int height, int fullWidth, int fullHeight, Context context) {
		this.width = width;
		this.height = height;
		shiftX = (fullWidth - width) / 2;
		shiftY = (fullHeight - height) / 2;
		scaleGestureDetector = new ScaleGestureDetector(context, new ScaleListener());
	}
	
	public void ageTouch() {
		if (touch == PRESS)
			touch = DOWN;
		else if (touch == RELEASE)
			touch = UP;
	}
	
	public boolean isDown() {
		return touch == DOWN || touch == PRESS;
	}
	
	public boolean isPress() {
		return touch == PRESS;
	}
	
	public boolean onTouch(View v, MotionEvent event) {
		scaleGestureDetector.onTouchEvent(event);
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
	
	private static class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
		
		public double scale = 1;
		
		public boolean onScale(ScaleGestureDetector detector) {
			scale *= detector.getScaleFactor();
			System.out.println(scale + " .. " + detector.getScaleFactor());
			return true;
		}
	}
}
