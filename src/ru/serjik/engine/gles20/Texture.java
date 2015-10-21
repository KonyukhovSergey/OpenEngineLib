package ru.serjik.engine.gles20;


import android.graphics.Bitmap;
import android.opengl.GLES20;
import android.opengl.GLUtils;

// glActiveTexture() - to select active slot
// glBindTextre() - to bind a texture to active slot


public class Texture
{
	private int id;

	public Texture(Bitmap bitmap)
	{
		id = create();

		bind();
		
		GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0);
	}

	public static void disable()
	{
		GLES20.glDisable(GLES20.GL_TEXTURE_2D);
	}

	public static void enable()
	{
		GLES20.glEnable(GLES20.GL_TEXTURE_2D);
	}

	/**
	 * @param min
	 * <br>
	 *            GL_NEAREST<br>
	 *            GL_LINEAR<br>
	 *            GL_NEAREST_MIPMAP_NEAREST<br>
	 *            GL_LINEAR_MIPMAP_NEAREST<br>
	 *            GL_NEAREST_MIPMAP_LINEAR<br>
	 *            GL_LINEAR_MIPMAP_LINEAR<br>
	 * <br>
	 * @param mag
	 * <br>
	 *            GL_NEAREST<br>
	 *            GL_LINEAR<br>
	 */
	public static void filter(int min, int mag)
	{
		GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, min);
		GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, mag);
	}

	/**
	 * values can be: GL_CLAMP_TO_EDGE, GL_MIRRORED_REPEAT, or GL_REPEAT.
	 * 
	 * @param s
	 * @param t
	 */
	public static void wrap(int s, int t)
	{
		GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, s);
		GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, t);
	}

	public void bind()
	{
		GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, id);
	}

	public void dispose()
	{
		if (id > 0)
		{
			int[] ids = new int[1];

			ids[0] = id;

			GLES20.glDeleteTextures(1, ids, 0);

			id = 0;
		}
	}

	public int id()
	{
		return id;
	}

	private int create()
	{
		int[] ids = new int[1];

		GLES20.glGenTextures(1, ids, 0);

		return ids[0];
	}

}
