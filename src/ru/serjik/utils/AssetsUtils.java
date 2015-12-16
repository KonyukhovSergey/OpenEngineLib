package ru.serjik.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.R.integer;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import ru.serjik.engine.Tile;

public class AssetsUtils
{
	public static byte[] readBytes(String name, AssetManager am)
	{
		InputStream stream = null;

		try
		{
			stream = am.open(name);
			return StreamUtils.toByteArray(stream);
		}
		catch (IOException e)
		{
			throw new RuntimeException(e);
		}
		finally
		{
			StreamUtils.close(stream);
		}
	}

	public static String readText(String name, AssetManager am)
	{
		InputStream stream = null;

		try
		{
			stream = am.open(name);
			return StreamUtils.readText(stream);
		}
		catch (IOException e)
		{
			throw new RuntimeException(e);
		}
		finally
		{
			StreamUtils.close(stream);
		}
	}

	public static List<String> readLines(String name, AssetManager am)
	{
		InputStream stream = null;

		try
		{
			stream = am.open(name);
			return StreamUtils.readLines(stream);
		}
		catch (IOException e)
		{
			throw new RuntimeException(e);
		}
		finally
		{
			StreamUtils.close(stream);
		}
	}

	public static Bitmap loadBitmap(String name, AssetManager am)
	{
		InputStream stream = null;

		try
		{
			stream = am.open(name);
			return BitmapFactory.decodeStream(stream);
		}
		catch (IOException e)
		{
			throw new RuntimeException(e);
		}
		finally
		{
			StreamUtils.close(stream);
		}
	}

	public static List<Bitmap> loadBitmaps(String mask, AssetManager am)
	{
		List<Bitmap> bitmaps = new ArrayList<Bitmap>();

		final String pattern = mask.replace(".", "\\.").replace("*", ".*");

		try
		{
			String[] fileNames = am.list("");
			Arrays.sort(fileNames);

			for (String fileName : fileNames)
			{
				if (fileName.matches(pattern))
				{
					bitmaps.add(loadBitmap(fileName, am));
				}
			}
		}
		catch (IOException e)
		{
			throw new RuntimeException(e);
		}

		return bitmaps;
	}

	public static Map<String, Tile> loadTiles(String name, AssetManager am)
	{
		Map<String, Tile> tiles = new HashMap<String, Tile>();

		for (String line : readLines(name, am))
		{
			String[] values = line.split(";");
			Tile tile = new Tile(Float.parseFloat(values[1]), Float.parseFloat(values[2]), Float.parseFloat(values[3]), Float.parseFloat(values[4]),
					Float.parseFloat(values[5]), Float.parseFloat(values[6]), Float.parseFloat(values[7]), Float.parseFloat(values[8]));
			tiles.put(values[0], tile);
		}

		return tiles;
	}
}
