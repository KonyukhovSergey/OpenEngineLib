package ru.serjik.engine;

import android.app.Activity;
import android.opengl.GLSurfaceView.Renderer;
import android.os.Bundle;

public abstract class EngineActivity extends Activity
{
	private EngineView view;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		view = new EngineView(this, rendererFactory());
		setContentView(view);
	}

	protected abstract Renderer rendererFactory();

	@Override
	protected void onResume()
	{
		view.onResume();
		super.onResume();
	}
	
	@Override
	protected void onPause()
	{
		view.onPause();
		super.onPause();
	}
}
