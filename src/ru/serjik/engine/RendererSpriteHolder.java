package ru.serjik.engine;

import javax.microedition.khronos.opengles.GL10;

import android.content.Context;

public abstract class RendererSpriteHolder extends RendererHolder
{
	public RendererSpriteHolder(Context context)
	{
		super(context);
	}

	private float width;
	private float height;

	public float width()
	{
		return width;
	}

	public float height()
	{
		return height;
	}

	@Override
	public void onChanged(GL10 gl, int width, int height)
	{
		if (width < height)
		{
			this.width = size();
			this.height = size() * (float) height / (float) width;
		}
		else
		{
			this.width = size() * (float) width / (float) height;
			this.height = size();
		}

		gl.glViewport(0, 0, width, height);
		gl.glMatrixMode(GL10.GL_PROJECTION);
		gl.glLoadIdentity();
		gl.glOrthof(0, this.width, this.height, 0, 1, -1);
	}

	protected float size()
	{
		return 240.0f;
	}

}
