package manuk.path.game;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.SurfaceHolder;
import manuk.path.game.util.Measurements;

public class Painter {
	private static final int OUT_COLOR = Color.rgb(100, 100, 100);
	
	private Paint paint;
	private SurfaceHolder surfaceHolder;
	private Canvas canvas;
	
	Painter() {
		paint = new Paint();
		paint.setTextSize(40);
		paint.setTextAlign(Paint.Align.CENTER);
	}
	
	void prep(SurfaceHolder surfaceHolder) {
		this.surfaceHolder = surfaceHolder;
		canvas = surfaceHolder.lockCanvas();
		canvas.drawColor(OUT_COLOR);
		drawRect(0, 0, 1, 1, Color.BLACK);
	}
	
	void post() {
		drawRect(0, -1, 1, 1, OUT_COLOR);
		drawRect(0, 1, 1, 1, OUT_COLOR);
	}
	
	void end() {
		surfaceHolder.unlockCanvasAndPost(canvas);
	}
	
	public double[] convertFromAbsolute(double x, double y) {
		return new double[] {(x - Measurements.SCREEN_SHIFT_X) / Measurements.SCREEN_WIDTH + Measurements.SCREEN_SHIFT_X, (y - Measurements.SCREEN_SHIFT_Y) / Measurements.SCREEN_HEIGHT};
	}
	
	void drawRect(double x, double y, double width, double height, int color, boolean frame) {
		paint.setColor(color);
		if (frame)
			paint.setStyle(Paint.Style.STROKE);
		else
			paint.setStyle(Paint.Style.FILL);
		float left = (float) (Measurements.SCREEN_SHIFT_X + x * Measurements.SCREEN_WIDTH);
		float top = (float) (Measurements.SCREEN_SHIFT_Y + y * Measurements.SCREEN_HEIGHT);
		float right = (float) (left + width * Measurements.SCREEN_WIDTH);
		float bottom = (float) (top + height * Measurements.SCREEN_HEIGHT);
		canvas.drawRect(left, top, right, bottom, paint);
	}
	
	void drawRect(double x, double y, double width, double height, int color) {
		drawRect(x, y, width, height, color, false);
	}
	
	void drawPolygon(double[] x, double[] y, int color, boolean frame) {
		paint.setColor(color);
		if (frame)
			paint.setStyle(Paint.Style.STROKE);
		else
			paint.setStyle(Paint.Style.FILL);
		
		Path path = new Path();
		float startX = (float) (Measurements.SCREEN_SHIFT_X + x[0] * Measurements.SCREEN_WIDTH);
		float startY = (float) (Measurements.SCREEN_SHIFT_Y + y[0] * Measurements.SCREEN_HEIGHT);
		path.moveTo(startX, startY);
		for (int i = 1; i < x.length; i++)
			path.lineTo((float) (Measurements.SCREEN_SHIFT_X + x[i] * Measurements.SCREEN_WIDTH), (float) (Measurements.SCREEN_SHIFT_Y + y[i] * Measurements.SCREEN_HEIGHT));
		path.lineTo(startX, startY);
		canvas.drawPath(path, paint);
	}
	
	public void drawText(String text, double x, double y, int color) {
		paint.setColor(color);
		float left = (float) (Measurements.SCREEN_SHIFT_X + x * Measurements.SCREEN_WIDTH);
		float top = (float) (Measurements.SCREEN_SHIFT_Y + y * Measurements.SCREEN_HEIGHT);
		canvas.drawText(text, left, top, paint);
	}
}
