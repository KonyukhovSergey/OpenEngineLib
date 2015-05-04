package ru.serjik.engine;

import android.content.Context;
import android.opengl.GLSurfaceView;

public class EngineView extends GLSurfaceView
{
	private RenderRequester renderRequester = new RenderRequester();

	public EngineView(Context context, Renderer renderer)
	{
		super(context);
		setRenderer(renderer);
		setRenderMode(RENDERMODE_WHEN_DIRTY);
	}

	@Override
	public void onPause()
	{
		renderRequester.pause();
		super.onPause();
	}

	@Override
	public void onResume()
	{
		renderRequester.resume(this);
		super.onResume();
	}
}
