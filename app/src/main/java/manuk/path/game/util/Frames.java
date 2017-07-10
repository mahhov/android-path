package manuk.path.game.util;

import manuk.path.game.painter.Painter;

public class Frames {
	private static final int MIN_FRAME_LENGTH = 1000 / 50;
	private long currentFrameStart;
	
	public static String DEBUG = "";
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
		double[] xy = painter.convertFromAbsolute(70, 50);
		painter.drawText("fps: " + getFPS() + DEBUG, xy[0], xy[1], .05f);
	}
	
	public void waitCurrentFrame() {
		long remaining = MIN_FRAME_LENGTH - System.currentTimeMillis() + currentFrameStart;
		while (remaining > 0) {
			try {
				Thread.sleep(remaining);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			remaining = MIN_FRAME_LENGTH - System.currentTimeMillis() + currentFrameStart;
		}
		currentFrameStart = System.currentTimeMillis();
	}
}