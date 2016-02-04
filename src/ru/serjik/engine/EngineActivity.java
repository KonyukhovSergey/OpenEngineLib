package ru.serjik.engine;

import android.app.Activity;
import android.opengl.GLSurfaceView.Renderer;
import android.os.Bundle;
import ru.serjik.engine.gles20.EngineView20;

public abstract class EngineActivity extends Activity
{
	protected EngineView view;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		view = new EngineView20(this, renderer());
		setContentView(view);
	}

	protected abstract Renderer renderer();

	@Override
	protected void onResume()
	{
		super.onResume();
		view.onResume();
	}

	@Override
	protected void onPause()
	{
		super.onPause();
		view.onPause();
	}
}
