package ru.serjik.utils;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class StreamUtils
{
	private final static Charset utf8 = Charset.forName("UTF-8");
	
	private final static byte[] buffer = new byte[4096];

	public static byte[] toByteArray(InputStream input) throws IOException
	{
		return copy(input, new ByteArrayOutputStream()).toByteArray();
	}

	public static <T extends OutputStream> T copy(InputStream input, T output) throws IOException
	{
		int count;

		while ((count = input.read(buffer)) != -1)
		{
			output.write(buffer, 0, count);
		}

		return output;
	}

	public static String readText(InputStream stream) throws IOException
	{
		return new String(toByteArray(stream), utf8);
	}

	public static List<String> readLines(InputStream stream) throws IOException
	{
		BufferedReader reader = new BufferedReader(new InputStreamReader(stream));

		List<String> list = new ArrayList<String>();

		String line = reader.readLine();

		while (line != null)
		{
			list.add(line);
			line = reader.readLine();
		}

		return list;
	}

	public static void close(Closeable closeable)
	{
		try
		{
			if (closeable != null)
			{
				closeable.close();
			}
		}
		catch (IOException ioe)
		{
		}
	}
}
