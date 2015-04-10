package ru.serjik.sprite.animation.model;

import java.util.HashMap;
import java.util.Map;

import ru.serjik.engine.AtlasGenerator;
import android.content.res.AssetManager;
import android.nfc.FormatException;

public class ObjectAnimation
{
	public Map<String, MotionAnimation> animations = new HashMap<String, MotionAnimation>();
	
	public void loadAssets(AssetManager am, AtlasGenerator ag) throws FormatException
	{
		for (MotionAnimation motionAnimation : animations.values())
		{
			motionAnimation.loadAssets(am, ag);
		}
	}
}
