package manuk.path.game.render;

import android.opengl.GLES20;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import static android.opengl.GLES20.GL_TRIANGLE_FAN;

public class Quad {
	public static final float DEMO_QUAD_1[] = {    // in counterclockwise order:
			0f, .5f,         // top
			-.5f, -.5f,      // left
			0f, -.8f,        // bottom
			.5f, -.5f,};     // right
	
	public static final float DEMO_QUAD_2[] = {    // in counterclockwise order:
			-1, 1,        // top left
			-1, .8f,      // bottom left
			-.8f, .8f,    // bottom right
			-.8f, 1};     // top right
	
	private static final int COORDS_PER_VERTEX = 2;
	private static final int VERTEX_COUNT = 4;
	private static final int VERTEX_STRIDE = COORDS_PER_VERTEX * 4; // 4 bytes per vertex
	private static final String VERTEX_SHADER_CODE =
			"attribute vec4 vPosition;" +
					"void main() {" +
					"  gl_Position = vPosition;" +
					"}";
	private static final String FRAGMENT_SHADER_CODE =
			"precision mediump float;" +
					"uniform vec4 vColor;" +
					"void main() {" +
					"  gl_FragColor = vColor;" +
					"}";
	private static final int program;
	
	private FloatBuffer vertexBuffer;
	float color[] = {1f, 1f, 0f, 1f}; // rgba
	private int mPositionHandle;
	private int mColorHandle;
	
	static {
		program = GLES20.glCreateProgram();
		int vertexShader = MyRenderer.loadShader(GLES20.GL_VERTEX_SHADER, VERTEX_SHADER_CODE);
		int fragmentShader = MyRenderer.loadShader(GLES20.GL_FRAGMENT_SHADER, FRAGMENT_SHADER_CODE);
		GLES20.glAttachShader(program, vertexShader);
		GLES20.glAttachShader(program, fragmentShader);
		GLES20.glLinkProgram(program);
	}
	
	public Quad(float[] coord) {
		vertexBuffer = ByteBuffer.allocateDirect(coord.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
		vertexBuffer.put(coord);
		vertexBuffer.position(0);
		//		program = GLES20.glCreateProgram();
		//		int vertexShader = MyRenderer.loadShader(GLES20.GL_VERTEX_SHADER, VERTEX_SHADER_CODE);
		//		int fragmentShader = MyRenderer.loadShader(GLES20.GL_FRAGMENT_SHADER, FRAGMENT_SHADER_CODE);
		//		GLES20.glAttachShader(program, vertexShader);
		//		GLES20.glAttachShader(program, fragmentShader);
		//		GLES20.glLinkProgram(program);
		mPositionHandle = GLES20.glGetAttribLocation(program, "vPosition");
		mColorHandle = GLES20.glGetUniformLocation(program, "vColor");
	}
	
	public void draw() {
		GLES20.glUseProgram(program);
		GLES20.glEnableVertexAttribArray(mPositionHandle);
		GLES20.glVertexAttribPointer(mPositionHandle, COORDS_PER_VERTEX, GLES20.GL_FLOAT, false, VERTEX_STRIDE, vertexBuffer);
		color[2] = (float) Math.random();
		GLES20.glUniform4fv(mColorHandle, 1, color, 0);
		GLES20.glDrawArrays(GL_TRIANGLE_FAN, 0, VERTEX_COUNT);
		GLES20.glDisableVertexAttribArray(mPositionHandle);
	}
}