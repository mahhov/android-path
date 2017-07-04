package manuk.path.game.render.texture;

import android.graphics.Bitmap;
import android.opengl.GLES20;
import android.opengl.GLUtils;
import manuk.path.R;

import static android.opengl.GLES20.GL_LINE_LOOP;
import static android.opengl.GLES20.GL_TRIANGLE_FAN;

abstract class Texture {
	private int textureId;
	
	Texture(Bitmap bitmap) {
		final int[] textureHandle = new int[1];
		GLES20.glGenTextures(1, textureHandle, 0);
		if (textureHandle[0] != 0) {
			GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureHandle[0]);
			GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_NEAREST);
			GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_NEAREST);
			GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0);
			bitmap.recycle();
			textureId = textureHandle[0];
		}
	}
	
	void draw(float x, float y) {
		prepDraw();
		coordDraw(x, y);
		endDraw();
	}
	
	private void prepDraw() {
//		mTextureUniformHandle = GLES20.glGetUniformLocation(mProgramHandle, "u_Texture");
//		mTextureCoordinateHandle = GLES20.glGetAttribLocation(mProgramHandle, "a_TexCoordinate");
		// Set the active texture unit to texture unit 0.
		GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
		
		// Bind the texture to this unit.
		GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureId);
		
		// Tell the texture uniform sampler to use this texture in the shader by binding to texture unit 0.
//		GLES20.glUniform1i(mTextureUniformHandle, 0);
	}
	
	abstract void coordDraw(float x, float y);
	
	private void endDraw() {
	}
}
