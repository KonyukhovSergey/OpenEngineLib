package ru.serjik.engine;

public class Tile
{
	public float u1, v1, u2, v2;
	public float x1, y1, x2, y2;

	public Tile(int left, int top, int width, int height, int textureSize)
	{
		this(left, top, width, height, width * 0.5f, height * 0.5f, textureSize, textureSize);
	}

	public Tile(int left, int top, int width, int height, int textureWidth, int textureHeight)
	{
		this(left, top, width, height, width * 0.5f, height * 0.5f, textureWidth, textureHeight);
	}

	public Tile(int left, int top, int width, int height, float ox, float oy, int textureSize)
	{
		this(left, top, width, height, ox, oy, textureSize, textureSize);
	}

	public Tile(int left, int top, int width, int height, float ox, float oy, int textureWidth, int textureHeight)
	{
		u1 = ((float) left) / (float) (textureWidth - 1);
		v1 = ((float) top) / (float) (textureHeight - 1);
		u2 = ((float) (left + width)) / (float) (textureWidth - 1);
		v2 = ((float) (top + height)) / (float) (textureHeight - 1);

		x1 = -ox;
		y1 = -oy;

		x2 = width - ox;
		y2 = height - oy;
	}

	public Tile(float u1, float v1, float u2, float v2, float ox, float oy, float width, float height)
	{
		this.u1 = u1;
		this.v1 = v1;
		this.u2 = u2;
		this.v2 = v2;

		x1 = -ox;
		y1 = -oy;

		x2 = width - ox;
		y2 = height - oy;
	}

	public final float width()
	{
		return x2 - x1;
	}

	public final float height()
	{
		return y2 - y1;
	}

	public final float ox()
	{
		return -x1;
	}

	public final float oy()
	{
		return -y1;
	}

}
