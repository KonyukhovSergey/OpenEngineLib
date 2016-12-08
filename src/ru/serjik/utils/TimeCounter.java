package ru.serjik.utils;

public class TimeCounter
{
	private static final long MAX_DELAY = 100;
	private long lastTimeTick = System.currentTimeMillis();

	public long deltaTimeMillis()
	{
		long currentTimeTick = System.currentTimeMillis();

		long deltaTimeTick = currentTimeTick - lastTimeTick;

		if (deltaTimeTick > MAX_DELAY)
		{
			deltaTimeTick = MAX_DELAY;
		}

		lastTimeTick = currentTimeTick;

		return deltaTimeTick;
	}

	public float deltaTimeSeconds()
	{
		return deltaTimeMillis() * 0.001f;
	}
}
