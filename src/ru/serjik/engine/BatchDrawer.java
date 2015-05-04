package ru.serjik.engine;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

public class BatchDrawer
{
	public static final int TEXTURED = 1;
	public static final int COLORED = 2;

	private static final byte[] modeVertexSizes = new byte[] { 2, 2 + 2, 3, 2 + 2 + 1 };

	private final float[] data;
	private final FloatBuffer bb;

	private int size = 0;
	private int mode = 0;

	private GL10 gl;

	public BatchDrawer(int bufferSize, GL10 gl)
	{
		data = new float[bufferSize];
		bb = ByteBuffer.allocateDirect(bufferSize * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
		this.gl = gl;
	}

	public void flush()
	{
		if (size > 0)
		{
			bb.position(0);
			bb.put(data, 0, size);

			int vertexSize = modeVertexSizes[mode];

			bb.position(0);
			gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
			gl.glVertexPointer(2, GL10.GL_FLOAT, vertexSize * 4, bb);

			int offset = 2;

			if ((mode & TEXTURED) > 0)
			{
				bb.position(offset);
				gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
				gl.glTexCoordPointer(2, GL10.GL_FLOAT, vertexSize * 4, bb);
				offset += 2;
			}
			else
			{
				gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
			}

			if ((mode & COLORED) > 0)
			{
				bb.position(offset);
				gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
				gl.glColorPointer(4, GL10.GL_UNSIGNED_BYTE, vertexSize * 4, bb);
			}
			else
			{
				gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
			}

			gl.glDrawArrays(GL10.GL_TRIANGLES, 0, size / vertexSize);

			size = 0;
			bb.position(0);
		}
	}

	public void draw(float[] data, int mode)
	{
		if (this.mode != mode || (size + data.length) > this.data.length)
		{
			flush();
		}

		this.mode = mode;

		System.arraycopy(data, 0, this.data, size, data.length);

		size += data.length;
	}

	public void draw(
			final float x1, final float y1, final float u1, final float v1, final float c1,
			final float x2, final float y2, final float u2, final float v2, final float c2,
			final float x3, final float y3, final float u3, final float v3, final float c3)
	{
		if ((mode != (TEXTURED | COLORED)) || ((size + 15) > data.length))
		{
			flush();
		}

		mode = TEXTURED | COLORED;

		data[size++] = x1;
		data[size++] = y1;
		data[size++] = u1;
		data[size++] = v1;
		data[size++] = c1;

		data[size++] = x2;
		data[size++] = y2;
		data[size++] = u2;
		data[size++] = v2;
		data[size++] = c2;

		data[size++] = x3;
		data[size++] = y3;
		data[size++] = u3;
		data[size++] = v3;
		data[size++] = c3;
	}

	public void draw(
			final float x1, final float y1, final float u1, final float v1,
			final float x2, final float y2, final float u2, final float v2,
			final float x3, final float y3, final float u3, final float v3)
	{
		if ((mode != TEXTURED) || ((size + 12) > data.length))
		{
			flush();
		}

		mode = TEXTURED;

		data[size++] = x1;
		data[size++] = y1;
		data[size++] = u1;
		data[size++] = v1;

		data[size++] = x2;
		data[size++] = y2;
		data[size++] = u2;
		data[size++] = v2;

		data[size++] = x3;
		data[size++] = y3;
		data[size++] = u3;
		data[size++] = v3;
	}

	public void draw(
			final float x1, final float y1, final float c1,
			final float x2, final float y2, final float c2,
			final float x3, final float y3, final float c3)
	{
		if ((mode != COLORED) || ((size + 9) > data.length))
		{
			flush();
		}

		mode = COLORED;

		data[size++] = x1;
		data[size++] = y1;
		data[size++] = c1;

		data[size++] = x2;
		data[size++] = y2;
		data[size++] = c2;

		data[size++] = x3;
		data[size++] = y3;
		data[size++] = c3;
	}

}
