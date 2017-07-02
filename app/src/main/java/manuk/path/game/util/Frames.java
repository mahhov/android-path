package manuk.path.game.util;

import android.graphics.Color;
import manuk.path.game.painter.Painter;

public class Frames {
	public boolean running;
	private long start, current;
	private int fps;
	private int frameCount;
	
	public Frames() {
		running = true;
	}
	
	private int getFPS() {
		frameCount++;
		current = System.currentTimeMillis();
		if (current - start > 1000) {
			start = current;
			fps = frameCount;
			frameCount = 0;
		}
		return fps;
	}
	
	public void draw(Painter painter) {
		double[] xy = painter.convertFromAbsolute(70, 50); // bottom left
		double[] size = painter.convertFromAbsolute(120, 30);
		double[] padding = painter.convertFromAbsolute(10, 10);
		painter.drawRect(xy[0] - padding[0], xy[1] - size[1] - padding[1], size[0] + padding[0] * 2, size[1] + padding[1] * 2, Color.BLACK);
		painter.drawText("fps: " + getFPS(), xy[0], xy[1], Color.WHITE);
	}
}