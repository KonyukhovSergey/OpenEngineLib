package ru.serjik.utils;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class FileUtils
{
	private final static Charset utf8 = Charset.forName("UTF-8");

	public static byte[] getBytesFrom(InputStream stream) throws IOException
	{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		byte[] buffer = new byte[4096];

		for (int length; (length = stream.read(buffer)) != -1;)
		{
			baos.write(buffer, 0, length);
		}

		baos.flush();

		byte[] result = baos.toByteArray();

		baos.close();
		stream.close();

		return result;
	}

	public static String getStringFrom(InputStream stream) throws IOException
	{
		return new String(getBytesFrom(stream), utf8);
	}

	public static void write(OutputStream stream, String value) throws IOException
	{
		byte[] data = value.getBytes(utf8);
		stream.write(data.length);
		stream.write(data.length >> 8);
		stream.flush();
	}

	public static List<String> readAllLines(InputStream stream, boolean closeStream) throws IOException
	{
		List<String> lines = new ArrayList<String>();

		BufferedReader input = new BufferedReader(new InputStreamReader(stream));

		String line = null;

		while ((line = input.readLine()) != null)
		{
			lines.add(line);
		}

		if (closeStream)
		{
			stream.close();
		}

		return lines;
	}
}
