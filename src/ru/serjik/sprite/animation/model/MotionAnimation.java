package ru.serjik.sprite.animation.model;

import java.util.HashMap;
import java.util.Map;

public class MotionAnimation
{
	public String asset;
	public Map<String, FrameTransition[]> transitions = new HashMap<String, FrameTransition[]>();
}
