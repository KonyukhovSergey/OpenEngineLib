package ru.serjik.engine;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Bitmap.Config;

public class AtlasGenerator
{
	private Bitmap texture;
	private Canvas canvas;
	private int xpos = 0;
	private int ypos = 0;
	private int maxh = 0;
	private int size;

	public AtlasGenerator(int size)
	{
		this.size = size;
		texture = Bitmap.createBitmap(size, size, Config.ARGB_8888);
		canvas = new Canvas(texture);
	}

	public Tile tile(Bitmap bitmap)
	{
		return tile(bitmap, bitmap.getWidth() * 0.5f, bitmap.getHeight() * 0.5f);
	}

	public Tile tile(Bitmap bitmap, float ox, float oy)
	{
		return tile(bitmap, ox, oy, false);
	}

	public Tile tile(Bitmap bitmap, boolean recycleSource)
	{
		return tile(bitmap, bitmap.getWidth() * 0.5f, bitmap.getHeight() * 0.5f, recycleSource);
	}

	public Tile tile(Bitmap bitmap, float ox, float oy, boolean recycleSource)
	{
		if (bitmap.getWidth() + xpos > size)
		{
			xpos = 0;
			ypos += maxh;
			maxh = 0;
		}

		canvas.drawBitmap(bitmap, xpos, ypos, null);

		Tile tile = new Tile(xpos, ypos, bitmap.getWidth(), bitmap.getHeight(), ox, oy, size);

		if (bitmap.getHeight() > maxh)
		{
			maxh = bitmap.getHeight();
		}

		xpos += bitmap.getWidth();

		if (recycleSource)
		{
			bitmap.recycle();
		}

		return tile;
	}

	public Tile[] tileSet(Bitmap bitmap)
	{
		return tileSet(bitmap, false);
	}

	public Tile[] tileSet(Bitmap bitmap, boolean recycleSource)
	{
		int size = bitmap.getHeight();
		int count = bitmap.getWidth() / size;

		Tile[] frames = new Tile[count];

		for (int i = 0; i < count; i++)
		{
			frames[i] = tile(Bitmap.createBitmap(bitmap, i * size, 0, size, size), true);
		}

		if (recycleSource)
		{
			bitmap.recycle();
		}

		return frames;
	}

	public Bitmap atlas()
	{
		return texture;
	}
}
