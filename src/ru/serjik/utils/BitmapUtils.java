package ru.serjik.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

public class BitmapUtils
{
	public static Bitmap generate(int size, int tileSize, Bitmap[] tiles, boolean recycleTiles)
	{
		Bitmap texture = Bitmap.createBitmap(size, size, Config.ARGB_8888);
		Canvas canvas = new Canvas(texture);

		int x = 0;
		int y = 0;

		for (Bitmap tile : tiles)
		{
			Rect rect = new Rect(x, y, x + tileSize, y + tileSize);
			canvas.drawBitmap(tile, null, rect, null);

			if (recycleTiles)
			{
				tile.recycle();
			}

			x += tileSize;

			if (x >= size)
			{
				y += tileSize;
				x = 0;
			}
		}

		return texture;
	}

	public static Bitmap loadBitmap(AssetManager am, String fileName)
	{
		try
		{
			InputStream is = am.open(fileName);
			Bitmap bitmap = BitmapFactory.decodeStream(is);
			is.close();
			return bitmap;
		}
		catch (IOException e)
		{
			throw new IllegalArgumentException("file name: " + fileName);
		}
	}

	public static Bitmap[] loadBitmaps(AssetManager am)
	{
		List<Bitmap> bitmaps = new ArrayList<Bitmap>();

		try
		{
			for (String fileName : am.list(""))
			{
				if (fileName.endsWith(".png"))
				{
					bitmaps.add(loadBitmap(am, fileName));
				}
			}
		}
		catch (IOException e)
		{

		}

		Bitmap[] array = new Bitmap[bitmaps.size()];
		bitmaps.toArray(array);
		return array;
	}
}
