package ru.serjik.engine;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import ru.serjik.utils.FrameRateCalculator;
import ru.serjik.utils.FrameRateCalculator.FrameRateUpdater;

import android.content.Context;
import android.opengl.GLSurfaceView.Renderer;
import android.util.Log;

public abstract class EngineRenderer2D implements Renderer, FrameRateUpdater
{
	private static final String TAG = "EngineRenderer";

	private static final float desiredWidth = 720;
	private FrameRateCalculator frameRateCalculator;

	private int width;
	private int height;

	protected Context context;

	public EngineRenderer2D(Context context)
	{
		this.context = context;
		frameRateCalculator = new FrameRateCalculator(this);
	}

	public int width()
	{
		return width;
	}

	public int height()
	{
		return height;
	}

	public abstract void draw(GL10 gl);

	public abstract void created(GL10 gl);

	public abstract void changed(GL10 gl, int width, int height);

	@Override
	public void onDrawFrame(GL10 gl)
	{
		frameRateCalculator.frameBegin();
		draw(gl);
		frameRateCalculator.frameDone();
	}

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config)
	{
		created(gl);
	}

	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height)
	{
		this.width = width;
		this.height = height;

		gl.glViewport(0, 0, width, height);
		gl.glMatrixMode(GL10.GL_PROJECTION);
		gl.glLoadIdentity();
		gl.glOrthof(0, this.width, this.height, 0, 1, -1);

		changed(gl, this.width, this.height);
	}

	@Override
	public void onFrameRateUpdate(FrameRateCalculator frameRateCalculator)
	{
		Log.v(TAG, frameRateCalculator.frameString());
	}
}
