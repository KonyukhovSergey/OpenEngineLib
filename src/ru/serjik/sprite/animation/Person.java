package ru.serjik.sprite.animation;

import java.util.Map;

import ru.serjik.engine.BatchDrawer;
import ru.serjik.engine.Sprite;
import ru.serjik.sprite.animation.model.FrameTransition;
import ru.serjik.sprite.animation.model.MotionAnimation;

public class Person
{
	private Map<String, MotionAnimation> animations;
	private MotionAnimation motionAnimation;
	private FrameTransition[] frameTransition;

	private int frameIndex;
	public float x, y;
	private int frameTime;

	private Sprite sprite;

	private String nextMotionName;

	private void setCurrentMotion(String motimonName, int startFrameIndex)
	{
		motionAnimation = animations.get(motimonName);
		frameTransition = motionAnimation.transitions.get(motimonName);
		this.frameIndex = startFrameIndex;
	}

	public Person(Map<String, MotionAnimation> animations, String motionName, float x, float y)
	{
		this.animations = animations;
		setCurrentMotion(motionName, 0);
		this.x = x;
		this.y = y;

		sprite = new Sprite(motionAnimation.frames[frameIndex]).position(x, y);
	}

	public void draw(BatchDrawer bd)
	{
		sprite.tile(motionAnimation.frames[frameIndex]).draw(bd);
	}

	public void tick(int deltaTime)
	{
		frameTime += deltaTime;

		if (frameTime > frameTransition[frameIndex].dt)
		{
			x += frameTransition[frameIndex].dx;
			y += frameTransition[frameIndex].dy;

			sprite.position(x, y);

			frameTime -= frameTransition[frameIndex].dt;

			frameIndex = frameTransition[frameIndex].to;

			if (frameIndex >= 1000)
			{
				setCurrentMotion(nextMotionName, frameIndex - 1000);
			}
		}
	}

	public boolean setNextMotionName(String nextMotionName)
	{
		if (motionAnimation.transitions.containsKey(nextMotionName))
		{
			this.nextMotionName = nextMotionName;
			frameTransition = motionAnimation.transitions.get(nextMotionName);
			return true;
		}
		return false;
	}
}
