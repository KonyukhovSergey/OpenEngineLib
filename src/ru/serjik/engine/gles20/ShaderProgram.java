package ru.serjik.engine.gles20;

import android.opengl.GLES20;
import android.util.Log;

public class ShaderProgram
{
	private static final String TAG = "ShaderProgram";

	private int programHandle;

	private int vertexShaderHandle;
	private int fragmentShaderHandle;

	public ShaderProgram(String vertexSourceCode, String fragmentSourceCode)
	{
		programHandle = createProgram(vertexSourceCode, fragmentSourceCode);
	}

	/**
	 * @param combinedSourceCode
	 *            one string with two shaders:
	 * 
	 *            <pre>
	 * vertex shader code ...
	 * ==== // splitter for shaders
	 * fragment shader code ...
	 * </pre>
	 */
	public ShaderProgram(String combinedSourceCode)
	{
		String[] shaders = combinedSourceCode.split("====");
		programHandle = createProgram(shaders[0], shaders[1]);
	}

	public int programHandle()
	{
		return programHandle;
	}

	private int createShader(int shaderType, String shaderSourceCode)
	{
		int shaderHandle = GLES20.glCreateShader(shaderType);

		if (shaderHandle != 0)
		{
			GLES20.glShaderSource(shaderHandle, shaderSourceCode);
			GLES20.glCompileShader(shaderHandle);

			int[] isCompiled = new int[1];
			GLES20.glGetShaderiv(shaderHandle, GLES20.GL_COMPILE_STATUS, isCompiled, 0);

			if (isCompiled[0] == 0)
			{
				Log.e(TAG, "Could not compile shader " + shaderType + ":");
				Log.e(TAG, GLES20.glGetShaderInfoLog(shaderHandle));
				GLES20.glDeleteShader(shaderHandle);
				shaderHandle = 0;
			}
		}
		else
		{
			Log.e(TAG, "Could not compile shader " + shaderType + ":");
		}

		return shaderHandle;
	}

	private int createProgram(String vertexSourceCode, String fragmentSourceCode)
	{
		vertexShaderHandle = createShader(GLES20.GL_VERTEX_SHADER, vertexSourceCode);

		if (vertexShaderHandle != 0)
		{
			fragmentShaderHandle = createShader(GLES20.GL_FRAGMENT_SHADER, fragmentSourceCode);
			if (fragmentShaderHandle != 0)
			{
				int programHandle = GLES20.glCreateProgram();
				if (programHandle != 0)
				{
					GLES20.glAttachShader(programHandle, vertexShaderHandle);
					Utils.checkGlError("glAttachShader");

					GLES20.glAttachShader(programHandle, fragmentShaderHandle);
					Utils.checkGlError("glAttachShader");

					GLES20.glLinkProgram(programHandle);
					int[] linkStatus = new int[1];
					GLES20.glGetProgramiv(programHandle, GLES20.GL_LINK_STATUS, linkStatus, 0);

					if (linkStatus[0] != GLES20.GL_TRUE)
					{
						Log.e(TAG, "Could not link program: ");
						Log.e(TAG, GLES20.glGetProgramInfoLog(programHandle));
						GLES20.glDeleteProgram(programHandle);
						GLES20.glDeleteShader(fragmentShaderHandle);
						fragmentShaderHandle = 0;
						GLES20.glDeleteShader(vertexShaderHandle);
						vertexShaderHandle = 0;
						programHandle = 0;
					}

					return programHandle;
				}
			}
			else
			{
				GLES20.glDeleteShader(vertexShaderHandle);
			}
		}

		return 0;
	}

	public void use()
	{
		GLES20.glUseProgram(programHandle);
	}
	
	public static void releaseCompiler()
	{
		GLES20.glReleaseShaderCompiler();
	}

}
