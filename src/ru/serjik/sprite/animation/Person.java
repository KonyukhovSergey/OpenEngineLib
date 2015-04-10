package ru.serjik.sprite.animation;

import ru.serjik.engine.BatchDrawer;
import ru.serjik.sprite.animation.model.ObjectAnimation;

public class Person
{
	private ObjectAnimation animation;
	private String state;
	private int frame;
	private float x, y;

	public void Draw(BatchDrawer bd)
	{
		animation.animations.get(state).frames[frame].draw(bd, x, y);
	}

	public void Tick(float dt)
	{

	}

}
