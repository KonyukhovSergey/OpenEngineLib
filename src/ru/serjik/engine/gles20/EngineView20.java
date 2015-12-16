package ru.serjik.engine.gles20;

import android.content.Context;
import ru.serjik.engine.EngineView;

public class EngineView20 extends EngineView
{
	public EngineView20(Context context, Renderer renderer)
	{
		super(context, renderer);
	}

	@Override
	protected void setupRenderer(Renderer renderer)
	{
		setEGLContextClientVersion(2);
		setRenderer(renderer);
		setRenderMode(RENDERMODE_WHEN_DIRTY);
	}
}
