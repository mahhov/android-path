package manuk.path.game.util;

public class Measurements {
	public static final int MAP_WIDTH = 100, MAP_LENGTH = MAP_WIDTH, MAP_HEIGHT = 3;
	
	private static double VIEW_RATIO;
	private static int VIEW_WIDTH, VIEW_HEIGHT;
	
	public static int SCALED_VIEW_WIDTH, SCALED_VIEW_HEIGHT;
	public static double SCALED_BLOCK_WIDTH, SCALED_BLOCK_HEIGHT;
	public static double VIEW_STRETCH_Z;
	
	public static int SCREEN_WIDTH, SCREEN_HEIGHT, SCREEN_SHIFT_X, SCREEN_SHIFT_Y;
	
	public static void init(int fullWidth, int fullHeight) {
		VIEW_RATIO = 1. * fullHeight / fullWidth;
		VIEW_WIDTH = 30;
		VIEW_HEIGHT = (int) (VIEW_WIDTH * VIEW_RATIO);
		
		setScale(1);
		
		SCREEN_WIDTH = fullWidth;
		SCREEN_HEIGHT = (int) (fullWidth * VIEW_RATIO);
		SCREEN_SHIFT_X = 0;
		SCREEN_SHIFT_Y = (fullHeight - SCREEN_HEIGHT) / 2;
	}
	
	public static void setScale(double scale) {
		double viewWidth = VIEW_WIDTH * scale;
		double viewHeight = VIEW_HEIGHT * scale;
		SCALED_VIEW_WIDTH = (int) viewWidth;
		SCALED_VIEW_HEIGHT = (int) viewHeight;
		SCALED_BLOCK_WIDTH = 1. / viewWidth;
		SCALED_BLOCK_HEIGHT = 1. / viewHeight;
		VIEW_STRETCH_Z = SCALED_BLOCK_WIDTH / (10 * scale);
	}
}
