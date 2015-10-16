package ru.serjik.engine.gles20;

import android.opengl.GLES20;

public class ShaderTools
{
	public static int handle(int type, String code)
	{
		int handle = GLES20.glCreateShader(type);

		if (handle != 0)
		{
			GLES20.glShaderSource(handle, code);
			GLES20.glCompileShader(handle);

			final int[] compileStatus = new int[1];
			GLES20.glGetShaderiv(handle, GLES20.GL_COMPILE_STATUS, compileStatus, 0);

			if (compileStatus[0] == 0)
			{
				GLES20.glDeleteShader(handle);
				handle = 0;
			}
		}

		if (handle == 0)
		{
			throw new RuntimeException(String.format("Error creating vertex shader. %s type %s code", type, code));
		}

		return handle;
	}
	
	//public 
}
