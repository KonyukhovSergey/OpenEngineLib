package ru.serjik.utils;

import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonUtils
{
	private final static Gson gson = new GsonBuilder().setPrettyPrinting().create();

	public final static String stringify(Object object)
	{
		return gson.toJson(object);
	}

	public final static <T> T parse(String json, Class<T> classOfT)
	{
		return gson.fromJson(json, classOfT);
	}

	public final static <T> T parse(String json, Type type)
	{
		return gson.fromJson(json, type);
	}
}
