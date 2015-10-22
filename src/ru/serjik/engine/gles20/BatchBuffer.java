package ru.serjik.engine.gles20;

import java.nio.FloatBuffer;

import ru.serjik.engine.utils.BufferAllocator;

public class BatchBuffer
{
	private float[] data;
	private int pos = 0;
	private FloatBuffer fb;

	public BatchBuffer()
	{
		this(4096);
	}

	public BatchBuffer(int size)
	{
		data = new float[size];
		fb = BufferAllocator.createFloatBuffer(size);
	}

	public final void put(float value)
	{
		data[pos] = value;
		pos++;
	}

	public final void put(float x, float y)
	{
		data[pos] = x;
		pos++;
		data[pos] = y;
		pos++;
	}

	public final void put(float x, float y, float s, float t)
	{
		data[pos] = x;
		pos++;
		data[pos] = y;
		pos++;
		data[pos] = s;
		pos++;
		data[pos] = t;
		pos++;
	}

	public FloatBuffer floatBuffer()
	{
		fb.position(0);
		fb.put(data, 0, pos);
		return fb;
	}

	public final boolean available(int count)
	{
		return data.length - pos >= count;
	}
}
