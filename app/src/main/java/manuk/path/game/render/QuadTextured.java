package manuk.path.game.render;

import android.opengl.GLES20;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import static android.opengl.GLES20.GL_LINE_LOOP;
import static android.opengl.GLES20.GL_TRIANGLE_FAN;

public class QuadTextured extends RenderElement {
	private static final int COORDS_PER_VERTEX = 2;
	private static final int VERTEX_COUNT = 4;
	private static final int VERTEX_STRIDE = COORDS_PER_VERTEX * 4; // 4 bytes per vertex
	private static final String VERTEX_SHADER_CODE = "" +
			"attribute vec4 vPosition;" +
			"void main() {" +
			"  gl_Position = vPosition;" +
			"}";
	private static final String FRAGMENT_SHADER_CODE = "" +
			"precision mediump float;" +
			"uniform vec4 vColor;" +
			"uniform sampler2D uTexture;" +
			"varying vec2 vTexCoordinate;" +
			"void main() {" +
			"  gl_FragColor = vColor * texture2D(uTexture, vTexCoordinate);" +
			"}";
	private static int program;
	
	private FloatBuffer vertexBuffer;
	private float[] color, frameColor; // rgba
	private int mPositionHandle;
	private int mColorHandle;
	
	public QuadTextured(float[] coord, int color) {
		vertexBuffer = ByteBuffer.allocateDirect(coord.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
		vertexBuffer.put(coord);
		vertexBuffer.position(0);
		this.color = floatColor(color);
		mPositionHandle = GLES20.glGetAttribLocation(program, "vPosition");
		mColorHandle = GLES20.glGetUniformLocation(program, "vColor");
	}
	
	public QuadTextured(float[] coord, int color, int frameColor) {
		this(coord, color);
		this.frameColor = floatColor(frameColor);
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
		GLES20.glEnableVertexAttribArray(mPositionHandle);
		GLES20.glVertexAttribPointer(mPositionHandle, COORDS_PER_VERTEX, GLES20.GL_FLOAT, false, VERTEX_STRIDE, vertexBuffer);
		GLES20.glUniform4fv(mColorHandle, 1, color, 0);
		GLES20.glDrawArrays(GL_TRIANGLE_FAN, 0, VERTEX_COUNT);
		if (frameColor != null) {
			GLES20.glLineWidth(10);
			GLES20.glUniform4fv(mColorHandle, 1, frameColor, 0);
			GLES20.glDrawArrays(GL_LINE_LOOP, 0, VERTEX_COUNT);
		}
		GLES20.glDisableVertexAttribArray(mPositionHandle);
	}
}