package ru.serjik.sprite.animation.model;

import java.util.HashMap;
import java.util.Map;

import ru.serjik.engine.AtlasGenerator;
import ru.serjik.engine.TileBase;
import ru.serjik.utils.BitmapUtils;
import android.content.res.AssetManager;
import android.nfc.FormatException;

public class MotionAnimation
{
	public String asset;
	public Map<String, FrameTransition[]> transitions = new HashMap<String, FrameTransition[]>();

	public TileBase[] frames;

	public void loadAssets(AssetManager am, AtlasGenerator ag)
	{
		frames = ag.tileSet(BitmapUtils.loadBitmap(am, asset), true);

		for (FrameTransition[] transition : transitions.values())
		{
			if (frames.length != transition.length)
			{
				throw new IllegalArgumentException("frame count for: " + asset);
			}
		}
	}
}
