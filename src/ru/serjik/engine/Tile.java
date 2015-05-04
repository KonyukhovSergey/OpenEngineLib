package ru.serjik.engine;

public class Tile
{
	public float u1, v1, u2, v2;
	public float x1, y1, x2, y2;

	public Tile(int left, int top, int width, int heigth, int textureSize)
	{
		this(left, top, width, heigth, width * 0.5f, heigth * 0.5f, textureSize);
	}

	public Tile(int left, int top, int width, int heigth, float ox, float oy, int textureSize)
	{
		u1 = ((float) left) / (float) (textureSize - 1);
		v1 = ((float) top) / (float) (textureSize - 1);
		u2 = ((float) (left + width)) / (float) (textureSize - 1);
		v2 = ((float) (top + heigth)) / (float) (textureSize - 1);

		x1 = -ox;
		y1 = -oy;

		x2 = width - ox;
		y2 = heigth - oy;
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
