package ru.serjik.engine;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import ru.serjik.utils.FrameRateCalculator;
import ru.serjik.utils.FrameRateCalculator.FrameRateUpdater;

import android.content.Context;
import android.opengl.GLSurfaceView.Renderer;
import android.util.Log;

public abstract class RendererHolder
{
	private boolean frameRateLoging = false;

	protected final Context context;

	public RendererHolder(Context context)
	{
		this.context = context;
	}

	public abstract void onCreated(GL10 gl, EGLConfig config);

	public abstract void onChanged(GL10 gl, int width, int heigth);

	public abstract void onDrawFrame(GL10 gl, float deltaTime);

	private FrameRateUpdater frameRateUpdater = new FrameRateUpdater()
	{
		@Override
		public void onFrameRateUpdate(FrameRateCalculator frameRateCalculator)
		{
			if (frameRateLoging)
			{
				Log.v("EngineRenderer", frameRateCalculator.frameString());

			}
		}
	};

	private final Renderer renderer = new Renderer()
	{
		private FrameRateCalculator frameRateCalculator = new FrameRateCalculator(frameRateUpdater);
		private long lastFrameTime = System.currentTimeMillis();

		@Override
		public void onSurfaceCreated(GL10 gl, EGLConfig config)
		{
			Log.v("RendererHolder", "onCreated");
			onCreated(gl, config);
		}

		@Override
		public void onSurfaceChanged(GL10 gl, int width, int height)
		{
			Log.v("RendererHolder", "onChanged");
			onChanged(gl, width, height);
		}

		@Override
		public void onDrawFrame(GL10 gl)
		{
			frameRateCalculator.frameBegin();

			long currentFrameTime = System.currentTimeMillis();

			long deltaTime = currentFrameTime - lastFrameTime;

			if (deltaTime > 125)
			{
				deltaTime = 125;
			}

			lastFrameTime = currentFrameTime;

			RendererHolder.this.onDrawFrame(gl, deltaTime * 0.001f);

			frameRateCalculator.frameDone();
		}
	};

	public void setFrameRateLoging(boolean frameRateLoging)
	{
		this.frameRateLoging = frameRateLoging;
	}

	public Renderer renderer()
	{
		return renderer;
	}

}
