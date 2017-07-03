package manuk.path.game.painter;

import android.graphics.Color;
import manuk.path.MySurface;
import manuk.path.game.render.MyRenderer;
import manuk.path.game.render.Quad;
import manuk.path.game.render.RenderElement;
import manuk.path.game.util.LList;
import manuk.path.game.util.Measurements;

public class Painter {
	private MySurface mySurface;
	private MyRenderer myRenderer;
	private LList<RenderElement> drawList;
	
	public Painter(MySurface mySurface, MyRenderer myRenderer) {
		this.mySurface = mySurface;
		this.myRenderer = myRenderer;
		drawList = new LList<>();
	}
	
	public void prep() {
		drawList = new LList<>();
		drawRect(0, 0, 1, 1, Color.BLACK);
	}
	
	public void post() {
		myRenderer.setDrawList(drawList);
		mySurface.requestRender();
	}
	
	public double[] convertFromAbsolute(double x, double y) {
		return new double[] {(x - Measurements.SCREEN_SHIFT_X) / Measurements.SCREEN_WIDTH + Measurements.SCREEN_SHIFT_X, (y - Measurements.SCREEN_SHIFT_Y) / Measurements.SCREEN_HEIGHT};
	}
	
	public void drawRect(double x, double y, double width, double height, int color, int frameColor) {
		float[] coord = openglCoordXYWH(x, y, width, height);
		drawList.addHead(new Quad(new float[] {
				coord[0], coord[1],
				coord[0], coord[1] + coord[3],
				coord[0] + coord[2], coord[1] + coord[3],
				coord[0] + coord[2], coord[1]}, color, frameColor));
	}
	
	public void drawRect(double x, double y, double width, double height, int color) {
		float[] coord = openglCoordXYWH(x, y, width, height);
		drawList.addHead(new Quad(new float[] {
				coord[0], coord[1],
				coord[0], coord[1] + coord[3],
				coord[0] + coord[2], coord[1] + coord[3],
				coord[0] + coord[2], coord[1]}, color));
	}
	
	public void drawPolygon(double[] x, double[] y, int color) {
		float[] coord = openglCoordXY(x, y);
		drawList.addHead(new Quad(coord, color));
	}
	
	public void drawText(String text, double x, double y, int color) {
		//		if (canvas == null)
		//			return;
		//		paint.setTextAlign(Paint.Align.LEFT);
		//		paint.setColor(color);
		//		float left = (float) (Measurements.SCREEN_SHIFT_X + x * Measurements.SCREEN_WIDTH);
		//		float top = (float) (Measurements.SCREEN_SHIFT_Y + y * Measurements.SCREEN_HEIGHT);
		//		canvas.drawText(text, left, top, paint);
	}
	
	public void drawCenteredText(String text, double x, double y, int color) {
		//		if (canvas == null)
		//			return;
		//		paint.setTextAlign(Paint.Align.CENTER);
		//		paint.setColor(color);
		//		float left = (float) (Measurements.SCREEN_SHIFT_X + x * Measurements.SCREEN_WIDTH);
		//		float top = (float) (Measurements.SCREEN_SHIFT_Y + y * Measurements.SCREEN_HEIGHT);
		//		canvas.drawText(text, left, top, paint);
	}
	
	private static float[] openglCoordXYWH(double x, double y, double width, double height) {
		return new float[] {
				(float) (x * 2 - 1),
				(float) (-y * 2 + 1),
				(float) (width * 2),
				(float) (height * -2)
		};
	}
	
	private static float[] openglCoordXY(double[] x, double[] y) {
		float coord[] = new float[x.length * 2];
		for (int i = 0; i < x.length; i++) {
			coord[i * 2] = (float) (x[i] * 2 - 1);
			coord[i * 2 + 1] = (float) (-y[i] * 2 + 1);
		}
		return coord;
	}
}
