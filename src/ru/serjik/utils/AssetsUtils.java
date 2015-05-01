package ru.serjik.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

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
			throw new RuntimeException();
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
			throw new RuntimeException();
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
			throw new RuntimeException();
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
			throw new RuntimeException();
		}
		finally
		{
			StreamUtils.close(stream);
		}
	}
}
