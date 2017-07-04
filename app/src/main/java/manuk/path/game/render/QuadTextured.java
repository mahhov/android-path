package manuk.path.game.render;

import android.opengl.GLES20;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import static android.opengl.GLES20.GL_TRIANGLE_FAN;

public class QuadTextured extends RenderElement {
	private static final int COORDS_PER_VERTEX = 2;
	private static final int VERTEX_COUNT = 4;
	private static final int VERTEX_STRIDE = COORDS_PER_VERTEX * 4; // 4 bytes per vertex
	private static final String VERTEX_SHADER_CODE = "" +
			"attribute vec4 vPosition;" +
			"attribute vec2 aTexCoordinate;" +
			"varying vec2 vTexCoordinate;" +
			"void main() {" +
			"  gl_Position = vPosition;" +
			"  vTexCoordinate = aTexCoordinate;" +
			"}";
	private static final String FRAGMENT_SHADER_CODE = "" +
			"precision mediump float;" +
			"uniform sampler2D uTexture;" +
			"varying vec2 vTexCoordinate;" +
			"void main() {" +
			"  gl_FragColor = texture2D(uTexture, vTexCoordinate);" +
			"}";
	private static int program;
	
	private FloatBuffer vertexBuffer;
	private int mPositionHandle;
	private int mColorHandle;
	
	// Store our model data in a float buffer. 
	private FloatBuffer mCubeTextureCoordinates;
	// This will be used to pass in the texture. 
	private int mTextureUniformHandle;
	// This will be used to pass in model texture coordinate information. 
	private int mTextureCoordinateHandle;
	// Size of the texture coordinate data in elements. 
	private int mTextureCoordinateDataSize = 2;
	// This is a handle to our texture data. 
	private int textureId;
	
	public QuadTextured(float[] coord, int textureId) {
		vertexBuffer = ByteBuffer.allocateDirect(coord.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
		vertexBuffer.put(coord).position(0);
		this.textureId = textureId;
		
		final float[] cubeTextureCoordinateData = {
				0.0f, 0.0f,
				0.0f, 1.0f,
				1.0f, 1.0f,
				1.0f, 0.0f};
		mCubeTextureCoordinates = ByteBuffer.allocateDirect(cubeTextureCoordinateData.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
		mCubeTextureCoordinates.put(cubeTextureCoordinateData).position(0);
	}
	
	static void init() {
		program = GLES20.glCreateProgram();
		int vertexShader = MyRenderer.loadShader(GLES20.GL_VERTEX_SHADER, VERTEX_SHADER_CODE);
		int fragmentShader = MyRenderer.loadShader(GLES20.GL_FRAGMENT_SHADER, FRAGMENT_SHADER_CODE);
		GLES20.glAttachShader(program, vertexShader);
		GLES20.glAttachShader(program, fragmentShader);
		GLES20.glLinkProgram(program);
	}
	
	void draw() {
		GLES20.glUseProgram(program);
		
		mTextureUniformHandle = GLES20.glGetUniformLocation(program, "uTexture");
		GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
		GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureId);
		GLES20.glUniform1i(mTextureUniformHandle, 0);
		
		mPositionHandle = GLES20.glGetAttribLocation(program, "vPosition");
		GLES20.glEnableVertexAttribArray(mPositionHandle);
		GLES20.glVertexAttribPointer(mPositionHandle, COORDS_PER_VERTEX, GLES20.GL_FLOAT, false, 0, vertexBuffer);
		
		mTextureCoordinateHandle = GLES20.glGetAttribLocation(program, "aTexCoordinate");
		GLES20.glEnableVertexAttribArray(mTextureCoordinateHandle);
		GLES20.glVertexAttribPointer(mTextureCoordinateHandle, mTextureCoordinateDataSize, GLES20.GL_FLOAT, false, 0, mCubeTextureCoordinates);
		
		GLES20.glDrawArrays(GL_TRIANGLE_FAN, 0, VERTEX_COUNT);
		
		GLES20.glDisableVertexAttribArray(mPositionHandle);
	}
}