package ru.serjik.engine;

import android.content.Context;
import android.opengl.GLSurfaceView;

public class EngineView extends GLSurfaceView
{
	public EngineView(Context context, Renderer renderer)
	{
		super(context);
		setRenderer(renderer);
		setRenderMode(RENDERMODE_CONTINUOUSLY);
	}
}
