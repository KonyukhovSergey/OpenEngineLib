package ru.serjik.sprite.animation.model;

import java.util.Map;
import java.util.Map.Entry;

import com.google.gson.reflect.TypeToken;

import ru.serjik.engine.AtlasGenerator;
import ru.serjik.utils.AssetsUtils;
import ru.serjik.utils.JsonUtils;
import android.content.res.AssetManager;

public class ObjectAnimation
{
	public static Map<String, MotionAnimation> loadAssets(String name, AssetManager am, AtlasGenerator ag)
	{
		Map<String, MotionAnimation> animations;

		animations = JsonUtils.parse(AssetsUtils.getString(name, am), new TypeToken<Map<String, MotionAnimation>>()
		{
		}.getType());

		for (Entry<String, MotionAnimation> entry : animations.entrySet())
		{
			entry.getValue().loadAssets(am, ag);
			//entry.getValue().transition = entry.getValue().transitions.get(entry.getKey());
		}

		return animations;
	}
}
