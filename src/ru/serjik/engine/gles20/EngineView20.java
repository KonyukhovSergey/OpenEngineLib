package ru.serjik.engine.gles20;

import ru.serjik.engine.utils.RenderRequester;
import android.content.Context;
import android.opengl.GLSurfaceView;

public class EngineView20 extends GLSurfaceView
{
	private RenderRequester renderRequester = new RenderRequester();

	public EngineView20(Context context, Renderer renderer)
	{
		super(context);
		setEGLContextClientVersion(2);
		setRenderer(renderer);
		setRenderMode(RENDERMODE_WHEN_DIRTY);
	}

	@Override
	public void onPause()
	{
		renderRequester.pause();
	}

	@Override
	public void onResume()
	{
		renderRequester.resume(this);
	}
}
