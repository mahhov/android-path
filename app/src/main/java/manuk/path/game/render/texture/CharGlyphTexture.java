package manuk.path.game.render.texture;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import manuk.path.game.render.QuadTextured;
import manuk.path.game.render.RenderElement;

public class CharGlyphTexture extends Texture {
	private static final float[] TEXTURE_COORD = new float[] {0, 0, 1, 0, 1, 1, 0, 1};
	private static final int SIZE = 36;
	
	public CharGlyphTexture(String s) {
		super(createImage(s));
	}
	
	private static Bitmap createImage(String s) {
		Bitmap textImage = Bitmap.createBitmap(SIZE, SIZE, Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(textImage);
		Paint paint = new Paint();
		paint.setColor(Color.WHITE);
		paint.setTextSize(20);
		canvas.drawText(s, SIZE / 5, SIZE * 7 / 10, paint);
		return textImage;
	}
	
	public RenderElement draw(float x, float y, float w, float h) {
		return new QuadTextured(new float[] {x, y, x, y + h, x + w, y + h, x + w, y}, textureId);
	}
}