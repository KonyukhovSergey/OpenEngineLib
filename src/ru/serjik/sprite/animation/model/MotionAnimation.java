package ru.serjik.sprite.animation.model;

import java.util.HashMap;
import java.util.Map;

import ru.serjik.engine.AtlasGenerator;
import ru.serjik.engine.Tile;
import ru.serjik.utils.AssetsUtils;
import android.content.res.AssetManager;

public class MotionAnimation
{
	public String asset;
	public Map<String, FrameTransition[]> transitions = new HashMap<String, FrameTransition[]>();

	public Tile[] frames;

	public void loadAssets(AssetManager am, AtlasGenerator ag)
	{
		frames = ag.tileSet(AssetsUtils.loadBitmap(asset, am), true);

		for (FrameTransition[] transition : transitions.values())
		{
			if (frames.length != transition.length)
			{
				throw new IllegalArgumentException("frame count for: " + asset);
			}
		}
	}
}
