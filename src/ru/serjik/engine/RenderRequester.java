package ru.serjik.engine;

import android.opengl.GLSurfaceView;
import android.os.Handler;

public class RenderRequester
{
	private GLSurfaceView view;
	private int delay;

	private Handler handler = new Handler();

	private Runnable runnable = new Runnable()
	{
		@Override
		public void run()
		{
			view.requestRender();
			handler.postDelayed(runnable, delay);
		}
	};

	public void delay(int delay)
	{
		this.delay = delay;
	}

	public void resume(GLSurfaceView view)
	{
		resume(view, 33);
	}

	public void resume(GLSurfaceView view, int delay)
	{
		this.view = view;
		this.delay = delay;
		handler.postDelayed(runnable, delay);
	}

	public void pause()
	{
		handler.removeCallbacks(runnable);
	}
}
