package ru.serjik.engine.utils;

public class TimeCounter
{
	private long lastTimeTick = 0;

	public int deltaTimeMillis()
	{
		long currentTimeTick = System.currentTimeMillis();

		int deltaTimeTick = (int) (currentTimeTick - lastTimeTick);

		if (deltaTimeTick > 50)
		{
			deltaTimeTick = 50;
		}

		lastTimeTick = currentTimeTick;

		return deltaTimeTick;
	}

	public float deltaTimeSeconds()
	{
		return deltaTimeMillis() * 0.001f;
	}
}
