package ru.serjik.engine;

import android.content.Context;
import android.opengl.GLSurfaceView;

public class EngineView extends GLSurfaceView
{
	private int delay = 16;

	public EngineView(Context context)
	{
		super(context);
	}

	@Override
	public void onPause()
	{
		removeCallbacks(renderRequester);
		super.onPause();
	}

	@Override
	public void onResume()
	{
		super.onResume();
		postDelayed(renderRequester, delay);
	}

	public void setFrameRate(int frameRate)
	{
		delay = 1000 / frameRate;
	}

	private Runnable renderRequester = new Runnable()
	{
		@Override
		public void run()
		{
			requestRender();
			postDelayed(renderRequester, delay);
		}
	};
}
