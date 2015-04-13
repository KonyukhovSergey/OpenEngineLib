package ru.serjik.utils;

import java.io.IOException;
import java.io.InputStream;

import android.content.res.AssetManager;

public class AssetsUtils
{
	public static String getString(String name, AssetManager am)
	{
		try
		{
			InputStream stream = am.open(name);
			String result = StreamUtils.getStringFrom(stream);
			stream.close();
			return result;
		}
		catch (IOException e)
		{
			throw new IllegalArgumentException("asset name: " + name);
		}
	}
}
