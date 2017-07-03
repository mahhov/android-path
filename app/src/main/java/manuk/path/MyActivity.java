package manuk.path;

import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import manuk.path.game.render.MyRenderer;

public class MyActivity extends AppCompatActivity {
	private GLSurfaceView myGLSurfaceView;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setupOpenGl();
		setContentView(myGLSurfaceView);
	}
	
	private void setupOpenGl() {
		myGLSurfaceView = new GLSurfaceView(this);
		try {
			myGLSurfaceView.setEGLContextClientVersion(3);
		} catch (Exception e) {
			System.out.println("could not found opengl 3");
			myGLSurfaceView.setEGLContextClientVersion(2);
		}
		myGLSurfaceView.setRenderer(new MyRenderer());
	}
	
	protected void onResume() {
		super.onResume();
		myGLSurfaceView.onResume();
	}
	
	protected void onPause() {
		super.onPause();
		myGLSurfaceView.onPause();
	}
}
