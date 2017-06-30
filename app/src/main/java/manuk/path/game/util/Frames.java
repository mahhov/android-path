package manuk.path.game.util;

public class Frames {
	public boolean running;
	private long start, current;
	private int fps;
	private int frameCount;
	
	public Frames() {
		running = true;
	}
	
	public int getFPS() {
		frameCount++;
		current = System.currentTimeMillis();
		if (current - start > 1000) {
			start = current;
			fps = frameCount;
			frameCount = 0;
		}
		return fps;
	}
}