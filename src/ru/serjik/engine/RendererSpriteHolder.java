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
	private float heigth;

	public float width()
	{
		return width;
	}

	public float heigth()
	{
		return heigth;
	}

	@Override
	public void onChanged(GL10 gl, int width, int heigth)
	{
		if (width < heigth)
		{
			this.width = size();
			this.heigth = size() * (float) heigth / (float) width;
		}
		else
		{
			this.width = size() * (float) width / (float) heigth;
			this.heigth = size();
		}

		gl.glViewport(0, 0, width, heigth);
		gl.glMatrixMode(GL10.GL_PROJECTION);
		gl.glLoadIdentity();
		gl.glOrthof(0, this.width, this.heigth, 0, 1, -1);
	}

	protected float size()
	{
		return 240.0f;
	}

}
