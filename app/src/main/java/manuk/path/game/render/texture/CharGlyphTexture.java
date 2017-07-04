package manuk.path.game.render.texture;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

class CharGlyphTexture extends Texture {
	private static final float[] TEXTURE_COORD = new float[] {0, 0, 1, 0, 1, 1, 0, 1};
	private static final int SIZE = 12;
	
	CharGlyphTexture(String s) {
		super(createImage(s));
	}
	
	private static Bitmap createImage(String s) {
		Bitmap textImage = Bitmap.createBitmap(SIZE, SIZE, Bitmap.Config.ARGB_4444);
		Canvas canvas = new Canvas(textImage);
		Paint paint = new Paint();
		paint.setColor(Color.WHITE);
		canvas.drawText(s, 0, 0, paint);
		return textImage;
	}
	
	void coordDraw(float x, float y) {
		//		float size = .03f;
		//		float leftX = -1 + x * size;
		//		float topY = 1 - y * size;
		//		float rightX = leftX + size;
		//		float bottomY = topY - size;
		//		float[] quad = new float[] {leftX, topY, rightX, topY, rightX, bottomY, leftX, bottomY};
		//		
		//		GLES20.glEnableVertexAttribArray(mPositionHandle);
		//		GLES20.glVertexAttribPointer(mPositionHandle, COORDS_PER_VERTEX, GLES20.GL_FLOAT, false, VERTEX_STRIDE, vertexBuffer);
		//		GLES20.glUniform4fv(mColorHandle, 1, color, 0);
		//		GLES20.glDrawArrays(GL_TRIANGLE_FAN, 0, VERTEX_COUNT);
		//		if (frameColor != null) {
		//			GLES20.glLineWidth(10);
		//			GLES20.glUniform4fv(mColorHandle, 1, frameColor, 0);
		//			GLES20.glDrawArrays(GL_LINE_LOOP, 0, VERTEX_COUNT);
		//		}
		//		GLES20.glDisableVertexAttribArray(mPositionHandle);
		//		
		//		glTexCoordPointer(2, GL_FLOAT, 0, TEXTURE_COORD);
		//		glVertexPointer(2, GL_FLOAT, 0, quad);
	}
}