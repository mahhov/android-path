package manuk.path.game.render;

import android.opengl.GLES20;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import static android.opengl.GLES20.GL_LINE_LOOP;
import static android.opengl.GLES20.GL_TRIANGLE_FAN;

public class Quad extends RenderElement {
	private static final int VERTEX_COUNT = 4;
	private static final int COORDS_PER_VERTEX = 2;
	private static final String VERTEX_SHADER_CODE = "" +
			"attribute vec4 vPosition;" +
			"void main() {" +
			"  gl_Position = vPosition;" +
			"}";
	private static final String FRAGMENT_SHADER_CODE = "" +
			"precision mediump float;" +
			"uniform vec4 vColor;" +
			"void main() {" +
			"  gl_FragColor = vColor;" +
			"}";
	private static int program;
	
	private FloatBuffer vertexBuffer;
	private float[] color, frameColor; // rgba
	
	public Quad(float[] coord, int color) {
		vertexBuffer = ByteBuffer.allocateDirect(coord.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
		vertexBuffer.put(coord).position(0);
		this.color = floatColor(color);
	}
	
	public Quad(float[] coord, int color, boolean frame) {
		vertexBuffer = ByteBuffer.allocateDirect(coord.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
		vertexBuffer.put(coord).position(0);
		if (frame)
			frameColor = floatColor(color);
		else
			this.color = floatColor(color);
	}
	
	public Quad(float[] coord, int color, int frameColor) {
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
		
		// pos
		int mPositionHandle = GLES20.glGetAttribLocation(program, "vPosition");
		GLES20.glEnableVertexAttribArray(mPositionHandle);
		GLES20.glVertexAttribPointer(mPositionHandle, COORDS_PER_VERTEX, GLES20.GL_FLOAT, false, 0, vertexBuffer);
		
		// color
		int mColorHandle = GLES20.glGetUniformLocation(program, "vColor");
		
		// draw area
		if (color != null) {
			GLES20.glUniform4fv(mColorHandle, 1, color, 0);
			GLES20.glDrawArrays(GL_TRIANGLE_FAN, 0, VERTEX_COUNT);
		}
		
		// draw frame
		if (frameColor != null) {
			GLES20.glLineWidth(10);
			GLES20.glUniform4fv(mColorHandle, 1, frameColor, 0);
			GLES20.glDrawArrays(GL_LINE_LOOP, 0, VERTEX_COUNT);
		}
		GLES20.glDisableVertexAttribArray(mPositionHandle);
	}
}