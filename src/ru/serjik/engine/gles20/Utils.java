package ru.serjik.engine.gles20;

import android.opengl.GLES20;
import android.util.Log;

public class Utils
{
	public static void checkGlError(String operation)
	{
		int error;

		while ((error = GLES20.glGetError()) != GLES20.GL_NO_ERROR)
		{
			Log.e("Utils", operation + ": glError " + error);
			// throw new RuntimeException(operation + ": glError " + error);
		}
	}
}
